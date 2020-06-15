package com.swufe.bond;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

public class SearchActivity extends AppCompatActivity implements Runnable,AdapterView.OnItemClickListener,TextWatcher {
    private static final String TAG="SearchActivity";
    private EditText in;
    private ListView out;
    private String data[];
    private Handler handler;
    private SimpleAdapter listItemAdapter;//适配器
    String href, title;
    String href2, title2;
    String href3, title3;
    String href4, title4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //获取SP中的数据
        SharedPreferences sp = getSharedPreferences("mybond", Activity.MODE_PRIVATE);
        int count = sp.getInt("recordCount", 0);
        data = new String[count];
        for (int n = 0; n < count; n++) {
            data[n] = sp.getString("" + n, "");
            Log.i(TAG, "onCreate:data:" + data[n].toString());
        }

        Thread thread = new Thread(this);
        thread.start();

        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == 7) {
                    Vector<String> data_list = (Vector<String>) msg.obj;

                    //将新的公告保存到SP中
                    SharedPreferences sp = getSharedPreferences("mybond", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    data = new String[data_list.size()];
                    for (int m = 0; m < data_list.size(); m++) {
                        data[m] = data_list.get(m);
                        editor.putString("" + m, data_list.get(m));
                    }
                    editor.putInt("recordCount", data_list.size());
                    editor.commit();

                    Toast.makeText(SearchActivity.this, "Data has updated", Toast.LENGTH_SHORT).show();
                }
                super.handleMessage(msg);
            }
        };
        out = findViewById(R.id.resultList);
        in = findViewById(R.id.keyWord);
        in.addTextChangedListener(this);
    }

    public void run(){
        Log.i(TAG,"run:run()....");
        List<HashMap<String, String>> retList = new ArrayList<HashMap<String, String>>();
        Vector<String> list=new Vector<String>();

        Document doc = null;
        try {
            for(int i=1;i<=10;i++) {
                String url = "https://www.chinabond.com.cn/cb/cn/xwgg/ggtz/zyjsgs/zytz/list_"+i+".shtml";
                doc = Jsoup.connect(url).get();
                Log.i(TAG, "run: " + doc.title());
                Elements tables = doc.getElementsByTag("li").select("a");

                for (int m = 24; m < tables.size(); m++) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("ItemTitle", title = tables.get(m).attr("title"));
                    map.put("ItemDetail", href = tables.get(m).attr("href"));

                    retList.add(map);
                    list.add(tables.get(m).attr("title")+"#https://www.chinabond.com.cn"+(tables.get(m).attr("href")).replace("..",""));

                    Log.i(TAG, "run: title=" + title + "==>" + "https://www.chinabond.com.cn" + href);
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            String url = "https://www.chinabond.com.cn/cb/cn/xwgg/ggtz/zyjsgs/zytz/list.shtml";
            doc = Jsoup.connect(url).get();
            Log.i(TAG, "run: " + doc.title());
            Elements tables = doc.getElementsByTag("li").select("a");
            for (int i = 24; i < tables.size(); i++) {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("ItemTitle", title2 = tables.get(i).attr("title"));
                map.put("ItemDetail", href2 = tables.get(i).attr("href"));
                retList.add(map);

                Log.i(TAG, "run: title=" + title2 + "==>" + "#https://www.chinabond.com.cn" + href2);
            }


        } catch (IOException e) {
            e.printStackTrace();
        } try { for(int i=1;i<=3;i++) {
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
        try {
            for(int i=1;i<=3;i++) {
                String url = "https://www.chinabond.com.cn/cb/cn/yjfx/hgfx/list_"+i+".shtml";
                doc = Jsoup.connect(url).get();
                Log.i(TAG, "run: " + doc.title());
                Elements tables = doc.getElementsByTag("li").select("a");

                for (int m = 31; m < tables.size(); m++) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("ItemTitle", title4 = tables.get(m).attr("title"));
                    map.put("ItemDetail", href4= tables.get(m).attr("href"));
                    retList.add(map);

                    Log.i(TAG, "run: title=" + title4 + "==>" + "https://www.chinabond.com.cn" + href4);
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        //获取Message对象，用于返回主线程
        Message msg=handler.obtainMessage(7);
        //msg.obj="Hello from run()";
        msg.obj=list;
        handler.sendMessage(msg);

    }

    //实现监听方法
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView title = view.findViewById(R.id.itemTitle);
        TextView detail = view.findViewById(R.id.itemDetail);


        String detail2 = String.valueOf(detail.getText());
        Uri uri = Uri.parse("https://www.chinabond.com.cn" + detail2);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        this.startActivity(intent);
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        List<HashMap<String,String>> dataList=new ArrayList<HashMap<String, String>>();
        int lag=0;
        for(int j=0;j<data.length;j++){
            if(data[j].indexOf(s.toString())!=-1){
                lag=1;
                HashMap<String,String> map=new HashMap<String,String>();
                map.put("ItemTitle",data[j].substring(0,data[j].indexOf("#")));
                map.put("ItemDetail",data[j].substring(data[j].indexOf("#")+1));
                dataList.add(map);
            }
        }
        if(lag==1){
            listItemAdapter=new SimpleAdapter(SearchActivity.this,dataList,//listItems数据簿
                    R.layout.activity_my_list,//listItem的XML布局实现
                    new String[]{"ItemTitle","ItemDetail"},
                    new int[]{R.id.itemTitle,R.id.itemDetail}
            );
            out.setAdapter(listItemAdapter);
            out.setOnItemClickListener(SearchActivity.this);
        }
        else {
            Toast.makeText(SearchActivity.this,"Sorry!No information containing the keyword ",Toast.LENGTH_SHORT).show();
        }
    }
}
