package cn.junecai.voa.buf;

/*
 * 后台服务取得文章列表信息
 * 服务可以自己脱离活动运行，使用Context.startService()启动
 */
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

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import cn.junecai.voa.dataimp.ClassifyImp;
import cn.junecai.voa.dataimp.ClassifyTable;
import cn.junecai.voa.dataimp.TitleImp;
import cn.junecai.voa.dataimp.TitleTable;
@SuppressWarnings("deprecation")
public class TitleServicce extends Service {
//	protected final IBinder mBinder() = new localBinder();
//	public class localBinder extends Binder{
//		TitleServicce geTitleServicce(){
//			return TitleServicce.this;
//		}
//	};
	   private Thread titleGet;
	   @Override
		public void onCreate() {
			// TODO Auto-generated method stub
			super.onCreate();
			Log.i("TitleServicce", "onCreate");
			titleGet=new Thread(null, TitleRunnable, "titleget");
		}
	   @Override
		public void onStart(Intent intent, int startId) {
			// TODO Auto-generated method stub
			super.onStart(intent, startId);
			Log.i("TitleServicce", "onStart");
			titleGet.start();
		}	
		
//	   @Override
//		public int onStartCommand(Intent intent, int flags, int startId) {
//			// TODO Auto-generated method stub
//		   if (!titleGet.isAlive()) {
//				Log.i("TitleServicce", "onStart");
//				titleGet.start();
//			}
//			return super.onStartCommand(intent, flags, startId);
//			
//		}
	   @Override
		public void onDestroy() {
			// TODO Auto-generated method stub
			super.onDestroy();
			titleGet.interrupt();
		}
	   @Override
		public IBinder onBind(Intent intent) {
			// TODO Auto-generated method stub
			return null;
		}
	public void TitleServicceStart() {
		// TODO Auto-generated constructor stub
		String html = getHtmlByUrl("http://www.51voa.com/");
		if (html != null && !"".equals(html)) {
			Document doc = Jsoup.parse(html, "http://www.51voa.com/");
			Element content = doc.getElementById("list");
			Elements linksElements = content
					.getElementsByTag("li");
			System.out.println("1");
			for (Element ele : linksElements) {
				Elements link = ele.getElementsByTag("a");
				int i = link.size()-1;
				String a = ele.child(i).attr("href");
				String title = ele.text();
		
					TitleImp temp =new TitleImp();
					temp.setTitle(title);
					temp.setTitleurl(a);
					temp.setPhourl(null);
					Log.i("access", "add one titleimp");
					TitleTable titleTable = new TitleTable(this);
					titleTable.addData(temp);
			}
		}
	}

	// 获取分类列表
		public void ClassifyTitle() {
			String classifyhtml = getHtmlByUrl("http://www.51voa.com/");
			if (classifyhtml != null && !"".equals(classifyhtml)) {
				Document classifydoc = Jsoup.parse(classifyhtml,
						"http://www.51voa.com/");
				Element classifycontent = classifydoc.getElementById("left_nav");
				Elements classifylinksElements = classifycontent
						.getElementsByClass("left_nav_title");
				for (Element classifyele : classifylinksElements) {
					String classifytitle = classifyele.text();
					String classifyurl = classifyele.attr("href");
					// 存入数据库
					ClassifyImp classifyimp = new ClassifyImp();
						classifyimp.setClassifyTitle(classifytitle);
						classifyimp.setClassifyTitleurl(classifyurl);
						ClassifyTable classifyTable = new ClassifyTable(this);
						classifyTable.addData(classifyimp);
				}
			}
		}

	
	/**
	 * 根据URL获得所有的html信息
	 * 
	 * @param url
	 * @return
	 */
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
//					System.out.println(html);
				}
			}
		} catch (Exception e) {
			System.out.println("访问【" + url + "】出现异常!");
			e.printStackTrace();
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return html;
	}

	private Runnable TitleRunnable=new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			TitleServicceStart();
			ClassifyTitle();
		}
	};
	
}
