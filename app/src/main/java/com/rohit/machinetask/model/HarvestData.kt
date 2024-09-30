package com.rohit.machinetask.model

data class HarvestData(
    val id: Int,
    val harvest_type: String,
    val field_number: Int,
    val location: String,
    val acres: Double,
    val farm_name: String,
    val harvest_started: String,
    val harvest_complete: String,
    val google_link: String,
    val pattern: String,
    val estimated_loads_count: String,
    val harvested_loads: String,
    val harvesters_used: String,
)

