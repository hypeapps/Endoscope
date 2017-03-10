package pl.hypeapp.endoscope.presenter;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import net.glxn.qrgen.android.QRCode;
import net.grandcentrix.thirtyinch.TiPresenter;

import pl.hypeapp.endoscope.view.QrCodeView;

public class QrCodePresenter extends TiPresenter<QrCodeView> {
    private String ipAddress;

    public QrCodePresenter(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    @Override
    protected void onAttachView(@NonNull QrCodeView view) {
        super.onAttachView(view);
        generateQrCode(ipAddress);
    }

    private void generateQrCode(String ipAddress) {
        Bitmap qrCOde = QRCode.from(ipAddress).bitmap();
        getView().setQrCodeBitmap(qrCOde);
    }
}
