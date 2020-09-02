package com.amartinez.reign.data.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class HighlightResultEntity (
    var author: AuthorEntity?,
    var comment_text: CommentTextEntity?,
    var story_title: StoryTitleEntity?
) : Parcelable