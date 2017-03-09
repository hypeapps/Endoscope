package pl.hypeapp.endoscope.ui.listener;

import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import pl.hypeapp.endoscope.R;

public class OnDotPageChangeListener implements ViewPager.OnPageChangeListener {
    private List<ImageView> dots = new ArrayList<>();

    public OnDotPageChangeListener(List<ImageView> dots) {
        this.dots = dots;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        changeDotFocus(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void changeDotFocus(int position) {
        for (ImageView dot : dots) {
            int dotPosition = Integer.parseInt(dot.getTag().toString());
            if (dotPosition == position) {
                setActiveDot(dot);
            } else {
                setInactiveDot(dot);
            }
        }
    }

    private void setActiveDot(ImageView dot) {
        dot.setImageResource(R.drawable.circle_shape_active);
    }

    private void setInactiveDot(ImageView dot) {
        dot.setImageResource(R.drawable.circle_shape_inactive);
    }
}
