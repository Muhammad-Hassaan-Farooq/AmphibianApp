package com.example.frogapi

import kotlinx.serialization.Serializable

@Serializable
data class Frog (
    val name:String,
    val type:String,
    val description:String,
    val img_src:String
        )