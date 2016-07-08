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
import android.widget.Toast;


import com.dlazaro66.qrcodereaderview.QRCodeReaderView;


import pl.hypeapp.vrstream.R;


public class QrCodeScannerFragment extends Fragment implements QRCodeReaderView.OnQRCodeReadListener
{
    private QRCodeReaderView qrCodeReaderView;
    private TextView ipAddress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.qrcode_scanner_fragment, container, false);

        qrCodeReaderView = (QRCodeReaderView) v.findViewById(R.id.qrdecoderview);
        if(qrCodeReaderView != null){
            qrCodeReaderView.setVisibility(View.VISIBLE);
            qrCodeReaderView.setOnQRCodeReadListener(this);
        }

        ipAddress = (TextView)v.findViewById(R.id.ip_addres_scanned);

        return v;
    }


    @Override
    public void onQRCodeRead(String text, PointF[] points) {
        Log.i("SZYNA GADA", text);

        if((qrCodeReaderView != null) && (ipAddress != null)) {
            qrCodeReaderView.setVisibility(View.GONE);
            ipAddress.setVisibility(View.VISIBLE);
            ipAddress.setText("IP " + text);
        }


    }

    @Override
    public void cameraNotFound() {

    }

    @Override
    public void QRCodeNotFoundOnCamImage() {

    }



}
