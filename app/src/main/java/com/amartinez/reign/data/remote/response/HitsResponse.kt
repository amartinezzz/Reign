package com.amartinez.reign.data.remote.response

import com.amartinez.reign.data.entity.HitsEntity

class HitsResponse (
    var hits: ArrayList<HitsEntity>,
    var nbHits: Long,
    var page: Long,
    var nbPages: Long,
    var hitsPerPage: Long,
    var exhaustiveNbHits: Boolean,
    var query: String,
    var params: String,
    var processingTimeMS: Long
)