package cn.junecai.voa.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.Toast;

import cn.junecai.voa.buf.TitleServicce;
import cn.junecai.voa.R;


public class MainActivity extends Activity {
        private FragmentManager fragmentManager;  
        private RadioGroup radioGroup;  
      
        @TargetApi(Build.VERSION_CODES.HONEYCOMB)  
        @Override  
        protected void onCreate(Bundle savedInstanceState) {  
            super.onCreate(savedInstanceState);  
            requestWindowFeature(Window.FEATURE_NO_TITLE);  
            setContentView(R.layout.activity);
            //读取**标题列表**进入数据库
             Intent titleservice =new Intent(this, TitleServicce.class);
            startService(titleservice);
            //读取**title内容**进入数据库
//            Intent titlecontentservice =new Intent(this, TitleContentService.class);
//            startService(titlecontentservice);
          
            fragmentManager = getFragmentManager();  
            radioGroup = (RadioGroup) findViewById(R.id.rg_tab);  
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {  
                @Override  
                public void onCheckedChanged(RadioGroup group, int checkedId) {  
                	Toast.makeText(MainActivity.this,checkedId+"  //dai", Toast.LENGTH_SHORT).show();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();  
                    Fragment fragment = FragmentFactory.getInstanceByIndex(checkedId);  
                    transaction.replace(R.id.content, fragment); 
                    transaction.addToBackStack(null);  
                    transaction.commit();  
                }  
            });  
        }  
      
    }  