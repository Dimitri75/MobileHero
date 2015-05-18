package kei.mobilehero.custom.widgets;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Dimitri on 18/05/2015.
 */
public class MyCustomTextView extends TextView{
    public MyCustomTextView(Context context, AttributeSet attrs){
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/wonderland.ttf"));
    }
}
