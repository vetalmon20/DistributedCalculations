package main

import (
	"fmt"
	"math/rand"
	"sync"
	"time"
)

var cashbox = 10000
var clients[3] int

func initializeClients() {
	for i := range clients {
		clients[i] = 1500
	}
}

func resetCashbox() {
	cashbox = 10000
}

func withdrawMoney(index, amount int) {
	if index >=0 && index < 3 {
		clients[index] = clients[index] - amount
		fmt.Print("Client number", index, " withdraws ", amount, " money")
		if clients[index] < 0 {
			clients[index] = 0
		}
	}
}

func deposit(index, amount int) {
	if index >= 0 && index < 3 {
		clients[index] = clients[index] + amount
		fmt.Print("Client number", index, " deposits ", amount, " money")
	}
}

func redirect(index, amount int) {
	if index >=0 && index < 3 {
		clients[index] = clients[index] - amount
		fmt.Print("Client number", index, " redirects ", amount, " money")
		if clients[index] < 0 {
			clients[index] = 0
		}
	}
}

func pay(index, amount int) {
	if index >=0 && index < 3 {
		clients[index] = clients[index] - amount
		fmt.Print("Client number", index, " pays ", amount, " money")
		if clients[index] < 0 {
			clients[index] = 0
		}
	}
}

func startClientOperation(index int, waitGroup *sync.WaitGroup) {
	defer waitGroup.Done()

	for{
		randGenerator := rand.New(rand.NewSource(time.Now().UnixNano()))
		option := randGenerator.Intn(3)
		switch option {
		case 0:
			pay(index, randGenerator.Intn(150))
		case 1:
			deposit(index, randGenerator.Intn(500))
		case 2:
			withdrawMoney(index, randGenerator.Intn(150))
		case 3:
			redirect(index, randGenerator.Intn(150))
		}

		time.Sleep(time.Millisecond * 1500)
	}

}

func cashboxWatcher() {
	if cashbox < 1000 || cashbox > 100000 {
		cashbox = 10000
	}
}

func processClient() {

}

func main() {
	var waitGroup sync.WaitGroup
	clientQueue := make(chan int, 1)
	for{
		clientQueue <- rand.New(rand.NewSource(time.Now().UnixNano())).Intn(3)

	}

	waitGroup.Wait()
}
