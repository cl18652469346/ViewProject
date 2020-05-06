package com.android.viewProject;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

// https://www.jianshu.com/p/369f66035666

public class MultiChoiceView extends RelativeLayout implements AppView.AppIconListener{
    private static final String TAG = "MultiChoiceView";
    private int iconSize;
    private int viewArea;
    private int MAX_COUNT = 4;
    private ImageView addIcons[];
    private LayoutParams layoutParams[];
    private ImageArea imageAreas[];
    private CharSequence imagePointsS[];

    private class ImageArea {
        Point pointA;
        Point pointB;
        Point pointC;
        Point pointD;

        public ImageArea(Point points[]) {
            pointA = points[0];
            pointB = points[1];
            pointC = points[2];
            pointD = points[3];
        }

        public boolean isInArea(int x, int y){
            // https://blog.csdn.net/san_junipero/article/details/79172260
            final Point A = pointA;
            final Point B = pointB;
            final Point C = pointC;
            final Point D = pointD;
            final int a = (B.x - A.x)*(y - A.y) - (B.y - A.y)*(x - A.x);
            final int b = (C.x - B.x)*(y - B.y) - (C.y - B.y)*(x - B.x);
            final int c = (D.x - C.x)*(y - C.y) - (D.y - C.y)*(x - C.x);
            final int d = (A.x - D.x)*(y - D.y) - (A.y - D.y)*(x - D.x);
            if((a > 0 && b > 0 && c > 0 && d > 0) || (a < 0 && b < 0 && c < 0 && d < 0)) {
                return true;
            }
//      AB X AP = (b.x - a.x, b.y - a.y) x (p.x - a.x, p.y - a.y) = (b.x - a.x) * (p.y - a.y) - (b.y - a.y) * (p.x - a.x);
//      BC X BP = (c.x - b.x, c.y - b.y) x (p.x - b.x, p.y - b.y) = (c.x - b.x) * (p.y - b.y) - (c.y - b.y) * (p.x - b.x);
            return false;
        }

        public String toString(){
            return "pointA = " + pointA.x + "  " + pointA.y +
                    " pointB = " + pointB.x + "  " + pointB.y +
                    " pointC = " + pointC.x + "  " + pointC.y +
                    " pointD = " + pointD.x + "  " + pointD.y;
        }
    }

    public MultiChoiceView(@NonNull Context context) {
        this(context,null);
        Log.i(TAG,"MultiChoiceView construct 1");
    }

