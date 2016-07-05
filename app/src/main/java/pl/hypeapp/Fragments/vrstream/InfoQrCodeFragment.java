package pl.hypeapp.Fragments.vrstream;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pl.hypeapp.vrstream.R;


public class InfoQrCodeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.info_qrcode_fragment, container, false);

        return v;
    }
}
