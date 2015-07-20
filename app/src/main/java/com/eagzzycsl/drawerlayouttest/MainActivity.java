package com.eagzzycsl.drawerlayouttest;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends ActionBarActivity {
    private Toolbar toolbar_main;
    private DrawerLayout drawerLayout_main;
    private ListView listView_menu;
    private TextView textView_test;

    //分类界面融合入框架 - 前期定义（3行） By宇
    private List<String> data = new ArrayList<String>(); // 数组 - 放数据
    ListView listContent1; // 容器填东西
    SimpleAdapter adapter;// 自定义适配器， 将 【数组】data 里的东西 放进【容器】ListView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar_main = (Toolbar) this.findViewById(R.id.toolbar_main);
//        textView_test=(TextView)this.findViewById(R.id.textView_test);
        setSupportActionBar(toolbar_main);
        listView_menu = (ListView) this.findViewById(R.id.listView_menu);

        ArrayList<String> arrayList = new ArrayList<>();
        Collections.addAll(arrayList, new String[]{"hello", "world"});

        listView_menu.setAdapter(new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, arrayList));

        listView_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = ((TextView) parent.getChildAt(position - listView_menu.getFirstVisiblePosition())).getText().toString();
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
//                textView_test.setText(s);

                drawerLayout_main.closeDrawers();
            }
        });
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerLayout_main = (DrawerLayout) this.findViewById(R.id.drawerLayout_main);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout_main, toolbar_main, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mDrawerToggle.syncState();
        drawerLayout_main.setDrawerListener(mDrawerToggle);

        //分类界面融合入框架 - 主函数中（3行） By宇
        adapter = new SimpleAdapter(this, getData(), R.layout.simpleadapter, new String[] {"btn_check_off_normal_holo_light", "title" },
                new int[] {R.id.btn_check_off_normal_holo_light, R.id.title });//适配器装填数据 , 把填充的 data 数组里的值打印到 【自定义】 ListView 里
        listContent1 = (ListView) findViewById(R.id.listContent1);
        listContent1.setAdapter(adapter);
    }
    //分类界面融合入框架 - 自定义函数  By宇
    private List<Map<String, Object>> getData() {
            data.add("ceshi1");
            data.add("ceshi2");
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            for(int j = 0; j < 2;j++){
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("btn_check_off_normal_holo_light", R.drawable.btn_check_off_normal_holo_light);
                map.put("title", data.get(j));
                list.add(map);
            }
            return list;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
