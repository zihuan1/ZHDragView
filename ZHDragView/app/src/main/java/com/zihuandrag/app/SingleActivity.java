package com.zihuandrag.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.zihuan.baseadapter.ViewOnItemClick;
import com.zihuan.baseadapter.ViewOnItemLongClick;
import com.zihuan.recyclerview.drag.DragItemTouchHelper;

import java.util.ArrayList;
import java.util.List;

public class SingleActivity extends AppCompatActivity implements ViewOnItemClick, ViewOnItemLongClick {

    RecyclerView rvRight;
    RightAdapter mRightAdapter;
    List<LeftEntity> mRight = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single);
        setTitle(R.string.app_title);
        rvRight = findViewById(R.id.rv_right);
        LinearLayoutManager managerRight = new LinearLayoutManager(this);
        managerRight.setOrientation(LinearLayoutManager.VERTICAL);
        mRightAdapter = new RightAdapter(this);
        rvRight.setLayoutManager(managerRight);
        rvRight.setAdapter(mRightAdapter);


        mRight.add(new LeftEntity("青青子衿", 0, 1));
        mRight.add(new LeftEntity("悠悠我心", 0, 1));
        mRight.add(new LeftEntity("纵我不往", 0, 1));
        mRight.add(new LeftEntity("子不嗣音", 0, 1));

        mRight.add(new LeftEntity("大江东去", 0, 2));
        mRight.add(new LeftEntity("浪淘尽", 0, 2));
        mRight.add(new LeftEntity("千古风流人物", 0, 2));
        mRight.add(new LeftEntity("故垒西边人道是", 0, 2));
        mRight.add(new LeftEntity("周郎赤壁", 0, 2));

        mRight.add(new LeftEntity("回眸一笑百媚生", 0, 3));
        mRight.add(new LeftEntity("六宫粉黛无颜色", 0, 3));
        mRight.add(new LeftEntity("一骑红尘妃子笑", 0, 3));
        mRight.add(new LeftEntity("无人知是荔枝来", 0, 3));

        mRight.add(new LeftEntity("渔舟唱晚", 0, 4));
        mRight.add(new LeftEntity("响穷彭蠡之滨", 0, 4));
        mRight.add(new LeftEntity("雁阵惊寒", 0, 4));
        mRight.add(new LeftEntity("声断衡阳之阜", 0, 4));
        mRightAdapter.update(mRight);
        itemTouchHelper = new ItemTouchHelper(new DragItemTouchHelper(mRightAdapter, mRightAdapter.baseDatas));
        itemTouchHelper.attachToRecyclerView(rvRight);
    }

    ItemTouchHelper itemTouchHelper;

    @Override
    public void setOnItemClickListener(View view, int postion) {
        switch (view.getId()) {
            case R.id.rl_left:
                break;
            case R.id.rl_right:
                break;
        }
    }


    @Override
    public void setOnItemLongClickListener(View view, int postion) {
        itemTouchHelper.startDrag(rvRight.getChildViewHolder(rvRight.getChildAt(postion)));
    }
}
