package pl.hypeapp.Fragments.vrstream;

import android.content.Context;
import android.graphics.PointF;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.google.zxing.BarcodeFormat;

import pl.hypeapp.vrstream.R;


public class QrCodeScannerFragment extends Fragment
{


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.qrcode_scanner_fragment, container, false);



        return v;
    }


//    @Override
//    public void onQRCodeRead(String text, PointF[] points) {
//        Log.i("SZYNA GADA", text);
//    }
//
//    @Override
//    public void cameraNotFound() {
//
//    }
//
//    @Override
//    public void QRCodeNotFoundOnCamImage() {
//
//    }
}
