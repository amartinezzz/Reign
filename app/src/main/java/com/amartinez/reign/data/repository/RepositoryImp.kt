package com.amartinez.reign.data.repository

import com.amartinez.reign.data.entity.*
import com.amartinez.reign.data.remote.Api
import com.amartinez.reign.data.remote.response.HitsResponse
import com.amartinez.reign.domain.model.*
import io.reactivex.Observable

class RepositoryImp(private val api: Api) : Repository {
    override fun loadHits(term: String): Observable<HitsPage> {
        return api.search(term).map {
            it.toDomain()
        }
    }

    private fun HitsResponse.toDomain() = HitsPage(
        hits.map { it.toDomain() },
        nbHits, page, nbPages, hitsPerPage, exhaustiveNbHits, query, params, processingTimeMS
    )

    private fun HitsEntity.toDomain() = Hits(
        created_at, title, url, author, points, story_text, comment_text, num_comments, story_id,
        story_title, story_url, parent_id, created_at_i, _tags, objectID,
        _highlightResult?.toDomain()
    )

    private fun HighlightResultEntity.toDomain() = HighlightResult(
        author.toDomain(), comment_text.toDomain(), story_title.toDomain()
    )

    private fun AuthorEntity.toDomain() = Author(
        value, matchLevel, matchedWords
    )

    private fun CommentTextEntity.toDomain() = CommentText(
        value, matchLevel, fullyHighlighted, matchedWords
    )

    private fun StoryTitleEntity.toDomain() = StoryTitle(
        value, matchLevel, matchedWords
    )
}