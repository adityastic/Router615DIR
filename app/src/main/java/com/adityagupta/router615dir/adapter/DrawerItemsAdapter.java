package com.adityagupta.router615dir.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.adityagupta.router615dir.R;
import com.adityagupta.router615dir.data.DrawerItemData;
import com.adityagupta.router615dir.views.Drawer.AdityaNavigationLayout;

import java.util.List;

/**
 * Created by adityagupta on 10/12/17.
 */

public class DrawerItemsAdapter extends RecyclerView.Adapter<DrawerItemsAdapter.DrawerItemHolder> {

    Context context;
    List<DrawerItemData> list;
    AdityaNavigationLayout mainLayout;
    FragmentManager fragmentManager;
    ActionBar actionBar;
    int index = 0;

    public DrawerItemsAdapter(Context context, List<DrawerItemData> list, AdityaNavigationLayout mainLayout, FragmentManager fragmentManager, ActionBar actionBar) {
        this.context = context;
        this.list = list;
        this.mainLayout = mainLayout;
        this.fragmentManager = fragmentManager;
        this.actionBar = actionBar;
    }

    @Override
    public DrawerItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_item, parent, false);
        return new DrawerItemHolder(v);
    }

    @Override
    public void onBindViewHolder(final DrawerItemHolder holder, final int position) {
        final DrawerItemData data = list.get(position);

        holder.icon.setImageBitmap(data.image);
        holder.title.setText(data.title);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionBar.setTitle(data.actiontitle);
                mainLayout.closeDrawer();
                new android.os.Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        changeFragment(data.fragment);
                    }
                }, 300);
                index = position;
                notifyDataSetChanged();
            }
        });

        if (data.actiontitle.equals("Tips")) {
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) holder.itemView.getLayoutParams();
            params.topMargin = 56;
            holder.itemView.setLayoutParams(params);
        }

        if (index == position) {
            holder.icon.setColorFilter(ContextCompat.getColor(context, R.color.mainbg), android.graphics.PorterDuff.Mode.SRC_IN);
            holder.title.setTextColor(context.getResources().getColor(R.color.mainbg));
            holder.bg_color.setVisibility(View.VISIBLE);
        } else {
            holder.icon.setColorFilter(ContextCompat.getColor(context, R.color.drawer_default), android.graphics.PorterDuff.Mode.SRC_IN);
            holder.title.setTextColor(context.getResources().getColor(R.color.drawer_text_default));
            holder.bg_color.setVisibility(View.INVISIBLE);
        }
    }


    public void changeFragment(Fragment fragment) {
        fragmentManager.beginTransaction().replace(R.id.frag_container, fragment).addToBackStack(null).commit();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class DrawerItemHolder extends RecyclerView.ViewHolder {

        ImageView icon;
        TextView title;
        CardView bg_color;

        public DrawerItemHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.icon);
            title = itemView.findViewById(R.id.title);
            bg_color = itemView.findViewById(R.id.bg_color);
        }

    }
}
