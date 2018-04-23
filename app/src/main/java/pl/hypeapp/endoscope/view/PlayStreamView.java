package pl.hypeapp.endoscope.view;

import android.net.Uri;

import net.grandcentrix.thirtyinch.TiView;
import net.grandcentrix.thirtyinch.callonmainthread.CallOnMainThread;

public interface PlayStreamView extends TiView {

    @CallOnMainThread
    void setFullscreenWindow();

    @CallOnMainThread
    void configureMediaPlayer(Uri videoUri);

    @CallOnMainThread
    void logError(String msg);

    @CallOnMainThread
    void releaseMediaPlayer();
}
