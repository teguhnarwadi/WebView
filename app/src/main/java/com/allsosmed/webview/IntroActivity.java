package com.allsosmed.webview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class IntroActivity extends AppCompatActivity {

    private String urlDaftar = "https://jasaallsosmed.co.id/reseller.html";
    private String urlContact = "https://jasaallsosmed.co.id/kontak.html";

    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        // initialize item view
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);

        // layouts of all welcome sliders
        // add few more layouts if you want
        layouts = new int[]{
                R.layout.view_slide1,
                R.layout.view_slide2,
                R.layout.view_slide3};

        // adding bottom dots
        addBottomDots(0);

        // making notification bar transparent
        changeStatusBarColor();

        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

    }

    private void launchLogin() {
        startActivity(new Intent(IntroActivity.this, MainActivity.class));
        IntroActivity.this.finish();
        IntroActivity.this.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
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

    //  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }


    /**
     * View pager adapter
     */
    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;
        private View v;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            v = new View(container.getContext());
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            int resId = 0;
            switch (position) {
                case 0:
                    resId = R.layout.view_slide1;
                    v = layoutInflater.inflate(resId, null, false);
                    Button btn_daftar = (Button) v.findViewById(R.id.btn_daftar);
                    btn_daftar.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View m) {
                            int current = getItem(+1);
                            if (current < layouts.length) {
                                // move to next screen
                                viewPager.setCurrentItem(current);
                            } else {
                                launchLogin();
                            }
                        }
                    });
                    break;

                case 1:
                    resId = R.layout.view_slide2;
                    v = layoutInflater.inflate(resId, null, false);
                    Button btn_contact = (Button) v.findViewById(R.id.btn_contact);
                    btn_contact.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View m) {
                            int current = getItem(+1);
                            if (current < layouts.length) {
                                // move to next screen
                                viewPager.setCurrentItem(current);
                            } else {
                                launchLogin();
                            }
                        }
                    });
                    break;

                case 2:
                    resId = R.layout.view_slide3;
                    v = layoutInflater.inflate(resId, null, false);
//                    Button btn_mulai = (Button) v.findViewById(R.id.btn_mulai);
                    Button btn_indo = (Button) v.findViewById(R.id.button_indo);
                    Button btn_world = (Button) v.findViewById(R.id.button_world);
                    ImageView iv_world = (ImageView) v.findViewById(R.id.iv_world);
                    ImageView iv_indo = (ImageView) v.findViewById(R.id.iv_indo);
                    btn_indo.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View m) {
                            Bundle b = new Bundle();
                            b.putString("url", Helper.url_indo);
                            b.putString("code", "indo");

                            Intent intent = new Intent(IntroActivity.this, MainActivity.class);
                            intent.putExtras(b);
                            startActivity(intent);
                            IntroActivity.this.finish();
                            IntroActivity.this.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        }
                    });

                    iv_indo.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View m) {
                            Bundle b = new Bundle();
                            b.putString("url", Helper.url_indo);
                            b.putString("code", "indo");

                            Intent intent = new Intent(IntroActivity.this, MainActivity.class);
                            intent.putExtras(b);
                            startActivity(intent);
                            IntroActivity.this.finish();
                            IntroActivity.this.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        }
                    });

                    btn_world.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View m) {
                            Bundle b = new Bundle();
                            b.putString("url", Helper.url_world);
                            b.putString("code", "world");

                            Intent intent = new Intent(IntroActivity.this, MainActivity.class);
                            intent.putExtras(b);
                            startActivity(intent);
                            IntroActivity.this.finish();
                            IntroActivity.this.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        }
                    });

                    iv_world.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View m) {
                            Bundle b = new Bundle();
                            b.putString("url", Helper.url_world);
                            b.putString("code", "world");

                            Intent intent = new Intent(IntroActivity.this, MainActivity.class);
                            intent.putExtras(b);
                            startActivity(intent);
                            IntroActivity.this.finish();
                            IntroActivity.this.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        }
                    });
                    break;

            }

            ((ViewPager) container).addView(v, 0);
            return v;

        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
}
