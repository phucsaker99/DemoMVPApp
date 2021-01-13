package com.example.petmanagerdemomvp

import com.example.petmanagerdemomvp.model.Pet

interface PetContract {
    interface View {
        fun showPetList(petList: MutableList<Pet>)
        fun showDialogAddPet()
        fun showDialogEditPet(sendObject: Pet)
    }

    interface Presenter {
        fun getPetList()
        fun addPet()
        fun setEditPet(position: Int)
        fun setDeletePet(position: Int)
        fun addPet(pet: Pet)
        fun updatePet(pet: Pet)
    }
}
