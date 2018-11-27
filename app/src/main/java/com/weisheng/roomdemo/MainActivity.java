package com.weisheng.roomdemo;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.weisheng.roomdemo.widget.CustomTabLayout;
import com.weisheng.roomdemo.widget.NoScrollViewPager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.tv_voltage)
    TextView tvVoltage;
    @BindView(R.id.ll_voltage)
    LinearLayout llVoltage;
    @BindView(R.id.tv_rent_type)
    TextView tvRentType;
    @BindView(R.id.ll_rent_type)
    LinearLayout llRentType;
    @BindView(R.id.tv_decor)
    TextView tvDecor;
    @BindView(R.id.ll_decor)
    LinearLayout llDecor;
    @BindView(R.id.tv_house_type)
    TextView tvHouseType;
    @BindView(R.id.ll_house_type)
    LinearLayout llHouseType;
    @BindView(R.id.tv_direction)
    TextView tvDirection;
    @BindView(R.id.ll_direction)
    LinearLayout llDirection;
    @BindView(R.id.tv_floor)
    TextView tvFloor;
    @BindView(R.id.ll_floor)
    LinearLayout llFloor;
    @BindView(R.id.tv_parking)
    TextView tvParking;
    @BindView(R.id.ll_parking)
    LinearLayout llParking;
    @BindView(R.id.tv_lift)
    TextView tvLift;
    @BindView(R.id.ll_lift)
    LinearLayout llLift;
    @BindView(R.id.hsv_view)
    CustomTabLayout hsView;
    @BindView(R.id.view_pager)
    NoScrollViewPager viewPager;
    @BindView(R.id.ll_select_dlg)
    LinearLayout llSelectDlg;
    @BindView(R.id.rl_root)
    RelativeLayout rlRoot;
    private PickerRoomUtils pickerUtils;
    private Activity mActivity;
    private PagerAdapter mPagerAdapter;
    private Unbinder bind;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bind = ButterKnife.bind(this);
        mActivity = this;
        initSelectDlg();
    }

    @Override
    protected void onDestroy() {
        if (bind != null) {
            bind.unbind();
        }
        super.onDestroy();
    }

    public void showVoltage(String text, int voltage) {
        tvVoltage.setTextColor(Color.parseColor("#4d4d4d"));
        tvVoltage.setText(text);
    }


    public void showRentType(String text, int first, int second) {
        tvRentType.setTextColor(Color.parseColor("#4d4d4d"));
        tvRentType.setText(text);
    }


    public void showDecor(String text) {
        tvDecor.setTextColor(Color.parseColor("#4d4d4d"));
        tvDecor.setText(text);
    }

    public void showHouseType(String text, int houseNum, int hallNum, int toiletNum) {
        tvHouseType.setTextColor(Color.parseColor("#4d4d4d"));
        tvHouseType.setText(text);
    }


    public void showDirection(String text) {
        tvDirection.setTextColor(Color.parseColor("#4d4d4d"));
        tvDirection.setText(text);
    }


    public void showFloor(String text, int where, int sum) {
        tvFloor.setTextColor(Color.parseColor("#4d4d4d"));
        tvFloor.setText(text);
    }


    public void showParking(String text, boolean hasParking) {
        tvParking.setTextColor(Color.parseColor("#4d4d4d"));
        tvParking.setText(text);
    }


    public void showLift(String text, boolean hasLift) {
        tvLift.setTextColor(Color.parseColor("#4d4d4d"));
        tvLift.setText(text);
    }


    List<String> titles = Arrays.asList("电压", "押付类型", "装修", "户型", "朝向", "楼层", "车位", "电梯");
    List<View> views = new ArrayList<>();

    private void initSelectDlg() {
        /**
         * 初始化对话框
         */
        pickerUtils = new PickerRoomUtils(mActivity, viewPager, hsView);
        views.add(pickerUtils.getVoltage(mActivity, viewPager, views.size()));

        views.add(pickerUtils.getRentPayType(mActivity, views.size()));

        views.add(pickerUtils.getDecoration(mActivity, views.size()));

        views.add(pickerUtils.getRoomPicker(mActivity, views.size()));//3

        views.add(pickerUtils.getRoomDirection(mActivity, views.size()));

        views.add(pickerUtils.getFloor(mActivity, views.size()));

        views.add(pickerUtils.getParking(mActivity, views.size()));

        views.add(pickerUtils.getLift(mActivity, views.size()));

        for (int i = 0; i < views.size(); i++) {
            hsView.addView(titles.get(i), "请选择");
        }
        hsView.setCheckTab(0);
        hsView.setOnItemTabSelectedListener(position -> {
            viewPager.setCurrentItem(position);
        });

        viewPager.setAdapter(mPagerAdapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return views.size();
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(views.get(position));
                return views.get(position);
            }

            @Override
            public int getItemPosition(@NonNull Object object) {
                return POSITION_NONE;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(views.get(position));
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int
                    positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                hsView.setCheckTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick({R.id.ll_voltage, R.id.ll_rent_type, R.id.ll_decor, R.id.ll_house_type, R.id
            .ll_direction, R.id.ll_floor, R.id.ll_parking, R.id.ll_lift, R.id.ll_select_dlg})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_voltage:
                showSelectDlg(0);
                break;
            case R.id.ll_rent_type:
                showSelectDlg(1);
                break;
            case R.id.ll_decor:
                showSelectDlg(2);
                break;
            case R.id.ll_house_type:
                showSelectDlg(3);
                break;
            case R.id.ll_direction:
                showSelectDlg(4);
                break;
            case R.id.ll_floor:
                showSelectDlg(5);
                break;
            case R.id.ll_parking:
                showSelectDlg(6);
                break;
            case R.id.ll_lift:
                showSelectDlg(7);
                break;
            case R.id.ll_select_dlg:
                llSelectDlg.setVisibility(View.INVISIBLE);
                break;
        }
    }

    public void showSelectDlg(int position) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        llSelectDlg.setVisibility(View.VISIBLE);
        ObjectAnimator.ofFloat(llSelectDlg, "translationY", displayMetrics.heightPixels +
                llSelectDlg.getHeight(), 0)
                .setDuration(300)
                .start();
        hsView.setCheckTab(position);
        hsView.smoothScrollToPosition(position);
    }
}
