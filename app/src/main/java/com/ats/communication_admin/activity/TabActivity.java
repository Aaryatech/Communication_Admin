package com.ats.communication_admin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.ats.communication_admin.R;
import com.ats.communication_admin.db.DatabaseHandler;
import com.ats.communication_admin.fragment.MessagesFragment;
import com.ats.communication_admin.fragment.NoticesFragment;
import com.ats.communication_admin.fragment.NotificationsFragment;
import com.ats.communication_admin.interfaces.MessagesInterface;
import com.ats.communication_admin.interfaces.NoticesInterface;
import com.ats.communication_admin.interfaces.NotificationsInterface;


public class TabActivity extends AppCompatActivity implements View.OnClickListener {

    public TabLayout tabLayout;
    public ViewPager viewPager;
    private FloatingActionButton fab;
    public static DatabaseHandler dbstatic;

    FragmentPagerAdapter adapterViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        dbstatic=new DatabaseHandler(this);

        tabLayout = findViewById(R.id.tab);
        viewPager = findViewById(R.id.viewpager);

        fab = findViewById(R.id.fabMenu);
        fab.setOnClickListener(this);

        adapterViewPager = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapterViewPager);

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    NoticesInterface fragmentNotices = (NoticesInterface) adapterViewPager.instantiateItem(viewPager, position);
                    if (fragmentNotices != null) {
                        fragmentNotices.fragmentGetVisible();
                    }
                } else if (position == 1) {
                    MessagesInterface fragmentMessages = (MessagesInterface) adapterViewPager.instantiateItem(viewPager, position);
                    if (fragmentMessages != null) {
                        fragmentMessages.fragmentGetVisible();
                    }
                } else if (position == 2) {
                    NotificationsInterface fragmentNotification = (NotificationsInterface) adapterViewPager.instantiateItem(viewPager, position);
                    if (fragmentNotification != null) {
                        fragmentNotification.fragmentGetVisible();
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        try {
            int tabId = 0;
            Intent mIntent = getIntent();
            tabId = mIntent.getIntExtra("tabId", 0);

            Log.e("TAB ID : ", "--------------" + tabId);
            if (tabId == 0) {
                viewPager.setCurrentItem(0);
            } else if (tabId == 1) {
                viewPager.setCurrentItem(1);
            } else if (tabId == 2) {
                viewPager.setCurrentItem(2);
            }
        } catch (Exception e) {
            Log.e("Exception : Tab Id : ", "-----" + e.getMessage());
            e.printStackTrace();
        }


    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fabMenu) {
            startActivity(new Intent(this, InnerTabActivity.class));
        }
    }


    public class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new NoticesFragment();
                case 1:
                    return new MessagesFragment();
                case 2:
                    return new NotificationsFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {
                case 0:
                    return "News";
                case 1:
                    return "Messages";
                case 2:
                    return "Notifications";
                default:
                    return null;
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(TabActivity.this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("ProgressDialog", 0);
        startActivity(intent);
        finish();
    }

}
