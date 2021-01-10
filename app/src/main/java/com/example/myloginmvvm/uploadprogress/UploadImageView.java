package com.example.myloginmvvm.uploadprogress;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by guoli on 2016/7/4.
 */
public class UploadImageView extends androidx.appcompat.widget.AppCompatImageView {

    private int mPercent = 0;   //上传百分比

    private Paint mPaint;   //表层的颜色画布
    private int mColor = Color.parseColor("#66000000");

    public UploadImageView(Context context) {
        this(context, null);
    }

    public UploadImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UploadImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint();
        mPaint.setColor(mColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0, 0, getWidth(), getHeight() * (100 - mPercent) / 100, mPaint);
    }

    public void updatePercent(int percent) {
        this.mPercent = percent ;
        if(mPercent < 0) mPercent = 0;
        if(mPercent > 100) mPercent = 100 ;
        invalidate();
    }

}
