package com.amartinez.reign.domain.model

class CommentText (
    var value: String? = "",
    var matchLevel: String? = "",
    var fullyHighlighted: Boolean? = false,
    var matchedWords: ArrayList<String>? = ArrayList()
)