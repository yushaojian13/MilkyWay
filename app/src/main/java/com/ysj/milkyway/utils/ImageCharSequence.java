package com.ysj.milkyway.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ImageSpan;

/**
 * Created by Yu Shaojian on 2015 12 21.
 */
public class ImageCharSequence {
    private ImageCharSequence() {
    }

    public static CharSequence create(Context context, int drawableId) {
        return create(context, drawableId, null);
    }

    public static CharSequence create(Context context, int drawableId, String text) {
        // TODO 学习ContextCompat
        // getDrawable(int i) is deprecated, use getDrawable(int i, Theme theme) for min SDK >=21
        // or ContextCompat.getDrawable(Context context, int id) if you want support for older versions.
        // Drawable image = context.getResources().getDrawable(iconIds[position], context.getTheme());
        // Drawable image = context.getResources().getDrawable(imageResId[position]);
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);

        return create(drawable, text);
    }

    public static CharSequence create(Drawable drawable) {
        return create(drawable, null);
    }

    public static CharSequence create(Drawable drawable, String text) {
        if (drawable == null) {
            return null;
        }

        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        SpannableString sb = TextUtils.isEmpty(text) ?
                new SpannableString(" ") : new SpannableString("   " + text);
        ImageSpan imageSpan = new ImageSpan(drawable, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(imageSpan, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;
    }
}
