package pl.hypeapp.Fragments.vrstream;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class ViewStreamPagerAdapter extends FragmentPagerAdapter {

    public ViewStreamPagerAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new TypeInputFragment();
            case 1:
                return new QrCodeScannerFragment();
            case 2:
                return new NfcReaderFragment();
            default:
                break;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
