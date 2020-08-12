package main

import (
	"bufio"
	"fmt"
	"net"
	"time"
)

func main() {
	c, err := net.Dial("tcp", "178.128.214.46:9998")
	if err != nil {
		fmt.Println(err)
		return
	}

	fmt.Fprintf(c, "Initial connection string\n")

	fmt.Print("Enter peer connection string -> ")
	var peer string
	fmt.Scanf("%s", &peer)

	fmt.Printf("Connecting to peer %s", peer);
	fmt.Println("Hello")

	go connectPeer(peer)

	for {
		message, _ := bufio.NewReader(c).ReadString('\n')
		fmt.Print("->: " + message)
	}
}

func connectPeer(p string) {
	c, err := net.Dial("tcp", p)

	for err != nil {
		fmt.Println("\nError occurred, waiting 2 seconds to retry.")
		fmt.Println(err)
		fmt.Println()
		time.Sleep(2 * time.Second)
		c, err = net.Dial("tcp", p)
	}

	fmt.Fprintf(c, "Sending data from peer DELL workstation\n")
}
