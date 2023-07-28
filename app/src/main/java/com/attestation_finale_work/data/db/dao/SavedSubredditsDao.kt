package com.attestation_finale_work.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.attestation_finale_work.domain.SubredditThing
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedSubredditsDao {

    @Insert(onConflict = REPLACE)
    suspend fun saveSubreddit(subreddit: SubredditThing)

    @Query("SELECT * FROM subreddits")
    fun showAll(): Flow<SubredditThing>
    @Query("SELECT * FROM subreddits WHERE displayName LIKE :subredditName")
    fun getSubreddit(subredditName: String): Flow<SubredditThing>
    @Query("DELETE FROM subreddits")
    suspend fun clearAll()
    @Update
    suspend fun updateSubreddit(subreddit: SubredditThing)
}