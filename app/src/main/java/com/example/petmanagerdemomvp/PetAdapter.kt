package com.example.petmanagerdemomvp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.petmanagerdemomvp.model.Pet
import kotlinx.android.synthetic.main.item_pet.view.*

class PetAdapter(
    private val context: Context,
    private val listener: (Pet) -> Unit
) : RecyclerView.Adapter<PetAdapter.ViewHolder>() {
    var petList: MutableList<Pet>? = null
        set(value) {
            notifyDataSetChanged()
            field = value
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_pet, parent, false))
    }

    override fun getItemCount(): Int = petList?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData((petList ?: return)[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                listener(
                    petList?.get(layoutPosition) ?: return@setOnClickListener
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
