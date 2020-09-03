package com.amartinez.reign.data.repository.local

import com.amartinez.reign.data.local.HitsLocal
import com.amartinez.reign.domain.model.HighlightResult
import com.amartinez.reign.domain.model.Hits
import io.reactivex.Observable
import io.realm.Realm
import io.realm.RealmList
import io.realm.RealmResults
import io.realm.Sort

class LocalRepositoryImp: LocalRepository {
    override fun save(hits: ArrayList<Hits>): Observable<Boolean> {
        return Observable.create { e ->
            val realm = Realm.getDefaultInstance()
            try {
                if (realm.isInTransaction) realm.cancelTransaction()
            } catch (ex: Exception) {
            }
            realm.beginTransaction()
            realm.insertOrUpdate(hits.map {
                it.toLocal()
            })
            realm.commitTransaction()
            e.onNext(true)
            e.onComplete()
            realm.close()
        }
    }

    override fun loadHits(): Observable<ArrayList<Hits>> {
        return Observable.create<ArrayList<Hits>> { e ->
            val realm = Realm.getDefaultInstance()
            val results: RealmResults<HitsLocal> = realm.where<HitsLocal>(HitsLocal::class.java)
                .sort("created_at", Sort.DESCENDING)
                .findAll()
            if (results.size > 0) {
                e.onNext(results.map {
                    it.toDomain()
                } as ArrayList<Hits>)
            } else e.onError(Throwable("Hits not found"))
            realm.close()
            e.onComplete()
        }
    }

    private fun HitsLocal.toDomain() : Hits {
        val list = ArrayList<String>()
        _tags?.let { list.addAll(it) }

        return Hits(
            created_at, title, url, author, points, story_text, comment_text, num_comments, story_id,
            story_title, story_url, parent_id, created_at_i, list, objectID, HighlightResult()
        )
    }

    private fun Hits.toLocal() : HitsLocal {
        val list = RealmList<String>()
        tags?.let { list.addAll(it) }

        return HitsLocal(
            createdAt, title, url, author, points, storyText, commentText, numComments, storyId,
            storyTitle, storyUrl, parentId, createdAtI, list, objectID
        )
    }
}