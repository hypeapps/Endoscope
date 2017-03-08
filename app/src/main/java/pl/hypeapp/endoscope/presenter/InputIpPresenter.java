package pl.hypeapp.endoscope.presenter;

import android.util.Patterns;

import net.grandcentrix.thirtyinch.TiPresenter;

import pl.hypeapp.endoscope.view.InputIpView;

public class InputIpPresenter extends TiPresenter<InputIpView> {

    public void validateIpAddress(String streamIpAddress) {
        if (Patterns.IP_ADDRESS.matcher(streamIpAddress).matches()) {
            getView().intentToPlayStreamActivity(streamIpAddress);
        } else {
            getView().onWrongIpAddress();
        }
    }
}
