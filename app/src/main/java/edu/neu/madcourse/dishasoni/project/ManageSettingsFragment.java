package edu.neu.madcourse.dishasoni.project;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import edu.neu.madcourse.dishasoni.MainActivity;
import edu.neu.madcourse.dishasoni.R;

/**
 * Created by dishasoni on 11/19/17.
 */

public class ManageSettingsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.manage_settings_fragment, container, false);
     //   ArrayList<SettingsInfo> items = (ArrayList<SettingsInfo>) ((ProjectMainActivity) getActivity()).locationItems;
       // ListView listLocations  =  (ListView) view.findViewById(R.id.listView_locations);
     //   adapter = new ListAdapter(getActivity().getApplicationContext(), items);
    //    adapter.setCustomButtonListner(getActivity());
      //  listLocations.setAdapter(adapter);
        return view;
    }
}