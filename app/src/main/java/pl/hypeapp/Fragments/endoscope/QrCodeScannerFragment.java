package pl.hypeapp.fragments.endoscope;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.dlazaro66.qrcodereaderview.QRCodeReaderView;


import pl.hypeapp.endoscope.ConnectToStreamActivity;
import pl.hypeapp.endoscope.R;


public class QrCodeScannerFragment extends Fragment implements
        QRCodeReaderView.OnQRCodeReadListener, View.OnClickListener {
    private QRCodeReaderView qrCodeReaderView;
    private View resultContainer;
    private TextView qrCodeIpTextView;
    private TextView permissionTextAlert;
    private Button buttonConnect;
    private Button permissionButton;
    private String ipAddress;
    private final static int MY_PERMISSIONS_REQUEST_CAMERA = 69;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.qrcode_scanner_fragment, container, false);

        qrCodeReaderView = (QRCodeReaderView) v.findViewById(R.id.qrdecoderview);

        resultContainer = v.findViewById(R.id.result_container);
        qrCodeIpTextView = (TextView) v.findViewById(R.id.qr_scanner_result);
        permissionTextAlert = (TextView) v.findViewById(R.id.error_permission);
        buttonConnect = (Button) v.findViewById(R.id.connect);
        buttonConnect.setOnClickListener(this);

        permissionButton = (Button) v.findViewById(R.id.permission_button);
        permissionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestPermission();
            }
        });
        requestPermission();
        return v;
    }

    public void requestPermission(){
//        if (ContextCompat.checkSelfPermission(getActivity(),
//                Manifest.permission.CAMERA)
//                != PackageManager.PERMISSION_GRANTED) {
//
//            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
//                    Manifest.permission.CAMERA)) {
//                Log.e("log3", "log3");
//                permissionButton.setVisibility(View.VISIBLE);
//                permissionTextAlert.setVisibility(View.VISIBLE);
//                ActivityCompat.requestPermissions(getActivity(),
//                        new String[]{Manifest.permission.CAMERA},
//                        MY_PERMISSIONS_REQUEST_CAMERA);
//            } else {
//                Log.e("log4", "log4");
//                permissionButton.setVisibility(View.VISIBLE);
//                permissionTextAlert.setVisibility(View.VISIBLE);
//                ActivityCompat.requestPermissions(getActivity(),
//                        new String[]{Manifest.permission.CAMERA},
//                        MY_PERMISSIONS_REQUEST_CAMERA);
//
//            }
//        }else{
//            Log.e("else", "else");
//        }

        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CAMERA},
                    MY_PERMISSIONS_REQUEST_CAMERA);
        }else{
            qrCodeReaderView.setVisibility(View.VISIBLE);
            permissionButton.setVisibility(View.GONE);
            permissionTextAlert.setVisibility(View.INVISIBLE);
            qrCodeReaderView.startCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(qrCodeReaderView != null) {
                        Log.e("log1", "log1");

                        qrCodeReaderView.setVisibility(View.VISIBLE);
                        permissionButton.setVisibility(View.INVISIBLE);
                        permissionTextAlert.setVisibility(View.INVISIBLE);
                        qrCodeReaderView.startCamera();
//                        ActivityCompat.requestPermissions(getActivity(),
//                        new String[]{Manifest.permission.CAMERA},
//                        MY_PERMISSIONS_REQUEST_CAMERA);
                    }
                } else {
                    Log.e("log2", "log2");
                    permissionButton.setVisibility(View.VISIBLE);
                    permissionTextAlert.setVisibility(View.VISIBLE);
                    qrCodeReaderView.setVisibility(View.GONE);
//                    ActivityCompat.requestPermissions(getActivity(),
//                            new String[]{Manifest.permission.CAMERA},
//                            MY_PERMISSIONS_REQUEST_CAMERA);
                }
                return;
            }
        }
    }

//    public void requestPermission(){
//        if (ContextCompat.checkSelfPermission(getActivity(),
//                Manifest.permission.CAMERA)
//                != PackageManager.PERMISSION_GRANTED) {
//
//            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
//                    Manifest.permission.CAMERA)) {
//                Log.e("log3", "log3");
//                qrCodeReaderView.setVisibility(View.GONE);
//                permissionTextAlert.setVisibility(View.VISIBLE);
//                permissionTextAlert.setText(getString(R.string.camera_not_found));
//                permissionButton.setVisibility(View.VISIBLE);
//                ActivityCompat.requestPermissions(getActivity(),
//                        new String[]{Manifest.permission.CAMERA},
//                        MY_PERMISSIONS_REQUEST_CAMERA);
//            } else {
//                Log.e("log4", "log4");
//                ActivityCompat.requestPermissions(getActivity(),
//                        new String[]{Manifest.permission.CAMERA},
//                        MY_PERMISSIONS_REQUEST_CAMERA);
//            }
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode) {
//            case MY_PERMISSIONS_REQUEST_CAMERA: {
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    if(qrCodeReaderView != null) {
//                        Log.e("log1", "log1");
//                        permissionTextAlert.setVisibility(View.GONE);
//                        permissionButton.setVisibility(View.GONE);
//                        qrCodeReaderView.setVisibility(View.VISIBLE);
//                        qrCodeReaderView.setOnQRCodeReadListener(this);
//                    }
//                } else {
//                    Log.e("log2", "log2");
//                    qrCodeReaderView.setVisibility(View.GONE);
//                    permissionTextAlert.setVisibility(View.VISIBLE);
//                    permissionTextAlert.setText(getString(R.string.camera_not_found));
//                    permissionButton.setVisibility(View.VISIBLE);
//                }
//                return;
//            }
//        }
//    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
        qrCodeReaderView.stopCamera();
    }

    @Override
    public void onQRCodeRead(String text, PointF[] points) {
        ipAddress = text;
        if((qrCodeReaderView != null) && (resultContainer != null) && (qrCodeIpTextView != null)) {
            qrCodeReaderView.setVisibility(View.GONE);
            resultContainer.setVisibility(View.VISIBLE);
            qrCodeIpTextView.setText("IP " + ipAddress);
        }
    }

    @Override
    public void onClick(View view) {
        ConnectToStreamActivity connectToStreamActivity = (ConnectToStreamActivity) getActivity();
        connectToStreamActivity.connectStream(ipAddress);
    }
}
