package pl.hypeapp.Fragments.endoscope;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pl.hypeapp.endoscope.R;


public class HowToUseFragmentFactory extends Fragment {

    public static HowToUseFragmentFactory newInstance(int position) {
        Bundle args = new Bundle();
        HowToUseFragmentFactory fragment = new HowToUseFragmentFactory();
        args.putInt("htu_fragment", position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int layoutResource = getProperLayout(getArguments().getInt("htu_fragment", 0));
        View v = inflater.inflate(layoutResource, container, false);

        return v;
    }

    int getProperLayout(int position){
        switch(position){
            case 0:
                return R.layout.fragment_tutorial_screen_1;
            case 1:
                return R.layout.fragment_tutorial_screen_2;
            case 2:
                return R.layout.fragment_tutorial_screen_3;
            case 3:
                return R.layout.fragment_tutorial_screen_4;
        }
        return 0;
    }
}
