package com.adityagupta.router615dir;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.adityagupta.router615dir.adapter.DrawerItemsAdapter;
import com.adityagupta.router615dir.data.DrawerItemData;
import com.adityagupta.router615dir.fragments.ConnectedDeviceFragment;
import com.adityagupta.router615dir.fragments.QOSFragment;
import com.adityagupta.router615dir.fragments.SettingsFragment;
import com.adityagupta.router615dir.fragments.StatusDeviceFragment;
import com.adityagupta.router615dir.utils.Common;
import com.adityagupta.router615dir.views.Drawer.AdityaDrawerToggle;
import com.adityagupta.router615dir.views.Drawer.AdityaNavigationLayout;

import java.util.ArrayList;
import java.util.List;

public class ParentActivity extends AppCompatActivity {

    Toolbar toolbar;
    List<DrawerItemData> list;
    AdityaNavigationLayout drawer;

    RecyclerView mRecyclerView;
    DrawerItemsAdapter mAdapter;

    boolean backflag = false;

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen()) {
            drawer.closeDrawer();
        } else {
            if (backflag) {
                finishAffinity();
            } else {
                backflag = true;
                Toast.makeText(this, "Press Back Again to Exit App", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void createDrawerItems() {
        mAdapter = null;

        list = new ArrayList<>();
        list.add(new DrawerItemData(Common.drawableToBitmap(getResources().getDrawable(R.drawable.nav_drawer_status_dead)), "Status", "Status", StatusDeviceFragment.newInstance(new StatusDeviceFragment.ChangeDrawerIcon() {
            @Override
            public void changeIcon(boolean yesorno) {
                if(yesorno)
                    mAdapter.changeIcon(0,getResources().getDrawable(R.drawable.nav_drawer_status_happy));
                else
                    mAdapter.changeIcon(0,getResources().getDrawable(R.drawable.nav_drawer_status_dead));
            }
        })));
        list.add(new DrawerItemData(Common.drawableToBitmap(getResources().getDrawable(R.drawable.nav_drawer_devices)), "Devices", "Connected Devices", ConnectedDeviceFragment.newInstance()));
        list.add(new DrawerItemData(Common.drawableToBitmap(getResources().getDrawable(R.drawable.nav_drawer_qos)), "QOS", "QOS", QOSFragment.newInstance()));
//        list.add(new DrawerItemData(Common.drawableToBitmap(getResources().getDrawable(R.drawable.nav_drawer_homepage)), "MyBookings", "My Bookings", QOSFragment.newInstance()));
        list.add(new DrawerItemData(Common.drawableToBitmap(getResources().getDrawable(R.drawable.nav_drawer_settings)), "Settings", "Settings", SettingsFragment.newInstance()));

        mRecyclerView.setAdapter(null);
        mAdapter = new DrawerItemsAdapter(this, list, (AdityaNavigationLayout) findViewById(R.id.drawer), getSupportFragmentManager(), getSupportActionBar());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mRecyclerView = findViewById(R.id.list);
        drawer = (AdityaNavigationLayout) findViewById(R.id.drawer);

        ((TextView) findViewById(R.id.name)).setText("Aditya Gupta");
        ((TextView) findViewById(R.id.email)).setText("adityaofficialgupta@gmail.com");

        //createDrawer();

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setNavigationIcon(R.drawable.ic_toolbar_navigation);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawer.openDrawer();
                }
            });
        }

        AdityaDrawerToggle duoDrawerToggle = new AdityaDrawerToggle(this,
                drawer,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        ((AdityaNavigationLayout) findViewById(R.id.drawer)).setDrawerListener(duoDrawerToggle);

        duoDrawerToggle.syncState();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        createDrawerItems();

        mAdapter.start();

        //drawer.setSelection(1);
    }
}
