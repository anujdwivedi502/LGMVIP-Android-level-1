package com.example.covidtracker

class trackerData {
    var state : String? = null
    var cases : Int? = null
    var recoverd : Int? = null
    var deaths : Int? = null
    var live : Int? = null
    var status : Boolean? = null


    constructor(){}

    constructor(state : String? , cases: Int? , recoverd:Int? , deaths : Int? , live : Int? , status : Boolean?){

        this.state = state
        this.cases = cases
        this.recoverd = recoverd
        this.deaths = deaths
        this.live = live

        this.status = status

    }
}