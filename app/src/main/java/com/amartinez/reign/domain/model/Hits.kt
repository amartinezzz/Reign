package com.amartinez.reign.domain.model

import java.util.*

class Hits (
    var createdAt: Date?,
    var title: String?,
    var url: String?,
    var author: String?,
    var points: String?,
    var storyText: String?,
    var commentText: String?,
    var numComments: Long?,
    var storyId: Long?,
    var storyTitle: String?,
    var storyUrl: String?,
    var parentId: Long?,
    var createdAtI: Long?,
    var tags: ArrayList<String>?,
    var objectID: String?,
    var highlightResult: HighlightResult?
)