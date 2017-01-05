package com.roomorama.caldroid;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;

/**
 * Created by TCIG_PC_54 on 1/3/2017.
 */

public class HorizontalSeparatorGridView extends GridView {
    public HorizontalSeparatorGridView(Context context) {
        super(context);
    }

    public HorizontalSeparatorGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HorizontalSeparatorGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public HorizontalSeparatorGridView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    // Additional methods

    @Override
    protected void dispatchDraw(Canvas canvas) {
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            int bottom = child.getBottom();
            int left = child.getLeft();
            int right = child.getRight();

            Paint paint = new Paint();
            paint.setColor(0xffececec);

            paint.setStrokeWidth(Math.round(1 * getResources().getDisplayMetrics().density));

            int offset = 200;// Some offset
            canvas.drawLine(left + offset, bottom, right - offset, bottom, paint);
        }
        super.dispatchDraw(canvas);
    }

}