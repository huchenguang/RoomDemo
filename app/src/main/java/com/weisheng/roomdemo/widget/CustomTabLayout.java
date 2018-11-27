package com.weisheng.roomdemo.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.weisheng.roomdemo.R;

import java.util.ArrayList;
import java.util.List;

public class CustomTabLayout extends HorizontalScrollView implements View.OnClickListener {

    private LinearLayout llHsv;
    private DisplayMetrics outMetrics;

    public CustomTabLayout(Context context) {
        this(context, null);
    }

    public CustomTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        outMetrics = getResources().getDisplayMetrics();
        llHsv = new LinearLayout(context);
        llHsv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        llHsv.setOrientation(LinearLayout.HORIZONTAL);
        addView(llHsv);
    }

    private List<TabBean> tabBeans = new ArrayList<>();

    public void addView(String text1, String text2) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.tab_item_view, null);
        view.setTag(tabBeans.size());
        view.setLayoutParams(new LinearLayout.LayoutParams(outMetrics.widthPixels / 3,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        TextView tv1 = view.findViewById(R.id.tv_1);
        TextView tv2 = view.findViewById(R.id.tv_2);
        View line = view.findViewById(R.id.line_view);
        // 设置第一个tab的TextView是被选择的样式
        tv2.setText("请选择");
        tv1.setText(text1);
        view.setOnClickListener(this);
        tabBeans.add(new TabBean(view, false));
        llHsv.addView(view);
    }


    @Override
    public void onClick(View v) {
        int index = (int) v.getTag();
        setCheckTab(index);
        if (mTabSelectedListener != null) {
            mTabSelectedListener.onItemTabSelected(index);
        }
    }

    private OnItemTabSelectedListener mTabSelectedListener;

    public interface OnItemTabSelectedListener {
        void onItemTabSelected(int position);
    }

    public void setOnItemTabSelectedListener(OnItemTabSelectedListener listener) {
        this.mTabSelectedListener = listener;
    }

    public void setCheckTab(int index) {
        for (TabBean tabBean : tabBeans) {
            if (tabBean.isChecked) {
                tabBean.isChecked = false;
                setTabChecked(tabBean, false);
                break;
            }
        }
        TabBean tabBean = tabBeans.get(index);
        tabBean.isChecked = true;
        setTabChecked(tabBean, true);
    }

    public void setText(int index, String text) {
        ((TextView) tabBeans.get(index).view.findViewById(R.id.tv_2)).setText(text);
        tabBeans.get(index).isHasValue = true;
    }

    public int goToNextNoHasValue(int index) {
        for (int i = index + 1; i < tabBeans.size(); i++) {
            if (!tabBeans.get(i).isHasValue) {
                smoothScrollToPosition(i);
                return i;
            }
        }
        for (int i = 0; i < index; i++) {
            if (!tabBeans.get(i).isHasValue) {
                //这个还没有被选择过
                smoothScrollToPosition(i);
                return i;
            }
        }
        return -1;
    }

    public void smoothScrollToPosition(int index) {
        tabBeans.get(index).view.performClick();
        float toScaleX = 0f;
        if (index <= 1) {
            toScaleX = 0f;
        } else {
            if (tabBeans.size() - 1 == index) {
                toScaleX = tabBeans.get(index).view.getWidth() * (tabBeans.size() - 1 - 1);
            } else {
                toScaleX = tabBeans.get(index).view.getWidth() * (index - 1);
            }
        }
        smoothScrollTo((int) (getScaleX() - toScaleX > 0 ? -toScaleX : toScaleX), 0);
    }

    private void setTabChecked(TabBean tabBean, boolean check) {
        if (!check) {
            //原先被选中的要恢复原状
            tabBean.view.findViewById(R.id.line_view).setVisibility(INVISIBLE);
            ((TextView) tabBean.view.findViewById(R.id.tv_2)).setTextColor(Color
                    .parseColor("#d0d0d0"));
            tabBean.view.setBackgroundColor(Color.parseColor("#ffffff"));
        } else {
            tabBean.view.findViewById(R.id.line_view).setVisibility(VISIBLE);
            ((TextView) tabBean.view.findViewById(R.id.tv_2)).setTextColor(getResources().getColor(R
                    .color.title_red));
            tabBean.view.setBackgroundColor(Color.parseColor("#ffd7c2"));
        }

    }

    class TabBean {
        public TabBean(View view, boolean isChecked) {
            this.view = view;
            this.isChecked = isChecked;
        }

        public boolean isHasValue = false;
        public View view;
        public boolean isChecked;

        public TabBean() {
        }
    }
}
