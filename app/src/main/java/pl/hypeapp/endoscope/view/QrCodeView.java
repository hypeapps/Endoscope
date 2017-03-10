package pl.hypeapp.endoscope.view;

import android.graphics.Bitmap;

import net.grandcentrix.thirtyinch.TiView;
import net.grandcentrix.thirtyinch.callonmainthread.CallOnMainThread;

public interface QrCodeView extends TiView {

    @CallOnMainThread
    void setQrCodeBitmap(Bitmap qrCodeBitmap);
}
