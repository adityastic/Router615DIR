package com.adityagupta.router615dir.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.adityagupta.router615dir.R;

public class SettingsFragment extends Fragment {

    EditText tvup, tvdown, mbup, mbdown;

    Button button;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvup = view.findViewById(R.id.tvup);
        tvdown = view.findViewById(R.id.tvdown);
        mbup = view.findViewById(R.id.mbup);
        mbdown = view.findViewById(R.id.mbdown);
        button = view.findViewById(R.id.changeSettings);

        final SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(getContext());

        tvup.setText(shared.getString("tvup", "0"));
        tvdown.setText(shared.getString("tvdown", "0"));
        mbup.setText(shared.getString("mbup", "0"));
        mbdown.setText(shared.getString("mbdown", "0"));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = shared.edit();
                editor.putString("tvup", tvup.getText().toString());
                editor.putString("tvdown", tvdown.getText().toString());
                editor.putString("mbup", mbup.getText().toString());
                editor.putString("mbdown", mbdown.getText().toString());
                editor.apply();
            }
        });
    }

    public static Fragment newInstance() {
        return new SettingsFragment();

    }
}