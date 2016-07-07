package pl.hypeapp.Fragments.vrstream;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.zxing.qrcode.encoder.QRCode;

import pl.hypeapp.vrstream.R;


public class InfoQrCodeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.info_qrcode_fragment, container, false);

        Bitmap qrCOde = net.glxn.qrgen.android.QRCode.from("192.168.1.3").bitmap();
        ImageView qrCodeContainer = (ImageView) v.findViewById(R.id.qr_code);
        qrCodeContainer.setImageBitmap(qrCOde);
//        qrCodeContainer.setImageResource(R.drawable.aparat_anim_1);

        return v;
    }
}
