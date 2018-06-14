package com.xinguang.reactbanner.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 动态添加View的适配器，适用React-Native
 * Created by vitozhang on 2018/6/12.
 */

public class ReactImageAdapter extends PagerAdapter{

    private final List<View> mViews = new ArrayList<>();
    private boolean mIsViewPagerInIntentionallyInconsistentState = false;

    private ViewPager mViewPager;

    public ReactImageAdapter(ViewPager viewPager){
        mViewPager = viewPager;
    }

    void addView(View child, int index) {
        mViews.add(index, child);
        notifyDataSetChanged();
        // This will prevent view pager from detaching views for pages that are not currently selected
        // We need to do that since {@link ViewPager} relies on layout passes to position those views
        // in a right way (also thanks to {@link ReactViewPagerManager#needsCustomLayoutForChildren}
        // returning {@code true}). Currently we only call {@link View#measure} and
        // {@link View#layout} after yoga step.

        // TODO(7323049): Remove this workaround once we figure out a way to re-layout some views on
        // request
        mViewPager.setOffscreenPageLimit(mViews.size());
    }

    void removeViewAt(int index) {
        mViews.remove(index);
        notifyDataSetChanged();

        // TODO(7323049): Remove this workaround once we figure out a way to re-layout some views on
        // request
        mViewPager.setOffscreenPageLimit(mViews.size());
    }

    /**
     * Replace a set of views to the ViewPager adapter and update the ViewPager
     */
    void setViews(List<View> views) {
        mViews.clear();
        mViews.addAll(views);
        notifyDataSetChanged();

        // we want to make sure we return POSITION_NONE for every view here, since this is only
        // called after a removeAllViewsFromAdapter
        mIsViewPagerInIntentionallyInconsistentState = false;
    }

    /**
     * Remove all the views from the adapter and de-parents them from the ViewPager
     * After calling this, it is expected that notifyDataSetChanged should be called soon
     * afterwards.
     */
    void removeAllViewsFromAdapter(ViewPager pager) {
        mViews.clear();
        pager.removeAllViews();
        // set this, so that when the next addViews is called, we return POSITION_NONE for every
        // entry so we can remove whichever views we need to and add the ones that we need to.
        mIsViewPagerInIntentionallyInconsistentState = true;
    }

    View getViewAt(int index) {
        return mViews.get(index);
    }

    @Override
    public int getCount() {
        return mViews.size();
    }

    @Override
    public int getItemPosition(Object object) {
        // if we've removed all views, we want to return POSITION_NONE intentionally
        return mIsViewPagerInIntentionallyInconsistentState || !mViews.contains(object) ?
                POSITION_NONE : mViews.indexOf(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mViews.get(position);
        container.addView(view, 0, new ViewPager.LayoutParams());
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
