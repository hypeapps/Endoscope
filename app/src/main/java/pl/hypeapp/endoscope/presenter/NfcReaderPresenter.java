package pl.hypeapp.endoscope.presenter;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Parcelable;

import net.grandcentrix.thirtyinch.TiPresenter;

import java.util.ArrayList;

import pl.hypeapp.endoscope.view.NfcReaderView;

public class NfcReaderPresenter extends TiPresenter<NfcReaderView> {
    private ArrayList<String> messagesReceivedArray = new ArrayList<>();
    private String ipAddress;

    public void handleNfcIntent(Intent NfcIntent) {
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(NfcIntent.getAction())) {
            Parcelable[] receivedArray =
                    NfcIntent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

            if (receivedArray != null) {
                messagesReceivedArray.clear();
                NdefMessage ndefMessage = (NdefMessage) receivedArray[0];
                NdefRecord[] attachedRecords = ndefMessage.getRecords();

                for (NdefRecord record : attachedRecords) {
                    String string = new String(record.getPayload());
                    //Make sure we don't pass along our AAR (Android Application Record)
//                    if (string.equals(getPackageName())) {
//                        continue;
//                    }
                    messagesReceivedArray.add(string);
                }
                String receivedMessage = messagesReceivedArray.get(0);
                this.ipAddress = receivedMessage;
                if (getView() != null) {
                    getView().showNfcResult(receivedMessage);
                }
            } else {
                getView().showNfcError();
            }

        }
    }

    public void connectToStream() {
        getView().intentToPlayStreamActivity(ipAddress);
    }
}
