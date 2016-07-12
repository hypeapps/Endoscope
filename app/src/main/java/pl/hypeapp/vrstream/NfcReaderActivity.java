package pl.hypeapp.vrstream;

import android.app.Activity;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by PrzemekEnterprise on 12.07.2016.
 */
public class NfcReaderActivity extends Activity {

    String TAG = "SzynaGada";
    private ArrayList<String> messagesReceivedArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc_reader);

        messagesReceivedArray = new ArrayList<>();
    }

    @Override
    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
        Log.i(TAG, "INTENT");
        handleNfcIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        handleNfcIntent(getIntent());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    private void handleNfcIntent(Intent NfcIntent) {
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(NfcIntent.getAction())) {
            Parcelable[] receivedArray =
                    NfcIntent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

            if(receivedArray != null) {
                messagesReceivedArray.clear();
                NdefMessage receivedMessage = (NdefMessage) receivedArray[0];
                NdefRecord[] attachedRecords = receivedMessage.getRecords();

                for (NdefRecord record:attachedRecords) {
                    String string = new String(record.getPayload());
                    //Make sure we don't pass along our AAR (Android Application Record)
                    if (string.equals(getPackageName())) { continue; }
                    messagesReceivedArray.add(string);
                }
                Toast.makeText(this, "Received " + messagesReceivedArray.size() +
                        " Messages" + messagesReceivedArray.get(0), Toast.LENGTH_LONG).show();
                Log.i(TAG, messagesReceivedArray.get(0));

                TextView ipAddresNfc = (TextView) findViewById(R.id.nfc_ip);
                ipAddresNfc.setText(messagesReceivedArray.get(0));

            }
            else {
                Toast.makeText(this, "Received Blank Parcel", Toast.LENGTH_LONG).show();
            }
        }
    }
}
