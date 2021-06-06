package pk.edu.fccollege.assignment_1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Recycle_view_adapter extends RecyclerView.Adapter<Recycle_view_adapter.Viewholder> {
    private ArrayList<Integer> pics_stuff;
    private Context context;
    private  OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        //void onItemClick(int position);
        void onItemClick(int position,View view);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        onItemClickListener=listener;
    }
    public Recycle_view_adapter(ArrayList<Integer> pics_stuff, Context context) {
        this.pics_stuff = pics_stuff;
        this.context = context;

    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adap_screen_pic_activity_layout,parent,false);

        Viewholder holder = new Viewholder(view,onItemClickListener);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        int name = pics_stuff.get(position);
        holder.img.setImageResource(name);

    }
    @Override
    public int getItemCount() {
        return pics_stuff.size();
    }

    public class Viewholder extends  RecyclerView.ViewHolder {

        ImageView img;

        public Viewholder(@NonNull View itemView,final OnItemClickListener listener) {
            super(itemView);
            img = itemView.findViewById(R.id.recycleview);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position,v);

                        }
                    }
                }
            });
        }

    }
}


