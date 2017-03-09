package pl.hypeapp.endoscope.presenter;

import android.Manifest;

import com.tbruyelle.rxpermissions.Permission;
import com.tbruyelle.rxpermissions.RxPermissions;

import net.grandcentrix.thirtyinch.TiPresenter;
import net.grandcentrix.thirtyinch.rx.RxTiPresenterSubscriptionHandler;
import net.grandcentrix.thirtyinch.rx.RxTiPresenterUtils;

import pl.hypeapp.endoscope.view.QrCodeScannerView;
import rx.functions.Action1;

public class QrCodeScannerPresenter extends TiPresenter<QrCodeScannerView> {
    private static final String CAMERA_PERMISSION = Manifest.permission.CAMERA;
    private final RxTiPresenterSubscriptionHandler rxHelper = new RxTiPresenterSubscriptionHandler(this);
    private RxPermissions rxPermissions;
    private String ipAddress;

    public QrCodeScannerPresenter(RxPermissions rxPermissions) {
        this.rxPermissions = rxPermissions;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        askForPermission();
    }

    @Override
    protected void onWakeUp() {
        super.onWakeUp();
        startQrCodeCamera();
    }

    @Override
    protected void onSleep() {
        super.onSleep();
        stopQrCodeCamera();
    }

    public void askForPermission() {
        rxHelper.manageSubscription(rxPermissions.requestEach(CAMERA_PERMISSION)
                .compose(RxTiPresenterUtils.<Permission>deliverLatestToView(this))
                .subscribe(new Action1<Permission>() {
                    @Override
                    public void call(Permission permission) {
                        if (permission.granted) {
                            onPermissionGranted();
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            onShouldShowRequestPermissionRationale();
                        } else {
                            onPermissionNotGranted();
                        }
                    }
                }));
    }

    public void onQrCodeRead(String ipAddress) {
        this.ipAddress = ipAddress;
        getView().stopQrCodeScanner();
        getView().showQrCodeScannerResult(ipAddress);
    }

    public void connectToStream() {
        getView().intentToPlayStreamActivity(this.ipAddress);
    }

    private void onPermissionNotGranted() {
        getView().showPermissionNeverAskInfo();
        getView().hidePermissionGrantButton();
    }

    private void onShouldShowRequestPermissionRationale() {
        getView().showPermissionNotGrantedInfo();
        getView().showPermissionGrantButton();
    }

    private void onPermissionGranted() {
        getView().hidePermissionGrantButton();
        getView().hidePermissionInfo();
        getView().startQrCodeScanner();
    }

    private void stopQrCodeCamera() {
        getView().stopQrCodeScanner();
    }

    private void startQrCodeCamera() {
        if (rxPermissions.isGranted(CAMERA_PERMISSION)) {
            getView().startQrCodeScanner();
        }
    }
}
