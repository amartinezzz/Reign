package com.amartinez.reign.data.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
class HitsEntity (
    var created_at : Date?,
    var title: String?,
    var url: String?,
    var author: String?,
    var points: String?,
    var story_text: String?,
    var comment_text: String?,
    var num_comments: Long?,
    var story_id: Long?,
    var story_title: String?,
    var story_url: String?,
    var parent_id: Long?,
    var created_at_i: Long?,
    var _tags: Array<String>?,
    var objectID: String?,
    var _highlightResult: HighlightResultEntity?
) : Parcelable