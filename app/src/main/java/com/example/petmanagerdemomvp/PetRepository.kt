package com.example.petmanagerdemomvp

import com.example.petmanagerdemomvp.model.Pet

interface PetRepository {
    interface Local {
        fun sendPetList(): MutableList<Pet>
        fun addPet(pet: Pet)
        fun sendPet(position: Int): Pet
        fun updatePet(pet: Pet): Int
        fun deletePet(position: Int)
    }
}
