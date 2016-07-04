package pl.hypeapp.HowToUseFragments.vrstream;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class HowToUsePagerAdapter extends FragmentPagerAdapter {

    public HowToUsePagerAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new HowToUseFragment1();
            case 1:
                return new HowToUseFragment2();
            case 2:
                return new HowToUseFragment3();
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
