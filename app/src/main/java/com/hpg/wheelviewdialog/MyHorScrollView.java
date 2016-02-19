package com.hpg.wheelviewdialog;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by paul on 16/2/18.
 */
public class MyHorScrollView extends HorizontalScrollView {
    private List<String> mList = new ArrayList<>();
    private LinearLayout mRootView;
    private Context mContext;
    private int mItemWidth;
    private int mCurrentPosition = 0;

    public MyHorScrollView(Context context) {
        super(context);
        init(context);
    }

    public MyHorScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyHorScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        mRootView = new LinearLayout(context);
        mRootView.setOrientation(LinearLayout.HORIZONTAL);
        this.addView(mRootView);
    }

    public void setData(List<String> list) {
        mList = list;
        mList.add(0, "");
        mList.add("");
        for (int i = 0; i < mList.size(); i++) {
            View view = createItemView(mList.get(i), i);
            mRootView.addView(view);
        }
    }

    private View createItemView(final String item, final int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        final View view = inflater.inflate(R.layout.item_view, null);
        TextView textView = (TextView) view.findViewById(R.id.text);
        final LinearLayout layout = (LinearLayout) view.findViewById(R.id.layout);
        layout.post(new Runnable() {
            @Override
            public void run() {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) layout.getLayoutParams();
                params.width = MainActivity.width / 3;
                params.height = 100;
                layout.setLayoutParams(params);
                mItemWidth = getViewMeasuredHeight(view);
            }
        });


        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                MyHorScrollView.this.post(new Runnable() {
                    @Override
                    public void run() {
                        if (position != 0 && position != mList.size() - 1) {
                            mCurrentPosition = position;
                            scrollToSelection();
                        }
                    }
                });
            }
        });
        textView.setText(item);
        return view;
    }

    private int getViewMeasuredHeight(View view) {
        int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, View.MeasureSpec.AT_MOST);
        view.measure(width, expandSpec);
        return view.getMeasuredWidth();
    }

    public void setSelection(final int position) {
        MyHorScrollView.this.post(new Runnable() {
            @Override
            public void run() {
                mCurrentPosition = position;
                scrollTo((mCurrentPosition - 1) * mItemWidth, 0);
                refresh();
            }
        });
    }

    private void scrollToSelection() {
        smoothScrollTo((mCurrentPosition - 1) * mItemWidth, 0);
        refresh();
    }

    private void refresh() {
        Log.e("HPG", "count=" + mRootView.getChildCount());
        for (int i = 0; i < mRootView.getChildCount(); i++) {
            View view = mRootView.getChildAt(i);
            TextView itemView = (TextView) view.findViewById(R.id.text);
            if (null == itemView) {
                return;
            }

            if (i < mCurrentPosition) {
                view.findViewById(R.id.left).setVisibility(View.VISIBLE);
                view.findViewById(R.id.right).setVisibility(View.GONE);
            }
            if (i> mCurrentPosition){
                view.findViewById(R.id.left).setVisibility(View.GONE);
                view.findViewById(R.id.right).setVisibility(View.VISIBLE);
            }
            if (mCurrentPosition == i) {
                itemView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                itemView.setTextColor(Color.parseColor("#FF07A9FA"));
            } else {
                itemView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                itemView.setTextColor(Color.parseColor("#FF999999"));
            }

            if (i == 0 || i == mRootView.getChildCount()-1||i== mCurrentPosition) {
                view.findViewById(R.id.left).setVisibility(View.GONE);
                view.findViewById(R.id.right).setVisibility(View.GONE);
            }
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);


    }
    interface OnTabClickListener{
        public void onTabClick();
    }
}
