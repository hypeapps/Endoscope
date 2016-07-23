package pl.hypeapp.vrstream;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import net.majorkernelpanic.streaming.Session;
import net.majorkernelpanic.streaming.SessionBuilder;
import net.majorkernelpanic.streaming.audio.AudioQuality;
import net.majorkernelpanic.streaming.rtsp.RtspServer;
import net.majorkernelpanic.streaming.video.VideoQuality;
import net.majorkernelpanic.streaming.gl.SurfaceView;


public class StartStreamActivity extends AppCompatActivity implements Session.Callback, RtspServer.CallbackListener {


    private Session session;
    private SurfaceView surfaceView;
    private RtspServer rtspServer;
    private WiFiStateChangeReceiver wiFiStateChangeReceiver;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_start_stream);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        initViewPager();

        surfaceView = (SurfaceView) findViewById(R.id.surface);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        boolean isAudioStream = sharedPreferences.getBoolean("is_audio_stream", false);
        int videoEncoder = sharedPreferences.getInt("video_encoder", 0);
        int resolution = sharedPreferences.getInt("resolution", 2);
        int width[] = getResources().getIntArray(R.array.resolution_width);
        int height[] = getResources().getIntArray(R.array.resolution_height);


        session = SessionBuilder.getInstance()
                .setCallback(this)
                .setSurfaceView(surfaceView)
                .setPreviewOrientation(90)
                .setContext(getApplicationContext())
                .setAudioEncoder(isAudioStream ? SessionBuilder.AUDIO_AAC : SessionBuilder.AUDIO_NONE)
                .setAudioQuality(new AudioQuality(16000, 32000))
                .setVideoEncoder((videoEncoder == 0) ? SessionBuilder.VIDEO_H264 : SessionBuilder.VIDEO_H263)
                .setVideoQuality(new VideoQuality(width[resolution], height[resolution], 30 , 500000))
                .build();

        startRtspServer();

        putIpAddressToSharedPref(getIpAddress());

        wiFiStateChangeReceiver = new WiFiStateChangeReceiver();
        registerReceiver(wiFiStateChangeReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        registerReceiver(wiFiStateChangeReceiver, new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION));
    }

    private void startRtspServer() {
        rtspServer = new RtspServer();
        rtspServer.addCallbackListener(this);
        rtspServer.start();
    }

    private void putIpAddressToSharedPref(String ipAddress) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        editor.putString("ip_local", ipAddress);
        editor.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
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
        unregisterReceiver(wiFiStateChangeReceiver);
    }

    @Override
    public void onBitrateUpdate(long bitrate) {

    }

    @Override
    public void onSessionError(int reason, int streamType, Exception e) {
        logError(e.getMessage());
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
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public void onSessionStopped() {
        showAboutConnectionLayout();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
    }

    @Override
    public void onError(RtspServer server, Exception e, int error) {
        logError(e.getMessage());
    }

    @Override
    public void onMessage(RtspServer server, int message) {}

    public void coverAboutConnectionLayout(){
        LinearLayout aboutConnectionLayout = (LinearLayout) findViewById(R.id.about_connection);
        if(aboutConnectionLayout != null) aboutConnectionLayout.setVisibility(View.GONE);
    }

    public void showAboutConnectionLayout(){
        View aboutConnectionLayout =  findViewById(R.id.about_connection);
        if(aboutConnectionLayout != null) aboutConnectionLayout.setVisibility(View.VISIBLE);
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

    private String getIpAddress(){
        WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
        int ipAddress = wifiManager.getConnectionInfo().getIpAddress();
        String ipAddressFormatted = String.format("%d.%d.%d.%d", (ipAddress & 0xff), (ipAddress >> 8 & 0xff),
                (ipAddress >> 16 & 0xff), (ipAddress >> 24 & 0xff));
        return ipAddressFormatted;
    }

    private void logError(final String msg) {
        final String error = (msg == null) ? "Error unknown" : msg;
        AlertDialog.Builder builder = new AlertDialog.Builder(StartStreamActivity.this);
        builder.setTitle("Error");
        builder.setMessage(error).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(StartStreamActivity.this, MainMenuActivity.class);
                startActivity(intent);
            }
        });
        builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_BACK) {
                    Intent intent = new Intent(StartStreamActivity.this, MainMenuActivity.class);
                    startActivity(intent);
                }
                return false;
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
