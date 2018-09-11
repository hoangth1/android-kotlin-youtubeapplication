package framgia.com.video.youtubevideo.screen.video

import android.util.Log
import framgia.com.video.youtubevideo.data.model.Video

class HandlerClick(var onItemVideoClick: OnItemVideoClick) {
    fun onClickItemVideo(video: Video) {
        onItemVideoClick.onVideoClick(video)
    }
}
