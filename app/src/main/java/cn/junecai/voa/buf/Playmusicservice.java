package cn.junecai.voa.buf;

import java.io.IOException;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class Playmusicservice extends Service implements 
	OnBufferingUpdateListener, OnPreparedListener {
 /*
  * 音乐播放服务，实现后台服务的播放
  *和播放页通过-绑定服务-的方式实现控制
  */
	 public MediaPlayer mediaPlayer;  
//	private ImageButton  pause;//暂停
//	private ImageButton  start;//开始
	private final IBinder mBinder =new LocalBinder();
	  
	//LocalBinder 内部类
	public class  LocalBinder extends Binder {
	   	public Playmusicservice getService() {
			return Playmusicservice.this;

		}
		
	}
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		mediaPlayer = new MediaPlayer();
		  try {  
	            mediaPlayer = new MediaPlayer();  
	            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);  
	            mediaPlayer.setOnBufferingUpdateListener( this);  
	            mediaPlayer.setOnPreparedListener(this);  
	        } catch (Exception e) {  
	            Log.e("mediaPlayer", "error", e);  
	        }  
	          
		return mBinder;
	}
	  //*****************************************************  
    
    public void play()  
    {  
        mediaPlayer.start();  
    }  
      
    public void playUrl(String videoUrl)  
    {  
        try {  
            mediaPlayer.reset();  
            mediaPlayer.setDataSource(videoUrl);  
            mediaPlayer.prepare();//prepare之后自动播放  
            //mediaPlayer.start();  
        } catch (IllegalArgumentException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (IllegalStateException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
    }  
  
      
    public void pause()  
    {  
        mediaPlayer.pause();  
    }  
      
    public void stop()  
    {  
        if (mediaPlayer != null) {   
            mediaPlayer.stop();  
            mediaPlayer.release();   
            mediaPlayer = null;   
        }   
    }  
  
      
    /**  
     * 通过onPrepared播放  
     */  
    public void onPrepared(MediaPlayer arg0) {  
        arg0.start();  
        Log.e("mediaPlayer", "onPrepared");  
    }  
  
    public void onCompletion(MediaPlayer arg0) {  
        Log.e("mediaPlayer", "onCompletion");  
    }

	@Override
	public void onBufferingUpdate(MediaPlayer mp, int percent) {
		// TODO Auto-generated method stub
		
	}  
	

}
