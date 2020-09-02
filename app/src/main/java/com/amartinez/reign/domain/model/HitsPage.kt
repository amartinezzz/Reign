package com.amartinez.reign.domain.model

class HitsPage (
    var hits: ArrayList<Hits>,
    var nbHits: Long,
    var page: Long,
    var nbPages: Long,
    var hitsPerPage: Long,
    var exhaustiveNbHits: Boolean,
    var query: String,
    var params: String,
    var processingTimeMS: Long
)