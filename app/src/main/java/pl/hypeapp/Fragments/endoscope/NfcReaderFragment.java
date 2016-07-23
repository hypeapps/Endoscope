package pl.hypeapp.Fragments.endoscope;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import pl.hypeapp.endoscope.ConnectToStreamActivity;
import pl.hypeapp.endoscope.R;


public class NfcReaderFragment extends Fragment implements View.OnClickListener{

    private TextView ipTextVIew;
    private Button button;
    private String ipAddress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.nfc_reader_fragment, container, false);
        ipTextVIew = (TextView) v.findViewById(R.id.ip_address_text_view);
        button = (Button) v.findViewById(R.id.connect_button);
        button.setOnClickListener(this);


        return v;

    }

    public void setOnNfcIp(String ip){
        ipAddress = ip;
        if((ipTextVIew != null) && (button != null)) {
            ipTextVIew.setVisibility(View.VISIBLE);
            ipTextVIew.setText(ip);
            button.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onClick(View view) {
        ConnectToStreamActivity connectToStreamActivity = (ConnectToStreamActivity) getActivity();
        connectToStreamActivity.connectStream(ipAddress);
    }
}
