package framgia.com.video.youtubevideo.data.source.local.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import framgia.com.video.youtubevideo.data.model.Video
import kotlin.reflect.KClass

@Database(entities = [Video::class], version = 1)
abstract class VideoDatabase : RoomDatabase() {
    abstract fun videoDAO(): VideoDAO

    companion object {
        const val DATABASE_NAME = "youtubevideodatabase"
        private var INSTANCE: VideoDatabase? = null
        fun newInstance(context: Context): VideoDatabase = INSTANCE
                ?: Room.databaseBuilder(context, VideoDatabase::class.java, DATABASE_NAME)
                        .build()
    }

}
