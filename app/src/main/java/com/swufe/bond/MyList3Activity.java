package com.swufe.bond;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyList3Activity extends ListActivity implements Runnable,AdapterView.OnItemClickListener {
    Handler handler;
    private static final String TAG = "MyList2";
    private List<HashMap<String, String>> listItems3;//存放文字
    private SimpleAdapter listItemAdapter;//适配器
    String href3, title3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initListView();
        this.setListAdapter(listItemAdapter);
        //MyAdapter myAdapter=new MyAdapter(this,R.layout.list_item,listItems);
        //this.setListAdapter(myAdapter);


        Thread t = new Thread(this);
        t.start();

        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == 7) {
                    listItems3 = (List<HashMap<String, String>>) msg.obj;
                    listItemAdapter = new SimpleAdapter(MyList3Activity.this, listItems3,//listitems数据源
                            R.layout.activity_my_list3,//listitem的XML布局实现
                            new String[]{"ItemTitle", "ItemDetail"},
                            new int[]{R.id.itemTitle3, R.id.itemDetail3}
                    );
                    setListAdapter(listItemAdapter);
                }
                super.handleMessage(msg);
            }
        };
        getListView().setOnItemClickListener(this);
    }

    private void initListView() {
        listItems3 = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < 20;i++){            HashMap<String, String> map = new HashMap<String, String>();
            map.put("ItemTitle", "Rate:" + i);//标题文字
            map.put("ItemDetail", "detail" + i);//详细描述
            listItems3.add(map);
        }
        //生成适配器的Item和动态数组对应的元素
        listItemAdapter = new SimpleAdapter(this, listItems3,//listitems数据源
                R.layout.activity_my_list3,//listitem的XML布局实现
                new String[]{"ItemTitle", "ItemDetail"},
                new int[]{R.id.itemTitle3, R.id.itemDetail3}
        );

    }

    public void run() {
        //获取网络数据，放入list带回到主线程
        List<HashMap<String, String>> retList = new ArrayList<HashMap<String, String>>();

        Document doc = null;
        try { for(int i=1;i<=3;i++) {
            String url = "https://www.chinabond.com.cn/Channel/41498?_tp_il_02="+i;
            doc = Jsoup.connect(url).get();
            Log.i(TAG, "run: " + doc.title());
            Elements tables = doc.getElementsByTag("li").select("a");
            for (int m= 39; m< tables.size(); m++){
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("ItemTitle", title3 = tables.get(m).attr("title"));
                map.put("ItemDetail", href3 = tables.get(m).attr("href"));
                retList.add(map);

                Log.i(TAG, "run: title=" + title3 + "==>" + "https://www.chinabond.com.cn" + href3);
            }

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
        TextView title = view.findViewById(R.id.itemTitle3);
        TextView detail = view.findViewById(R.id.itemDetail3);


        String detail2 = String.valueOf(detail.getText());
        Uri uri = Uri.parse("https://www.chinabond.com.cn" + detail2);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        this.startActivity(intent);
    }
}

