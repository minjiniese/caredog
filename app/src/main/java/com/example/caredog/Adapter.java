package com.example.caredog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.Holder> {

    private final ArrayList<String> list;

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView time;
        ImageButton del_time;

        public Holder(@NonNull View itemView) {
            super(itemView);

            time = itemView.findViewById(R.id.clock);
            del_time = itemView.findViewById(R.id.del);
            del_time.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            int pos = getAdapterPosition();
            if(pos != RecyclerView.NO_POSITION) {
                list.remove(pos);
                notifyItemRemoved(pos);
                //갱신
                notifyItemChanged(pos);
            }

        }
    }

    Adapter(ArrayList<String> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.listview, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.time.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}

