package com.zihuan.recyclerview.drag;

import android.support.v7.widget.RecyclerView;

public interface DragItemTouchInterface {
//    选中的回调
    void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState);
//    完成的回调
    void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder);
}
