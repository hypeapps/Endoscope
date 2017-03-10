package pl.hypeapp.endoscope.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.HashMap;

import pl.hypeapp.endoscope.ui.fragment.NfcReaderFragment;
import pl.hypeapp.endoscope.ui.fragment.QrCodeScannerFragment;
import pl.hypeapp.endoscope.ui.fragment.WriteIpAddressFragment;


public class ConnectToStreamPagerAdapter extends FragmentPagerAdapter {
    private static final int CONNECT_TO_STREAM_PAGES = 3;
    private HashMap<Integer, Fragment> fragmentHashMap = new HashMap<>();

    public ConnectToStreamPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new WriteIpAddressFragment();
            case 1:
                return new QrCodeScannerFragment();
            case 2:
                //Due to use fragment's method in activity must be added to hashmap. (Pointer reference)
                if (fragmentHashMap.get(position) != null) {
                    return fragmentHashMap.get(position);
                }
                NfcReaderFragment nfcReaderFragment = new NfcReaderFragment();
                fragmentHashMap.put(position, nfcReaderFragment);
                return nfcReaderFragment;
            default:
                break;
        }
        return null;
    }

    @Override
    public int getCount() {
        return CONNECT_TO_STREAM_PAGES;
    }
}
