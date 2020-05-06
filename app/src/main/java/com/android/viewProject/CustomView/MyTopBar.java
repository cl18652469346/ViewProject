package com.android.viewProject.CustomView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.viewProject.R;
// 自定义View方式二:  组合
public class MyTopBar extends RelativeLayout {
    //定义了各个控件的属性变量
    String titleText, leftText, rightText;
    int titleColor, leftTextColor, rightTextColor;
    float titleTextSize;
    Drawable leftBackground, rightBackground;
    Button rightButton, leftButton;
    TextView titleTextView;
    MyTopBarClickListener myTopBarClickListener;

    public MyTopBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        //通过 TypedArray 可以从 XML 文件中取出相应的属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TopBar);

        //使用R.styleable.xxxxx 就可以取出对应数据类型的具体数据，第二个参数是默认值 如果取值为空就会使用默认值
        titleText = typedArray.getString(R.styleable.TopBar_titleText);
        titleTextSize = typedArray.getDimension(R.styleable.TopBar_titleTextSize, 20);
        titleColor = typedArray.getColor(R.styleable.TopBar_titleColor, 0);

        leftText = typedArray.getString(R.styleable.TopBar_leftText);
        leftTextColor = typedArray.getColor(R.styleable.TopBar_leftTextColor, 0);
        leftBackground = typedArray.getDrawable(R.styleable.TopBar_leftBackground);

        rightText = typedArray.getString(R.styleable.TopBar_rightText);
        rightTextColor = typedArray.getColor(R.styleable.TopBar_rightTextColor, 0);
        rightBackground = typedArray.getDrawable(R.styleable.TopBar_rightBackground);

        typedArray.recycle();
        initView();
        setListener();
    }

    public void initView() {
        //创建三个控件对象
        rightButton = new Button(getContext());
        leftButton = new Button(getContext());
        titleTextView = new TextView(getContext());

        //将之前得到的相应属性赋给相应的对象

        rightButton.setText(rightText);
        rightButton.setTextColor(rightTextColor);
        rightButton.setBackground(rightBackground);

        leftButton.setText(leftText);
        leftButton.setTextColor(leftTextColor);
        leftButton.setBackground(leftBackground);

        titleTextView.setText(titleText);
        titleTextView.setTextSize(titleTextSize);
        titleTextView.setTextColor(titleColor);

        //对各个子控件设置布局元素，并将它添加到此 ViewGroup 中。
        LayoutParams leftParams =
                new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        leftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, TRUE);
        addView(leftButton, leftParams);

        LayoutParams rightParams =
                new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rightParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, TRUE);
        addView(rightButton, rightParams);

        LayoutParams titleParams =
                new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        titleParams.addRule(RelativeLayout.CENTER_IN_PARENT, TRUE);
        addView(titleTextView, titleParams);
    }


    /**
     * 定义一个接口 让调用者自己实现具体逻辑
     */
    public interface MyTopBarClickListener {
        void leftClick();

        void rightClick();
    }

    /**
     * 开放一个方法给外部来设置点击事件，参数用接口的形式得到调用者自己设置的逻辑
     * @param myTopBarClickListener 监听器接口类
     */
    public void setTopBarOnClickListener(MyTopBarClickListener myTopBarClickListener) {
        this.myTopBarClickListener = myTopBarClickListener;
    }

    private void setListener() {
        rightButton.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View v) {
                myTopBarClickListener.rightClick();
            }
        });

        leftButton.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View v) {
                myTopBarClickListener.leftClick();
            }
        });
    }

    public void setButtonVisable(int i, boolean x) {

        if (x) {
            if (i == 0) {
                leftButton.setVisibility(View.VISIBLE);

            }else {
                rightButton.setVisibility(View.VISIBLE);
            }

        }else {
            if (i == 0) {
                leftButton.setVisibility(View.GONE);
            }else {
                rightButton.setTextColor(View.GONE);
            }
        }
    }
}
