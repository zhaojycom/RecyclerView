package com.zhaojy.recyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class InvertedSquareView extends View {
    private final String TAG = this.getClass().getSimpleName();
    private Context context;
    /**
     * 设置画笔变量
     */
    private Paint paint;

    /**
     * 文字内容
     */
    private String text;

    private int width;
    private int height;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public InvertedSquareView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public InvertedSquareView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public InvertedSquareView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 获取控件的高度和宽度
        width = getWidth();
        height = getHeight();

        //绘制正方形
        drawInvertedSquare(canvas, width, height);
        //绘制文字内容
        drawText(canvas);

    }

    /**
     * 绘制正方形
     *
     * @param canvas 画布
     * @param width  宽度
     * @param height 长度
     */
    private void drawInvertedSquare(Canvas canvas, int width, int height) {
        //根据宽高计算正方形边长
        float sideLen = (float) Math.sqrt(Math.pow(width / 2, 2) + Math.pow(height / 2, 2));
        //绘制翻转45度角的正方形
        canvas.translate(width / 2.0f, 0);
        canvas.rotate(45);
        canvas.drawRect(0, 0, sideLen, sideLen, paint);
    }

    /**
     * 绘制文字内容
     *
     * @param canvas
     */
    private void drawText(Canvas canvas) {
        paint.setColor(context.getResources().getColor(R.color.white));
        paint.setStrokeWidth(3);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(SizeUtils.sp2px(context, 14));

        canvas.rotate(-45);
        Rect rect = getFontSize(text);
        int left = (width - rect.width()) / 2;
        int top = (height + rect.height() / 2) / 2;
        //  canvas.translate(-left, top);
        canvas.translate(0, top);
        canvas.drawText(text, 0, 0, paint);
    }


    /**
     * 画笔初始化
     *
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs) {
        this.context = context;
        //获取属性
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.invertedSquare);
        //获取背景颜色
        int color = array.getColor(R.styleable.invertedSquare_bkColor, 0);
        text = array.getString(R.styleable.invertedSquare_text);
        array.recycle();

        // 创建画笔
        paint = new Paint();
        // 设置画笔颜色为蓝色
        paint.setColor(color);
        // 设置画笔宽度为10px
        paint.setStrokeWidth(5f);
        //设置画笔模式为填充
        paint.setStyle(Paint.Style.FILL);

    }

    /**
     * 获取文字size
     *
     * @param str
     * @return
     */
    private Rect getFontSize(String str) {
        Rect rect = new Rect();
        paint.getTextBounds(str, 0, str.length(), rect);

        return rect;
    }

}

