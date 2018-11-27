package com.weisheng.roomdemo;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.weisheng.roomdemo.widget.CustomTabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PickerRoomUtils {
    private Context context;
    private ViewPager viewPager;
    private CustomTabLayout customTabLayout;
    MainActivity mActivity;

    public PickerRoomUtils(Context context, ViewPager viewPager, CustomTabLayout customTabLayout) {
        this.context = context;
        mActivity = (MainActivity) context;
        this.viewPager = viewPager;
        this.customTabLayout = customTabLayout;
    }

    OptionsPickerView pvPerTime;

    //电压  size =index
    public void getPerTime(Context context) {

        List<String> directions = Arrays.asList("元/月", "元/日", "元/时");
        pvPerTime = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {

            }
        }).setLayoutRes(R.layout.picker_custom_layout, new CustomListener() {
            @Override
            public void customLayout(View v) {
                TextView tvCancel = v.findViewById(R.id.tv_cancel);
                tvCancel.setVisibility(View.VISIBLE);
                tvCancel.setOnClickListener(v1 -> {
                    pvPerTime.dismiss();
                });
                TextView tvFinish = v.findViewById(R.id.tv_finish);
                tvFinish.setText("确定");
                TextView tvTitle = v.findViewById(R.id.tv_title);
                tvTitle.setText("请选择租金类型");
                tvFinish.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pvPerTime.returnData();
                        pvPerTime.dismiss();
                    }
                });
            }
        })
                .setSelectOptions(0)
                .setContentTextSize(20)
                .build();
        pvPerTime.setKeyBackCancelable(false);
        pvPerTime.setPicker(directions);
        pvPerTime.show(false);
        pvPerTime.show();
    }


    OptionsPickerView pvVoltage;

    //电压  size =index
    public FrameLayout getVoltage(Context context, ViewPager viewPager, int size) {

        List<String> directions = Arrays.asList("220 V", "380 V");
        FrameLayout fl1 = new FrameLayout(context);
        fl1.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        pvVoltage = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                customTabLayout.setText(size, directions.get(options1));
                mActivity.showVoltage(directions.get(options1), options1 == 0 ? 220 : 380);
            }
        })
                .setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
                    @Override
                    public void onOptionsSelectChanged(int options1, int options2, int options3) {
                        customTabLayout.setText(size, directions.get(options1));
                        mActivity.showVoltage(directions.get(options1), options1 == 0 ? 220 : 380);
                    }
                })
                .setLayoutRes(R.layout.picker_custom_layout, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        TextView tvFinish = v.findViewById(R.id.tv_finish);
                        tvFinish.setText("确定");
                        TextView tvTitle = v.findViewById(R.id.tv_title);
                        tvTitle.setText("请选择电压");
                        tvFinish.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvVoltage.returnData();
                                if (customTabLayout.goToNextNoHasValue(size) == -1) {
                                    mActivity.llSelectDlg.setVisibility(View.INVISIBLE);
                                }
                            }
                        });
                    }
                })
                .setSelectOptions(0)
                .setOutSideCancelable(false)
                .setContentTextSize(20)
                .setDecorView(fl1)
                .build();
        pvVoltage.setKeyBackCancelable(false);
        pvVoltage.setPicker(directions);
        pvVoltage.show(false);
        fl1.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver
                .OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                fl1.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                ViewGroup.LayoutParams lp = viewPager.getLayoutParams();
                lp.height = pvVoltage.getDialogContainerLayout().getHeight();
                viewPager.setLayoutParams(lp);
            }
        });
        return fl1;
    }

    OptionsPickerView pvRentPayType;

    //押付类型
    public FrameLayout getRentPayType(Context context, int size) {
        final FrameLayout fl1 = new FrameLayout(context);
        fl1.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        List<String> list1 = Arrays.asList("押一", "押二", "押三", "押四", "押五");
        List<String> list2 = Arrays.asList("付一", "付二", "付三", "付四", "付五");

        pvRentPayType = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                customTabLayout.setText(size, list1.get(options1) + list2.get(options2));
                mActivity.showRentType(list1.get(options1) + list2.get(options2),
                        options1 + 1, options2 + 1);
            }
        })
                .setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
                    @Override
                    public void onOptionsSelectChanged(int options1, int options2, int options3) {
                        customTabLayout.setText(size, list1.get(options1) + list2.get(options2));
                        mActivity.showRentType(list1.get(options1) + list2.get(options2),
                                options1 + 1, options2 + 1);
                    }
                })
                .setLayoutRes(R.layout.picker_custom_layout, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        TextView tvFinish = v.findViewById(R.id.tv_finish);
                        tvFinish.setText("确定");
                        TextView tvTitle = v.findViewById(R.id.tv_title);
                        tvTitle.setText("请选择押付类型");
                        tvFinish.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvRentPayType.returnData();
                                if (customTabLayout.goToNextNoHasValue(size) == -1) {
                                    mActivity.llSelectDlg.setVisibility(View.INVISIBLE);
                                }
                            }
                        });
                    }
                })
                .setDecorView(fl1)
                .setOutSideCancelable(false)
                .setContentTextSize(20)
                .setSelectOptions(0, 0)
                .build();
        pvRentPayType.setKeyBackCancelable(false);
        pvRentPayType.setNPicker(list1, list2, null);
        pvRentPayType.show(false);
        return fl1;
    }

    OptionsPickerView pvDecoration;

    //装修
    public FrameLayout getDecoration(Context context, int size) {

        List<String> directions = Arrays.asList("毛坯", "简单装修", "精装修", "豪华装修", "其他");
        FrameLayout fl1 = new FrameLayout(context);
        fl1.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        pvDecoration = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                customTabLayout.setText(size, directions.get(options1));
                mActivity.showDecor(directions.get(options1));
            }
        })
                .setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
                    @Override
                    public void onOptionsSelectChanged(int options1, int options2, int options3) {
                        customTabLayout.setText(size, directions.get(options1));
                        mActivity.showDecor(directions.get(options1));
                    }
                })
                .setLayoutRes(R.layout.picker_custom_layout, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        TextView tvFinish = v.findViewById(R.id.tv_finish);
                        tvFinish.setText("确定");
                        TextView tvTitle = v.findViewById(R.id.tv_title);
                        tvTitle.setText("请选择装修类型");
                        tvFinish.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvDecoration.returnData();
                                if (customTabLayout.goToNextNoHasValue(size) == -1) {
                                    mActivity.llSelectDlg.setVisibility(View.INVISIBLE);
                                }
                            }
                        });
                    }
                })
                .setSelectOptions(1)
                .setOutSideCancelable(false)
                .setContentTextSize(20)
                .setDecorView(fl1)
                .build();
        pvDecoration.setKeyBackCancelable(false);
        pvDecoration.setPicker(directions);
        pvDecoration.show(false);
        return fl1;
    }

    OptionsPickerView pvRoom;

    //户型
    public FrameLayout getRoomPicker(Context context, int size) {
        final FrameLayout fl1 = new FrameLayout(context);
        fl1.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        final List<String> room = Arrays.asList("1室", "2室", "3室", "4室", "5室", "6室",
                "7室", "8室", "9室");
        final List<String> hall = Arrays.asList("0厅", "1厅", "2厅", "3厅", "4厅", "5厅", "6厅",
                "7厅", "8厅",
                "9厅");
        final List<String> toilet = Arrays.asList("0卫", "1卫", "2卫", "3卫", "4卫", "5卫",
                "6卫", "7卫", "8卫",
                "9卫");

        pvRoom = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                customTabLayout.setText(size, room.get(options1) + hall.get(options2) +
                        toilet.get(options3));
                mActivity.showHouseType(room.get(options1) + hall.get(options2) +
                        toilet.get(options3), options1 + 1, options2, options3);
            }
        })
                .setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
                    @Override
                    public void onOptionsSelectChanged(int options1, int options2, int options3) {
                        customTabLayout.setText(size, room.get(options1) + hall.get(options2) +
                                toilet.get(options3));
                        mActivity.showHouseType(room.get(options1) + hall.get(options2) +
                                toilet.get(options3), options1 + 1, options2, options3);
                    }
                })
                .setLayoutRes(R.layout.picker_custom_layout, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        TextView tvFinish = v.findViewById(R.id.tv_finish);
                        tvFinish.setText("确定");
                        TextView tvTitle = v.findViewById(R.id.tv_title);
                        tvTitle.setText("请选择厅室数量");
                        tvFinish.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvRoom.returnData();
                                if (customTabLayout.goToNextNoHasValue(size) == -1) {
                                    mActivity.llSelectDlg.setVisibility(View.INVISIBLE);
                                }
                            }
                        });
                    }
                })
                .setDecorView(fl1)
                .setOutSideCancelable(false)
                .setContentTextSize(20)
                .setSelectOptions(1, 0, 0)
                .build();
        pvRoom.setKeyBackCancelable(false);
        pvRoom.setNPicker(room, hall, toilet);
        pvRoom.show(false);
        return fl1;
    }

    OptionsPickerView pvRoom2;

    public FrameLayout getRoomPicker2(Context context, int size) {
        final FrameLayout fl1 = new FrameLayout(context);
        fl1.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        List<Integer> widths = new ArrayList<>();
        List<Integer> heights = new ArrayList<>();
        List<Integer> depths = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            widths.add(i);
            heights.add(i);
            depths.add(i);//长
        }

        pvRoom2 = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                customTabLayout.setText(size, "长" + depths.get(options1) + "" + "宽" + widths.get
                        (options2) + "高" + heights.get
                        (options3));
                mActivity.showHouseType("长" + depths.get(options1) + "" + "宽" + widths.get
                                (options2) + "高"
                                + heights.get(options3)
                        , options1 + 1, options2, options3);
            }
        })
                .setOptionsSelectChangeListener((options1, options2, options3) -> {
                    customTabLayout.setText(size, "长" + depths.get(options1) + "" + "宽" + widths
                            .get(options2) + "高" + heights.get
                            (options3));
                    mActivity.showHouseType("长" + depths.get(options1) + "" + "宽" + widths.get
                                    (options2) + "高"
                                    + heights.get(options3)
                            , options1 + 1, options2, options3);
                })
                .setLayoutRes(R.layout.picker_custom_layout, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        TextView tvFinish = v.findViewById(R.id.tv_finish);
                        tvFinish.setText("确定");
                        TextView tvTitle = v.findViewById(R.id.tv_title);
                        tvTitle.setText("请选择长/宽/高(米)");//宽高深
                        tvFinish.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvRoom2.returnData();
                                if (customTabLayout.goToNextNoHasValue(size) == -1) {
                                    mActivity.llSelectDlg.setVisibility(View.INVISIBLE);
                                }
                            }
                        });
                    }
                })
                .setDecorView(fl1)
                .setOutSideCancelable(false)
                .setContentTextSize(20)
                .setSelectOptions(1, 0, 0)
                .build();
        pvRoom2.setNPicker(depths, widths, heights);
        pvRoom2.setKeyBackCancelable(false);
        pvRoom2.show(false);
        return fl1;
    }

    OptionsPickerView pvDirection;

    public FrameLayout getRoomDirection(Context context, int size) {

        List<String> directions = Arrays.asList("东", "南", "西", "北", "南北", "东西", "东南", "西南", "东北",
                "西北");
        FrameLayout fl1 = new FrameLayout(context);
        fl1.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        pvDirection = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                customTabLayout.setText(size, directions.get(options1));
                mActivity.showDirection(directions.get(options1));
            }
        })
                .setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
                    @Override
                    public void onOptionsSelectChanged(int options1, int options2, int options3) {
                        customTabLayout.setText(size, directions.get(options1));
                        mActivity.showDirection(directions.get(options1));
                    }
                })
                .setLayoutRes(R.layout.picker_custom_layout, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        TextView tvFinish = v.findViewById(R.id.tv_finish);
                        tvFinish.setText("确定");
                        TextView tvTitle = v.findViewById(R.id.tv_title);
                        tvTitle.setText("请选择朝向");
                        tvFinish.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvDirection.returnData();
                                if (customTabLayout.goToNextNoHasValue(size) == -1) {
                                    mActivity.llSelectDlg.setVisibility(View.INVISIBLE);
                                }
                            }
                        });
                    }
                })
                .setSelectOptions(1)
                .setOutSideCancelable(false)
                .setContentTextSize(20)
                .setDecorView(fl1)
                .build();
        pvDirection.setKeyBackCancelable(false);
        pvDirection.setPicker(directions);
        pvDirection.show(false);
        return fl1;
    }

    OptionsPickerView pvFloor;

    public FrameLayout getFloor(Context context, int size) {
        final FrameLayout fl1 = new FrameLayout(context);
        fl1.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        List<Integer> list1 = new ArrayList<>();
        List<String> optionsList1 = new ArrayList<>();
        List<List<Integer>> list2 = new ArrayList<>();
        List<List<String>> optionsList2 = new ArrayList<>();
        for (int i = -2; i < 100; i++) {
            if (i == 0) {
                continue;
            }
            list1.add(i);
            optionsList1.add(i + "层");
            List<Integer> item = new ArrayList<>();
            List<String> item2 = new ArrayList<>();
            if (i <= 1) {
                for (int i1 = 1; i1 < 100; i1++) {
                    item.add(+i1);
                    item2.add("共" + i1 + "层");
                }
            } else {
                for (int i2 = i; i2 < 100; i2++) {
                    item.add(i2);
                    item2.add("共" + i2 + "层");
                }
            }
            list2.add(item);
            optionsList2.add(item2);
        }


        pvFloor = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                customTabLayout.setText(size, list1.get(options1) + "/" + list2.get
                        (options1).get(options2));
                mActivity.showFloor(list1.get(options1) + "/" + list2.get
                        (options1).get(options2), list1.get(options1), list2.get
                        (options1).get(options2));
            }
        })
                .setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
                    @Override
                    public void onOptionsSelectChanged(int options1, int options2, int options3) {
                        customTabLayout.setText(size, list1.get(options1) + "/" + list2.get
                                (options1).get(options2));
                        mActivity.showFloor(list1.get(options1) + "/" + list2.get
                                (options1).get(options2), list1.get(options1), list2.get
                                (options1).get(options2));
                    }
                })
                .setLayoutRes(R.layout.picker_custom_layout, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        TextView tvFinish = v.findViewById(R.id.tv_finish);
                        tvFinish.setText("确定");
                        TextView tvTitle = v.findViewById(R.id.tv_title);
                        tvTitle.setText("请选择楼层");
                        tvFinish.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvFloor.returnData();
                                if (customTabLayout.goToNextNoHasValue(size) == -1) {
                                    mActivity.llSelectDlg.setVisibility(View.INVISIBLE);
                                }
                            }
                        });
                    }
                })
                .setDecorView(fl1)
                .setOutSideCancelable(false)
                .setContentTextSize(20)
                .setSelectOptions(0, 0)
                .build();
        pvFloor.setKeyBackCancelable(false);
        pvFloor.setPicker(optionsList1, optionsList2);
        pvFloor.show(false);
        return fl1;
    }

    OptionsPickerView pvParking;

    //车位
    public FrameLayout getParking(Context context, int size) {

        List<String> directions = Arrays.asList("有车位", "无车位");
        FrameLayout fl1 = new FrameLayout(context);
        fl1.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        pvParking = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                customTabLayout.setText(size, directions.get(options1));
                mActivity.showParking(directions.get(options1), options1 == 0 ? true :
                        false);
            }
        })
                .setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
                    @Override
                    public void onOptionsSelectChanged(int options1, int options2, int options3) {
                        customTabLayout.setText(size, directions.get(options1));
                        mActivity.showParking(directions.get(options1), options1 == 0 ? true :
                                false);
                    }
                })
                .setLayoutRes(R.layout.picker_custom_layout, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        TextView tvFinish = v.findViewById(R.id.tv_finish);
                        tvFinish.setText("确定");
                        TextView tvTitle = v.findViewById(R.id.tv_title);
                        tvTitle.setText("请选择车位");
                        tvFinish.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvParking.returnData();
                                if (customTabLayout.goToNextNoHasValue(size) == -1) {
                                    mActivity.llSelectDlg.setVisibility(View.INVISIBLE);
                                }
                            }
                        });
                    }
                })
                .setSelectOptions(1)
                .setOutSideCancelable(false)
                .setContentTextSize(20)
                .setDecorView(fl1)
                .build();
        pvParking.setKeyBackCancelable(false);
        pvParking.setPicker(directions);
        pvParking.show(false);
        return fl1;
    }

    OptionsPickerView pvLift;

    //电梯
    public FrameLayout getLift(Context context, int size) {

        List<String> directions = Arrays.asList("有电梯", "无电梯");
        FrameLayout fl1 = new FrameLayout(context);
        fl1.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        pvLift = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                customTabLayout.setText(size, directions.get(options1));
                mActivity.showLift(directions.get(options1), options1 == 0 ? true :
                        false);
            }
        })
                .setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
                    @Override
                    public void onOptionsSelectChanged(int options1, int options2, int options3) {
                        customTabLayout.setText(size, directions.get(options1));
                        mActivity.showLift(directions.get(options1), options1 == 0 ? true :
                                false);
                    }
                })
                .setLayoutRes(R.layout.picker_custom_layout, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        TextView tvFinish = v.findViewById(R.id.tv_finish);
                        tvFinish.setText("确定");
                        TextView tvTitle = v.findViewById(R.id.tv_title);
                        tvTitle.setText("请选择电梯");
                        tvFinish.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvLift.returnData();
                                if (customTabLayout.goToNextNoHasValue(size) == -1) {
                                    mActivity.llSelectDlg.setVisibility(View.INVISIBLE);
                                }
                            }
                        });
                    }
                })
                .setSelectOptions(1)
                .setOutSideCancelable(false)
                .setContentTextSize(20)
                .setDecorView(fl1)
                .build();
        pvLift.setKeyBackCancelable(false);
        pvLift.setPicker(directions);
        pvLift.show(false);
        return fl1;
    }
}
