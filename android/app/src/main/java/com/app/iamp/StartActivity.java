package com.app.iamp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class StartActivity extends AppCompatActivity implements NormalAdapter.OnItemClicked {

    private RecyclerView recyclerView;
    private String[] data = {
            "react native quick start",
            "museum local",
            "museum http",
            "navigation",
            "eee",
            "fff",
            "aaa",
            "bbb",
            "ccc",
            "ddd",
            "eee",
            "aaa",
            "bbb",
            "ccc",
            "ddd",
            "eee",
            "aaa",
            "bbb",
            "ccc",
            "ddd",
            "eee"
    };

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        JsBundleLoader.INSTANCE.load(getApplication());

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        //设置Adapter
        NormalAdapter adapter = new NormalAdapter(data);
        adapter.setOnClick(this);
        recyclerView.setAdapter(adapter);
        //设置分隔线
//        recyclerView.addItemDecoration( new DividerGridItemDecoration(this ));
        //设置增加或删除条目的动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onItemClick (int position) {
        Log.i("Start", "Click on " + position + " with data \"" + data[position] + "\"");
        if (position == 0) {
            JsBundleLoader.INSTANCE
                    .setLoadStatus(true) //load the bundle from assets
                    .setBundleName("guide")
                    .setModuleName("InAppMiniProgram");
            Intent intent = new Intent(this, ReactBaseActivity.class);
            startActivity(intent);
        } else if (position == 1) {
            JsBundleLoader.INSTANCE
                    .setLoadStatus(true) //load the bundle from assets
                    .setBundleName("museum")
                    .setModuleName("museum");

            Intent intent = new Intent(this, ReactBaseActivity.class);
            startActivity(intent);
        } else if (position == 2) {
            JsBundleLoader.INSTANCE
                    .setLoadStatus(false) //load the bundle from web
                    .setBundleName("museum")
                    .setModuleName("museum")
                    .loadHttpBundle(StartActivity.this.getApplication(), "https://config.xa306.com/museum.android.bundle",
                            new JsBundleLoader.DownloadListener() {
                                @Override
                                public void onFinish () {
                                    Intent intent = new Intent(StartActivity.this, ReactBaseActivity.class);
                                    startActivity(intent);
                                }

                                @Override
                                public void onStart () {

                                }
                            });
        } else if (position == 3) {
            JsBundleLoader.INSTANCE
                    .setLoadStatus(true) //load the bundle from assets
                    .setBundleName("navigation")
                    .setModuleName("navigation");

            Intent intent = new Intent(this, ReactBaseActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}
