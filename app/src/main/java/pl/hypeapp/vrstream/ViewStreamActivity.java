package pl.hypeapp.vrstream;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.io.IOException;

import pl.hypeapp.Fragments.vrstream.StartStreamPagerAdapter;
import pl.hypeapp.Fragments.vrstream.ViewStreamPagerAdapter;


public class ViewStreamActivity extends AppCompatActivity implements SurfaceHolder.Callback, MediaPlayer.OnPreparedListener {

    MediaPlayer mediaPlayer;
    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;
    ViewPager viewPager;
    String TAG = "SzynaGada";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_stream);

        initViewPager();

        surfaceView = (SurfaceView) findViewById(R.id.surfacePlay);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);




    }

    private Activity getActivity(){ return this; }

    void initViewPager(){
        viewPager = (ViewPager)findViewById(R.id.about_connect_pager);
        ViewStreamPagerAdapter viewStreamPagerAdapter = new ViewStreamPagerAdapter(getSupportFragmentManager());

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                PagerCirclesManager.dotStatusManage(position, getActivity());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setAdapter(viewStreamPagerAdapter);
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
//        play();

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
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

    public void SlideToInputPage(View view){
        viewPager.setCurrentItem(0);
    }

    public void SlideToQrCodePage(View view){
        viewPager.setCurrentItem(1);
    }

    public void SlideToNfcPage(View view){
        viewPager.setCurrentItem(2);
    }


}

