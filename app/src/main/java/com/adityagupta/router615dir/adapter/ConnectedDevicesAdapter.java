package com.adityagupta.router615dir.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adityagupta.router615dir.R;
import com.adityagupta.router615dir.data.ConnectedDevicesData;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class ConnectedDevicesAdapter extends RecyclerView.Adapter<ConnectedDevicesAdapter.ConnectedDevicesHolder> {

    Context context;
    ArrayList<ConnectedDevicesData> list;

    public ConnectedDevicesAdapter(Context context, ArrayList<ConnectedDevicesData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ConnectedDevicesHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ConnectedDevicesHolder(LayoutInflater.from(context).inflate(R.layout.recycler_connected_item, viewGroup, false));
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull ConnectedDevicesHolder connectedDevicesHolder, int i) {
        ConnectedDevicesData data = list.get(i);
        connectedDevicesHolder.title.setText(data.getName());

        StringTokenizer str = new StringTokenizer(data.getIp(),".");
        str.nextToken();str.nextToken();str.nextToken();
        connectedDevicesHolder.ip.setText(str.nextToken());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ConnectedDevicesHolder extends RecyclerView.ViewHolder {

        TextView title,ip;

        public ConnectedDevicesHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            ip = itemView.findViewById(R.id.ip);
        }
    }
}
