package com.zihuan.recyclerview.drag;

import android.content.ClipData;
import android.content.ClipDescription;
import android.support.v7.widget.RecyclerView;
import android.view.DragEvent;
import android.view.View;

public class DragListenerHelper implements View.OnDragListener {

    RecyclerView mTargetView;
    DragListenerInterface mDragListener;

    private DragListenerHelper() {
    }

    public DragListenerHelper(RecyclerView targetView, DragListenerInterface dragListener) {
        mTargetView = targetView;
        mDragListener = dragListener;
        mTargetView.setOnDragListener(this);
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        final int action = event.getAction();
        switch (action) {
            case DragEvent.ACTION_DRAG_STARTED: // 拖拽开始
                return event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN);
            case DragEvent.ACTION_DRAG_ENTERED: // 被拖拽View进入目标区域
                return true;
            case DragEvent.ACTION_DRAG_LOCATION: // 被拖拽View在目标区域移动
                int pos = getCurrentViewPosition(event);
                if (pos < 0) return true;
                mDragListener.actionDragLocation(pos);
                return true;
            case DragEvent.ACTION_DRAG_EXITED: // 被拖拽View离开目标区域
                return true;
            case DragEvent.ACTION_DROP: // 放开被拖拽View
                mCurrentPosition = getCurrentViewPosition(event);
                if (mCurrentPosition < 0) return true;
                ClipData data = event.getClipData();
                String content = "";
                if (data != null) {
                    content = data.getItemAt(0).getText().toString(); //接收数据
                }
                mDragListener.actionDrop(mStartPosition, mCurrentPosition, content);
                return true;
            case DragEvent.ACTION_DRAG_ENDED: // 拖拽完成
                if (mCurrentPosition < 0) return true;
                mDragListener.actionDragEnded(mCurrentPosition);
                return true;
        }
        return false;
    }

    int mStartPosition;
    int mCurrentPosition;

    public void startDrag(RecyclerView.ViewHolder viewHolder, int startPosition) {
        mStartPosition = startPosition;
//        mClickPosition = clickPosition;
        viewHolder.itemView.startDrag(null, new View.DragShadowBuilder(viewHolder.itemView), null, 0);
    }

    public void startDrag(RecyclerView.ViewHolder viewHolder, int startPosition, String value) {
        mStartPosition = startPosition;
//        mClickPosition = clickPosition;

        ClipData.Item item = new ClipData.Item(value);  //传过去的数据
        ClipData data = new ClipData(null, new String[]{ClipDescription.MIMETYPE_TEXT_PLAIN}, item);
        viewHolder.itemView.startDrag(data, new View.DragShadowBuilder(viewHolder.itemView), null, 0);
    }


    private int getCurrentViewPosition(DragEvent event) {
        View onTopOf = mTargetView.findChildViewUnder(event.getX(), event.getY());
        int position = mTargetView.getChildAdapterPosition(onTopOf);
        return position;
    }
}
