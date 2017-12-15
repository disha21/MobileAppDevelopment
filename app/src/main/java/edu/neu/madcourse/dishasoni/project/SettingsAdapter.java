package edu.neu.madcourse.dishasoni.project;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import edu.neu.madcourse.dishasoni.R;

/**
 * Created by dishasoni on 12/3/17.
 */

public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.SettingsViewHolder> {
    private Context mContext;
    List<SettingsInfo> locSettings;
    SettingsAdapterListener listener;

    SettingsAdapter(Context context,List<SettingsInfo> locSettings,SettingsAdapterListener listener ){
        Log.d("SettingsAdapterConst", "SettingsAdapterConst");
        this.locSettings =  locSettings;
        this.listener = listener;
        this.mContext = context;
    }

    @Override
    public SettingsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.settings_info_row, parent, false);

        return new SettingsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SettingsViewHolder holder, int position) {
        SettingsInfo settingsInfo = locSettings.get(position);

        // displaying text view data
        holder.primary.setText(settingsInfo.getLOCATION_NAME() + ":" + settingsInfo.getGEOFENCE_RADIUS() );
        holder.secondary.setText(settingsInfo.getADDRESS());
        holder.ringingMode.setText(settingsInfo.getRINGING_MODE());
        String ringingStatus = settingsInfo.getRINGING_MODE();
        if(ringingStatus.equalsIgnoreCase("RINGING"))
            holder.ringingMode.setTextColor(Color.GREEN);
        //holder.itemView.setActivated(selectedItems.get(position, false));
    }

    @Override
    public int getItemCount() {
        return locSettings.size();
    }


    public class SettingsViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        public TextView primary, secondary,ringingMode;
     //   public ImageView iconImp, imgProfile;
        public LinearLayout messageContainer;
     //   public RelativeLayout iconContainer, iconBack, iconFront;

        public SettingsViewHolder(View view) {
            super(view);
            primary = (TextView) view.findViewById(R.id.txt_primary);
            secondary = (TextView) view.findViewById(R.id.txt_secondary);
//
            ringingMode = (TextView) view.findViewById(R.id.ringingMode);


//
            messageContainer = (LinearLayout) view.findViewById(R.id.message_container);
            view.setOnLongClickListener(this);

        }


        @Override
        public boolean onLongClick(View view) {
            listener.onRowLongClicked(getAdapterPosition());
            view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
            return true;
        }
    }

    public void removeLocation(int position){
        locSettings.remove(position);

    }

    public void refreshList(List<SettingsInfo> newList){
        Log.d("refreshList-new", String.valueOf(newList.size()));
        Log.d("refreshList-old", String.valueOf(locSettings.size()));
//        locSettings.clear();
        locSettings = newList;
        Log.d("refreshList-old-after", String.valueOf(locSettings.size()));
    }


    public interface SettingsAdapterListener {
//        void onIconClicked(int position);
//
//        void onIconImportantClicked(int position);
//
//        void onMessageRowClicked(int position);

        void onRowLongClicked(int position);
    }
}
