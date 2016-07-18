package pl.hypeapp.vrstream;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;


public class PlayStreamActivity extends Activity implements SurfaceHolder.Callback, MediaPlayer.OnPreparedListener {

    private MediaPlayer mediaPlayer;
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private String TAG = "SzynaGada";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_stream);

        surfaceView = (SurfaceView) findViewById(R.id.surface_play);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        play();

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

    private void play() {
        if(mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }
        setErrorListener();
        mediaPlayer.setDisplay(surfaceHolder);
        mediaPlayer.setOnPreparedListener(this);
        try {
            String videoUri = "rtsp://192.168.1.5:8086/";
            Log.i(TAG, "Video URI: " + videoUri);
            mediaPlayer.setDataSource(videoUri);
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
