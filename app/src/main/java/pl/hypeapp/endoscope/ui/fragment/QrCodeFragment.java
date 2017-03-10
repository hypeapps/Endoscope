package pl.hypeapp.endoscope.ui.fragment;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import net.grandcentrix.thirtyinch.TiFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.hypeapp.endoscope.R;
import pl.hypeapp.endoscope.presenter.QrCodePresenter;
import pl.hypeapp.endoscope.view.QrCodeView;

public class QrCodeFragment extends TiFragment<QrCodePresenter, QrCodeView> implements QrCodeView {
    private static final String IP_DEFAULT = "255.255.255.255";
    @BindView(R.id.qr_code) ImageView qrCodeContainer;

    @NonNull
    @Override
    public QrCodePresenter providePresenter() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String ipAddress = sharedPreferences.getString("ip_local", IP_DEFAULT);
        return new QrCodePresenter(ipAddress);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_stream_qrcode, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void setQrCodeBitmap(Bitmap qrCodeBitmap) {
        qrCodeContainer.setImageBitmap(qrCodeBitmap);
    }
}
