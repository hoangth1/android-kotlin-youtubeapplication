package framgia.com.video.youtubevideo.data.source.local.database

import android.arch.persistence.room.*
import framgia.com.video.youtubevideo.data.model.Video
import io.reactivex.Single

@Dao
interface VideoDAO {
    @Query("SELECT * FROM video")
    fun getVideos(): Single<List<Video>>

    @Query("SELECT * FROM video WHERE mId=:id")
    fun getVideo(id: String): Single<Video>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertVideo(video: Video): Long

    @Delete
    fun deleteVideo(video: Video)
}
