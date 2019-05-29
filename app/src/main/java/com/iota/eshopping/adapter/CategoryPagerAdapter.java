package com.iota.eshopping.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.iota.eshopping.fragment.category.CategoryPagerFragment;

import java.util.List;

/**
 * @author yeakleang.ay on 9/18/18.
 */

public class CategoryPagerAdapter extends FragmentPagerAdapter {

    private List<CategoryPagerFragment> categoryPagerFragments;

    /**
     * Constructor
     * @param fragmentManager FragmentManager
     * @param categoryPagerFragments List<CategoryPagerFragment>
     */
    public CategoryPagerAdapter(FragmentManager fragmentManager, List<CategoryPagerFragment> categoryPagerFragments) {
        super(fragmentManager);
        this.categoryPagerFragments = categoryPagerFragments;
    }

    @Override
    public CategoryPagerFragment getItem(int position) {
        return categoryPagerFragments.get(position);
    }

    @Override
    public int getCount() {
        return categoryPagerFragments.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return getItem(position).getTitle();
    }
}
