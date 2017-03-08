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
import pl.hypeapp.endoscope.presenter.InputIpPresenter;
import pl.hypeapp.endoscope.ui.activity.ConnectToStreamActivity;
import pl.hypeapp.endoscope.view.InputIpView;

public class InputIpFragment extends TiFragment<InputIpPresenter, InputIpView> implements InputIpView {
    @BindView(R.id.ip_edit_text)
    EditText inputIpAddress;

    @NonNull
    @Override
    public InputIpPresenter providePresenter() {
        return new InputIpPresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.type_input_fragment, container, false);
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
