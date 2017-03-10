package pl.hypeapp.endoscope.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import net.grandcentrix.thirtyinch.TiFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.hypeapp.endoscope.R;
import pl.hypeapp.endoscope.presenter.WriteIpAddressPresenter;
import pl.hypeapp.endoscope.ui.activity.ConnectToStreamActivity;
import pl.hypeapp.endoscope.view.WriteIpAddressView;

public class WriteIpAddressFragment extends TiFragment<WriteIpAddressPresenter, WriteIpAddressView> implements WriteIpAddressView {
    @BindView(R.id.ip_edit_text)
    EditText inputIpAddress;

    @NonNull
    @Override
    public WriteIpAddressPresenter providePresenter() {
        return new WriteIpAddressPresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragemnt_write_ip, container, false);
        ButterKnife.bind(this, v);
        int inputMaxLength = 15;
        inputIpAddress.setFilters(new InputFilter[]{new InputFilter.LengthFilter(inputMaxLength)});
        return v;
    }

    @OnClick(R.id.connect_button)
    public void onClickConnect() {
        String streamIpAddress = inputIpAddress.getText().toString();
        getPresenter().validateIpAddress(streamIpAddress);
    }

    @Override
    public void intentToPlayStreamActivity(String ipAddress) {
        ConnectToStreamActivity connectToStreamActivity = (ConnectToStreamActivity) getActivity();
        connectToStreamActivity.intentToPlayStreamActivity(ipAddress);
    }

    @Override
    public void onWrongIpAddress() {
        Toast.makeText(getContext(), "Wrong IP", Toast.LENGTH_SHORT).show();
    }
}
