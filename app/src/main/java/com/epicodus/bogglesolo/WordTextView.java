package com.epicodus.bogglesolo;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Guest on 3/16/16.
 */
public class WordTextView extends TextView {
    public WordTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/futura medium bt.ttf"));
    }
}
