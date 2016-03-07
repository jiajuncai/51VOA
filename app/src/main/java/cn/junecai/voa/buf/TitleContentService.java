package cn.junecai.voa.buf;

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

import cn.junecai.voa.dataimp.TitleContent;
import cn.junecai.voa.dataimp.TitleContentImp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class TitleContentService extends Service{

//	  public TitleContentService() {
//	        super("TitleContentService");//调用父类有参构造函数。这里我们手动给服务起个名字为：MyIntentService
//	        // TODO Auto-generated constructor stub
//	    }

	   private Thread titlecontentGet;
	   @Override
		public void onCreate() {
			// TODO Auto-generated method stub
			super.onCreate();
			Log.i("TitleContentServicce", "onCreate");
			titlecontentGet=new Thread(null, TitleContentRunnable, "titlecontentGet");
		}
	   @SuppressWarnings("deprecation")
	@Override
		public void onStart(Intent intent, int startId) {
			// TODO Auto-generated method stub
			super.onStart(intent, startId);
			Log.i("TitleContentServicce", "onStart");
			titlecontentGet.start();
		}	
		
	   @Override
		public void onDestroy() {
			// TODO Auto-generated method stub
			super.onDestroy();
			titlecontentGet.interrupt();
		}
	   @Override
		public IBinder onBind(Intent intent) {
			// TODO Auto-generated method stub
			return null;
		}
	public void TitleContentServicceStart() {
		// TODO Auto-generated constructor stub
		String html = getHtmlByUrl("http://www.51voa.com/");
		if (html != null && !"".equals(html)) {
			Document doc = Jsoup.parse(html, "http://www.51voa.com/");
			Element content = doc.getElementById("list");
			Elements linksElements = content.getElementsByTag("li");
			for (Element ele : linksElements) {
				Elements link = ele.getElementsByTag("a");
				int i = link.size()-1;
				String a = ele.child(i).attr("href");
				String title = ele.text();
		
				TitleContentImp t =new TitleContentImp();
				t.setTitle(title);
				Log.i("titleshow", "加载内容");
				t.setTitleurl(a);
				t.setPhourl(null);
				String contenthtml = getHtmlByUrl("http://www.51voa.com"+a);
				if (contenthtml != null && !"".equals(contenthtml)) {
					String titlecontent="";
					Document doc1 = Jsoup.parse(contenthtml);
					//获取音乐链接
					Element musicElement = doc1.getElementById("menubar");
					Elements musicurl = musicElement.getElementsByTag("a");
					String Musurl=musicurl.attr("href");
					//获取文章
					Element content1 = doc1.getElementById("content");
					//获取图片链接
					Elements contentphourl = content1.getElementsByTag("img");
					String phourl = contentphourl.attr("src");
					Elements linksElements1 =content1.getElementsByTag("p");
					for(Element ele1 : linksElements1){
						titlecontent = titlecontent+ele1.text()+"\n"+"我的天啊";
					}
					t.setPhourl(phourl);
					t.setTitletxt(titlecontent);
					t.setMusurl(Musurl);
				}
//				contentImage
				
					Log.i("TitleContentService", "titlecontentadd");
					TitleContent titlecontent = new TitleContent(this);
					titlecontent.addData(t);
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
					html = EntityUtils.toString(entity);// 获得html源代码
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

	private Runnable TitleContentRunnable = new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				TitleContentServicceStart();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	};
}
