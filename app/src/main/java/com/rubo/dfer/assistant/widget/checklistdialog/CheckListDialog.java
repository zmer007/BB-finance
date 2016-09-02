package com.rubo.dfer.assistant.widget.checklistdialog;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.rubo.dfer.assistant.R;
import com.rubo.dfer.assistant.data.entry.Check;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * User: lgd(1973140289@qq.com)
 * Date: 2016-08-29
 * Function:
 */
public class CheckListDialog extends Dialog {
    @Bind(R.id.cld_listView)
    RecyclerView listView;

    CheckListAdapter adapter;
    List<Check> data;

    public CheckListDialog(Context context, List<Float> data) {
        super(context);
        setContentView(R.layout.dialog_check_list);
        ButterKnife.bind(this);
        this.data = new ArrayList<>();
        for (Float f : data) {
            this.data.add(new Check(String.valueOf(f)));
        }
        adapter = new CheckListAdapter(this.data);
        listView.setHasFixedSize(true);
        listView.setLayoutManager(new LinearLayoutManager(context));
        listView.setAdapter(adapter);
    }

    @OnClick(R.id.cld_ok)
    void onCancelClicked() {
        dismiss();
        if (listener != null) {
            listener.onClickOk(getCheckedList());
        }
    }

    private List<Float> getCheckedList() {
        List<Float> list = new ArrayList<>();
        for (Check check : data) {
            if (check.isChecked()) {
                list.add(Float.valueOf(check.getMessage()));
            }
        }
        return list;
    }

    private OnClickCheckDialogListener listener;

    public void setOnClickCheckDialogListener(OnClickCheckDialogListener listener) {
        this.listener = listener;
    }

    public interface OnClickCheckDialogListener {
        void onClickOk(List<Float> checkValues);
    }
}
