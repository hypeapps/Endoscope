package pl.hypeapp.endoscope.ui.fragment;

import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.tbruyelle.rxpermissions.RxPermissions;

import net.grandcentrix.thirtyinch.TiFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.hypeapp.endoscope.R;
import pl.hypeapp.endoscope.presenter.QrCodeScannerPresenter;
import pl.hypeapp.endoscope.ui.activity.ConnectToStreamActivity;
import pl.hypeapp.endoscope.view.QrCodeScannerView;

public class QrCodeScannerFragment extends TiFragment<QrCodeScannerPresenter, QrCodeScannerView>
        implements QrCodeScannerView, QRCodeReaderView.OnQRCodeReadListener {
    @BindView(R.id.qr_scanner_result) TextView qrCodeScannerResult;
    @BindView(R.id.error_permission) TextView permissionError;
    @BindView(R.id.permission_button) Button permissionButton;
    @BindView(R.id.button_connect) Button connectButton;
    @BindView(R.id.qrdecoderview) QRCodeReaderView qrCodeReaderView;

    @NonNull
    @Override
    public QrCodeScannerPresenter providePresenter() {
        RxPermissions rxPermissions = new RxPermissions(getActivity());
        return new QrCodeScannerPresenter(rxPermissions);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.qrcode_scanner_fragment, container, false);
        ButterKnife.bind(this, v);
        initQrCodeScanner();
        return v;
    }

    private void initQrCodeScanner() {
        qrCodeReaderView.setOnQRCodeReadListener(this);
        qrCodeReaderView.setBackCamera();
        qrCodeReaderView.setQRDecodingEnabled(true);
    }

    @Override
    public void showPermissionGrantButton() {
        permissionButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void hidePermissionGrantButton() {
        permissionButton.setVisibility(View.GONE);
    }

    @Override
    public void showPermissionNotGrantedInfo() {
        permissionError.setVisibility(View.VISIBLE);
        permissionError.setText(getString(R.string.permission_not_granted));
    }

    @Override
    public void showPermissionNeverAskInfo() {
        permissionError.setVisibility(View.VISIBLE);
        permissionError.setText(getString(R.string.permission_never_ask));
    }

    @Override
    public void hidePermissionInfo() {
        permissionError.setVisibility(View.GONE);
    }

    @OnClick(R.id.permission_button)
    public void onPermissionGrantClickButton() {
        getPresenter().askForPermission();
    }

    @Override
    public void onQRCodeRead(String text, PointF[] points) {
        getPresenter().onQrCodeRead(text);
    }

    @Override
    public void showQrCodeScannerResult(String ipAddress) {
        qrCodeScannerResult.setVisibility(View.VISIBLE);
        connectButton.setVisibility(View.VISIBLE);
        qrCodeScannerResult.setText(ipAddress);
    }

    @OnClick(R.id.button_connect)
    public void onClickConnect() {
        getPresenter().connectToStream();
    }

    @Override
    public void intentToPlayStreamActivity(String ipAddress) {
        ConnectToStreamActivity connectToStreamActivity = (ConnectToStreamActivity) getActivity();
        connectToStreamActivity.intentToPlayStreamActivity(ipAddress);
    }

    @Override
    public void startQrCodeScanner() {
        qrCodeReaderView.setVisibility(View.VISIBLE);
        qrCodeReaderView.startCamera();
    }

    @Override
    public void stopQrCodeScanner() {
        qrCodeReaderView.setVisibility(View.GONE);
        qrCodeReaderView.stopCamera();
    }
}
