package pl.hypeapp.endoscope.view;

import net.grandcentrix.thirtyinch.TiView;
import net.grandcentrix.thirtyinch.callonmainthread.CallOnMainThread;

public interface SettingsView extends TiView {

    @CallOnMainThread
    void setResolution(int item);

    @CallOnMainThread
    void onChangeResolution();

    @CallOnMainThread
    void setVideoEncoder(int item);

    @CallOnMainThread
    void onChangeVideoEncoder();

    @CallOnMainThread
    void setAudioStream(boolean isAudioStream);

    @CallOnMainThread
    void onChangeAudioStream();

    @CallOnMainThread
    void showSelectItemDialog(CharSequence[] items, String title, int selectedItem, final String option);

    @CallOnMainThread
    void setPort(String port);

    @CallOnMainThread
    void onChangePortClick();

    @CallOnMainThread
    void changePortError();

    @CallOnMainThread
    void changePortSuccessful();
}
