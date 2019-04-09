package boat_tracker.joris.neble.boattracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterContainership extends BaseAdapter {
    ArrayList<Containership> containershipArrayList;
    Context context;

    public AdapterContainership(ArrayList<Containership> containershipArrayList, Context context) {
        this.containershipArrayList = containershipArrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return containershipArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return containershipArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return containershipArrayList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.activity_listview,parent,false);
        TextView nameBoat = convertView.findViewById(R.id.boatName);
        TextView nameCap = convertView.findViewById(R.id.boatCaptain);
        nameBoat.setText(" Bato nom : "+containershipArrayList.get(position).getName());
        nameCap.setText(" \n Le capitaine est " + containershipArrayList.get(position).getCaptainName());
        return convertView;
    }
}
