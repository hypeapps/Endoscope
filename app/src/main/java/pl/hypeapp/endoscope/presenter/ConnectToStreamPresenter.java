package pl.hypeapp.endoscope.presenter;

import android.content.Intent;

import net.grandcentrix.thirtyinch.TiPresenter;

import pl.hypeapp.endoscope.view.ConnectToStreamView;

public class ConnectToStreamPresenter extends TiPresenter<ConnectToStreamView> {

    public void onIntent(Intent intent) {
        if (getView() != null) getView().passIntentToNfcReader(intent);
    }
}
