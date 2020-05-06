package com.android.viewProject.CustomView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
// 自定义View方式一:  扩展2
// https://www.jianshu.com/p/369f66035666
@SuppressLint("AppCompatCustomView")
public class LinearGradient_MatrixTextView extends TextView {
    int mViewWidth;
    // https://blog.csdn.net/t12x3456/article/details/10566219
    LinearGradient mLinearGradient;
    // https://www.cnblogs.com/plokmju/p/android_Matrix.html#Matrix
    Matrix mMatrix;
    Paint mPaint;
    int mTranslate=0;

    public LinearGradient_MatrixTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //在 onSizeChanged 方法中获取到宽度，并对各个类进行初始化
        if (mViewWidth == 0) {
            mViewWidth = getMeasuredWidth();
            if (mViewWidth > 0) {
                //得到 父类 TextView 中写字的那支笔。。。
                mPaint = getPaint();
                //初始化线性渲染器 不了解的请看上面连接
                mLinearGradient = new LinearGradient(0, 0, mViewWidth, 0,
                        new int[]{Color.BLUE, Color.YELLOW, Color.RED, Color.GREEN}, null, Shader.TileMode.REPEAT);
                //把渲染器给笔套上
                mPaint.setShader(mLinearGradient);
                //初始化 Matrix
                mMatrix = new Matrix();
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //先让父类方法执行，由于上面我们给父类的 Paint 套上了渲染器，所以这里出现的文字已经是彩色的了
        super.onDraw(canvas);
        Log.i("LinearGradient_MatrixTextView", "onDraw");
        if (mMatrix != null) {
            //利用 Matrix 的平移动作实现霓虹灯的效果，这里是每次滚动1/10
            mTranslate += mViewWidth / 10;
            //如果滚出了控件边界，就要拉回来重置开头，这里重置到了屏幕左边的空间
            if (mTranslate >  mViewWidth) {
                mTranslate = -mViewWidth/2;
            }
            //设置平移距离
            mMatrix.setTranslate(mTranslate, 0);
            //平移效果生效
            mLinearGradient.setLocalMatrix(mMatrix);
            //延迟 100 毫秒再次刷新 View 也就是再次执行本 onDraw 方法
            postInvalidateDelayed(2000);

        }
    }
}
