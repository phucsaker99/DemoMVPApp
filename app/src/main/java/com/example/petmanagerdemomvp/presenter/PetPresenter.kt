package com.example.petmanagerdemomvp.presenter

import com.example.petmanagerdemomvp.PetContract
import com.example.petmanagerdemomvp.model.Repository
import com.example.petmanagerdemomvp.model.Pet

class PetPresenter(
    private var petView: PetContract.View,
    private val petRepository: Repository
) : PetContract.Presenter {
    override fun getPetList() {
        petView.showPetList(petRepository.sendPetList())
    }

    override fun addPet() {
        petView.showDialogAddPet()
    }

    override fun setEditPet(position: Int) {
        petView.showDialogEditPet(petRepository.sendPet(position))
    }

    override fun setDeletePet(position: Int) {
        petRepository.deletePet(position)
        petView.showPetList(petRepository.sendPetList())
    }

    override fun addPet(pet: Pet) {
        petRepository.addPet(pet)
        petView.showPetList(petRepository.sendPetList())
    }

    override fun updatePet(pet: Pet) {
        petRepository.updatePet(pet)
        petView.showPetList(petRepository.sendPetList())
    }
}
