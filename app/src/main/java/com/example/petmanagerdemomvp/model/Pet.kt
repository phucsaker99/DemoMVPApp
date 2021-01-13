package com.example.petmanagerdemomvp.model

data class Pet(
    var name: String = "",
    var weight: Float = 0f,
    var price: Double = 0.0,
    var description: String = ""
) {
    var date: String = System.currentTimeMillis().toString()
    var id: Int = 0
}