    public MultiChoiceView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
        Log.i(TAG,"MultiChoiceView construct 2");
    }

    public MultiChoiceView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.i(TAG,"MultiChoiceView construct 3");

        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.MultiChoiceView);
        resloveTypedArray(typedArray);
        typedArray.recycle();
        setWillNotDraw(false); // 解决onDraw不执行
        initView();
    }

    private void resloveTypedArray(TypedArray typedArray) {
        iconSize = typedArray.getInt(R.styleable.MultiChoiceView_iconSize_,0);
        viewArea = typedArray.getInt(R.styleable.MultiChoiceView_viewArea_,0);
        imagePointsS = typedArray.getTextArray(R.styleable.MultiChoiceView_imagePoints);
    }

    private void initView() {
//        addIcons = new ImageView[MAX_COUNT];
//        layoutParams = new LayoutParams[MAX_COUNT];
//        for (int i = 0; i < MAX_COUNT; i++) {
//            addIcons[i] = new ImageView(getContext());
//            Log.i(TAG,"MultiChoiceView initView  iconSize = " + iconSize + "  i = " +i);
//            layoutParams[i] = new LayoutParams(iconSize,iconSize);
//            if (i == 0) { // 左
//                layoutParams[i].leftMargin = iconSize/2;
//                layoutParams[i].topMargin = viewArea/2 - iconSize/2;
//            } else if (i == 1) { // 上
//                layoutParams[i].leftMargin = viewArea/2 - iconSize/2;
//                layoutParams[i].topMargin = iconSize/2;
//            } else if (i == 2) { // 右
//                layoutParams[i].leftMargin = viewArea - iconSize/2*3;
//                layoutParams[i].topMargin = viewArea/2 - iconSize/2;
//            } else { // 下
//                layoutParams[i].leftMargin = viewArea/2 - iconSize/2;
//                layoutParams[i].topMargin = viewArea - iconSize/2*3;
//            }
//
//            addIcons[i].setLayoutParams(layoutParams[i]);
//            addIcons[i].setImageResource(R.drawable.add_icon);
//            addView(addIcons[i]);
//        }

        //
        imageAreas = new ImageArea[imagePointsS.length];
        Point point[] = new Point[imagePointsS.length];
        for (int i = 0; i < imagePointsS.length; i++){
            String singleImagePoints[] = imagePointsS[i].toString().split(";");
            for (int j = 0; j < singleImagePoints.length; j++) {
                point[j] = new Point((int)Integer.valueOf(singleImagePoints[j].split(",")[0]),(int)Integer.valueOf(singleImagePoints[j].split(",")[1]));
            }
            imageAreas[i] = new ImageArea(point);
            Log.i(TAG,"imageAreas = " + imageAreas[i].toString());
        }
    }

    private void drawImageAreas(Canvas canvas, Paint paint) {
        Path path = new Path();
        for (int i = 0; i < imageAreas.length; i++) {
            path.moveTo(imageAreas[i].pointA.x,imageAreas[i].pointA.y);
            path.lineTo(imageAreas[i].pointB.x,imageAreas[i].pointB.y);
            path.lineTo(imageAreas[i].pointC.x,imageAreas[i].pointC.y);
            path.lineTo(imageAreas[i].pointD.x,imageAreas[i].pointD.y);
            path.lineTo(imageAreas[i].pointA.x,imageAreas[i].pointA.y);
        }
        canvas.drawPath(path,paint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i(TAG,"MultiChoiceView onDraw");
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(getResources().getColor(R.color.colorPrimary));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);

        canvas.drawCircle(getMeasuredWidth()/2, getMeasuredHeight()/2, getMeasuredWidth()/2-10,  paint);
//        canvas.drawColor(getResources().getColor(R.color.colorAccent));
        drawImageAreas(canvas,paint);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        Log.i(TAG,"MultiChoiceView onLayout  getChildCount =  " + getChildCount());
        int childCount = getChildCount();
        layoutParams = new LayoutParams[childCount];
        for (int i = 0; i < childCount; i++) {
            layoutParams[i] = new LayoutParams(iconSize,iconSize);
            if (i == 0) { // 左
                layoutParams[i].leftMargin = iconSize/2;
                layoutParams[i].topMargin = viewArea/2 - iconSize/2;
            } else if (i == 1) { // 上
                layoutParams[i].leftMargin = viewArea/2 - iconSize/2;
                layoutParams[i].topMargin = iconSize/2;
            } else if (i == 2) { // 右
                layoutParams[i].leftMargin = viewArea - iconSize/2*3;
                layoutParams[i].topMargin = viewArea/2 - iconSize/2;
            } else { // 下
                layoutParams[i].leftMargin = viewArea/2 - iconSize/2;
                layoutParams[i].topMargin = viewArea - iconSize/2*3;
            }
            getChildAt(i).setLayoutParams(layoutParams[i]);
            ((AppView)getChildAt(i)).setAppIconListener(this);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.i(TAG,"MultiChoiceView onMeasure");
        measureChildren(widthMeasureSpec,heightMeasureSpec);
    }

    @Override
    public void onAppIconClick(int fingerIndex) {
        fingerSelectListener.onFingerSelect(fingerIndex);
    }

    private FingerSelectListener fingerSelectListener;
    public interface FingerSelectListener{
        void onFingerSelect(int fingerIndex);
    }

    public void setFingerSelectListener(FingerSelectListener listener) {
        fingerSelectListener = listener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean isInArea = false;

        for (int i = 0; i < imageAreas.length; i++) {
            isInArea = imageAreas[i].isInArea((int) event.getX(),(int) event.getY());
            if (isInArea) {
                fingerSelectListener.onFingerSelect(i);
                break;
            }
        }
        return super.onTouchEvent(event);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.i(TAG,"MultiChoiceView onFinishInflate");
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.i(TAG,"MultiChoiceView onSizeChanged");
    }
}
