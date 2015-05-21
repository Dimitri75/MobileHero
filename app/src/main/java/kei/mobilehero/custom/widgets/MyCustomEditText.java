package kei.mobilehero.custom.widgets;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by Dimitri on 21/05/2015.
 */
public class MyCustomEditText extends EditText {
    public MyCustomEditText(Context context, AttributeSet attrs){
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/belwe-bold.ttf"));
    }
}
