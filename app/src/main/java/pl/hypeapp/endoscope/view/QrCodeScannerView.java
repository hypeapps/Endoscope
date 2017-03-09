package pl.hypeapp.endoscope.view;

import net.grandcentrix.thirtyinch.TiView;
import net.grandcentrix.thirtyinch.callonmainthread.CallOnMainThread;

public interface QrCodeScannerView extends TiView {

    @CallOnMainThread
    void showPermissionGrantButton();

    @CallOnMainThread
    void hidePermissionGrantButton();

    @CallOnMainThread
    void showPermissionNotGrantedInfo();

    @CallOnMainThread
    void showPermissionNeverAskInfo();

    @CallOnMainThread
    void hidePermissionInfo();

    @CallOnMainThread
    void startQrCodeScanner();

    @CallOnMainThread
    void stopQrCodeScanner();

    @CallOnMainThread
    void showQrCodeScannerResult(String ipAddress);

    @CallOnMainThread
    void intentToPlayStreamActivity(String ipAddress);
}
