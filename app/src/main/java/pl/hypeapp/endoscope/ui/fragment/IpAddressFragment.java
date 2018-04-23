package pl.hypeapp.endoscope.ui.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.grandcentrix.thirtyinch.TiFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.hypeapp.endoscope.R;
import pl.hypeapp.endoscope.presenter.IpAddressPresenter;
import pl.hypeapp.endoscope.view.IpAddressView;

public class IpAddressFragment extends TiFragment<IpAddressPresenter, IpAddressView> implements IpAddressView {
    private static final String IP_DEFAULT = "255.255.255.255";
    @BindView(R.id.ip_address) TextView ipAddressTextView;

    @NonNull
    @Override
    public IpAddressPresenter providePresenter() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String ipAddress = sharedPreferences.getString("ip_local", IP_DEFAULT);
        return new IpAddressPresenter(ipAddress);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_stream_ip_address, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void setIpAddress(String ipAddress) {
        ipAddressTextView.setText(ipAddress);
    }
}
