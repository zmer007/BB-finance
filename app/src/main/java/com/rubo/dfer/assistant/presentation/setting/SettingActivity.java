package com.rubo.dfer.assistant.presentation.setting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.rubo.dfer.assistant.R;
import com.rubo.dfer.assistant.data.OutlayModule;
import com.rubo.dfer.assistant.data.cache.Parser;
import com.rubo.dfer.assistant.presentation.TitleActivity;
import com.rubo.dfer.assistant.utils.SystemUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * User: lgd(1973140289@qq.com)
 * Date: 2016-08-31
 * Function:
 */
public class SettingActivity extends TitleActivity {

    @Bind(R.id.as_jsonInfoView)
    TextView outlaysJson;
    @Bind(R.id.as_copy)
    View copyView;
    OutlayModule outlayModule;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        showBaseTitle();
        outlayModule = OutlayModule.getInstance();
    }

    @OnClick(R.id.as_exportView)
    void exportOutlays() {
        outlaysJson.setVisibility(View.VISIBLE);
        outlaysJson.setText(Parser.getJsonString(outlayModule.getOutlays()));
    }

    @OnClick(R.id.as_copy)
    void copyToClipboard() {
        SystemUtils.setPrimaryClipPlaintText(this, outlaysJson.getText());
    }

    @OnTextChanged(R.id.as_jsonInfoView)
    void jsonTextChange(CharSequence text) {
        String str = text.toString().trim();
        if (TextUtils.isEmpty(str)) {
            copyView.setVisibility(View.GONE);
        } else {
            copyView.setVisibility(View.VISIBLE);
        }
    }

    public static void launch(Context ctx) {
        Intent i = new Intent(ctx, SettingActivity.class);
        ctx.startActivity(i);
    }
}
