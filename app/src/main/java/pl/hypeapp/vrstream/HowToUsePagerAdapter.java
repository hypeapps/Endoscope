package pl.hypeapp.vrstream;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import pl.hypeapp.Fragments.vrstream.HowToUseFragmentFactory;


public class HowToUsePagerAdapter extends FragmentPagerAdapter {

    public HowToUsePagerAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return HowToUseFragmentFactory.newInstance(position);
    }

    @Override
    public int getCount() {
        return 3;
    }
}
