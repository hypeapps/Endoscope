package pl.hypeapp.endoscope.presenter;

import android.support.annotation.NonNull;

import net.grandcentrix.thirtyinch.TiPresenter;

import pl.hypeapp.endoscope.view.IpAddressView;

public class IpAddressPresenter extends TiPresenter<IpAddressView> {
    private String ipAddress;

    public IpAddressPresenter(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    @Override
    protected void onAttachView(@NonNull IpAddressView view) {
        super.onAttachView(view);
        setIpAddress(ipAddress);
    }

    private void setIpAddress(String ipAddress) {
        getView().setIpAddress(ipAddress);
    }
}
