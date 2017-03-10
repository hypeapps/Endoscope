package pl.hypeapp.endoscope.view;

import net.grandcentrix.thirtyinch.TiView;
import net.grandcentrix.thirtyinch.callonmainthread.CallOnMainThread;

public interface StartStreamView extends TiView {

    @CallOnMainThread
    void initViewPager();

    @CallOnMainThread
    void registerReceivers();

    @CallOnMainThread
    void onNeverAskPermission();

    @CallOnMainThread
    void onPermissionNotGranted();

    @CallOnMainThread
    void buildSession();

    @CallOnMainThread
    void showStreamConnectingToast();

    @CallOnMainThread
    void logError(String msg);

    @CallOnMainThread
    void putIpAddressToSharedPreferences();

    @CallOnMainThread
    void releaseSession();

    @CallOnMainThread
    void hideAboutConnectionLayout();

    @CallOnMainThread
    void showAboutConnectionLayout();

    @CallOnMainThread
    void setFullscreenWindow();

    @CallOnMainThread
    void setNotFullscreenWindow();
}
