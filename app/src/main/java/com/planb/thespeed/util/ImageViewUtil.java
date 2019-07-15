package com.planb.thespeed.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.planb.thespeed.R;

import java.io.ByteArrayOutputStream;

/**
 * @author channarith.bong
 */

public class ImageViewUtil {

    /**
     * @param imageView ImageView
     * @return byte[]
     */
    public static byte[] byteArray(ImageView imageView) {
        if (imageView != null && imageView.getDrawable() instanceof BitmapDrawable) {
            BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
            if (drawable == null) {
                return null;
            }
            Bitmap bitmap = drawable.getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            return stream.toByteArray();
        }
        return null;
    }

    /**
     * @param imageView int
     * @param newWidth int
     * @param newHeight ImageView
     * @return byte[]
     */
    public static byte[] byteArray(ImageView imageView, int newWidth, int newHeight) {
        if (imageView != null) {
            BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
            if (drawable == null) {
                drawable = (BitmapDrawable) imageView.getBackground();
            }

            Bitmap bitmap = drawable.getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            Bitmap resized = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
            resized.compress(Bitmap.CompressFormat.PNG, 100, stream);
            return stream.toByteArray();

        }
        return null;
    }

    /**
     * @param byteArray byte[]
     * @return Bitmap
     */
    public static Bitmap bitMap(byte[] byteArray) {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }

    /**
     * @param context Context
     * @param imageUrl String
     * @param imageView ImageView
     */
    public static void loadImageByUrl(Context context, String imageUrl, ImageView imageView) {
        loadImageByUrl(context, imageUrl, imageView, null);
    }

    /**
     * @param context Context
     * @param imageUrl String
     * @param imageView ImageView
     * @param listener RequestListener
     */
    public static void loadImageByUrl(Context context, String imageUrl, ImageView imageView, RequestListener<Drawable> listener) {
        if (imageView == null) {
            return;
        }
        if (imageUrl == null || imageUrl.isEmpty()) {
            imageView.setImageResource(R.color.colorDeliverBackgroundLightGrayI);
            return;
        }
        Glide.with(context)
                .load(imageUrl)
                .thumbnail(0.3f)
                .listener(listener)
                .apply(new RequestOptions()
                        .dontAnimate()
                        .dontTransform()
                        .fitCenter()
                        .error(R.drawable.default_product)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .skipMemoryCache(true)
                )
                .into(imageView);
    }
}
