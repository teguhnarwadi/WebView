package com.allsosmed.webview;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import at.grabner.circleprogress.CircleProgressView;

public class SplashActivity extends AppCompatActivity {

    private static int splashInterval = 3000;
    private CircleProgressView mCircleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // making notification bar transparent
        changeStatusBarColor();

        mCircleView = (CircleProgressView) findViewById(R.id.circleView);
        mCircleView.setShowTextWhileSpinning(false); // Show/hide text in spinning mode
//        mCircleView.setText("Loading...");
        mCircleView.spin();

        if (Helper.isOnline(this)) {
            new Handler().postDelayed(new startTo(), splashInterval);
        } else {
            Helper.showToast(this, "Please check your internet connection");
        }

    }

    private void notifBarTransparent() {
        // Making notification bar transparent
        // if build versiond sdk is greter then 21
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
    }

    /**
     * Making notification bar transparent
     */
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private class startTo implements Runnable {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            startActivity(new Intent(SplashActivity.this, IntroActivity.class));
            SplashActivity.this.finish();
            SplashActivity.this.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
    }
}
