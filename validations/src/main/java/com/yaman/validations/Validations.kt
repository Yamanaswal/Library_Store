package com.yaman.validations

import android.util.Log

const val TAG = "Validations: "

data class Valid(val validStatus: Boolean, val validReason: String) {}

/** Mobile Number Validation **/
fun validateMobileNumber(mobileNumber: String): Valid {
    Log.e(TAG, "validateMobileNumber: $mobileNumber")

    if (mobileNumber.isEmpty()) {
        return Valid(
            validStatus = false,
            validReason = "Please Enter Mobile Number."
        )
    } else if (mobileNumber.length < 9 || mobileNumber.length > 13) {
        return Valid(
            validStatus = false,
            validReason = "Please Enter Mobile Number Between 9 to 12."
        )
    }

    return Valid(validStatus = true, validReason = "")
}


/** Name Validation **/
fun validateName(name: String): Valid {
    Log.e(TAG, "validateName: $name")

    if (name.isEmpty()) {
        return Valid(
            validStatus = false,
            validReason = "Please Enter Name."
        )
    }

    return Valid(validStatus = true, validReason = "")
}


/** Image Validation **/
fun validateImage(image: String): Valid {
    Log.e(TAG, "validateImage: $image")

    if (image.isEmpty()) {
        return Valid(
            validStatus = false,
            validReason = "Please Select Image."
        )
    }

    return Valid(validStatus = true, validReason = "")
}


/** Custom Validation **/
fun validateCustom(value: String = "", validReasonMessage: String = ""): Valid {
    Log.e(TAG, "validateImage: $value : $validReasonMessage")

    if (value.isEmpty()) {
        return Valid(
            validStatus = false,
            validReason = validReasonMessage
        )
    }

    return Valid(validStatus = true, validReason = "")
}