package pl.hypeapp.Fragments.vrstream;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pl.hypeapp.vrstream.R;
import pl.hypeapp.vrstream.StartStreamActivity;


public class InfoInputFragment extends Fragment {

    private Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.info_input_fragment, container, false);

        context = getActivity();
        TextView ipAddress = (TextView) v.findViewById(R.id.ip_address);
        if(ipAddress!= null) ipAddress.setText(getIpAddress());

        return v;
    }

    private String getIpAddress(){
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        int ipAddress = wifiManager.getConnectionInfo().getIpAddress();
        String ipAddressFormatted = String.format("%d.%d.%d.%d", (ipAddress & 0xff), (ipAddress >> 8 & 0xff),
                (ipAddress >> 16 & 0xff), (ipAddress >> 24 & 0xff));
        Log.i("SZYNA", "ip address " + ipAddressFormatted);
        Log.i("SZYNA", "JESTEM TU");
        return ipAddressFormatted;
    }


}
