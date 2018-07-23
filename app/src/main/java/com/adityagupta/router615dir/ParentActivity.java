package com.adityagupta.router615dir;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.adityagupta.router615dir.adapter.DrawerItemsAdapter;
import com.adityagupta.router615dir.data.DrawerItemData;
import com.adityagupta.router615dir.fragments.ConnectedDeviceFragment;
import com.adityagupta.router615dir.fragments.QOSFragment;
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
    RecyclerView.Adapter mAdapter;

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
        list.add(new DrawerItemData(Common.drawableToBitmap(getResources().getDrawable(R.drawable.nav_drawer_devices)), "Devices", "Connected Devices", ConnectedDeviceFragment.newInstance()));
        list.add(new DrawerItemData(Common.drawableToBitmap(getResources().getDrawable(R.drawable.nav_drawer_qos)), "QOS", "QOS", QOSFragment.newInstance()));
//        list.add(new DrawerItemData(Common.drawableToBitmap(getResources().getDrawable(R.drawable.nav_drawer_homepage)), "MyBookings", "My Bookings", QOSFragment.newInstance()));
//        list.add(new DrawerItemData(Common.drawableToBitmap(getResources().getDrawable(R.drawable.nav_drawer_homepage)), "Tips", "Tips", QOSFragment.newInstance()));

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

        getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, ConnectedDeviceFragment.newInstance()).addToBackStack(null).commit();
        getSupportActionBar().setTitle("Connected Devices");

        //drawer.setSelection(1);
    }
}
