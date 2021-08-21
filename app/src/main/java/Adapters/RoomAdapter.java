package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.chattry.R;

import java.util.ArrayList;
import java.util.List;

import Models.Room;

public class RoomAdapter extends ArrayAdapter<Room> {

    Context context;
    int resource;
    ArrayList<Room> rooms;

    public RoomAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Room> rooms) {
        super(context, resource, rooms);

        this.context=context;
        this.resource=resource;
        this.rooms=rooms;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = LayoutInflater.from(this.context).inflate(resource,null);

        Room room = rooms.get(position);

        TextView lblNameOfGroup = v.findViewById(R.id.lblNameOfRoom);

        lblNameOfGroup.setText(room.getRoomName());

        return v;
    }
}
