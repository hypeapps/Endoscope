package pl.hypeapp.endoscope.presenter;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.NonNull;

import net.grandcentrix.thirtyinch.TiPresenter;

import pl.hypeapp.endoscope.util.SettingsPreferencesUtil;
import pl.hypeapp.endoscope.view.PlayStreamView;

public class PlayStreamPresenter extends TiPresenter<PlayStreamView> {

    private String ipAddress;
    private SettingsPreferencesUtil settingsPreferencesUtil;
    private String port;

    public PlayStreamPresenter(String ipAddress, SettingsPreferencesUtil settingsPreferencesUtil) {
        this.ipAddress = ipAddress;
        this.settingsPreferencesUtil = settingsPreferencesUtil;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        port = settingsPreferencesUtil.loadPortPreference();
    }

    @Override
    protected void onAttachView(@NonNull PlayStreamView view) {
        super.onAttachView(view);
        getView().setFullscreenWindow();
    }

    public void onSurfaceDestroyed() {
        getView().releaseMediaPlayer();
    }

    public void onSurfaceCreated() {
        StringBuilder videoUrlBuilder = new StringBuilder();
        videoUrlBuilder.append("rtsp://").append(ipAddress).append(":").append(port).append("/");
        Uri videoUri = Uri.parse(videoUrlBuilder.toString());
        getView().configureMediaPlayer(videoUri);
    }

    public void onMediaPlayerPrepared(MediaPlayer mp) {
        mp.start();
    }
}
