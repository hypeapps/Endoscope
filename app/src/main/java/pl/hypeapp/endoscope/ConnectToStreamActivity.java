package pl.hypeapp.endoscope;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import pl.hypeapp.Fragments.endoscope.NfcReaderFragment;


public class ConnectToStreamActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private ViewStreamPagerAdapter viewStreamPagerAdapter;
    private ArrayList<String> messagesReceivedArray;
    private WiFiStateChangeReceiver wiFiStateChangeReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_stream);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        initViewPager();

        messagesReceivedArray = new ArrayList<>();

        wiFiStateChangeReceiver = new WiFiStateChangeReceiver();
        registerReceiver(wiFiStateChangeReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        registerReceiver(wiFiStateChangeReceiver, new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION));

    }

    private Activity getActivity(){ return this; }

    void initViewPager(){
        viewPager = (ViewPager)findViewById(R.id.about_connect_pager);
        viewStreamPagerAdapter = new ViewStreamPagerAdapter(getSupportFragmentManager());

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                PagerCirclesManager.dotStatusManage(position, getActivity());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setAdapter(viewStreamPagerAdapter);


    }

    public void SlideToInputPage(View view){
        viewPager.setCurrentItem(0);
    }

    public void SlideToQrCodePage(View view){
        viewPager.setCurrentItem(1);
    }

    public void SlideToNfcPage(View view){
        viewPager.setCurrentItem(2);
    }

    private void handleNfcIntent(Intent NfcIntent) {
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(NfcIntent.getAction())) {
            Parcelable[] receivedArray =
                    NfcIntent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

            if(receivedArray != null) {
                messagesReceivedArray.clear();
                NdefMessage ndefMessage = (NdefMessage) receivedArray[0];
                NdefRecord[] attachedRecords = ndefMessage.getRecords();

                for (NdefRecord record:attachedRecords) {
                    String string = new String(record.getPayload());
                    //Make sure we don't pass along our AAR (Android Application Record)
                    if (string.equals(getPackageName())) { continue; }
                    messagesReceivedArray.add(string);
                }

                String receivedMessage = messagesReceivedArray.get(0);

                NfcReaderFragment f = (NfcReaderFragment) viewStreamPagerAdapter.getItem(2);
                f.setOnNfcIp(receivedMessage);

            }
            else {
                Toast.makeText(this, "Received Blank Parcel", Toast.LENGTH_LONG).show();
            }

        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleNfcIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        handleNfcIntent(getIntent());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(wiFiStateChangeReceiver);
    }

    public void connectStream(String ipAddress){
        Intent i = new Intent(ConnectToStreamActivity.this, PlayStreamActivity.class);
        i.putExtra("ip_connect", ipAddress);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(ConnectToStreamActivity.this, MainMenuActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}

