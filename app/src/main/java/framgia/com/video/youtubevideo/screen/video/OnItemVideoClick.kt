package framgia.com.video.youtubevideo.screen.video

import android.view.View
import framgia.com.video.youtubevideo.data.model.Video

interface OnItemVideoClick {
    fun onVideoClick(video: Video)
    fun onPopupButtonClick(video: Video,view:View)
}
