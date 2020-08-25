package com.example.database;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    //Initialize variables
    private List<MainData> dataList;
    private OnItemClickListener listener;
    private Activity context;
    private RoomDB database;

    //Create constructor
    public MainAdapter(Activity context, List<MainData> dataList) {
        this.context = context;
        this.dataList = dataList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Initialize view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_main, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        //Initialize main data
        final MainData data = dataList.get(position);
        //Initialize database
        database = RoomDB.getInstance(context);

        Bitmap bitmap = BitmapFactory.decodeFile(data.getBest());
        //set text on text view
        holder.textView.setText(data.getText());
        holder.NameView.setText(data.getName());
        holder.StyleView.setText(data.getStyle());
        holder.VolumeView.setText(data.getVolume());
        holder.BrewedView.setText(data.getBrewed());
        holder.BestView.setText(data.getExpdate());
        holder.IView.setImageBitmap(bitmap);

/*        holder.btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Initialize main data
                MainData d = dataList.get(holder.getAdapterPosition());
                //Delete text from database
                database.mainDao().delete(d);
                //Notify when data is deleted
                int position = holder.getAdapterPosition();
                dataList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, dataList.size());
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //Initialize variables
        TextView textView, NameView,StyleView,VolumeView,BrewedView,BestView;
        ImageView IView, btDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //Assign variable
            textView = itemView.findViewById(R.id.text_view);
            NameView = itemView.findViewById(R.id.name_view);
            StyleView = itemView.findViewById(R.id.style_view);
            VolumeView = itemView.findViewById(R.id.volume_view);
            BrewedView = itemView.findViewById(R.id.brewed_view);
            BestView = itemView.findViewById(R.id.best_view);
            //btDelete = itemView.findViewById(R.id.bt_delete);
            IView = itemView.findViewById(R.id.image_taken);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.OnItemClick(dataList.get(position));
                    }
                }
            });
        }
    }

    //For updating the data when the item is clicked
    public interface OnItemClickListener {
        void OnItemClick(MainData mainData);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
