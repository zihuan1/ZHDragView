package com.zihuan.recyclerview.drag;

public interface DragMultipleInterface {
    /***
     * 被拖拽View在目标区域移动
     * @param position
     */
    void actionDragLocation(int position);

    /***
     * 放开被拖拽View
     * @param value 传递的值
     * @param currentPosition 当前位置
     */
    void actionDrop(int startPosition, int currentPosition, String value);

    /***
     * 拖拽完成
     * @param currentPosition 当前位置
     */
    void actionDragEnded(int currentPosition);
}
