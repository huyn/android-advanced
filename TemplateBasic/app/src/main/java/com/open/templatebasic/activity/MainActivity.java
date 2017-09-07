package com.open.templatebasic.activity;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MenuItem;

import com.open.templatebasic.R;
import com.open.templatebasic.adapter.HomePagerAdapter;
import com.open.templatebasic.fragment.FourthFragment;
import com.open.templatebasic.fragment.FristFragment;
import com.open.templatebasic.fragment.SecondFragment;
import com.open.templatebasic.fragment.ThirdFragment;
import com.open.templatebasic.utils.BottomNavigationViewHelper;

public class MainActivity extends BaseActivity {
    private final static String TAG = "MainActivity";

    // fragment
    private FristFragment firstFragment;
    private SecondFragment secondFragment;
    private ThirdFragment thirdFragment;
    private FourthFragment fourthFragment;

    // bottom navigation
    private BottomNavigationView bottomNavigationView;
    private MenuItem currentItem; // current menu item

    // adapter
    private ViewPager mViewPager;
    private final static boolean allowScroll = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initData() {
        firstFragment = FristFragment.newInstance("param1", "param2");
        secondFragment = SecondFragment.newInstance("param1", "param2");
        thirdFragment = ThirdFragment.newInstance("param1", "param2");
        fourthFragment = FourthFragment.newInstance("param1", "param2");
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);

        // bottom navigation
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> dealBottomItemSelected(item.getItemId()));
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        initViewPager();
    }

    private void initViewPager() {
        HomePagerAdapter mPagerAdapter = new HomePagerAdapter(getSupportFragmentManager(), this);
        mPagerAdapter.addFragment(firstFragment);
        mPagerAdapter.addFragment(secondFragment);
        mPagerAdapter.addFragment(thirdFragment);
        mPagerAdapter.addFragment(fourthFragment);

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (currentItem != null) {
                    currentItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                currentItem = bottomNavigationView.getMenu().getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // disable viewpager scroll
        mViewPager.setOnTouchListener((v, event) -> !allowScroll);
    }

    @Override
    protected void loadData() {
        currentItem = bottomNavigationView.getMenu().getItem(0);
    }

    private boolean dealBottomItemSelected(int itemId) {
        // current id pretreatment
        if (itemId == currentItem.getItemId()) {
            Log.d(TAG, "onNavigationItemSelected not changed");
            return false;
        }
        // reset current item
        currentItem = bottomNavigationView.getMenu().findItem(itemId);
        // set viewpager item
        switch (itemId) {
            case R.id.navigation_fist:
                mViewPager.setCurrentItem(0);
                return true;
            case R.id.navigation_second:
                mViewPager.setCurrentItem(1);
                return true;
            case R.id.navigation_third:
                mViewPager.setCurrentItem(2);
                return true;
            case R.id.navigation_fourth:
                mViewPager.setCurrentItem(3);
                return true;
        }

        return false;
    }

}
