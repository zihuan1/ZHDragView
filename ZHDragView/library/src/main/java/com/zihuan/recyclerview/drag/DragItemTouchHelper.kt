package com.zihuan.recyclerview.drag

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import java.util.*

/**
 * 在单个RecyclerView中拖拽Item
 */
class DragItemTouchHelper : ItemTouchHelper.Callback {
    var mAdapter: RecyclerView.Adapter<*>
    var mList: List<*>
    var mTouchInterface: DragItemTouchInterface? = null

    constructor(adapter: RecyclerView.Adapter<*>, list: List<*>) {
        mAdapter = adapter
        mList = list
    }

    /**
     * @param adapter
     * @param list
     * @param touchInterface 回调接口
     */
    constructor(adapter: RecyclerView.Adapter<*>, list: List<*>, touchInterface: DragItemTouchInterface?) {
        mAdapter = adapter
        mList = list
        mTouchInterface = touchInterface
    }

    //  设置是否滑动时间，以及拖拽的方向
//  如果是列表布局的话则拖拽方向有DOWN和UP，
//  如果是网格布局的话有DOWN和UP和LEFT和RIGHT4个方向
    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: ViewHolder): Int {
        return if (recyclerView.layoutManager is GridLayoutManager) {
            val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            val swipeFlags = 0
            makeMovementFlags(dragFlags, swipeFlags)
        } else {
            val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
            val swipeFlags = 0
            makeMovementFlags(dragFlags, swipeFlags)
        }
    }

    //    正在拖动中
    override fun onMove(recyclerView: RecyclerView, viewHolder: ViewHolder, target: ViewHolder): Boolean { //得到当拖拽的viewHolder的Position
        val fromPosition = viewHolder.adapterPosition
        //拿到当前拖拽到的item的viewHolder
        val toPosition = target.adapterPosition
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(mList, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(mList, i, i - 1)
            }
        }
        mAdapter.notifyItemMoved(fromPosition, toPosition)
        return true
    }

    //拖拽完成
    override fun onSwiped(viewHolder: ViewHolder, direction: Int) {}

    //    选中view
    override fun onSelectedChanged(viewHolder: ViewHolder?, actionState: Int) {
        super.onSelectedChanged(viewHolder, actionState)
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE && mTouchInterface != null) mTouchInterface!!.onSelectedChanged(viewHolder, actionState)
    }

    //    放开view
    override fun clearView(recyclerView: RecyclerView, viewHolder: ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        if (mTouchInterface != null) mTouchInterface!!.clearView(recyclerView, viewHolder)
    }

    //重写拖拽不可用
    override fun isLongPressDragEnabled(): Boolean {
        return false
    }
}