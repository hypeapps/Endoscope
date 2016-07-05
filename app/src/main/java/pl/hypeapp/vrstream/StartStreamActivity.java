package pl.hypeapp.vrstream;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import net.majorkernelpanic.streaming.Session;
import net.majorkernelpanic.streaming.SessionBuilder;
import net.majorkernelpanic.streaming.audio.AudioQuality;
import net.majorkernelpanic.streaming.rtsp.RtspServer;
import net.majorkernelpanic.streaming.video.VideoQuality;
import net.majorkernelpanic.streaming.gl.SurfaceView;

import pl.hypeapp.Fragments.vrstream.HowToUsePagerAdapter;
import pl.hypeapp.Fragments.vrstream.StartStreamPagerAdapter;


public class StartStreamActivity extends AppCompatActivity
        implements Session.Callback, RtspServer.CallbackListener
//          SurfaceHolder.Callback,
//        RtspServer.CallbackListener

{

    String TAG = "szynaGada";

    private Session session;
    private SurfaceView surfaceView;
    private RtspServer rtspServer;

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_start_stream);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        initViewPager();

        surfaceView = (SurfaceView) findViewById(R.id.surface);

        WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
        int ipAddress = wifiManager.getConnectionInfo().getIpAddress();
        Log.i("info", "ip address " + String.format("%d.%d.%d.%d", (ipAddress & 0xff), (ipAddress >> 8 & 0xff),
                (ipAddress >> 16 & 0xff), (ipAddress >> 24 & 0xff)));


        session = SessionBuilder.getInstance()
                .setCallback(this)
                .setSurfaceView(surfaceView)
                .setPreviewOrientation(90)
                .setContext(getApplicationContext())
                .setAudioEncoder(SessionBuilder.AUDIO_NONE)
                .setAudioQuality(new AudioQuality(16000, 32000))
                .setVideoEncoder(SessionBuilder.VIDEO_H264)
                .setVideoQuality(new VideoQuality(320,240,30,500000))
                .build();


        rtspServer = new RtspServer();
        rtspServer.addCallbackListener(this);
        rtspServer.start();
        Log.i(TAG, "ONCREATE");



    }

    void initViewPager(){
        viewPager = (ViewPager)findViewById(R.id.about_connect_pager);

        StartStreamPagerAdapter startStreamPagerAdapter = new StartStreamPagerAdapter(getSupportFragmentManager());

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
        viewPager.setAdapter(startStreamPagerAdapter);
    }

    private Activity getActivity(){
        return this;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        session.release();
        rtspServer.stop();
    }

    @Override
    public void onBitrateUpdate(long bitrate) {

    }

    @Override
    public void onSessionError(int reason, int streamType, Exception e) {
        Log.i(TAG, "sError " +  reason);
    }

    @Override
    public void onPreviewStarted() {

    }

    @Override
    public void onSessionConfigured() {

    }

    @Override
    public void onSessionStarted() {
        coverAboutConnectionLayout();
        Log.i(TAG, "sStarted");
    }

    @Override
    public void onSessionStopped() {
        showAboutConnectionLayout();
        Log.i(TAG, "sStopped");
    }

    @Override
    public void onError(RtspServer server, Exception e, int error) {
        Log.i(TAG, "rERROR " + error);
    }

    @Override
    public void onMessage(RtspServer server, int message) {
        Log.i(TAG, "rSTART " + message);
    }

    public void coverAboutConnectionLayout(){
        LinearLayout aboutConnectionLayout = (LinearLayout) findViewById(R.id.about_connection);
        if(aboutConnectionLayout != null) aboutConnectionLayout.setVisibility(View.GONE);
    }

    public void showAboutConnectionLayout(){
        View aboutConnectionLayout =  findViewById(R.id.about_connection);
        if(aboutConnectionLayout != null) aboutConnectionLayout.setVisibility(View.VISIBLE);
    }

//

//    @Override
//    public void onBitrateUpdate(long bitrate) {
//        Log.d(TAG,"Bitrate: " + bitrate);
//    }
//
//    @Override
//    public void onSessionError(int reason, int streamType, Exception e) {
//        Log.e(TAG, "An error occured " + e.getMessage());
//    }
//
//    @Override
//    public void onPreviewStarted() {
//        Log.d(TAG,"Preview started.");
//    }
//
//    @Override
//    public void onSessionConfigured() {
//        Log.d("szynaGada","Preview configured.");
//        // Once the stream is configured, you can get a SDP formated session description
//        // that you can send to the receiver of the stream.
//        // For example, to receive the stream in VLC, store the session description in a .sdp file
//        // and open it with VLC while streming.
//        Log.d("szynaGada", " session desc " + session.getSessionDescription());
//        session.start();
//    }
//
//    @Override
//    public void onSessionStarted() {
//        Log.d(TAG,"Streaming session started.");
//        ImageView ig = (ImageView) findViewById(R.id.imageView11);
//        ig.setVisibility(View.INVISIBLE);
//    }
//
//    @Override
//    public void onSessionStopped() {
//        Log.d(TAG,"Streaming session stopped.");
//    }
//
//    @Override
//    public void surfaceCreated(SurfaceHolder holder) {
//        session.startPreview();
//    }
//
//    @Override
//    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
//
//    }
//
//    @Override
//    public void surfaceDestroyed(SurfaceHolder holder) {
//        session.stop();
//    }
//
//
//    @Override
//    public void onError(RtspServer server, Exception e, int error) {
//            Log.d(TAG, e.getMessage() +  " error number: " + Integer.toString(error));
//    }
//
//    @Override
//    public void onMessage(RtspServer server, int message) {
//
//    }
//
//    private void logError(final String msg) {
//        final String error = (msg == null) ? "Error unknown" : msg;
//        AlertDialog.Builder builder = new AlertDialog.Builder(StartStreamActivity.this);
//        builder.setMessage(error).setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int id) {}
//        });
//        AlertDialog dialog = builder.create();
//        dialog.show();
//    }
}
