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

public class settingActivity_Adapter extends RecyclerView.Adapter<settingActivity_Adapter.Holder> {

    private final ArrayList<String> list;
    private final String list_name;
    Context mContext = settingActivity.mContext;
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
            if (pos != RecyclerView.NO_POSITION) {
                list.remove(pos);
                notifyItemRemoved(pos);
                //갱신
                notifyItemChanged(pos);

                String time;

                //밥 시간 삭제
                if (list_name.equals("food")) {
                    int num_food = settingActivity_preferenceData.getJ(mContext, "num_food");
                    for (int i = pos + 1; i <= num_food; i++) {
                        time = settingActivity_preferenceData.getString(mContext, "time_food" + i);
                        if (!time.equals("")) {
                            settingActivity_preferenceData.setString(mContext, "time_food" + pos, time);
                        }
                    }
                    settingActivity_preferenceData.removeKey(mContext, "time_food" + num_food);
                    settingActivity_preferenceData.setJ(mContext, "num_food", --num_food);
                }
                // 약 시간 삭제
                else {
                    int num_medicine = settingActivity_preferenceData.getJ(mContext, "num_medicine");
                    for (int i = pos + 1; i <= num_medicine; i++) {
                        time = settingActivity_preferenceData.getString(mContext, "time_medicine" + i);
                        if (!time.equals("")) {
                            settingActivity_preferenceData.setString(mContext, "time_medicine" + pos, time);
                        }
                    }
                    settingActivity_preferenceData.removeKey(mContext, "time_medicine" + num_medicine);
                    settingActivity_preferenceData.setJ(mContext, "num_medicine", --num_medicine);
                }
            }
        }
    }

    settingActivity_Adapter(String list_name, ArrayList<String> list) {
        this.list = list;
        this.list_name = list_name;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recyclerview, parent, false);
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
