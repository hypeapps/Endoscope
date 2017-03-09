package pl.hypeapp.endoscope.ui.listener;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pl.hypeapp.endoscope.R;

public class OnDotPageChangeListener implements ViewPager.OnPageChangeListener {
    private List<ImageView> dots = new ArrayList<>();
    private ImageView nextPageButton;
    private TextView skipButton;
    private TextView doneButton;

    public OnDotPageChangeListener(List<ImageView> dots) {
        this.dots = dots;
    }

    public OnDotPageChangeListener(List<ImageView> dots,
                                   ImageView nextPageButton, TextView skipButton, TextView doneButton) {
        this(dots);
        this.nextPageButton = nextPageButton;
        this.skipButton = skipButton;
        this.doneButton = doneButton;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        changeDotFocus(position);
        viewDoneButton(position);
        viewSkipButton(position);
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

    private void viewDoneButton(int position) {
        if ((nextPageButton != null) && (doneButton != null)) {
            if (position < 3) {
                nextPageButton.setVisibility(View.VISIBLE);
                doneButton.setVisibility(View.GONE);
            } else {
                nextPageButton.setVisibility(View.GONE);
                doneButton.setVisibility(View.VISIBLE);
            }
        }
    }

    private void viewSkipButton(int position) {
        if (skipButton != null) {
            if (position > 0) {
                skipButton.setVisibility(View.GONE);
            } else {
                skipButton.setVisibility(View.VISIBLE);
            }
        }
    }
}
