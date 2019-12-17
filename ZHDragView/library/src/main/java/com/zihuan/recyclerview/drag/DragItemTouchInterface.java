package com.zihuan.recyclerview.drag;

import androidx.recyclerview.widget.RecyclerView;

public interface DragItemTouchInterface {

    /**
     * 拖拽选中
     *
     * @param viewHolder
     * @param actionState
     */
    void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState);

    /**
     * 拖拽完成
     *
     * @param recyclerView
     * @param viewHolder
     */
    void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder);
}
