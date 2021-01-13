package com.example.petmanagerdemomvp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.example.petmanagerdemomvp.PetContract
import com.example.petmanagerdemomvp.PetAdapter
import com.example.petmanagerdemomvp.R
import com.example.petmanagerdemomvp.model.Pet
import com.example.petmanagerdemomvp.model.Repository
import com.example.petmanagerdemomvp.presenter.PetPresenter
import com.example.petmanagerdemomvp.util.Checkout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.edit_item.view.*

class MainActivity : AppCompatActivity(), PetContract.View, View.OnClickListener {
    private var petPresenter: PetPresenter? = null
    private var petAdapter: PetAdapter? = null
    private var alertDialog: AlertDialog? = null
    private var position = -1
    private var pet: Pet? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initControl()
    }

    override fun showPetList(petList: MutableList<Pet>) {
        petAdapter?.petList = petList
        recyclerPet.adapter = petAdapter
    }

    override fun showDialogAddPet() {
        val view = layoutInflater.inflate(R.layout.edit_item, null)
        alertDialog = AlertDialog.Builder(this)
            .setView(view)
            .show()
        view.apply {
            buttonChoose.setOnClickListener(this@MainActivity)
            buttonChoose.text = getString(R.string.text_add)
            textTitle.text = getString(R.string.text_add_content)
        }
    }

    override fun showDialogEditPet(sendObject: Pet) {
        val view = layoutInflater.inflate(R.layout.edit_item, null)
        alertDialog = AlertDialog.Builder(this)
            .setView(view)
            .create()
        alertDialog?.show()
        view.apply {
            editName.setText(sendObject.name)
            editWeight.setText(sendObject.weight.toString())
            editPrice.setText(sendObject.price.toString())
            editDescription.setText(sendObject.description)
            buttonChoose.text = getString(R.string.text_edit)
            textTitle.text = getString(R.string.text_edit_content)
        }
        view.buttonChoose.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonAdd -> showDialogAddPet()
            R.id.buttonEdit -> {
                if (position == -1) return
                petPresenter?.setEditPet(position)
            }
            R.id.buttonDelete -> askUser()
            R.id.buttonChoose -> {
                when (v.buttonChoose.text) {
                    getString(R.string.text_add) -> {
                        checkContent(v.rootView)
                    }
                    else -> {
                        updateContent(v.rootView)
                    }
                }
            }
        }
    }

    fun setPetItemClicked(pet: Pet) {
        position = pet.id
        this.pet = pet
    }

    private fun initView() {
        petAdapter = PetAdapter(this, ::setPetItemClicked)
        petPresenter = PetPresenter(this, Repository(this))
        petPresenter?.getPetList()
    }

    private fun initControl() {
        buttonAdd.setOnClickListener(this)
        buttonEdit.setOnClickListener(this)
        buttonDelete.setOnClickListener(this)
    }

    private fun askUser() {
        AlertDialog.Builder(this)
            .setTitle("Notification!")
            .setMessage("Do you want to delete the pet?")
            .setNegativeButton("No") { dialog, _ -> dialog.dismiss() }
            .setPositiveButton("Yes") { _, _ -> petPresenter?.setDeletePet(position) }
            .show()
    }

    private fun updateContent(v: View) {
        v.apply {
            if (Checkout.isEmpty(editName, editWeight, editPrice, editDescription)) {
                return
            }
            pet?.apply {
                name = editName.text.toString()
                weight = editWeight.text.toString().toFloat()
                price = editPrice.text.toString().toDouble()
                description = editDescription.text.toString()
            }
        }
        petPresenter?.updatePet(pet ?: return)
        alertDialog?.dismiss()
    }

    private fun checkContent(v: View) {
        v.apply {
            if (Checkout.isEmpty(editName, editWeight, editPrice, editDescription)) {
                return
            }
            val pet = Pet(
                editName.text.toString(),
                editWeight.text.toString().toFloat(),
                editPrice.text.toString().toDouble(),
                editDescription.text.toString()
            )
            petPresenter?.addPet(pet)
            alertDialog?.dismiss()
        }
    }
}
