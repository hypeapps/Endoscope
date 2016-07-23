package pl.hypeapp.Fragments.endoscope;

import android.graphics.PointF;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.dlazaro66.qrcodereaderview.QRCodeReaderView;


import pl.hypeapp.endoscope.ConnectToStreamActivity;
import pl.hypeapp.endoscope.R;


public class QrCodeScannerFragment extends Fragment implements QRCodeReaderView.OnQRCodeReadListener, View.OnClickListener {
    private QRCodeReaderView qrCodeReaderView;
    private View resultContainer;
    private TextView qrCodeIpTextView;
    private Button buttonConnect;
    private String ipAddress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.qrcode_scanner_fragment, container, false);

        qrCodeReaderView = (QRCodeReaderView) v.findViewById(R.id.qrdecoderview);

        if(qrCodeReaderView != null){
            qrCodeReaderView.setVisibility(View.VISIBLE);
            qrCodeReaderView.setOnQRCodeReadListener(this);
        }

        resultContainer = v.findViewById(R.id.result_container);
        qrCodeIpTextView = (TextView) v.findViewById(R.id.qr_scanner_result);
        buttonConnect = (Button) v.findViewById(R.id.connect);
        buttonConnect.setOnClickListener(this);
        return v;
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
    public void cameraNotFound() {
        if((qrCodeReaderView != null) && (qrCodeIpTextView != null)) {
            qrCodeReaderView.setVisibility(View.GONE);
            qrCodeIpTextView.setVisibility(View.VISIBLE);
            qrCodeIpTextView.setText(getString(R.string.camera_not_found));
        }
    }

    @Override
    public void QRCodeNotFoundOnCamImage() {

    }


    @Override
    public void onClick(View view) {
        ConnectToStreamActivity connectToStreamActivity = (ConnectToStreamActivity) getActivity();
        connectToStreamActivity.connectStream(ipAddress);
    }
}
