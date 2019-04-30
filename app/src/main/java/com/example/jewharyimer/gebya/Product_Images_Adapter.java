package com.example.jewharyimer.gebya;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

public class Product_Images_Adapter extends PagerAdapter {
    private List<Integer> producrImages;

    public Product_Images_Adapter(List<Integer> producrImages) {
        this.producrImages = producrImages;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
       ImageView productimage=new ImageView(container.getContext());
       productimage.setImageResource(producrImages.get(position));
       container.addView(productimage,0);
       return productimage;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ImageView)object);
    }

    @Override
    public int getCount() {
        return producrImages.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }
}


