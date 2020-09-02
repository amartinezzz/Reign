package com.amartinez.reign.data.local

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*
import javax.annotation.Nullable

open class HitsLocal (
    var created_at : Date? = Date(),
    var title: String? = "",
    var url: String? = "",
    var author: String? = "",
    var points: String? = "",
    var story_text: String? = "",
    var comment_text: String? = "",
    var num_comments: Long? = 1,
    @PrimaryKey
    var story_id: Long? = 1,
    var story_title: String? = "",
    var story_url: String? = "",
    var parent_id: Long? = 1,
    var created_at_i: Long? = 1,
    var _tags: RealmList<String>? = RealmList(),
    var objectID: String? = ""
) : RealmObject()