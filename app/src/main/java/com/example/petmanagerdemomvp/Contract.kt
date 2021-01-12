package com.example.petmanagerdemomvp

import com.example.petmanagerdemomvp.model.Pet

interface Contract {
    interface View {
        fun showPetList(arr: MutableList<Pet>)
        fun showDialogAdd()
        fun showDialogEdit(sendObject: Pet)
    }

    interface Presenter {
        fun getPetList()
        fun addPet()
        fun setEditPet(position: Int)
        fun setDeletePet(position: Int)
        fun addItem(pet: Pet)
        fun updateItem(pet: Pet)
    }
}
