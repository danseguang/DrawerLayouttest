package com.eagzzycsl.drawerlayouttest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.PrivateCredentialPermission;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class classify_list extends Activity {

	private static final OnClickListener Listener = null;
//	private SQLHelper dbHelper; // 数据库
	
	private List<String> data = new ArrayList<String>(); // 数组 - 放数据
	
	ListView listContent1; // 容器填东西
	
	SimpleAdapter adapter;// 自定义适配器， 将 【数组】data 里的东西 放进【容器】ListView
	

	/**
	 * @param args
	 */
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.classify_list);

		// 打开数据库后，用cursor 扫数据，然后填到　data 数组里
//		dbHelper = new SQLHelper(this, "jiujie.db", null, 1);
//		SQLiteDatabase db = dbHelper.getWritableDatabase();
//		Cursor cursor = db.query("jiujie_table", null, null, null, null, null,
//				null);
//		int i = 0;
//		if (cursor.moveToFirst()) {
//			do {
//
//				data.add(cursor.getString(cursor.getColumnIndex("sTitle")));
//				i++;
//			} while (cursor.moveToNext());
//		}
//		cursor.close();
//		db.close();


		adapter = new SimpleAdapter(this, getData(), R.layout.simpleadapter,
				new String[] { "title" }, new int[] { R.id.title });
		// //适配器装填数据 , 把刚刚填充的 data 数组里的值打印到 【自定义】 ListView 里
		listContent1 = (ListView) findViewById(R.id.listContent1);
		// listContent1.setAdapter(new ArrayAdapter<String>(this,
		// android.R.layout.simple_expandable_list_item_1,data));
		listContent1.setAdapter(adapter);
	}

	private List<Map<String, Object>> getData() {
		data.add("ceshi1");
		data.add("ceshi2");


		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for(int j = 0; j < 2;j++){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("title", data.get(j));
			
			list.add(map);
		}
		
		return list;

	}

}
