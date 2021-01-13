package com.example.petmanagerdemomvp.util

import android.widget.EditText

object Checkout {
    fun isEmpty(vararg editText: EditText): Boolean {
        var check = false
        for (edit in editText) {
            if (edit.text.toString().isEmpty()) {
                edit.error = "You need to fill out information here"
                check = true
            }
        }
        return check
    }
}
