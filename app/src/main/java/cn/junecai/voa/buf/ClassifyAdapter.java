package cn.junecai.voa.buf;

import java.util.ArrayList;
import java.util.HashMap;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import cn.junecai.voa.R;

public class ClassifyAdapter extends BaseAdapter {

	private Activity activity;
	private ArrayList<HashMap<String, String>> data;
	private static LayoutInflater inflater = null;

	public ClassifyAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
		activity = a;
		data = d;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		return data.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null)
			vi = inflater.inflate(R.layout.classifylistraw, null);

		TextView title = (TextView) vi.findViewById(R.id.title); // 标题

		HashMap<String, String> song = new HashMap<String, String>();
		song = data.get(position);

		// 设置ListView的相关值
		title.setText(song.get("titlebef"));
		return vi;
	}
}
