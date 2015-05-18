package kei.mobilehero.custom.widgets;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by Dimitri on 18/05/2015.
 */
public class MyCustomButton extends Button{
    public MyCustomButton(Context context, AttributeSet attrs){
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/oblivion.ttf"));
    }
}
