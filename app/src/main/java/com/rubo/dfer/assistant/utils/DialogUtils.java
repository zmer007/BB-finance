package com.rubo.dfer.assistant.utils;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;

import com.rubo.dfer.assistant.R;
import com.rubo.dfer.assistant.callback.BaseCallback;
import com.rubo.dfer.assistant.widget.ConfirmDialog;
import com.rubo.dfer.assistant.widget.checklistdialog.CheckListDialog;
import com.rubo.dfer.assistant.widget.checklistdialog.CheckListDialog.OnClickCheckDialogListener;

import java.util.List;

/**
 * User: lgd(1973140289@qq.com)
 * Date: 2016-08-04
 * Function: 弹窗工具类
 */
public class DialogUtils {
    public static AlertDialog newAlertDialog(Context ctx, String message) {
        Builder builder = new Builder(ctx);
        builder.setMessage(message);
        return builder.create();
    }

    public static ProgressDialog newProgressDialog(Context ctx, String message) {
        ProgressDialog dialog = new ProgressDialog(ctx);
        dialog.setMessage(message);
        return dialog;
    }

    public static CheckListDialog newCheckListDialog(Context ctx, List<Float> data, OnClickCheckDialogListener callback) {
        CheckListDialog dialog = new CheckListDialog(ctx, data);
        dialog.setOnClickCheckDialogListener(callback);
        return dialog;
    }

    public static PopupMenu newPopupMenu(Context ctx, View anchorView, final BaseCallback<Integer> handler) {
        PopupMenu popupMenu = new PopupMenu(ctx, anchorView);
        popupMenu.inflate(R.menu.main_menu);
        popupMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                handler.onBack(item.getItemId());
                return true;

            }
        });
        return popupMenu;
    }

    public static ConfirmDialog newConfirmDialog(Context ctx, String msg, final BaseCallback<Boolean> handler) {
        return new ConfirmDialog(ctx, msg, handler);
    }
}
