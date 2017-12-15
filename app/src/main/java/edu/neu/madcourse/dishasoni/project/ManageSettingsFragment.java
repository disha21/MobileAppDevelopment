package edu.neu.madcourse.dishasoni.project;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import edu.neu.madcourse.dishasoni.MainActivity;
import edu.neu.madcourse.dishasoni.R;

/**
 * Created by dishasoni on 11/19/17.
 */

public class ManageSettingsFragment extends Fragment implements SettingsAdapter.SettingsAdapterListener {
    public RecyclerView.LayoutManager mLayoutManager;
    public SettingsAdapter mAdapter;
    List<SettingsInfo> items;
    static AlertDialog alertBox;
    public ManageSettingsFragment manageSettingsFragment = this;
    RecyclerView mRecyclerView;
    View view;

    public ManageSettingsFragment(){
        Log.d("MSFragment", "constructor");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("MSFragment", "oncreate");
        view =  inflater.inflate(R.layout.manage_settings_fragment, container, false);

        mRecyclerView  = (RecyclerView)view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        items =  ((ProjectMainActivity) getActivity()).locationItems;

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        mAdapter = new SettingsAdapter(getActivity().getApplicationContext(), items, this);
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }


    @Override
    public void onRowLongClicked(int position) {
        Log.d("pos", "pos" + position);
        final int pos = position;
        final AlertDialog.Builder alertDelete = new AlertDialog.Builder(getActivity());
        alertDelete.setTitle("Delete Geofence");
        alertDelete.setMessage("Do you really want to delete this location")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SettingsInfo location = items.get(pos);
        ((ProjectMainActivity)getActivity()).deleteGeofence(location);
                        mAdapter.removeLocation(pos);
                        mAdapter.notifyDataSetChanged();
                    }
                });
        alertDelete.setNegativeButton("No", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });



        alertBox = alertDelete.show();
        Button nbutton = alertBox.getButton(DialogInterface.BUTTON_NEGATIVE);
        nbutton.setTextColor(Color.RED);
    }

    public void refreshSettingsTab(List<SettingsInfo> newItems){

        Log.d("refreshItems", "refreshItems");
        try {
            items = newItems;
            Log.d("refreshItems", newItems.toString());
//           mAdapter = new SettingsAdapter(getActivity(), newItems, manageSettingsFragment);
            mAdapter.refreshList(newItems);
//            mRecyclerView.setAdapter(mAdapter);
//            mRecyclerView.swapAdapter(mAdapter, false);
            mAdapter.notifyDataSetChanged();
           // mAdapter.notifyDataSetChanged();
            //  createLocationSettingsTable();
            // getGeofences();

        } catch (Exception e){
            Log.d("refreshsettingerror", "exception" + e.toString());
        }

    }
}
