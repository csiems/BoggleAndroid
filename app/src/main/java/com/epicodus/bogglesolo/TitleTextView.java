package com.epicodus.bogglesolo;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Guest on 3/16/16.
 */
public class TitleTextView extends TextView {
    public TitleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/BodoniXT.ttf"));
    }
}
