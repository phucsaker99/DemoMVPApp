package com.example.petmanagerdemomvp

import com.example.petmanagerdemomvp.model.Pet

interface InterfaceRepository {
    interface Local {
        fun sendData(): MutableList<Pet>
        fun addData(pet: Pet)
        fun sendObject(position: Int): Pet
        fun updateData(pet: Pet): Int
        fun deleteItem(position: Int)
    }
}
