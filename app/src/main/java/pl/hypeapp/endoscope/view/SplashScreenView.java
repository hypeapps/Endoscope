package pl.hypeapp.endoscope.view;

import net.grandcentrix.thirtyinch.TiView;
import net.grandcentrix.thirtyinch.callonmainthread.CallOnMainThread;

public interface SplashScreenView extends TiView {

    @CallOnMainThread
    void startSplashAnimation();

    @CallOnMainThread
    void intentToMainMenu();

    @CallOnMainThread
    void intentToHowToUse();
}
