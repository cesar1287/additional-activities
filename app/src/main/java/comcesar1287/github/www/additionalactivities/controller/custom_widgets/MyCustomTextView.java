package comcesar1287.github.www.saudecard.controller.custom_widgets;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class MyCustomTextView extends android.support.v7.widget.AppCompatTextView {
    public MyCustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/OpenSans-Regular.ttf"));
    }
}
