package com.zihuan.recyclerview.drag;

public interface DragListenerInterface {
    /***
     * 被拖拽View在目标区域移动
     * @param position
     */
    void actionDragLocation(int position);

    // 放开被拖拽View value 是传递的值
    void actionDrop(int startPosition, int currentPosition, String value);

    // 拖拽完成
    void actionDragEnded(int currentPosition);
}
