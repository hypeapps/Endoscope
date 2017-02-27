package pl.hypeapp.endoscope.presenter;

import android.content.SharedPreferences;

import net.grandcentrix.thirtyinch.TiPresenter;
import net.majorkernelpanic.streaming.rtsp.RtspServer;

import pl.hypeapp.endoscope.view.SettingsView;

public class SettingsPresenter extends TiPresenter<SettingsView> {
    private static final String SHARED_PREF_DEFAULT_PORT = "8086";
    private static final String SHARED_PREF_RESOLUTION = "resolution";
    private static final String SHARED_PREF_VIDEO_ENCODER = "video_encoder";
    private static final int SHARED_PREF_DEF_RESOLUTION = 2;
    private static final int SHARED_PREF_DEF_VIDEO_ENCODER = 0;
    public static final String SHARED_PREF_IS_AUDIO_STREAM = "is_audio_stream";
    private final SharedPreferences sharedPreferences;
    private boolean isAudioStream;

    public SettingsPresenter(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    protected void onWakeUp() {
        super.onWakeUp();
        String port = sharedPreferences.getString(RtspServer.KEY_PORT, SHARED_PREF_DEFAULT_PORT);
        getView().setPort(port);
        int resolution = sharedPreferences.getInt(SHARED_PREF_RESOLUTION, SHARED_PREF_DEF_RESOLUTION);
        getView().setResolution(resolution);
        int videoEncoder = sharedPreferences.getInt(SHARED_PREF_VIDEO_ENCODER, SHARED_PREF_DEF_VIDEO_ENCODER);
        getView().setVideoEncoder(videoEncoder);
        isAudioStream = sharedPreferences.getBoolean(SHARED_PREF_IS_AUDIO_STREAM, true);
        getView().setAudioStream(isAudioStream);
    }

    public void onChangeResolution(int selectedPosition) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(SHARED_PREF_RESOLUTION, selectedPosition);
        editor.apply();
        getView().setResolution(selectedPosition);
    }

    public void onChangeVideoEncoder(int selectedPosition) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(SHARED_PREF_VIDEO_ENCODER, selectedPosition);
        editor.apply();
        getView().setVideoEncoder(selectedPosition);
    }

    public void onChangeAudioStream() {
        isAudioStream = !isAudioStream;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(SHARED_PREF_IS_AUDIO_STREAM, isAudioStream);
        editor.apply();
        getView().setAudioStream(isAudioStream);
    }

    public void onChangePort(String port) {
        if (port.isEmpty()) {
            getView().changePortError();
        } else {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(RtspServer.KEY_PORT, String.valueOf(port));
            editor.apply();
            getView().setPort(port);
            getView().changePortSuccessful();
        }
    }

    public void showChangeVideoEncoderDialog(CharSequence[] videoEncoders, String dialogTitle) {
        int selectedItem = sharedPreferences.getInt(SHARED_PREF_VIDEO_ENCODER, 0);
        getView().showSelectItemDialog(videoEncoders, dialogTitle, selectedItem, SHARED_PREF_VIDEO_ENCODER);
    }

    public void showChangeResolutionDialog(CharSequence[] resolutions, String dialogTitle) {
        int selectedItem = sharedPreferences.getInt(SHARED_PREF_RESOLUTION, 0);
        getView().showSelectItemDialog(resolutions, dialogTitle, selectedItem, SHARED_PREF_RESOLUTION);
    }
}
