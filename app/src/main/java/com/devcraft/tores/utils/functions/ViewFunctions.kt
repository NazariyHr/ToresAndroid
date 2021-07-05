package com.devcraft.tores.utils.functions

import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

fun setClearErrorAfterTextChanged(mapOfViews: Map<EditText, TextInputLayout>) {
    for ((key, value) in mapOfViews) {
        key.doAfterTextChanged {
            value.error = null
        }
    }
}

fun watchAllEditTextFilled(
    editTexts: MutableList<EditText>,
    onAllFilled: (isFilled: Boolean) -> Unit
) {
    for (editText in editTexts) {
        editText.doAfterTextChanged {
            var allIsFilled = true
            editTexts.forEach {
                if (it.text.toString().isEmpty()) {
                    allIsFilled = false
                }
            }
            onAllFilled.invoke(allIsFilled)
        }
    }
}

fun setClearErrorAfterChecked(checkBox: MaterialCheckBox) {
    checkBox.setOnCheckedChangeListener { v, isChecked ->
        if (isChecked) {
            checkBox.error = null
        }
    }
}

fun checkAllIsNotEmptyAndSetError(
    mapOfViews: Map<TextInputEditText, TextInputLayout>,
    errorMsg: String
): Boolean {
    var allIsEmpty = false
    for ((key, value) in mapOfViews) {
        if (key.text.toString().isEmpty()) {
            value.error = errorMsg
            allIsEmpty = true
        }
    }
    return allIsEmpty
}