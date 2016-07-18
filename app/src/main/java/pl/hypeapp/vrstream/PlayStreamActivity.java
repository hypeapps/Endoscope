package pl.hypeapp.vrstream;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import net.majorkernelpanic.streaming.rtsp.RtspServer;

import java.io.IOException;


public class PlayStreamActivity extends Activity implements SurfaceHolder.Callback, MediaPlayer.OnPreparedListener {

    private MediaPlayer mediaPlayer;
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private String TAG = "SzynaGada";
    private String ipAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_stream);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        surfaceView = (SurfaceView) findViewById(R.id.surface_play);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);

        Intent i = getIntent();
        ipAddress = i.getStringExtra("ip_connect");
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        play(ipAddress);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        surfaceHolder.removeCallback(this);
        mediaPlayer.release();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }

    private void play(String ipAddress) {
        if(mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }
        setErrorListener();
        mediaPlayer.setDisplay(surfaceHolder);
        mediaPlayer.setOnPreparedListener(this);
        try {
            Log.i(TAG, "Video URI: " + "rtsp://" + ipAddress + ":" + getPort() + "/");
            String videoUri = "rtsp://" + ipAddress + ":" + getPort() + "/";
            mediaPlayer.setDataSource(videoUri);
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getPort(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        return sharedPreferences.getString(RtspServer.KEY_PORT, "8086");
    }

    private void setErrorListener() {
        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                if(extra == MediaPlayer.MEDIA_ERROR_IO) {
                    Log.e(TAG, "MEDIA ERROR");
                }
                else if(extra == MediaPlayer.MEDIA_ERROR_SERVER_DIED) {
                    Log.e(TAG, "SERVER DIED ERROR");
                }
                else if(extra == MediaPlayer.MEDIA_ERROR_UNSUPPORTED) {
                    Log.e(TAG, "MEDIA UNSUPPORTED");
                }
                else if(extra == MediaPlayer.MEDIA_ERROR_UNKNOWN) {
                    Log.e(TAG, "MEDIA ERROR UNKOWN");
                }
                else if(extra == MediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK) {
                    Log.e(TAG, "NOT VALID PROGRESSIVE PLAYBACK");
                }
                else {
                    Log.e(TAG, String.valueOf(what));
                    Log.e(TAG, String.valueOf(extra));
                    Log.e(TAG, "ERROR UNKNOWN!");
                }
                return false;
            }
        });
    }

}
