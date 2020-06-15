package com.swufe.bond;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.provider.DocumentsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.androidkun.xtablayout.XTabLayout;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class BondshowActivity extends AppCompatActivity {
    private final String TAG = "Bondshow";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bondshow);
        ViewPager viewPager = findViewById(R.id.viewpager);


        MypageAdapter pageAdapter = new MypageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pageAdapter);

        XTabLayout xTabLayout = findViewById(R.id.tabLayout);
        xTabLayout.setupWithViewPager(viewPager);


    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share,menu);
        return true;
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_set) {
            Intent list = new Intent(this, SettingActivity.class);
            startActivity(list);

        }else if(item.getItemId()==R.id.open_list) {
            //打开列表窗口
            Intent list = new Intent(this, ShareActivity.class);
            startActivityForResult(list,1);
            //测试数据库
            /*RateItem item1=new RateItem("aaaa","123");
            RateManager manager=new RateManager(this);
            manager.add(item1);
            manager.add(new RateItem("bbbm","2222"));
            Log.i(TAG, "onOptionsItemSelected: 写入数据完毕");

            //查询所有数据
            List<RateItem> testList=manager.listAll();
            for(RateItem i:testList){
                Log.i(TAG, "onOptionsItemSelected: 取出数据[id="+i.getId()+"]Name="+i.getCurName()+"Rate="+i.getCurRate());
            }*/
        }
        else if(item.getItemId()==R.id.menu_search) {
            //打开列表窗口
            Intent list = new Intent(this, SearchActivity.class);
            startActivityForResult(list,1);}
        return super.onOptionsItemSelected(item);
    }
}
    /*Handler handler;
    private static final String TAG = "MyList";
    private Vector<String> listItems;//存放文字
    private ListAdapter adapter;
    String title,href;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setListAdapter(adapter);
        //MyAdapter myAdapter=new MyAdapter(this,R.layout.list_item,listItems);
        //this.setListAdapter(myAdapter);


        Thread t = new Thread(this);
        t.start();

        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == 7) {
                    listItems = (Vector<String>) msg.obj;
                    adapter = new ArrayAdapter(MyListActivity.this, android.R.layout.simple_list_item_1, listItems);
                    setListAdapter(adapter);
                }
                super.handleMessage(msg);
            }
        };
        getListView().setOnItemClickListener(this);
    }

    public void run() {
        //获取网络数据，放入list带回到主线程
        Vector<String> retList = new Vector<String>();

        Document doc = null;
        try {
            String url = "https://www.chinabond.com.cn/cb/cn/xwgg/ggtz/zyjsgs/zytz/list.shtml";
            doc = Jsoup.connect(url).get();
            Log.i(TAG, "run: " + doc.title());
            Elements tables = doc.getElementsByTag("li").select("a");
            for(int i=24;i<tables.size();i++){
                retList.add(( title = tables.get(i).attr("title"))+"#https://www.chinabond.com.cn"+(href=tables.get(i).attr("href")). replace("..",""));
                Log.i(TAG, "run: title="+title+"==>"+"#https://www.chinabond.com.cn"+href);
            }



        } catch (IOException e) {
            e.printStackTrace();
        }
        Message msg = handler.obtainMessage(7);
        msg.obj = retList;
        handler.sendMessage(msg);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Uri uri = Uri.parse(s);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        this.startActivity(intent);
    }*/



