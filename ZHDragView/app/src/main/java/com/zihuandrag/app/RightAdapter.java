package com.zihuandrag.app;

import android.content.Context;
import android.widget.TextView;

import com.zihuan.baseadapter.RecyclerAdapter;
import com.zihuan.baseadapter.RecyclerViewHolder;

public class RightAdapter extends RecyclerAdapter {
    public RightAdapter(Object object) {
        super(object);
    }

    @Override
    public void convert(RecyclerViewHolder holder, int position, Context context) {
        LeftEntity entity = (LeftEntity) baseDatas.get(position);
        TextView tv_name = (TextView) holder.getView(R.id.tv_name);
        tv_name.setText(entity.getName());
    }


    @Override
    public int getLayoutResId() {
        return R.layout.rv_right;
    }

    int pos = -1;

    public void updateDrag(int drag) {
        pos = drag;
        notifyDataSetChanged();
    }
}
