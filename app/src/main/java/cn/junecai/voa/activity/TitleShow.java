package cn.junecai.voa.activity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import cn.junecai.voa.buf.Playmusicservice;
import cn.junecai.voa.R;

public class TitleShow extends Activity {
	 static String titlecontStr="";
	 Message msg = new Message();
	String urlString;
	static cn.junecai.voa.dataimp.TitleContentImp titleContentImp = new cn.junecai.voa.dataimp.TitleContentImp();
	private boolean isbond= false;
	TextView textViewshow;
	Handler myhHandler;
	cn.junecai.voa.buf.Playmusicservice playmusicservice;
	ProgressDialog progressDialog;
	private ImageButton  pause;//暂停
	private ImageButton  start;//开始
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		setContentView(R.layout.titleshow);
		
		ImageButton begainButton = (ImageButton)findViewById(R.id.begain);
		ImageButton startButton = (ImageButton)findViewById(R.id.start);
		ImageButton  pausButton= (ImageButton)findViewById(R.id.pause);
		
		progressDialog =ProgressDialog.show(TitleShow.this, "用力，用力加载中", "加载中，请稍后。。。");
		//初始化实体类
//		titleContentImp = new TitleContentImp();
		
		 textViewshow = (TextView) findViewById(R.id.titleshow);
		titlecontStr=new String();
		// 获取intent 的相关key
		Bundle localBundle = getIntent().getExtras();
		String titleurl = localBundle.getString("titleurl", "NO TITLE");
		Log.e("showtitle", titleurl);
		urlString = "http://www.51voa.com" + titleurl;
		Log.e("showtitle", urlString);
		//线程启动
		Thread thread = new Thread(titleShowrunaRun);
		thread.start();
		//音乐播放，绑定服务
		begainButton.setOnClickListener(new  OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!isbond) {
					Intent intentmusic= new Intent(TitleShow.this, cn.junecai.voa.buf.Playmusicservice.class);
					bindService(intentmusic, mConnection,Context.BIND_AUTO_CREATE);
			     	isbond=true;
					Log.i("绑定服务", "绑定");
				}
				if (isbond) {
					Toast.makeText(TitleShow.this, "请播放", Toast.LENGTH_SHORT).show();
				}
					
				}
				
			}
		);
		//启动服务,播放
		startButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i("启动服务", "加载");
				Toast.makeText(TitleShow.this, "播放开始", Toast.LENGTH_SHORT).show();
				playmusicservice.playUrl(titleContentImp.getMusurl());
				Log.i("启动服务", "播放");
				playmusicservice.play();
				
			}
		});
		//暂停服务，展厅播放
		pausButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(TitleShow.this, "停止", Toast.LENGTH_SHORT).show();
				playmusicservice.pause();
			}
		});
		
		//更新textviwew
		 myhHandler = new Handler(){
			public void handleMessage(android.os.Message msg) {
				super.handleMessage(msg);
				progressDialog.dismiss();
				switch (msg.what) {
				case 1:
					textViewshow.setText(titlecontStr);
					break;
				case -1:
					textViewshow.setText("暂时不支持的视频文件，请查看其他文件选项");
				break;
				default:
					textViewshow.setText("请查看其他文件选项");
					break;
				}
			};
		 };
	}
	    
	

	
	

	    private  Runnable titleShowrunaRun =new Runnable() {
	    	
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				Log.i("启动线程加载", "启动");
				titlecontStr=getcontent(urlString);
				Log.e("out.println", titlecontStr);
				msg.what=1;
				if (titlecontStr.equalsIgnoreCase(" ")) {
					msg.what = -1;
				}
				myhHandler.sendMessage(msg);
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	};
	//绑定服务连接
	private ServiceConnection mConnection =new  ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			playmusicservice=null;
			
			
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			playmusicservice=((Playmusicservice.LocalBinder)service).getService();
			
		}
	};
	
	public String getcontent(String url) {
		Log.e("showtitle", "我到了");
		Log.e("out.println", titlecontStr);
		String html = getHtmlByUrl(url);
		String titlecontent = "";
		if (html != null && !"".equals(html)) {
			Document doc1 = Jsoup.parse(html);
			cn.junecai.voa.dataimp.TitleContentImp t = new cn.junecai.voa.dataimp.TitleContentImp();
			t.setTitleurl(url);
			// 获取音乐链接
			 Element musicElement = doc1.getElementById("menubar");
			Element titleElement = doc1.getElementById("title");
		String title = titleElement.text();
				t.setTitle(title);
			 Elements musicurl1 = musicElement.getElementsByTag("a");
			 String musurl = musicurl1.attr("href");
			 titleContentImp.setMusurl(musurl);
			 t.setMusurl(musurl);
			// 获取文章
			Element content1 = doc1.getElementById("content");
			// 获取图片链接
			 Elements contentphourl = content1.getElementsByTag("img");
			 String phourl = contentphourl.attr("src");
			 t.setPhourl(phourl);
			Elements linksElements1 = content1.getElementsByTag("p");
			for (Element ele1 : linksElements1) {
				titlecontent = titlecontent + ele1.text();
			}
			t.setTitletxt(titlecontent);
			cn.junecai.voa.dataimp.TitleContent tt = new cn.junecai.voa.dataimp.TitleContent(this);
			tt.addData(t);
		}
		Log.e("content", titlecontent);
		return titlecontent;
	}

	public static String getHtmlByUrl(String url) {
		String html = null;
		@SuppressWarnings("resource")
		HttpClient httpClient = new DefaultHttpClient();// 创建httpClient对象
		HttpGet httpget = new HttpGet(url);// 以get方式请求该URL
		try {
			HttpResponse responce = httpClient.execute(httpget);// 得到responce对象
			int resStatu = responce.getStatusLine().getStatusCode();// 返回码
			if (resStatu == HttpStatus.SC_OK) {// 200正常 其他就不对
				// 获得相应实体
				HttpEntity entity = responce.getEntity();
				if (entity != null) {
					html = EntityUtils.toString(entity,"utf-8");// 获得html源代码
//					 System.out.println(html);
				}
			}
		} catch (Exception e) {
//			System.out.println("访问【" + url + "】出现异常!");
			e.printStackTrace();
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
//		Log.e("html", html);
		return html;
	}
	
}
