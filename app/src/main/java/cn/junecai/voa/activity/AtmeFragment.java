package cn.junecai.voa.activity;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import cn.junecai.voa.R;

/**
 * Created by admin on 13-11-23.
 */
@SuppressLint("NewApi")
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class AtmeFragment extends Fragment {
	Button exit;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.atmefragment, null);
		// TODO Auto-generated method stub
		exit= (Button) view.findViewById(R.id.atmeexit);
		exit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 Intent intent = new Intent(Intent.ACTION_MAIN);  
		            intent.addCategory(Intent.CATEGORY_HOME);  
		            startActivity(intent);  
		            System.exit(0);  
				
			}
		});
		
		return view;
	}

	
}
