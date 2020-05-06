package com.android.viewProject.CustomView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class MyView extends View {
    Paint circlePaint, arcPaint, textPaint;
    RectF rectF;
    //弧线度数变量
    int sweepAngle = 10;

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    private void initPaint() {
        textPaint = new TextPaint(0);
        textPaint.setColor(Color.RED);
        textPaint.setTextSize(50);
        //实心圆画笔
        circlePaint = new Paint(0);
        circlePaint.setColor(Color.BLUE);
        //外圈弧线画笔
        arcPaint = new Paint(0);
        arcPaint.setColor(Color.GREEN);                    //设置画笔颜色
        arcPaint.setStyle(Paint.Style.STROKE);    //设置不填充中间
        arcPaint.setStrokeWidth((float) 80.0);   //画笔粗细

        //用来定位弧线的矩形
        rectF = new RectF();
        rectF.top = 340;
        rectF.bottom = 740;
        rectF.left = 340;
        rectF.right = 740;
    }

    @Override protected void onDraw(Canvas canvas) {

        //这是圆心的坐标，我的屏幕的1080的，使用540的话就是横向居中了。
        float x = getMeasuredWidth()/2;
        float y = getMeasuredHeight()/2;
        //圆圈的半径 这里是100
        float radius = 50;
        //画圆~
//        canvas.drawColor(Color.GRAY);
        canvas.drawCircle(x, y, radius, circlePaint);

        //画弧线
        RectF rectF = new RectF(getMeasuredWidth()/2 - 150,getMeasuredHeight()/2-150,getMeasuredWidth()/2 + 150,getMeasuredHeight()/2+150);
        canvas.drawArc(rectF,270,sweepAngle,false,arcPaint);
//        canvas.drawArc(rectF, 270, i, false, arcPaint);

        //显示度数与提示
        canvas.drawText(String.valueOf(sweepAngle), getMeasuredWidth()/2, getMeasuredHeight()/2, textPaint);
        canvas.drawText("点一点会转圈！", getMeasuredWidth()/4, getMeasuredHeight()/2 +100, textPaint);
    }

    /**
     * 公开一个方法，可以更新弧线的度数~
     */
    public void updateSweepAngle() {
        //超过360度就还原到10度
        if (sweepAngle >= 360) {
            sweepAngle = 10;
            postInvalidate();
        } else {
            this.sweepAngle += 10;
            postInvalidate();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            updateSweepAngle();
        }
        return super.onTouchEvent(event);
    }
}
