package com.swufe.bond;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Message;
import android.provider.DocumentsContract;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.androidkun.xtablayout.XTabLayout;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
}
    /*public void Test(View v) {
        Document doc = null;
        try {
            String url = "https://www.chinabond.com.cn/cb/cn/xwgg/ggtz/zyjsgs/zytz/list.shtml";
            doc = Jsoup.connect(url).get();
            Log.i(TAG, "run: " + doc.title());
            Elements tables = doc.getElementsByTag("li").select("a");

            String title,detail;
            for (int i = 0; i < tables.size(); i ++) {
                title = tables.get(i).attr("title");
                detail = tables.get(i).attr("href");


                Log.i(TAG, "run: " + title + "==>" + detail);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /////////
    Handler handler;
    private static final String TAG = "FirstFragment1";
    private boolean isViewPrepared = false; //是否初始化完成
    private  List<String> title;
    public FirstFragment1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //将碎片的XML文件转换为视图用inflate()
        View fragmentView = inflater.inflate(R.layout.activity_first_fragment1, container, false);
        //求碎片视图中的ListView控件还是使用findViewById();
        final ListView lv = fragmentView.findViewById(R.id.listview);

        Thread t = new Thread(this);
        t.start();


        //定义适配器的目的还是为了将字符串数组与碎片中的ListView结合起来，形成新闻条目的显示。
        //第一个参数为getActivity()的原因，是因为碎片纳入活动后，ListView它就是主活动中的视图了。
        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == 7) {
                    title = (List<String>) msg.obj;
                    ArrayAdapter<String> ada = new ArrayAdapter<>(getActivity(),
                            android.R.layout.simple_list_item_1, title
                    );
                    lv.setAdapter(ada);
                }
                super.handleMessage(msg);
            }
        };
        //点击事件
        lv.setOnItemClickListener(this);
        //最后返回的是碎片形成的视图
        return fragmentView;
    }

        //生成适配器的Item和动态数组对应的元素

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
   }

    @Override
    public void run() {
        //获取网络数据，放入list带回到主线程
        List<String> retList = new ArrayList<String>();
        Document doc = null;
        try {
            String url = "https://www.chinabond.com.cn/cb/cn/xwgg/ggtz/zyjsgs/zytz/list.shtml";
            doc = Jsoup.connect(url).get();
            Log.i(TAG, "run: " + doc.title());
            Elements tables = doc.getElementsByTag("li").select("a");

            String title,detail;
            for (int i = 0; i < tables.size(); i++) {
                title = tables.get(i).attr("title");
                //detail = tables.get(i).attr("href");

                Log.i(TAG, "run: " + title );

                retList.add(title);

            }


            } catch (IOException e) {
                e.printStackTrace();

            }
        Message msg = handler.obtainMessage(7);
        msg.obj = retList;
        handler.sendMessage(msg);
    }

    */


