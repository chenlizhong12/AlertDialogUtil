package com.marvin.alertdialogcustom.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.marvin.alertdialogcustom.R;

/**
 * ┏┓　   ┏┓
 * ┏┛┻━━━━━┛┻━┓
 * ┃　　　　   ┃
 * ┃　━　━　   ┃
 * ████━████   ┃
 * ┃　　　　   ┃
 * ┃　 ┻　    ┃
 * ┗━┓      ┏━┛
 * 　┃      ┃
 * 　┃ 0BUG ┗━━━┓
 * 　┃0Error     ┣┓
 * 　┃0Warning   ┏┛
 * 　┗┓┓┏━┳┓┏┛ ━
 * 　　┃┫┫ ┃┫┫
 * 　　┗┻┛ ┗┻┛
 * Created by clz on 2019/7/31
 */
public class TestPaintView extends View {

    private Paint mPaint;
    private Context mContext;
    public TestPaintView(Context context) {
        super(context);
        init(context);
    }


    public TestPaintView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TestPaintView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    private void init(Context context) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.mContext = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);//宽度的大小
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);//宽度的测量模式
        int heightSize = MeasureSpec.getSize(widthMeasureSpec);//高度的大小
        int heightMode = MeasureSpec.getMode(widthMeasureSpec);//高度的测量模式
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setTextSize(60);
        canvas.drawText("你好啊", 100, 100,mPaint);
        mPaint.setTextSize(60);
        mPaint.setTypeface(Typeface.DEFAULT_BOLD);
        mPaint.setColor(Color.RED);
        canvas.drawText("你好啊", 100, 200,mPaint);
        mPaint.setTypeface(Typeface.MONOSPACE);
        canvas.drawText("你好啊",100,300,mPaint);
        mPaint.setTypeface(Typeface.SANS_SERIF);
        canvas.drawText("你好啊",100,400,mPaint);
        mPaint.setTypeface(Typeface.SERIF);
        canvas.drawText("你好啊",100,500,mPaint);
        canvas.drawText("这是一个测试",100,600,mPaint);
        canvas.save();
//        canvas.drawRGB(128,0,0);
//        canvas.drawColor(getResources().getColor(R.color.red));
        canvas.drawColor(ContextCompat.getColor(mContext,R.color.red));
        getResources().getDimension(R.dimen.test_sp);
        getResources().getColor(R.color.red);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }
}
