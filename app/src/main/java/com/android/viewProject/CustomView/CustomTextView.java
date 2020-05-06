package com.android.viewProject.CustomView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;
// https://www.jianshu.com/p/369f66035666
// 自定义View方式一:  扩展1
@SuppressLint("AppCompatCustomView")
public class CustomTextView  extends TextView {
    private Paint mPaint;

    public CustomTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //这部分是在原生控件绘制之前进行绘制的，位于原生控件绘制区域底层，不会遮挡其他内容

        //第一个矩形，大小跟此控件一样大，位于底层
        mPaint.setColor(Color.BLUE);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);

        //第二个矩形，稍微小一点，但是会遮挡第一个矩形
        mPaint.setColor(Color.RED);
        canvas.drawRect(10, 10, getMeasuredWidth() - 10, getMeasuredHeight() - 10, mPaint);

        //原生控件开始绘制，会遮挡上面代码绘制的内容。
        super.onDraw(canvas);

        //第三个矩形，这是在原生控件绘制完毕后进行绘制的，位于原生控制的上层，会遮挡所有之前绘制的内容
        mPaint.setColor(Color.GREEN);
        canvas.drawRect(20, 20,200, 100, mPaint);
    }
}
