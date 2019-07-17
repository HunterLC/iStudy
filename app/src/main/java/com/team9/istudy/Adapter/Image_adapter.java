package com.team9.istudy.Adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class Image_adapter extends PagerAdapter {

    List<View> views;


    public Image_adapter(List<View> views){
        this.views=views;
    }


    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view=views.get(position%views.size());
        if(view.getParent()!=null){
            container.removeView(view);
        }
        container.addView(view);
        return view;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);这里调用了...
        //container.removeView((View)object);
    }


}
