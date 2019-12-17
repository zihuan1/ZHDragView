package com.zihuan.recyclerview.drag

import android.content.ClipData
import android.content.ClipDescription
import android.view.DragEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

/**
 * 把一个RecyclerView的Item拖拽到另一个RecyclerView中
 */
class DragMultipleHelper(var mTargetView: RecyclerView, var mDragListener: DragMultipleInterface) : View.OnDragListener {
    override fun onDrag(v: View, event: DragEvent): Boolean {
        val action = event.action
        when (action) {
            DragEvent.ACTION_DRAG_STARTED -> return event.clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)
            DragEvent.ACTION_DRAG_ENTERED -> return true
            DragEvent.ACTION_DRAG_LOCATION -> {
                val pos = getCurrentViewPosition(event)
                if (pos < 0) return true
                mDragListener.actionDragLocation(pos)
                return true
            }
            DragEvent.ACTION_DRAG_EXITED -> return true
            DragEvent.ACTION_DROP -> {
                mCurrentPosition = getCurrentViewPosition(event)
                if (mCurrentPosition < 0) return true
                val data = event.clipData
                var content = ""
                if (data != null) {
                    content = data.getItemAt(0).text.toString() //接收数据
                }
                mDragListener.actionDrop(mStartPosition, mCurrentPosition, content)
                return true
            }
            DragEvent.ACTION_DRAG_ENDED -> {
                if (mCurrentPosition < 0) return true
                mDragListener.actionDragEnded(mCurrentPosition)
                return true
            }
        }
        return false
    }

    var mStartPosition = 0
    var mCurrentPosition = 0
    fun startDrag(viewHolder: ViewHolder, startPosition: Int) {
        mStartPosition = startPosition
        //        mClickPosition = clickPosition;
        viewHolder.itemView.startDrag(null, View.DragShadowBuilder(viewHolder.itemView), null, 0)
    }

    fun startDrag(viewHolder: ViewHolder, startPosition: Int, value: String?) {
        mStartPosition = startPosition
        //        mClickPosition = clickPosition;
        val item = ClipData.Item(value) //传过去的数据
        val data = ClipData(null, arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN), item)
        viewHolder.itemView.startDrag(data, View.DragShadowBuilder(viewHolder.itemView), null, 0)
    }

    private fun getCurrentViewPosition(event: DragEvent): Int {
        val onTopOf = mTargetView.findChildViewUnder(event.x, event.y)
        return mTargetView.getChildAdapterPosition(onTopOf!!)
    }

    init {
        mTargetView.setOnDragListener(this)
    }
}