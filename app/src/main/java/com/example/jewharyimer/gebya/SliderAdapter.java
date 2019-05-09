package com.example.jewharyimer.gebya;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class SliderAdapter extends PagerAdapter {
    private List<SliderModel> sliderModelListp;

    public SliderAdapter(List<SliderModel> sliderModelListp) {
        this.sliderModelListp = sliderModelListp;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view=LayoutInflater.from(container.getContext()).inflate(R.layout.slider_layout,container,false);
        ConstraintLayout bannerSlider=view.findViewById(R.id.bannet_containet);
        bannerSlider.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(sliderModelListp.get(position).getBackgroundcolor())));

        ImageView banner=view.findViewById(R.id.banner_slider);

        Glide.with(container.getContext()).load(sliderModelListp.get(position).toString()).apply(new RequestOptions().placeholder(R.drawable.home)).into(banner);
        //banner.setImageResource(sliderModelListp.get(position).getBanner());
        container.addView(view,0);
        return view;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return sliderModelListp.size();
    }
}
