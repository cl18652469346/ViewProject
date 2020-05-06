package com.android.viewProject;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class AppView extends FrameLayout {
    private static final String TAG = "AppView";
    private int iconSize;
    private ImageView appIcon;
    private AppIconListener appIconListener;

    public AppView(@NonNull Context context) {
        this(context,null);
        Log.i(TAG,"AppView construct 1");
    }

    public AppView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
        Log.i(TAG,"AppView construct 2");
    }

    public AppView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.i(TAG,"AppView construct 3");
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.AppView);
        resloveTypedArray(typedArray);
        typedArray.recycle();
        setWillNotDraw(false);
        initView();
    }

    private void resloveTypedArray(TypedArray typedArray) {
        iconSize = typedArray.getInt(R.styleable.AppView_iconSize,0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i(TAG,"onDraw canvas");
    }

    private void initView() {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(getResources().getColor(R.color.colorPrimary));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        Log.i(TAG,"initView iconSize = " + iconSize);
        appIcon = new ImageView(getContext());
        appIcon.setImageResource(R.drawable.add_icon);
        appIcon.setLayoutParams(new LayoutParams(iconSize,iconSize));
        appIcon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                appIconListener.onAppIconClick(1);
                setAppIcon(ApplicationUtil.getIcon(getContext(),"com.android.settings"));
            }
        });
        addView(appIcon);
    }

    public interface AppIconListener{
        void onAppIconClick(int fingerIndex);
    }

    public void setAppIconListener(AppIconListener listener) {
        appIconListener = listener;
    }

    public void setAppIcon(Drawable drawable) {
        if (appIcon != null) {
            appIcon.setImageDrawable(drawable);
        }
    }
}
