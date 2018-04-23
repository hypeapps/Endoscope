package pl.hypeapp.endoscope.ui.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import net.grandcentrix.thirtyinch.TiActivity;

import java.util.List;

import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.hypeapp.endoscope.R;
import pl.hypeapp.endoscope.adapter.ConnectToStreamPagerAdapter;
import pl.hypeapp.endoscope.presenter.ConnectToStreamPresenter;
import pl.hypeapp.endoscope.receiver.WiFiStateChangeReceiver;
import pl.hypeapp.endoscope.ui.fragment.NfcReaderFragment;
import pl.hypeapp.endoscope.ui.listener.OnDotPageChangeListener;
import pl.hypeapp.endoscope.view.ConnectToStreamView;

public class ConnectToStreamActivity extends TiActivity<ConnectToStreamPresenter, ConnectToStreamView>
        implements ConnectToStreamView {
    public static final int PAGE_INPUT = 0;
    public static final int PAGE_QR_CODE = 1;
    public static final int PAGE_NFC = 2;
    public static final String INTENT_EXTRA_IP_CONNECT = "ip_connect";
    private ViewPager viewPager;
    private WiFiStateChangeReceiver wiFiStateChangeReceiver = new WiFiStateChangeReceiver();
    @BindViews({R.id.circle_page1, R.id.circle_page2, R.id.circle_page3}) List<ImageView> dots;
    private ConnectToStreamPagerAdapter connectToStreamPagerAdapter;

    @NonNull
    @Override
    public ConnectToStreamPresenter providePresenter() {
        return new ConnectToStreamPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_stream);
        ButterKnife.bind(this);
        initViewPager();
        registerReceivers();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().onIntent(getIntent());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(wiFiStateChangeReceiver);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        getPresenter().onIntent(intent);
    }

    @Override
    public void passIntentToNfcReader(Intent intent) {
        Fragment fragment = connectToStreamPagerAdapter.getItem(PAGE_NFC);
        if (fragment instanceof NfcReaderFragment) {
            NfcReaderFragment nfcReaderFragment = (NfcReaderFragment) fragment;
            nfcReaderFragment.onNfcIntent(intent);
        }
    }

    @Override
    @OnClick(R.id.input_layout)
    public void SlideToInputPage() {
        viewPager.setCurrentItem(PAGE_INPUT);
    }

    @Override
    @OnClick(R.id.qr_code_layout)
    public void SlideToQrCodePage() {
        viewPager.setCurrentItem(PAGE_QR_CODE);
    }

    @Override
    @OnClick(R.id.nfc_layout)
    public void SlideToNfcPage() {
        viewPager.setCurrentItem(PAGE_NFC);
    }

    @Override
    public void intentToPlayStreamActivity(String ipAddress) {
        Intent i = new Intent(ConnectToStreamActivity.this, PlayStreamActivity.class);
        i.putExtra(INTENT_EXTRA_IP_CONNECT, ipAddress);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(ConnectToStreamActivity.this, MainMenuActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    private void initViewPager() {
        viewPager = (ViewPager) findViewById(R.id.about_connect_pager);
        connectToStreamPagerAdapter = new ConnectToStreamPagerAdapter(getSupportFragmentManager());
        viewPager.addOnPageChangeListener(new OnDotPageChangeListener(dots));
        viewPager.setAdapter(connectToStreamPagerAdapter);
    }

    private void registerReceivers() {
        registerReceiver(wiFiStateChangeReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        registerReceiver(wiFiStateChangeReceiver, new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION));
    }
}
