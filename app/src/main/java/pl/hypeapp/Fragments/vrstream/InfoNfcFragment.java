package pl.hypeapp.Fragments.vrstream;

import android.content.SharedPreferences;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.nio.charset.Charset;
import java.util.ArrayList;

import pl.hypeapp.vrstream.R;


public class InfoNfcFragment extends Fragment  implements NfcAdapter.OnNdefPushCompleteCallback,
        NfcAdapter.CreateNdefMessageCallback {

    private NfcAdapter nfcAdapter;
    private ArrayList<String> messagesToSendArray;
    String TAG = "SZYNA_GADA";
    private final String IP_DEFAULT = "000.000.000.0000" ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.info_nfc_fragment, container, false);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String ipAddress = sharedPreferences.getString("ip_local", IP_DEFAULT);

        nfcAdapter = NfcAdapter.getDefaultAdapter(getActivity());
        messagesToSendArray = new ArrayList<>();
        messagesToSendArray.add(ipAddress);

        if(nfcAdapter != null) {
            nfcAdapter.setNdefPushMessageCallback(this, getActivity());
            nfcAdapter.disableForegroundDispatch(getActivity());
            //This will be called if the message is sent successfully
            nfcAdapter.setOnNdefPushCompleteCallback(this, getActivity());
        }
        return v;

    }


    @Override
    public NdefMessage createNdefMessage(NfcEvent nfcEvent) {
        NdefRecord[] recordsToAttach = createRecords();
        Log.i(TAG, "createNdefMessage");
        //When creating an NdefMessage we need to provide an NdefRecord[]
        return new NdefMessage(recordsToAttach);
    }

    @Override
    public void onNdefPushComplete(NfcEvent nfcEvent) {
        Log.i("SZYNA GADA", "onNdefPushComplete");
    }


    public NdefRecord[] createRecords() {
        Log.i(TAG, "createRecords");
        NdefRecord[] records = new NdefRecord[messagesToSendArray.size() + 1];
        //To Create Messages Manually if API is less than
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            for (int i = 0; i < messagesToSendArray.size(); i++){
                byte[] payload = messagesToSendArray.get(i).getBytes(Charset.forName("UTF-8"));
                NdefRecord record = new NdefRecord(
                        NdefRecord.TNF_WELL_KNOWN,      //Our 3-bit Type name format
                        NdefRecord.RTD_TEXT,            //Description of our payload
                        new byte[0],                    //The optional id for our Record
                        payload);                       //Our payload for the Record

                records[i] = record;
                Log.i(TAG, "createRecodrds IF1");
            }
        }
        //Api is high enough that we can use createMime, which is preferred.
        else {
            for (int i = 0; i < messagesToSendArray.size(); i++){
                byte[] payload = messagesToSendArray.get(i).
                        getBytes(Charset.forName("UTF-8"));

                NdefRecord record = NdefRecord.createMime("text/plain",payload);
                records[i] = record;

                Log.i(TAG, "createRecodrds IF2");
            }
        }
        records[messagesToSendArray.size()] = NdefRecord.createApplicationRecord(getActivity().getPackageName());
        return records;
    }



}
