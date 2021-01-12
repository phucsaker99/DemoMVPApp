package com.example.petmanagerdemomvp.presenter

import com.example.petmanagerdemomvp.Contract
import com.example.petmanagerdemomvp.model.Repository
import com.example.petmanagerdemomvp.model.Pet

class PetPresenter(
    private var mView: Contract.View,
    private val mModel: Repository
) : Contract.Presenter {
    override fun getPetList() {
        mView.showPetList(mModel.sendData())
    }

    override fun addPet() {
        mView.showDialogAdd()
    }

    override fun setEditPet(position: Int) {
        mView.showDialogEdit(mModel.sendObject(position))
    }

    override fun setDeletePet(position: Int) {
        mModel.deleteItem(position)
        mView.showPetList(mModel.sendData())
    }

    override fun addItem(pet: Pet) {
        mModel.addData(pet)
        mView.showPetList(mModel.sendData())
    }

    override fun updateItem(pet: Pet) {
        mModel.updateData(pet)
        mView.showPetList(mModel.sendData())
    }
}
