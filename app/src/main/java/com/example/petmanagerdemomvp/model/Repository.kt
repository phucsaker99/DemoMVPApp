package com.example.petmanagerdemomvp.model

import android.content.Context
import android.widget.Toast
import com.example.petmanagerdemomvp.InterfaceRepository

class Repository(private val context: Context) :
    InterfaceRepository.Local {
    private val mHelper = SqlData(context)

    override fun sendData() = mHelper.getAllItem()

    override fun addData(pet: Pet) {
        mHelper.addItem(pet)
        Toast.makeText(context, "pet added successfully", Toast.LENGTH_LONG).show()
    }

    override fun sendObject(position: Int) = mHelper.getItem(position)

    override fun updateData(pet: Pet): Int = mHelper.updateItem(pet)

    override fun deleteItem(position: Int) = mHelper.deleteItem(position)
}
