package com.rubo.dfer.assistant.presentation.summary;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.TextView;

import com.rubo.dfer.assistant.R;
import com.rubo.dfer.assistant.callback.BaseCallback;
import com.rubo.dfer.assistant.data.entry.Outlay;
import com.rubo.dfer.assistant.presentation.summary.OutlayListAdapter.OutlayHolder;
import com.rubo.dfer.assistant.utils.SpanUtils;
import com.rubo.dfer.assistant.utils.TimeFormatUtils;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * User: lgd(1973140289@qq.com)
 * Date: 2016-08-29
 * Function:
 */
public class OutlayListAdapter extends RecyclerView.Adapter<OutlayHolder> {
    public static final int NORMAL_MODE = 0;
    public static final int SELECT_MODE = 1;
    public static final int ADD_MODE = 2;

    List<Outlay> data;
    Map<Integer, Boolean> checkedMark;
    Context context;

    private int mode = NORMAL_MODE;

    public OutlayListAdapter(List<Outlay> data) {
        this.data = data;
        checkedMark = new HashMap<>();
    }

    @Override
    public OutlayHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_outlay, parent, false);
        return new OutlayHolder(view);
    }

    @Override
    public void onBindViewHolder(final OutlayHolder holder, int position) {
        final Outlay outlay = data.get(position);
        holder.setProperties(outlay, mode, checkedMark.get(position));

        holder.setMarkedChangedListener(markChangedListener);

        holder.itemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mode == SELECT_MODE) {
                    holder.toggle();
                }
            }
        });

        holder.itemView.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mode == NORMAL_MODE) {
                    mode = SELECT_MODE;
                    checkedMark.clear();
                    holder.setChecked(true);
                    notifyDataSetChanged();
                    if (v.getContext() instanceof SummaryActivity) {
                        ((SummaryActivity) v.getContext()).showSelectView();
                    }
                    return true;
                }
                return false;
            }
        });

        if (mode == ADD_MODE && position == 0) {
            setAnimation(holder.itemView);
            mode = NORMAL_MODE;
        }
    }

    public void setMode(int mode) {
        this.mode = mode;
        notifyDataSetChanged();
    }

    public int getMode() {
        return mode;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onViewDetachedFromWindow(OutlayHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.clearAnimation();
    }

    private void setAnimation(View viewToAnimate) {
        Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(), R.anim.item_translate_right);
        viewToAnimate.startAnimation(animation);
    }

    public List<Outlay> removeSelectedItem() {
        List<Outlay> removed = new ArrayList<>();
        for (Entry<Integer, Boolean> e : checkedMark.entrySet()) {
            if (e.getValue()) {
                int position = e.getKey();
                if (position < data.size()) {
                    removed.add(data.get(position));
                }
            }
        }
        checkedMark.clear();
        setMode(NORMAL_MODE);
        data.removeAll(removed);
        return removed;
    }

    private BaseCallback<Entry<Integer, Boolean>> markChangedListener = new BaseCallback<Entry<Integer, Boolean>>() {
        @Override
        public void onBack(Entry<Integer, Boolean> back) {
            checkedMark.put(back.getKey(), back.getValue());
            if (context instanceof SummaryActivity) {
                ((SummaryActivity) context).setSelectedNumber(getCheckedCount());
            }
        }
    };

    public int getCheckedCount() {
        int result = 0;
        for (Boolean b : checkedMark.values()) {
            if (b) {
                result++;
            }
        }
        return result;
    }

    static class OutlayHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.io_rootView)
        View rootView;
        @Bind(R.id.io_selectBox)
        CheckBox checkBox;
        @Bind(R.id.io_outlayView)
        TextView outlayView;
        @Bind(R.id.io_timeView)
        TextView timeView;
        @Bind(R.id.io_totalConsumeView)
        TextView consumeView;

        BaseCallback<Entry<Integer, Boolean>> markChangedListener;

        public OutlayHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void setMarkedChangedListener(BaseCallback<Entry<Integer, Boolean>> listener) {
            markChangedListener = listener;
        }

        void setProperties(Outlay outlay, int mode, Boolean isChecked) {
            if (mode == SELECT_MODE) {
                checkBox.setVisibility(View.VISIBLE);
                setChecked(isChecked == null ? false : isChecked);
            } else {
                checkBox.setVisibility(View.GONE);
                rootView.setBackgroundColor(itemView.getResources().getColor(R.color.white));
            }
            outlayView.setText(outlay.summary);
            timeView.setText(TimeFormatUtils.getFormatTimeString(outlay.time, System.currentTimeMillis()));
            SpanUtils.setTextViewSpan(outlayView, "-?\\d*\\.?\\d+");
            consumeView.setText(String.format(Locale.CHINA, "%.2f元", outlay.outlay));
        }

        void toggle() {
            setChecked(!checkBox.isChecked());
        }

        void setChecked(boolean checked) {
            checkBox.setChecked(checked);
            if (checked) {
                rootView.setBackgroundColor(itemView.getResources().getColor(R.color.grayDark));
            } else {
                rootView.setBackgroundColor(itemView.getResources().getColor(R.color.white));
            }

            if (markChangedListener != null) {
                markChangedListener.onBack(new SimpleEntry<>(getAdapterPosition(), checked));
            }
        }

        void clearAnimation() {
            itemView.clearAnimation();
        }
    }
}
