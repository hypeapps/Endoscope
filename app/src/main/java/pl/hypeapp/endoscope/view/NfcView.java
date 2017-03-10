package pl.hypeapp.endoscope.view;

import net.grandcentrix.thirtyinch.TiView;
import net.grandcentrix.thirtyinch.callonmainthread.CallOnMainThread;

public interface NfcView extends TiView {

    @CallOnMainThread
    void initNfcAdapter();
}
