package cn.junecai.voa.activity;

import java.util.ArrayList;
import java.util.HashMap;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import cn.junecai.voa.buf.ClassifyAdapter;
import cn.junecai.voa.dataimp.ClassifyImp;
import cn.junecai.voa.dataimp.ClassifyTable;
import cn.junecai.voa.R;


/**
 * Created by admin on 13-11-23.
 */
@SuppressLint("NewApi")
public class AttentionFragment extends Fragment {
//    public String initContent() {
//        return "这是关注我界面";
//    }
    @SuppressLint("NewApi")
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.classify, null);
		/*
		 * 获取view 的控件
		 */
		ListView lv = (ListView) view.findViewById(R.id.classifylist);
		// ListView
		// 从本地的sqlite数据库读取
		ClassifyTable ct = new ClassifyTable(AttentionFragment.this.getActivity());
		final ClassifyImp[] classifyImps = ct.getAllUser();
		// 方法一
		// List<String> list = new ArrayList<String>();
		// 方法二
		ArrayList<HashMap<String, String>> classifyList = new ArrayList<HashMap<String, String>>();
		// 循环添加
		for (ClassifyImp c : classifyImps) {
			// list.add(u.getTitle());
			// 新建一个 HashMap
			HashMap<String, String> map = new HashMap<String, String>();
			// 每个对象的值添加到HashMap关键= >值
			String classifytitle1 = c.getClassifyTitle();
			map.put("titlebef", classifytitle1);
			// map.put(KEY_DURATION, parser.getValue(e, KEY_DURATION));
			// map.put(KEY_THUMB_URL, parser.getValue(e, KEY_THUMB_URL));
			// HashList添加到数组列表
			classifyList.add(map);
		}
		// ArrayAdapter<String> adapter = new
		// ArrayAdapter<String>(MyListFragment.this.getActivity(),android.R.layout.simple_list_item_1,list);
		// lv.setAdapter(adapter);
		// SimpleAdapter adapter = new SimpleAdapter(
		// MyListFragment.this.getActivity(),android.R.layout.simple_list_item_1,list
		// },
		// new int[] { R.id.tv10, R.id.tv20 });
		// 方法一
		// lv.setAdapter(adapter);
		// adapter.notifyDataSetChanged();
		// 方法二
		ClassifyAdapter adapter3 = new ClassifyAdapter(
				AttentionFragment.this.getActivity(), classifyList);
		lv.setAdapter(adapter3);
		adapter3.notifyDataSetChanged();
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
//				int selecttiem = arg2;
//				Intent intent = new Intent(MyListFragment.this.getActivity(),
//						TitleShow.class);
//				Log.i("selectitem", titleImps[selecttiem].getTitleurl());
//				intent.putExtra("titleurl", titleImps[selecttiem].getTitleurl());
//				Log.i("启动show", "qidong show");
//				startActivity(intent);
			}
		});
		return view;
	}
}
