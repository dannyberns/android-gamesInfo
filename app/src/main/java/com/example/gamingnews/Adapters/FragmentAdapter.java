package com.example.gamingnews.Adapters;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.gamingnews.Fragments.TabFragmentInfo;
import com.example.gamingnews.Fragments.TabFragmentScreenshots;
import com.example.gamingnews.Fragments.TabFragmentVideo;


public class FragmentAdapter extends FragmentStateAdapter{

    Bundle bundle;

    public FragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, Bundle bundle) {
        super(fragmentManager, lifecycle);
        this.bundle = bundle;

    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position){
            case 1:
                TabFragmentVideo tabFragmentVideo = new TabFragmentVideo();
                tabFragmentVideo.setArguments(bundle);
                return tabFragmentVideo;
            case 2:
                TabFragmentScreenshots tabFragmentScreenshots = new TabFragmentScreenshots();
                tabFragmentScreenshots.setArguments(bundle);
                return tabFragmentScreenshots;
            default:
                TabFragmentInfo tabFragmentInfo = new TabFragmentInfo();
                tabFragmentInfo.setArguments(bundle);
                return tabFragmentInfo;
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}