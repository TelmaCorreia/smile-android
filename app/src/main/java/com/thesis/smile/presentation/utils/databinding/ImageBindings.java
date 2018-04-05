package com.thesis.smile.presentation.utils.databinding;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.thesis.smile.presentation.utils.actions.Utils;

public class ImageBindings {

    @BindingAdapter("android:src")
    public static void setImageResource(ImageView imageView, int resource) {
        imageView.setImageResource(resource);
    }

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String url) {
        url = Utils.normalizeUrl(url);

        Glide.with(imageView.getContext())
                .load(url)
                .into(imageView);
    }

    //TODO
    @BindingAdapter({"gifUrl", "gifError"})
    public static void setGifUrl(ImageView imageView, String url, Drawable error) {
        url = Utils.normalizeUrl(url);

        Glide.with(imageView.getContext())
                .load(url)
                .apply(new RequestOptions().placeholder(imageView.getDrawable()))
                .apply(new RequestOptions().error(error))
                .into(imageView);
    }

    @BindingAdapter({"imageUrl", "imageError"})
    public static void setImageUrl(ImageView imageView, String url, Drawable error) {
        url = Utils.normalizeUrl(url);

        Glide.with(imageView.getContext())
                .load(url)
                .apply(new RequestOptions().placeholder(imageView.getDrawable()))
                .apply(new RequestOptions().error(error))
                .into(imageView);
    }

    @BindingAdapter({"imageCircleUrl", "imageCircleError"})
    public static void setImageCircleUrl(ImageView imageView, String url, Drawable error) {
        url = Utils.normalizeUrl(url);

        Glide.with(imageView.getContext())
                .load(url)
                .apply(new RequestOptions().placeholder(imageView.getDrawable()))
                .apply(new RequestOptions().error(error))
                .apply(RequestOptions.circleCropTransform())
                .into(imageView);
    }

    @BindingAdapter("imageUri")
    public static void setImageUri(ImageView imageView, String uri) {
        Glide.with(imageView.getContext())
                .load("file://" + uri)
                .into(imageView);
    }

    @BindingAdapter({"imageUri", "imageError"})
    public static void setImageUri(ImageView imageView, String uri, Drawable error) {
        Glide.with(imageView.getContext())
                .load("file://" + uri)
                .apply(new RequestOptions()
                        .placeholder(error))
                .apply(new RequestOptions()
                        .error(error))
                .into(imageView);
    }
}
