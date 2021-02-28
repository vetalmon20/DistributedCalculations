package main

import (
	"fmt"
	"math/rand"
	"sync"
	"time"
)

type Component int


const SMOKERS_NUM = 3
var CheckedSmokersNum = 0


func (c Component) String() string {
	return [...]string{"Tobacco", "Paper", "Matches"}[c]
}


func smoker(table *[]bool, component Component, smokerCheckingSemaphore, emptyTableSemaphore, newCycleSemaphore chan bool, waitGroup *sync.WaitGroup) {
	defer waitGroup.Done()
	for {
		<-smokerCheckingSemaphore
		fmt.Println("Smoker with *", component, "* checks the table")
		if !(*table)[component] {
			fmt.Println("Smoker with *", component, "* smoking the cigarette...")
			time.Sleep(time.Second * 1)
			for i := range *table {
				(*table)[i] = false
			}

			for i := 0; i < CheckedSmokersNum; i++ {
				newCycleSemaphore <- true
			}

			CheckedSmokersNum = 0
			emptyTableSemaphore <- true
		} else {
			CheckedSmokersNum++
			smokerCheckingSemaphore<-true
			<-newCycleSemaphore
		}
	}
}

func mediator(table *[]bool, emptyTableSemaphore, smokerCheckingSemaphore chan bool, waitGroup *sync.WaitGroup) {
	defer waitGroup.Done()
	for {
		<-emptyTableSemaphore
		var firstComponent, secondComponent = generateTwoComponents()
		(*table)[firstComponent], (*table)[secondComponent] = true, true
		fmt.Println("Mediator put two components on the table: * ", firstComponent, "* and *", secondComponent, "*")
		smokerCheckingSemaphore <- true
	}
}

func generateTwoComponents() (Component, Component) {
	randGenerator := rand.New(rand.NewSource(time.Now().UnixNano()))

	component1 := randGenerator.Intn(SMOKERS_NUM)
	time.Sleep(time.Millisecond * 100)
	component2 := randGenerator.Intn(SMOKERS_NUM)

	for component1 == component2 {
		component2 = randGenerator.Intn(SMOKERS_NUM)
	}

	return Component(component1), Component(component2)
}

func main() {
	var waitGroup sync.WaitGroup
	var table = make([]bool, SMOKERS_NUM)

	var smokerCheckingSemaphore = make(chan bool, 1)
	var emptyTableSemaphore = make(chan bool, 1)
	var newCycleSemaphore = make(chan bool)

	emptyTableSemaphore <- true
	waitGroup.Add(1)
	go mediator(&table, emptyTableSemaphore, smokerCheckingSemaphore, &waitGroup)

	for i := 0; i < SMOKERS_NUM; i++ {
		waitGroup.Add(1)
		go smoker (&table, Component(i), smokerCheckingSemaphore, emptyTableSemaphore, newCycleSemaphore, &waitGroup)
	}
	waitGroup.Wait()


}