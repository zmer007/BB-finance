package com.rubo.dfer.assistant.presentation.summary;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.rubo.dfer.assistant.R;
import com.rubo.dfer.assistant.callback.BaseCallback;
import com.rubo.dfer.assistant.data.OutlayModule;
import com.rubo.dfer.assistant.data.entry.Outlay;
import com.rubo.dfer.assistant.data.util.SimpleAnimationListener;
import com.rubo.dfer.assistant.data.util.TwoTuples;
import com.rubo.dfer.assistant.presentation.TitleActivity;
import com.rubo.dfer.assistant.presentation.setting.SettingActivity;
import com.rubo.dfer.assistant.utils.AnimUtils;
import com.rubo.dfer.assistant.utils.ToastUtils;
import com.rubo.dfer.assistant.widget.keyboardListenerLayout;
import com.rubo.dfer.assistant.widget.keyboardListenerLayout.SimpleKeyboardListener;
import com.rubo.dfer.assistant.widget.checklistdialog.CheckListDialog.OnClickCheckDialogListener;
import com.rubo.dfer.assistant.utils.DialogUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import butterknife.OnTextChanged;

public class SummaryActivity extends TitleActivity {

    OutlayModule outlayModule;

    @Bind(R.id.tc_leftView)
    TextView startTimeView;
    @Bind(R.id.tc_middleView)
    TextView titleView;
    @Bind(R.id.tc_leftSubView)
    TextView durationDayView;

    @Bind(R.id.as_inputPanel)
    View inputPanel;
    @Bind(R.id.lip_sendView)
    View sendView;
    @Bind(R.id.lip_editText)
    TextView outlayEdit;

    @Bind(R.id.tom_numberView)
    TextView selectedNumberView;


    @Bind(R.id.as_keyboardListenerView)
    keyboardListenerLayout keyboardListenerLayout;
    @Bind(R.id.as_outlayList)
    RecyclerView outlayList;
    OutlayListAdapter adapter;
    List<Outlay> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        ButterKnife.bind(this);

        outlayModule = OutlayModule.getInstance();
        showConsumerTitle();

        data = new ArrayList<>();
        adapter = new OutlayListAdapter(data);
        outlayList.setAdapter(adapter);
        outlayList.setLayoutManager(new LinearLayoutManager(this));
        outlayList.setHasFixedSize(true);
        refreshData();

