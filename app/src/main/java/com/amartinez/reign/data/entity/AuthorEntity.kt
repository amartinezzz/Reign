package com.amartinez.reign.data.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class AuthorEntity (
    var value : String?,
    var matchLevel: String?,
    var matchedWords: ArrayList<String>?
) : Parcelable