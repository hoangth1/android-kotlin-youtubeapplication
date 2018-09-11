package framgia.com.video.youtubevideo.utils

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide

object BindAdapter {
    @BindingAdapter("imageUrl")
    @JvmStatic
    fun loadImage(imageView: ImageView, image: String) {
        Glide.with(imageView.context).load(image).into(imageView)
    }

}
