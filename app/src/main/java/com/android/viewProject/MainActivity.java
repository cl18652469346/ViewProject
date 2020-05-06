package com.android.viewProject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.viewProject.CustomView.MyTopBar;

public class MainActivity extends AppCompatActivity implements AppView.AppIconListener,
        View.OnClickListener,
        MultiChoiceView.FingerSelectListener,
        MyTopBar.MyTopBarClickListener {

    private AppView appView;
    private MultiChoiceView multiChoiceView;
    private AppView appViews[];
    private Button changeModeButton;
    private MyTopBar myTopBar;
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appView = findViewById(R.id.appView);
        appView.setAppIconListener(this);
        changeModeButton = findViewById(R.id.changeMod);
        changeModeButton.setOnClickListener(this);

        multiChoiceView = findViewById(R.id.multiChoiceView);
        multiChoiceView.setFingerSelectListener(this);
        myTopBar = (MyTopBar) findViewById(R.id.mytopbar);
        myTopBar.setTopBarOnClickListener(this);
    }

    @Override
    public void onAppIconClick(int fingerIndex) {
        Log.i(TAG,"onAppIconClick  fingerIndex = " + fingerIndex);
        appView.setAppIcon(ApplicationUtil.getIcon(this,"com.android.settings"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.changeMod:
                if (appView.getVisibility() == View.VISIBLE) {
                    appView.setVisibility(View.GONE);
                    multiChoiceView.setVisibility(View.VISIBLE);
                } else {
                    appView.setVisibility(View.VISIBLE);
                    multiChoiceView.setVisibility(View.GONE);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onFingerSelect(int fingerIndex) {
//        onAppIconClick(fingerIndex);
        Log.i(TAG," onFingerSelect fingerIndex = " + fingerIndex);
        Toast.makeText(this,"index = " + fingerIndex, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void leftClick() {
        Toast.makeText(MainActivity.this, "左边被点击了", Toast.LENGTH_LONG).show();
    }

    @Override
    public void rightClick() {
        Toast.makeText(MainActivity.this, "右边被点击了", Toast.LENGTH_LONG).show();
    }
}
