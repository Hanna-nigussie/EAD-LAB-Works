package main

import (
	"fmt"
	"math/rand"
	"sync"
	"time"
)

type Task struct {
	id   int
	data string
}

func worker(id int, taskChan <-chan Task, wg *sync.WaitGroup, failChan chan<- Task, retryLimitMap map[int]int, retryLimit int, mu *sync.Mutex) {
	defer wg.Done()

	for task := range taskChan {
		fmt.Printf("[INFO] Worker %d started task %d: %s\n", id, task.id, task.data)

		if rand.Float32() < 0.3 {
			fmt.Printf("[ERROR] Worker %d failed on task %d\n", id, task.id)

			mu.Lock()
			retryLimitMap[task.id]++
			retries := retryLimitMap[task.id]
			mu.Unlock()

			if retries > retryLimit {
				fmt.Printf("[ERROR] Task %d failed after %d retries and will not be retried further.\n", task.id, retryLimit)
			} else {
				failChan <- task
			}
			continue
		}

		time.Sleep(time.Duration(rand.Intn(3)+1) * time.Second)
		fmt.Printf("[INFO] Worker %d completed task %d\n", id, task.id)
	}
}

func main() {
	rand.Seed(time.Now().UnixNano())

	tasks := []Task{
		{id: 1, data: "Task 1"},
		{id: 2, data: "Task 2"},
		{id: 3, data: "Task 3"},
		{id: 4, data: "Task 4"},
		{id: 5, data: "Task 5"},
	}

	taskChan := make(chan Task, len(tasks))
	failChan := make(chan Task, len(tasks))

	var wg sync.WaitGroup
	numWorkers := 3
	retryLimit := 3
	retryLimitMap := make(map[int]int)
	mu := &sync.Mutex{}

	for i := 1; i <= numWorkers; i++ {
		wg.Add(1)
		go worker(i, taskChan, &wg, failChan, retryLimitMap, retryLimit, mu)
	}

	workerID := 1
	for _, task := range tasks {
		fmt.Printf("[INFO] Assigning task %d to worker %d\n", task.id, workerID)
		taskChan <- task

		workerID++
		if workerID > numWorkers {
			workerID = 1
		}
	}
	close(taskChan)

	go func() {
		for failedTask := range failChan {
			fmt.Printf("[INFO] Reassigning failed task %d\n", failedTask.id)

			workerID++
			if workerID > numWorkers {
				workerID = 1
			}

			wg.Add(1)
			go worker(workerID, taskChan, &wg, failChan, retryLimitMap, retryLimit, mu)
		}
	}()

	wg.Wait()
	close(failChan)

	fmt.Println("[INFO] All tasks completed.")
}
