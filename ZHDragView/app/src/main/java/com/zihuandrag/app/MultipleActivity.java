package com.zihuandrag.app;

import android.app.Service;
import android.os.Bundle;
import android.os.Vibrator;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.zihuan.baseadapter.ViewOnItemClick;
import com.zihuan.baseadapter.ViewOnItemLongClick;
import com.zihuan.recyclerview.drag.DragMultipleHelper;
import com.zihuan.recyclerview.drag.DragMultipleInterface;

import java.util.ArrayList;
import java.util.List;

public class MultipleActivity extends AppCompatActivity implements ViewOnItemClick, ViewOnItemLongClick, DragMultipleInterface {

    RecyclerView rvLeft, rvRight;
    LeftAdapter mLeftAdapter;
    RightAdapter mRightAdapter;
    List<LeftEntity> mLeft = new ArrayList<>();
    List<LeftEntity> mRight = new ArrayList<>();
    Vibrator vib;
    List<LeftEntity> mEntities = new ArrayList<>();
    int clickPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple);
        setTitle(R.string.app_title);
        rvLeft = findViewById(R.id.rv_left);
        rvRight = findViewById(R.id.rv_right);
        vib = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);//震动70毫秒
        mLeftAdapter = new LeftAdapter(this);
        mRightAdapter = new RightAdapter(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvLeft.setLayoutManager(manager);
        rvLeft.setAdapter(mLeftAdapter);
        LinearLayoutManager managerRight = new LinearLayoutManager(this);
        managerRight.setOrientation(LinearLayoutManager.VERTICAL);
        rvRight.setLayoutManager(managerRight);
        rvRight.setAdapter(mRightAdapter);
        mLeft.add(new LeftEntity("诗", 4, 1));
        mLeft.add(new LeftEntity("词", 5, 2));
        mLeft.add(new LeftEntity("歌", 4, 3));
        mLeft.add(new LeftEntity("赋", 4, 4));
        mLeftAdapter.update(mLeft);

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

        mDragHelper = new DragMultipleHelper(rvLeft, this);
    }


    DragMultipleHelper mDragHelper;

    @Override
    public void setOnItemClickListener(View view, int postion) {
        switch (view.getId()) {
            case R.id.rl_left:
                clickPos = postion;
                mEntities.clear();
                for (LeftEntity entity : mRight) {
                    if (entity.getId() == mLeft.get(postion).getId()) {
                        mEntities.add(entity);
                    }
                }
                mRightAdapter.update(mEntities);
                mLeftAdapter.updateDrag(postion);
                break;
            case R.id.rl_right:
                break;
        }
    }

    @Override
    public void setOnItemLongClickListener(View view, int postion) {
        RecyclerView.ViewHolder viewHolder = rvRight.getChildViewHolder(rvRight.getChildAt(postion));
        if (viewHolder.itemView.getId() == R.id.rl_right) {
            mDragHelper.startDrag(viewHolder, postion, mEntities.get(postion).getName());
            //获取系统震动服务
            vib.vibrate(70);
        }
    }

    @Override
    public void actionDragLocation(int position) {
        mLeftAdapter.updateDrag(position);
    }

    @Override
    public void actionDrop(int startPosition, int currentPosition, String value) {

//        这个数据显示有问题
        LeftEntity rentity = mEntities.get(startPosition);
        LeftEntity lentity = mLeft.get(currentPosition);

        rentity.setId(lentity.getId());
        lentity.setCount(lentity.getCount() + 1);
        mEntities.clear();
        for (LeftEntity entity : mRight) {
            if (entity.getId() == lentity.getId()) {
                mEntities.add(entity);
            }
        }
        mLeft.get(startPosition).setCount(mLeft.get(startPosition).getCount() - 1);
        mRightAdapter.update(mEntities);
        mLeftAdapter.update(mLeft);
        Toast.makeText(MultipleActivity.this, value, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void actionDragEnded(int position) {
        mLeftAdapter.updateDrag(position);
    }
}
