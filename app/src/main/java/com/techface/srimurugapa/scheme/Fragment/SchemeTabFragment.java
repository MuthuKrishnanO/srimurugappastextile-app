package com.techface.srimurugapa.scheme.Fragment;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.android.material.tabs.TabLayout;
import com.techface.srimurugapa.scheme.Adapter.TapAdapter;
import com.techface.srimurugapa.scheme.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SchemeTabFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SchemeTabFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TapAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    FrameLayout simpleFrameLayout;


    private int[] tabIcons = {
            R.drawable.ic_add_24dp,
            R.drawable.ic_write

    };
    public SchemeTabFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SchemeTabFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SchemeTabFragment newInstance(String param1, String param2) {
        SchemeTabFragment fragment = new SchemeTabFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View  rootView=inflater.inflate(R.layout.fragment_scheme_tab, container, false);
        viewPager = (ViewPager)rootView.findViewById(R.id.viewPager);
        tabLayout = (TabLayout)rootView. findViewById(R.id.tabLayout);
        simpleFrameLayout = (FrameLayout)rootView.findViewById(R.id.simpleFrameLayout);



        TabLayout.Tab firstTab = tabLayout.newTab();
        firstTab.setText("Add New "); // set the Text for the first Tab
        firstTab.setIcon(R.drawable.ic_add_24dp); // set an icon for the
        firstTab.getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
// first tab
        tabLayout.addTab(firstTab); // add  the tab at in the TabLayout
// Create a new Tab named "Second"
        TabLayout.Tab secondTab = tabLayout.newTab();
        secondTab.setText("Existing"); // set the Text for the second Tab
        secondTab.setIcon(R.drawable.ic_write); // set an icon for the second tab
        tabLayout.addTab(secondTab);
       SchemeFragment  fragment1 = new SchemeFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.simpleFrameLayout, fragment1);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
// get the current selected tab's position and replace the fragment accordingly
                Fragment fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new SchemeFragment();
                       // highLightCurrentTab(0);
                        break;
                    case 1:
                        fragment = new ExistingSchemeFragment();
                       // highLightCurrentTab(1);
                        break;

                }
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.simpleFrameLayout, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        /*adapter = new TapAdapter(getChildFragmentManager(), getContext());
        adapter.addFragment(new SchemeFragment(), "ADD NEW ", tabIcons[0]);
        adapter.addFragment(new ExistingSchemeFragment(), "Existing  ", tabIcons[1]);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        highLightCurrentTab(0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                highLightCurrentTab(position);

            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });*/

       return  rootView;
    }

    private void highLightCurrentTab(int position) {
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            assert tab != null;
            tab.setCustomView(null);
            tab.setCustomView(adapter.getTabView(i));
        }
        TabLayout.Tab tab = tabLayout.getTabAt(position);
        assert tab != null;
        tab.setCustomView(null);
        tab.setCustomView(adapter.getSelectedTabView(position));

    }
}