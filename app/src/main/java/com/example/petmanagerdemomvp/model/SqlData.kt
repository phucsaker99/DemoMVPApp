package com.example.petmanagerdemomvp.model

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.text.DateFormat
import java.util.*

class SqlData(context: Context) : SQLiteOpenHelper(context, "Pet Manager", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        val string: String = "Create table ${TABLE}(" +
                "${ID} integer Primary key, " +
                "${NAME} text," +
                "${PRICE} text," +
                "${WEIGHT} float," +
                "${DESCRIPTION} text, " +
                "${DATE} long)"

        db?.execSQL(string)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        val string = "drop table if exists ${TABLE}"
        db?.execSQL(string)
        onCreate(db)
    }

    fun addPet(pet: Pet) {
        val db: SQLiteDatabase = this.writableDatabase

        val value = ContentValues().apply {
            put(NAME, pet.name)
            put(WEIGHT, pet.weight)
            put(DESCRIPTION, pet.description)
            put(DATE, System.currentTimeMillis())
            put(PRICE, pet.price)
        }

        db.insert(TABLE, null, value)
        db.close()
    }

    fun getPet(id: Int): Pet {
        val db: SQLiteDatabase = this.writableDatabase

        val cursor = db.query(
            TABLE,
            arrayOf(
                ID,
                NAME,
                WEIGHT,
                PRICE,
                DESCRIPTION,
                DATE
            ),
            ID + "=?",
            arrayOf(id.toString()),
            null,
            null,
            null,
            null
        )

        cursor?.moveToFirst()
        val pet = Pet()
        if (cursor != null) {
            pet.apply {
                this.id = cursor.getInt(cursor.getColumnIndex(ID))
                name = cursor.getString(cursor.getColumnIndex(NAME))
                weight = cursor.getFloat(cursor.getColumnIndex(WEIGHT))
                price = cursor.getDouble(cursor.getColumnIndex(PRICE))
                description = cursor.getString(cursor.getColumnIndex(DESCRIPTION))
                date = DateFormat.getInstance().format(
                    Date(cursor.getLong(cursor.getColumnIndex(DATE))).time
                )
            }
        }
        cursor?.close()
        db.close()
        return pet
    }

    fun getAllPet(): MutableList<Pet> {
        val db: SQLiteDatabase = this.writableDatabase
        val petList: MutableList<Pet> = mutableListOf()
        val cursor = db.query(
            TABLE,
            arrayOf(
                ID,
                NAME,
                WEIGHT,
                PRICE,
                DESCRIPTION,
                DATE
            ),
            null,
            null,
            null,
            null,
            DATE + " DESC"
        )

        if (cursor.moveToFirst()) {
            do {
                Pet().apply {
                    this.id = cursor.getInt(cursor.getColumnIndex(ID))
                    name = cursor.getString(cursor.getColumnIndex(NAME))
                    weight = cursor.getFloat(cursor.getColumnIndex(WEIGHT))
                    price = cursor.getDouble(cursor.getColumnIndex(PRICE))
                    description =
                        cursor.getString(cursor.getColumnIndex(DESCRIPTION))
                    date = DateFormat.getInstance().format(
                        Date(cursor.getLong(cursor.getColumnIndex(DATE)))
                            .time
                    )
                    petList.add(this)
                }
            } while (cursor.moveToNext())
        }
        cursor.close()
        return petList
    }

    fun updatePet(pet: Pet): Int {
        val db = this.writableDatabase
        val value = ContentValues().apply {
            put(NAME, pet.name)
            put(WEIGHT, pet.weight)
            put(PRICE, pet.price)
            put(DESCRIPTION, pet.description)
            put(DATE, System.currentTimeMillis())
        }

        return db.update(
            TABLE, value, "${ID}= ?",
            arrayOf(pet.id.toString())
        )
    }

    fun deletePet(id: Int) {
        val db = this.writableDatabase
        db.delete(
            TABLE,
            "${ID}= ?",
            arrayOf(id.toString())
        )
        db.close()
    }

/*    fun getItemsCount(): Int {
        val cursor = this.readableDatabase.rawQuery("Select*from ${UtilData.TABLE}", null)
        val count = cursor.count
        cursor.close()
        return count
    }*/

    companion object {
        const val TABLE = "Pet"
        const val ID = "Id"
        const val NAME = "Name"
        const val WEIGHT = "Weight"
        const val PRICE = "Price"
        const val DESCRIPTION = "Description"
        const val DATE = "Date"
    }
}
