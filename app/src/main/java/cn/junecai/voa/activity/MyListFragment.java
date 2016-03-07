package cn.junecai.voa.activity;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import cn.junecai.voa.buf.LazyAdapter;
import cn.junecai.voa.dataimp.TitleImp;
import cn.junecai.voa.dataimp.TitleTable;
import cn.junecai.voa.R;


/**
 * Created by admin on 03-11-23.
 */
public class MyListFragment extends Fragment {
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		// TODO Auto-generated method stub
    	View view=inflater.inflate(R.layout.listview, null);
    	/*
    	 * 获取view 的控件
    	 */ 
    	ListView lv=(ListView)view.findViewById(R.id.list);
//    	ListView
    	//从本地的sqlite数据库读取
		TitleTable tt = new TitleTable(MyListFragment.this.getActivity());
		final TitleImp [] titleImps = tt.getAllUser();
		//方法一
//		List<String> list = new ArrayList<String>();
		//方法二
        ArrayList<HashMap<String, String>> titleList = new ArrayList<HashMap<String, String>>();  
        //循环添加
		for(TitleImp u : titleImps){
//			list.add(u.getTitle());
			// 新建一个 HashMap  
            HashMap<String, String> map = new HashMap<String, String>();  
            //每个对象的值添加到HashMap关键= >值  
            int post=u.getTitle().indexOf("]"); //标题标注地址
            int datapos = u.getTitle().indexOf("("); // 标题时间地址
            String beftitle=  u.getTitle().substring(0, post+1);// 标题标注
            String title = u.getTitle().substring(post+1, datapos); //标题
            String datatitle = u.getTitle().substring(datapos+1,u.getTitle().length()-1);
            map.put("titlebef",beftitle);  
            map.put("title", title);  
            map.put("data",datatitle);  
//            map.put(KEY_DURATION, parser.getValue(e, KEY_DURATION));  
//            map.put(KEY_THUMB_URL, parser.getValue(e, KEY_THUMB_URL));  
  
            // HashList添加到数组列表  
            titleList.add(map); 
		}
//		ArrayAdapter<String> adapter = new ArrayAdapter<String>(MyListFragment.this.getActivity(),android.R.layout.simple_list_item_1,list);
//		lv.setAdapter(adapter);
//    	 SimpleAdapter adapter = new SimpleAdapter(
//    			 MyListFragment.this.getActivity(),android.R.layout.simple_list_item_1,list },
//                 new int[] { R.id.tv10, R.id.tv20 });
		//方法一
//         lv.setAdapter(adapter);
//         adapter.notifyDataSetChanged();
       //方法二
         LazyAdapter adapter2 = new LazyAdapter(MyListFragment.this.getActivity(),titleList);
         lv.setAdapter(adapter2);
         adapter2.notifyDataSetChanged();
         lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				int selecttiem = arg2;
				Intent intent= new Intent(MyListFragment.this.getActivity(),TitleShow.class);
				Log.i("selectitem",titleImps[selecttiem].getTitleurl() );
				intent.putExtra("titleurl", titleImps[selecttiem].getTitleurl());
//				Log.i("启动show", "qidong show");
//				Intent intent2 = new Intent(MyListFragment.this.getActivity(),TestPage.class);
				
				startActivity(intent);
			}
		});
		return view;
	}
}
