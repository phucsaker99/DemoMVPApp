package com.example.petmanagerdemomvp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.petmanagerdemomvp.model.Pet
import com.example.petmanagerdemomvp.model.SqlData
import kotlinx.android.synthetic.main.item_pet.view.*

class PetAdapter(
    private val layoutInflater: LayoutInflater,
    private val listener: (Pet) -> Unit
) : RecyclerView.Adapter<PetAdapter.ViewHolder>() {
    var arr: MutableList<Pet>? = null
        set(value) {
            notifyDataSetChanged()
            field = value
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(layoutInflater.inflate(R.layout.item_pet, parent, false))
    }

    override fun getItemCount(): Int = arr?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData((arr ?: return)[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                listener(
                    arr?.get(layoutPosition) ?: return@setOnClickListener
                )
            }
        }

        fun bindData(pet: Pet) {
            itemView.apply {
                textName.text = pet.name
                textWeight.text = pet.weight.toString()
                textPrice.text = pet.price.toString()
                textDescription.text = pet.description
                textDate.text = pet.date
                textPosition.text = (layoutPosition + 1).toString()
            }
        }
    }
}
