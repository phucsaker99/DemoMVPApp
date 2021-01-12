package com.example.petmanagerdemomvp.model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.text.DateFormat
import java.util.*

class SqlData(context: Context) : SQLiteOpenHelper(context, "Pet Manager", null, 1) {

    override fun onCreate(p0: SQLiteDatabase?) {
        val string: String = "Create table ${TABLE}(" +
                "${ID} integer Primary key, " +
                "${NAME} text," +
                "${PRICE} text," +
                "${WEIGHT} float," +
                "${DESCRIPTION} text, " +
                "${DATE} long)"

        p0?.execSQL(string)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        val string = "drop table if exists ${TABLE}"
        p0?.execSQL(string)
        onCreate(p0)
    }

    fun addItem(item: Pet) {
        val db: SQLiteDatabase = this.writableDatabase

        val value = ContentValues().apply {
            put(NAME, item.name)
            put(WEIGHT, item.weight)
            put(DESCRIPTION, item.description)
            put(DATE, System.currentTimeMillis())
            put(PRICE, item.price)
        }

        db.insert(TABLE, null, value)
        db.close()
    }

    fun getItem(id: Int): Pet {
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
        val item = Pet()
        if (cursor != null) {
            item.apply {
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
        return item
    }

    fun getAllItem(): MutableList<Pet> {
        val db: SQLiteDatabase = this.writableDatabase
        val itemList: MutableList<Pet> = mutableListOf()
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
                    itemList.add(this)
                }
            } while (cursor.moveToNext())
        }
        cursor.close()
        return itemList
    }

    fun updateItem(item: Pet): Int {
        val db = this.writableDatabase
        val value = ContentValues().apply {
            put(NAME, item.name)
            put(WEIGHT, item.weight)
            put(PRICE, item.price)
            put(DESCRIPTION, item.description)
            put(DATE, System.currentTimeMillis())
        }

        return db.update(
            TABLE, value, "${ID}= ?",
            arrayOf(item.id.toString())
        )
    }

    fun deleteItem(id: Int) {
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
        const val TABLE: String = "Pet"
        const val ID: String = "Id"
        const val NAME: String = "Name"
        const val WEIGHT: String = "Weight"
        const val PRICE: String = "Price"
        const val DESCRIPTION: String = "Description"
        const val DATE: String = "Date"
    }
}
