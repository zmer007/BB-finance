package com.rubo.dfer.assistant.widget;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import com.rubo.dfer.assistant.R;
import com.rubo.dfer.assistant.callback.BaseCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * User: lgd(1973140289@qq.com)
 * Date: 2016-08-30
 * Function:
 */
public class ConfirmDialog extends Dialog {
    @Bind(R.id.dc_contentView)
    TextView contentView;
    private BaseCallback<Boolean> listener;

    public ConfirmDialog(Context context, String msg, BaseCallback<Boolean> listener) {
        super(context);
        setContentView(R.layout.dialog_confirm);
        ButterKnife.bind(this);
        contentView.setText(msg);
        this.listener = listener;
    }

    @OnClick(R.id.dc_cancelView)
    void onCancelClick() {
        dismiss();
        listener.onBack(false);
    }

    @OnClick(R.id.dc_okView)
    void onOkClick() {
        dismiss();
        listener.onBack(true);
    }
}
