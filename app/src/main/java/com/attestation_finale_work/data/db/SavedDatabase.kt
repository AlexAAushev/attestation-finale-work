package com.attestation_finale_work.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.attestation_finale_work.data.db.dao.SavedSubredditsDao
import com.attestation_finale_work.domain.SubredditThing

@Database(
    entities = [SubredditThing::class],
    version = 1,
    exportSchema = false
)
abstract class SavedDatabase : RoomDatabase() {

    abstract fun getCachedSubredditsDao(): SavedSubredditsDao

    companion object {
        const val DATABASE_NAME = "cached_reddit_things"
    }
}