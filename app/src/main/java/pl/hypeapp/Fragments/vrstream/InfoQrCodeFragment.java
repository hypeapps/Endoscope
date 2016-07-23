package pl.hypeapp.Fragments.vrstream;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import pl.hypeapp.vrstream.R;


public class InfoQrCodeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.info_qrcode_fragment, container, false);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String ipAddress = sharedPreferences.getString("ip_local", "000.000.000.0000");
        Bitmap qrCOde = net.glxn.qrgen.android.QRCode.from(ipAddress).bitmap();
        ImageView qrCodeContainer = (ImageView) v.findViewById(R.id.qr_code);
        qrCodeContainer.setImageBitmap(qrCOde);

        return v;
    }
}
