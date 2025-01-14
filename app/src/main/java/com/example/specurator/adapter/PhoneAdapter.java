package com.example.specurator.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.specurator.CompareActivity;
import com.example.specurator.DetailActivity;
import com.example.specurator.R;
import com.example.specurator.model.PhoneModel;

import java.util.List;

public class PhoneAdapter extends RecyclerView.Adapter<PhoneAdapter.PhoneViewHolder> {
    private List<PhoneModel> phoneList;
    private Context context;
    private Class targetActivity;


    public PhoneAdapter(List<PhoneModel> phoneList, Class targetActivity) {
        this.phoneList = phoneList;
        this.targetActivity = targetActivity;
    }


    public PhoneAdapter(List<PhoneModel> phoneList) {
        this.phoneList = phoneList;
        this.targetActivity = DetailActivity.class;
    }

    public class PhoneViewHolder extends RecyclerView.ViewHolder {
        ImageView itemIV;
        TextView itemTV;

        ConstraintLayout containerCL;

        public PhoneViewHolder(@NonNull View itemView) {
            super(itemView);

            itemIV = itemView.findViewById(R.id.itemIV);
            itemTV = itemView.findViewById(R.id.itemTV);
            containerCL = itemView.findViewById(R.id.containerCL);
        }
    }




    @NonNull
    @Override
    public PhoneAdapter.PhoneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();


        View view = LayoutInflater.from(context).inflate(R.layout.list_items, parent, false);
        PhoneViewHolder holder = new PhoneViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PhoneAdapter.PhoneViewHolder holder, int position) {
        String currentName = phoneList.get(position).getName();
        String currentImage = phoneList.get(position).getImage();

        holder.itemTV.setText(currentName);
        Glide.with(context).load(currentImage).into(holder.itemIV);

        PhoneModel phone = phoneList.get(position);

        holder.containerCL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneModel phoneInDetail;
                PhoneModel phone = phoneList.get(holder.getAdapterPosition());
                Log.d("phoneAdapter", "onClick: phone2 sent");

                Intent moveIntent = new Intent(context, targetActivity);

                moveIntent.putExtra("phoneData", phone);
                if (context instanceof DetailActivity) {
                    phoneInDetail = ((DetailActivity) context).getPhone();
                    moveIntent.putExtra("phoneInDetailData", phoneInDetail);
                    Log.d("phoneAdapter", "onClick: phone1 sent");
                }

                context.startActivity(moveIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return phoneList.size();
    }

    public void setPhoneList(List<PhoneModel> phoneList) {
        this.phoneList = phoneList;
    }
}
