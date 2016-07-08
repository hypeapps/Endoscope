package pl.hypeapp.Fragments.vrstream;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pl.hypeapp.vrstream.R;
import pl.hypeapp.vrstream.StartStreamActivity;


public class InfoInputFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.info_input_fragment, container, false);


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String ipAddress = sharedPreferences.getString("ip_local", "000.000.000.0000");
        TextView ipAddressTextView = (TextView) v.findViewById(R.id.ip_address);
        if(ipAddressTextView!= null) ipAddressTextView.setText(ipAddress);

        return v;
    }




}