        keyboardListenerLayout.setInterceptTouchEventEnable(true);
        keyboardListenerLayout.setOnKeyboardListener(new SimpleKeyboardListener());
    }

    private void addData(final String rawMsg) {

        final TwoTuples<List<Float>, List<Float>> valueTuple = getOutlayFromMsg(rawMsg);
        if (!valueTuple.second.isEmpty()) {
            DialogUtils.newCheckListDialog(this, valueTuple.second, new OnClickCheckDialogListener() {
                @Override
                public void onClickOk(List<Float> checkValues) {
                    adapter.setMode(OutlayListAdapter.ADD_MODE);
                    float sum = 0;
                    for (Float f : checkValues) {
                        sum += f;
                    }
                    for (Float f : valueTuple.first) {
                        sum += f;
                    }
                    outlayModule.addOutlay(new Outlay(sum, rawMsg));
                    refreshData();
                }
            }).show();
        } else {
            adapter.setMode(OutlayListAdapter.ADD_MODE);
            float sum = 0;
            for (Float f : valueTuple.first) {
                sum += f;
            }
            outlayModule.addOutlay(new Outlay(sum, rawMsg));
            refreshData();
        }
    }

    private void refreshData() {
        data.clear();
        data.addAll(outlayModule.getOutlays());
        adapter.notifyDataSetChanged();

        startTimeView.setText(outlayModule.getStartDate());
        durationDayView.setText(outlayModule.getDurationDays());
        titleView.setText("总消费：" + outlayModule.sumOutlays() + "元");
    }

    private TwoTuples<List<Float>, List<Float>> getOutlayFromMsg(String rawMsg) {
        Pattern pattern = Pattern.compile("-?\\d*\\.?\\d+");
        Matcher matcher = pattern.matcher(rawMsg);
        char[] rawChar = rawMsg.toCharArray();
        List<Float> positive = new ArrayList<>();
        List<Float> negative = new ArrayList<>();
        while (matcher.find()) {
            String mt = matcher.group().trim();
            int index = matcher.end();
            if (index < rawChar.length && rawChar[index] == '元') {
                positive.add(Float.valueOf(mt));
            } else {
                negative.add(Float.valueOf(mt));
            }
        }
        return new TwoTuples<>(positive, negative);
    }

    @OnClick(R.id.tc_leftContainer)
    void showDatePrompt() {
        Snackbar.make(keyboardListenerLayout, "于 " + startTimeView.getText() + " 开始计时，总计" + durationDayView.getText() + "天", Snackbar.LENGTH_LONG)
                .setAction("了解", new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                })
                .show();
    }

    @OnClick(R.id.tc_menuView)
    void onMenuClick(View v) {
        DialogUtils.newPopupMenu(this, v, new BaseCallback<Integer>() {
            @Override
            public void onBack(Integer back) {
                if (back == R.id.menu_reset) {
                    DialogUtils.newConfirmDialog(SummaryActivity.this, "是否要清空记录", new BaseCallback<Boolean>() {
                        @Override
                        public void onBack(Boolean back) {
                            if (back) {
                                resetData();
                            }
                        }
                    }).show();
                } else if (back == R.id.menu_sort_by_outlay) {
                    sortByOutlay();
                } else if (back == R.id.menu_sort_by_time) {
                    sortByTime();
                }
            }
        }).show();
    }

    @OnClick(R.id.lip_imgView)
    void showInputPrompt() {
        Snackbar.make(keyboardListenerLayout, "输入格式如，买书50元", Snackbar.LENGTH_LONG)
                .setAction("了解", new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                })
                .show();
    }

    private void sortByOutlay() {
        outlayModule.sortDataByOutlay();
        refreshData();
    }

    private void sortByTime() {
        outlayModule.sortDataByTime();
        refreshData();
    }

    private void resetData() {
        outlayModule.resetData();
        refreshData();
    }

    @OnClick(R.id.lip_sendView)
    void sendOutlay() {
        String inputInfo = outlayEdit.getText().toString().trim();
        if (!TextUtils.isEmpty(inputInfo)) {
            addData(inputInfo);
            outlayEdit.setText("");
        }
    }

    @OnTextChanged(R.id.lip_editText)
    void inputTextWarch(CharSequence text) {
        if (TextUtils.isEmpty(text.toString().trim())) {
            sendView.setEnabled(false);
        } else {
            sendView.setEnabled(true);
        }
    }

    @OnEditorAction(R.id.lip_editText)
    boolean send(int actionId) {
        if (actionId == EditorInfo.IME_ACTION_SEND) {
            sendOutlay();
            return true;
        }
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            SettingActivity.launch(this);
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_BACK && adapter.getMode() == OutlayListAdapter.SELECT_MODE) {
            adapter.setMode(OutlayListAdapter.NORMAL_MODE);
            showConsumerTitle();
            refreshData();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void showSelectView() {
        showOptionMenuTitle(R.anim.fade_in);
        Animation dropDown = AnimUtils.loadAnimation(R.anim.drop_down);
        dropDown.setAnimationListener(new SimpleAnimationListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                inputPanel.setVisibility(View.GONE);
            }
        });
        inputPanel.startAnimation(dropDown);
    }

    public void setSelectedNumber(int number) {
        selectedNumberView.setText(String.valueOf(number));
    }

    @OnClick(R.id.tom_backView)
    void optionMenuBackClick() {
        showConsumerTitle();
        adapter.setMode(OutlayListAdapter.NORMAL_MODE);
    }

    @OnClick(R.id.tom_gcView)
    void removeSelectedItem() {
        if (adapter.getCheckedCount() == 0) {
            ToastUtils.showToast("请选择要删除的记录");
            return;
        }
        DialogUtils.newConfirmDialog(this, "真的要删除吗？！", new BaseCallback<Boolean>() {
            @Override
            public void onBack(Boolean back) {
                if (back) {
                    List<Outlay> outlays = adapter.removeSelectedItem();
                    outlayModule.removeOutlays(outlays);
                    refreshData();
                    showConsumerTitle();
                }
            }
        }).show();
    }

    @Override
    protected void showConsumerTitle() {
        super.showConsumerTitle();
        Animation anim = AnimUtils.loadAnimation(R.anim.rise_up);
        inputPanel.setVisibility(View.VISIBLE);
        inputPanel.startAnimation(anim);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        outlayModule.saveOutlaysData();
    }
}
