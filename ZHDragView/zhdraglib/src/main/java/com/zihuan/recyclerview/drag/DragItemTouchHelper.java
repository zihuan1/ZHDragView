package com.zihuan.recyclerview.drag;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import java.util.Collections;
import java.util.List;

public class DragItemTouchHelper extends ItemTouchHelper.Callback {

    RecyclerView.Adapter mAdapter;
    List mList;
    DragItemTouchInterface mTouchInterface;

    public DragItemTouchHelper(RecyclerView.Adapter adapter, List list) {
        mAdapter = adapter;
        mList = list;
    }

    /**
     * @param adapter
     * @param list
     * @param touchInterface 回调接口
     */
    public DragItemTouchHelper(RecyclerView.Adapter adapter, List list, DragItemTouchInterface touchInterface) {
        mAdapter = adapter;
        mList = list;
        mTouchInterface = touchInterface;
    }

    //  设置是否滑动时间，以及拖拽的方向
    //  如果是列表布局的话则拖拽方向有DOWN和UP，
    //  如果是网格布局的话有DOWN和UP和LEFT和RIGHT4个方向
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            final int swipeFlags = 0;
            return makeMovementFlags(dragFlags, swipeFlags);
        } else {
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            final int swipeFlags = 0;
            return makeMovementFlags(dragFlags, swipeFlags);
        }
    }

    //    正在拖动中
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        //得到当拖拽的viewHolder的Position
        int fromPosition = viewHolder.getAdapterPosition();
        //拿到当前拖拽到的item的viewHolder
        int toPosition = target.getAdapterPosition();
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(mList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(mList, i, i - 1);
            }
        }
        mAdapter.notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    //拖拽完成
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
    }

    //    选中view
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE && mTouchInterface != null)
            mTouchInterface.onSelectedChanged(viewHolder, actionState);
    }

    //    放开view
    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        if (mTouchInterface != null)
            mTouchInterface.clearView(recyclerView, viewHolder);
    }

    //重写拖拽不可用
    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

}
