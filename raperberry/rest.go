package main

import (
	"encoding/json"
	"fmt"
	"github.com/drone/routes"
	"io"
	"log"
	"net/http"
)

//Definir interfaces do serviço
//Ex
//http://localhost:8080/led?lid=1
//led = get all leds
//ar = get all ar condicionados etc
//led?lid=1 : pegar o estado desse led especifico
//led?lid=1&action=1 : mandar ação 1 para o led 1(ligar)
//led?lid=1&action=0 : mandar ação 0 para o led 1(desligar)

var statusLed1 bool = false
var statusLed2 bool = false

type Item struct {
	Id        string
	Descricao string
	Status    string
}

func getleds(w http.ResponseWriter, r *http.Request) {
	//return a json
	//{"leds":[
	//{"id":"1", "descricao":"Led 1", "estado":"off"},
	//{"id":"2", "descricao":"Led 1", "estado":"off"}
	//]}
	var itens []Item
	var item1 Item
	var item2 Item

	if statusLed1 == true {
		item1 = Item{"1", "Led 1", "on"}
	} else {
		item1 = Item{"1", "Led 1", "off"}
	}

	if statusLed2 == true {
		item2 = Item{"2", "Led 2", "on"}
	} else {
		item2 = Item{"2", "Led 2", "off"}
	}

	itens = []Item{item1, item2}

	js, err := json.Marshal(itens)
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}

	w.Header().Set("Content-Type", "application/json")
	w.Write(js)
}

func getled(w http.ResponseWriter, r *http.Request) {
	params := r.URL.Query()
	led := params.Get(":led")
	if led == "1" {
		if statusLed1 == true {
			fmt.Fprintf(w, "led 1 is ON")
		} else {
			fmt.Fprintf(w, "led 1 is OFF")
		}
	} else if led == "2" {
		if statusLed2 == true {
			fmt.Fprintf(w, "led 2 is ON")
		} else {
			fmt.Fprintf(w, "led 2 is OFF")
		}
	}
}

func modifyled(w http.ResponseWriter, r *http.Request) {
	//err := rpio.Open()
	log.Printf("Entrou")
	params := r.URL.Query()
	led := params.Get(":led")
	ledstatus := params.Get(":ledstatus")
	if led == "1" {
		//pin := rpio.Pin(38)
		//pin.Output()

		if ledstatus == "1" {
			statusLed1 = true
			fmt.Fprintf(w, "modify led 1 to ON")

			//pin.High()
		} else if ledstatus == "0" {
			statusLed1 = false
			fmt.Fprintf(w, "modify led 1 to OFF")
			//pin.Low()
		}
	}
	if led == "2" {
		//pin := rpio.Pin(40)
		//pin.Output()

		if ledstatus == "1" {
			statusLed2 = true
			fmt.Fprintf(w, "modify led 1 to ON")

			//pin.High()
		} else if ledstatus == "0" {
			statusLed2 = false
			fmt.Fprintf(w, "modify led 1 to OFF")

			//pin.Low()
		}
	}
}

func deleteuser(w http.ResponseWriter, r *http.Request) {
	params := r.URL.Query()
	uid := params.Get(":uid")
	fmt.Fprintf(w, "you are delete user %s", uid)
}

func adduser(w http.ResponseWriter, r *http.Request) {
	params := r.URL.Query()
	uid := params.Get(":uid")
	fmt.Fprint(w, "you are add user %s", uid)
}

func test(w http.ResponseWriter, r *http.Request) {
	log.Printf("Entrou")
	dec := json.NewDecoder(r.Body)
	for {
		var t Item
		if err := dec.Decode(&t); err == io.EOF {
			break
		} else if err != nil {
			log.Fatal(err)
		}
		log.Printf("%s\n", t.Id)
	}
}

func main() {
	mux := routes.New()
	//get led
	mux.Get("/led/", getleds)
	mux.Get("/led/:led/", getled)
	mux.Post("/led/:led/:ledstatus/", modifyled)
	mux.Post("/led/", test)
	//mux.Del("/user/:uid", deleteuser)
	//mux.Put("/user/", adduser)
	http.Handle("/", mux)
	http.ListenAndServe(":8088", nil)
}
