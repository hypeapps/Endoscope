package pl.hypeapp.HowToUseFragments.vrstream;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pl.hypeapp.vrstream.R;

/**
 * Created by PrzemekEnterprise on 14.06.2016.
 */
public class HowToUseFragment3 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.htu_fragment3,container,false);

        return v;
    }
}
