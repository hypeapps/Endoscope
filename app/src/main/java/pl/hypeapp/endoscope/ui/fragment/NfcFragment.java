package pl.hypeapp.endoscope.ui.fragment;

import android.content.SharedPreferences;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.grandcentrix.thirtyinch.TiFragment;

import pl.hypeapp.endoscope.R;
import pl.hypeapp.endoscope.presenter.NfcPresenter;
import pl.hypeapp.endoscope.view.NfcView;

public class NfcFragment extends TiFragment<NfcPresenter, NfcView> implements NfcView, NfcAdapter.CreateNdefMessageCallback {
    private static final String IP_DEFAULT = "255.255.255.255";

    @NonNull
    @Override
    public NfcPresenter providePresenter() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String ipAddress = sharedPreferences.getString("ip_local", IP_DEFAULT);
        return new NfcPresenter(ipAddress);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_stream_nfc, container, false);
    }

    @Override
    public void initNfcAdapter() {
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(getActivity());
        if (nfcAdapter != null) {
            nfcAdapter.setNdefPushMessageCallback(this, getActivity());
            nfcAdapter.disableForegroundDispatch(getActivity());
        }
    }

    @Override
    public NdefMessage createNdefMessage(NfcEvent nfcEvent) {
        NdefRecord[] recordsToAttach = getPresenter().createRecords();
        //When creating an NdefMessage we need to provide an NdefRecord[]
        return new NdefMessage(recordsToAttach);
    }
}
