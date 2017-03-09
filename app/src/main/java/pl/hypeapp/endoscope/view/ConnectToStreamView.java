package pl.hypeapp.endoscope.view;

import android.content.Intent;

import net.grandcentrix.thirtyinch.TiView;
import net.grandcentrix.thirtyinch.callonmainthread.CallOnMainThread;

public interface ConnectToStreamView extends TiView {

    @CallOnMainThread
    void intentToPlayStreamActivity(String ipAddress);

    @CallOnMainThread
    void SlideToInputPage();

    @CallOnMainThread
    void SlideToNfcPage();

    @CallOnMainThread
    void SlideToQrCodePage();

    @CallOnMainThread
    void passIntentToNfcReader(Intent intent);
}
