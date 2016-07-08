package pl.hypeapp.Fragments.vrstream;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


import pl.hypeapp.vrstream.R;


public class TypeInputFragment extends Fragment {


    EditText editTextIpAddress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.type_input_fragment, container, false);

        editTextIpAddress = (EditText) v.findViewById(R.id.ip_edit_text);
        int maxLength = 15;
        editTextIpAddress.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLength)});

        return v;
    }




}
