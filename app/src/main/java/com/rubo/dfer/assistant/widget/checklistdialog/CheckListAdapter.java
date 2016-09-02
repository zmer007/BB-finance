package com.rubo.dfer.assistant.widget.checklistdialog;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.rubo.dfer.assistant.R;
import com.rubo.dfer.assistant.data.entry.Check;
import com.rubo.dfer.assistant.widget.checklistdialog.CheckListAdapter.CheckHolder;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

/**
 * User: lgd(1973140289@qq.com)
 * Date: 2016-08-29
 * Function:
 */
class CheckListAdapter extends RecyclerView.Adapter<CheckHolder> {

    List<Check> data;

    public CheckListAdapter(List<Check> data) {
        this.data = data;
    }

    @Override
    public CheckHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_check_list, parent, false);
        return new CheckHolder(view);
    }

    @Override
    public void onBindViewHolder(CheckHolder holder, int position) {
        final Check check = data.get(position);
        holder.setProperties(check);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class CheckHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.icl_checkView)
        CheckBox checkBox;
        @Bind(R.id.icl_textView)
        TextView textView;
        Check check;

        public CheckHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void setProperties(Check check) {
            this.check = check;
            textView.setText(check.getMessage());
            checkBox.setChecked(check.isChecked());
        }

        @OnCheckedChanged(R.id.icl_checkView)
        void onCheckedChanged(boolean isCheck) {
            check.setChecked(isCheck);
        }
    }
}
