package pl.hypeapp.endoscope.presenter;

import android.nfc.NdefRecord;
import android.os.Build;
import android.support.annotation.NonNull;

import net.grandcentrix.thirtyinch.TiPresenter;

import java.nio.charset.Charset;
import java.util.ArrayList;

import pl.hypeapp.endoscope.view.NfcView;

public class NfcPresenter extends TiPresenter<NfcView> {
    private static final String PACKAGE_NAME = "pl.hypeapp.endoscope";
    private ArrayList<String> messagesToSendArray = new ArrayList<>();
    private String ipAddress;

    public NfcPresenter(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        messagesToSendArray.add(ipAddress);
    }

    @Override
    protected void onAttachView(@NonNull NfcView view) {
        super.onAttachView(view);
        getView().initNfcAdapter();
    }

    public NdefRecord[] createRecords() {
        NdefRecord[] records = new NdefRecord[messagesToSendArray.size() + 1];
        //To Create Messages Manually if API is less than
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            for (int i = 0; i < messagesToSendArray.size(); i++) {
                byte[] payload = messagesToSendArray.get(i).getBytes(Charset.forName("UTF-8"));
                NdefRecord record = new NdefRecord(
                        NdefRecord.TNF_WELL_KNOWN,      //Our 3-bit Type name format
                        NdefRecord.RTD_TEXT,            //Description of our payload
                        new byte[0],                    //The optional id for our Record
                        payload);                       //Our payload for the Record

                records[i] = record;
            }
        }
        //Api is high enough that we can use createMime, which is preferred.
        else {
            for (int i = 0; i < messagesToSendArray.size(); i++) {
                byte[] payload = messagesToSendArray.get(i).
                        getBytes(Charset.forName("UTF-8"));

                NdefRecord record = NdefRecord.createMime("text/plain", payload);
                records[i] = record;

            }
        }
        records[messagesToSendArray.size()] = NdefRecord.createApplicationRecord(PACKAGE_NAME);
        return records;
    }
}
