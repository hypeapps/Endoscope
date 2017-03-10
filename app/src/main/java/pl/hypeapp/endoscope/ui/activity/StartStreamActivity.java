package pl.hypeapp.endoscope.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.tbruyelle.rxpermissions.RxPermissions;

import net.grandcentrix.thirtyinch.TiActivity;
import net.majorkernelpanic.streaming.Session;
import net.majorkernelpanic.streaming.SessionBuilder;
import net.majorkernelpanic.streaming.audio.AudioQuality;
import net.majorkernelpanic.streaming.gl.SurfaceView;
import net.majorkernelpanic.streaming.video.VideoQuality;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.hypeapp.endoscope.R;
import pl.hypeapp.endoscope.adapter.StartStreamPagerAdapter;
import pl.hypeapp.endoscope.presenter.StartStreamPresenter;
import pl.hypeapp.endoscope.receiver.WiFiStateChangeReceiver;
import pl.hypeapp.endoscope.ui.listener.OnDotPageChangeListener;
import pl.hypeapp.endoscope.util.SettingsPreferencesUtil;
import pl.hypeapp.endoscope.view.StartStreamView;

public class StartStreamActivity extends TiActivity<StartStreamPresenter, StartStreamView> implements StartStreamView,
        SurfaceHolder.Callback, Session.Callback {
    public static final int IP_PAGE = 0;
    public static final int QR_CODE_PAGE = 1;
    public static final int NFC_PAGE = 2;
    public static final String IP_LOCAL = "ip_local";
    private Session session;
    private WiFiStateChangeReceiver wiFiStateChangeReceiver;
    private SettingsPreferencesUtil settingsPreferencesUtil;
    private ViewPager viewPager;
    @BindView(R.id.surface) SurfaceView surfaceView;
    @BindView(R.id.about_connection) View aboutConnectionView;
    @BindViews({R.id.circle_page1, R.id.circle_page2, R.id.circle_page3}) List<ImageView> dots;

    @NonNull
    @Override
    public StartStreamPresenter providePresenter() {
        return new StartStreamPresenter(new RxPermissions(this));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_stream);
        ButterKnife.bind(this);
        settingsPreferencesUtil = new SettingsPreferencesUtil(PreferenceManager.getDefaultSharedPreferences(this));
        surfaceView.getHolder().addCallback(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(wiFiStateChangeReceiver);
    }

    @Override
    public void initViewPager() {
        viewPager = (ViewPager) findViewById(R.id.about_connect_pager);
        StartStreamPagerAdapter startStreamPagerAdapter = new StartStreamPagerAdapter(getSupportFragmentManager());
        viewPager.addOnPageChangeListener(new OnDotPageChangeListener(dots));
        viewPager.setAdapter(startStreamPagerAdapter);
    }

    @Override
    public void registerReceivers() {
        wiFiStateChangeReceiver = new WiFiStateChangeReceiver();
        registerReceiver(wiFiStateChangeReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        registerReceiver(wiFiStateChangeReceiver, new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION));
    }

    @Override
    public void onNeverAskPermission() {
        logError(getString(R.string.permission_never_ask));
    }

    @Override
    public void onPermissionNotGranted() {
        logError(getString(R.string.permission_not_granted));
    }

    @Override
    public void buildSession() {
        boolean isAudioStream = settingsPreferencesUtil.loadAudioPreference();
        int videoEncoder = settingsPreferencesUtil.loadVideoEncoderPreference();
        int resolution = settingsPreferencesUtil.loadResolutionPreference();
        int width[] = getResources().getIntArray(R.array.resolution_width);
        int height[] = getResources().getIntArray(R.array.resolution_height);
        session = SessionBuilder.getInstance()
                .setCallback(this)
                .setSurfaceView(surfaceView)
                .setPreviewOrientation(90)
                .setContext(this)
                .setAudioEncoder(isAudioStream ? SessionBuilder.AUDIO_AAC : SessionBuilder.AUDIO_NONE)
                .setAudioQuality(new AudioQuality(16000, 32000))
                .setVideoEncoder((videoEncoder == 0) ? SessionBuilder.VIDEO_H264 : SessionBuilder.VIDEO_H263)
                .setVideoQuality(new VideoQuality(width[resolution], height[resolution], 20, 500000))
                .build();
    }

    @OnClick(R.id.input_ip_layout)
    public void SlideToIpPage() {
        viewPager.setCurrentItem(IP_PAGE);
    }

    @OnClick(R.id.qr_code_layout)
    public void SlideToQrCodePage() {
        viewPager.setCurrentItem(QR_CODE_PAGE);
    }

    @OnClick(R.id.nfc_layout)
    public void SlideToNfcPage() {
        viewPager.setCurrentItem(NFC_PAGE);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        getPresenter().onSurfaceCreated();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        getPresenter().onSurfaceDestroyed();
    }

    @Override
    public void onSessionError(int reason, int streamType, Exception e) {
        getPresenter().onSessionError(e);
    }

    @Override
    public void onSessionStarted() {
        getPresenter().onSessionStarted();
    }

    @Override
    public void onSessionStopped() {
        getPresenter().onSessionStopped();
    }

    @Override
    public void releaseSession() {
        session.release();
    }

    @Override
    public void showStreamConnectingToast() {
        Toast.makeText(this, getString(R.string.rtsp_connecting), Toast.LENGTH_LONG).show();
    }

    @Override
    public void setFullscreenWindow() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public void setNotFullscreenWindow() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
    }

    @Override
    public void putIpAddressToSharedPreferences() {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        editor.putString(IP_LOCAL, getLocalIpAddress());
        editor.apply();
    }

    @Override
    public void hideAboutConnectionLayout() {
        aboutConnectionView.setVisibility(View.GONE);
    }

    @Override
    public void showAboutConnectionLayout() {
        aboutConnectionView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (session.isStreaming()) {
            getPresenter().onSessionStopped();
        }
    }

    @Override
    public void logError(String msg) {
        String error = (msg == null) ? "Error unknown" : msg;
        new AlertDialog.Builder(StartStreamActivity.this)
                .setTitle(R.string.error_dialog_title)
                .setCancelable(false)
                .setMessage(error).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(StartStreamActivity.this, MainMenuActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }).setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_BACK) {
                    Intent intent = new Intent(StartStreamActivity.this, MainMenuActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                return false;
            }
        }).create().show();
    }

    private String getLocalIpAddress() {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        int ipAddress = wifiManager.getConnectionInfo().getIpAddress();
        String ipAddressFormatted = String.format(Locale.ENGLISH, "%d.%d.%d.%d", (ipAddress & 0xff), (ipAddress >> 8 & 0xff),
                (ipAddress >> 16 & 0xff), (ipAddress >> 24 & 0xff));
        return ipAddressFormatted;
    }

    @Override
    public void onPreviewStarted() {
    }

    @Override
    public void onSessionConfigured() {
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void onBitrateUpdate(long bitrate) {
    }
}
