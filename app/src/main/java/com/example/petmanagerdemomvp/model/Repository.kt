package com.example.petmanagerdemomvp.model

import android.content.Context
import android.widget.Toast
import com.example.petmanagerdemomvp.PetRepository

class Repository(private val context: Context) :
    PetRepository.Local {
    private val petDatabase = SqlData(context)

    override fun sendPetList() = petDatabase.getAllPet()

    override fun addPet(pet: Pet) {
        petDatabase.addPet(pet)
        Toast.makeText(context, "pet added successfully", Toast.LENGTH_LONG).show()
    }

    override fun sendPet(position: Int) = petDatabase.getPet(position)

    override fun updatePet(pet: Pet): Int = petDatabase.updatePet(pet)

    override fun deletePet(position: Int) = petDatabase.deletePet(position)
}
