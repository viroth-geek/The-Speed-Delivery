package com.iota.eshopping.util;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by channarith.bong on 12/7/17.
 */

public class FontManager {

    public static final String ROOT = "fonts/";
    public static final String FONTAWESOME = ROOT + "fontawesome_webfont.ttf";

    /**
     * @param context
     * @param font
     * @return
     */
    public static Typeface getTypeface(Context context, String font) {
        return Typeface.createFromAsset(context.getAssets(), font);
    }
}
