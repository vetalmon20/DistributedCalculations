package main

import (
	"fmt"
	"math/rand"
	"sync"
	"time"
)

const N = 10
const M = 50
const BeesGroupsNum = 3
var isBearFound = false

type ForestArea struct {
	area [M]int
	areaNum int
}

func printMatrix(n, m int, matrix[][] int) {
	for i := 0; i < n; i++ {
		for j := 0; j < m; j++ {
			fmt.Print(matrix[i][j], " ")
		}
		fmt.Print(" - ", i, " region")
		fmt.Println()
	}
}

func generateForestMatrix(n, m int) [][]int {
	forestMatrix := make([][]int, n)
	for i := range forestMatrix {
		forestMatrix[i] = make([]int, m)
	}

	randSource := rand.NewSource(time.Now().UnixNano())
	randGenerator := rand.New(randSource)

	xBearPos := randGenerator.Intn(n)
	yBearPos := randGenerator.Intn(m)

	forestMatrix[xBearPos][yBearPos] = 1

	return forestMatrix
}

func divideForestInAreas(n, m int, forestMatrix[][] int) []ForestArea{
	var forestAreas[] ForestArea
	for i := 0; i < n; i++ {
		var forestArea ForestArea
		forestArea.areaNum = i

		for j := 0; j < m; j++ {
			forestArea.area[j] = forestMatrix[i][j]
		}

		forestAreas = append(forestAreas, forestArea)
	}

	return forestAreas
}

func searchInArea(area[M] int, beesGroupId int, areaId int) {
	for index, i := range area {
		if i == 1 {
			isBearFound = true
			fmt.Println("Winnie The Pooh is found!")
			fmt.Println("Bees group number ", beesGroupId, " found Winnie The Pooh in region number", areaId, "at index number", index)
			return
		}
	}

	fmt.Println("Bees group number ", beesGroupId, " haven't found Winnie The Pooh in region number", areaId)
}

func startBearSearching(waitGroup *sync.WaitGroup, areas <-chan ForestArea, beesGroupId int) {
	defer waitGroup.Done()

	for currentArea := range areas {
		switch isBearFound {
		case true :
			return
		default:
			fmt.Println("Bees group number ", beesGroupId, " starts searching in region number ", currentArea.areaNum)
			searchInArea(currentArea.area, beesGroupId, currentArea.areaNum)
			fmt.Println("Bees group number ", beesGroupId, " is coming back")
		}
	}
}

func main() {

	forestMatrix := generateForestMatrix(N, M)
	var waitGroup sync.WaitGroup
	areas := make(chan ForestArea, N)
	forestAreas := divideForestInAreas(N, M, forestMatrix)

	//filling the channel with forest areas
	for i := 0; i < N; i++ {
		areas <- forestAreas[i]
	}

	fmt.Println("Current situation in the forest:")
	printMatrix(N, M, forestMatrix)
	fmt.Println("Starting searching process")

	//starting goroutines that searches for Winnie The Pooh
	for i := 0; i < BeesGroupsNum; i++ {
		waitGroup.Add(1)
		go startBearSearching(&waitGroup, areas, i)
	}

	close(areas)
	waitGroup.Wait()
}
