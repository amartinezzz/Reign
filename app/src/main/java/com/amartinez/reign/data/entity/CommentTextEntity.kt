package com.amartinez.reign.data.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
class CommentTextEntity (
    var value: String?,
    var matchLevel: String?,
    var fullyHighlighted: Boolean?,
    var matchedWords: ArrayList<String>?
) : Parcelable