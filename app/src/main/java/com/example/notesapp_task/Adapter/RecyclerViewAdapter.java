package com.example.notesapp_task.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.notesapp_task.ModelClass.ExpenseModel;
import com.example.notesapp_task.R;
import com.google.android.material.card.MaterialCardView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private ArrayList<ExpenseModel> arrayList;
    private OnClickListener onClickListener;
    String type;
    private Context context;

    public RecyclerViewAdapter(ArrayList<ExpenseModel> arrayList, String type, OnClickListener onClickListener, Context context) {

        this.arrayList   = arrayList;
        this.type = type;
        this.onClickListener = onClickListener;
        this.context = context;
    }

    public void changeType(String type) {
        this.type = type;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        switch (type) {
            case "1": {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_list, parent, false);
                return new ViewHolder(view);
            }
            case "2": {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_grid, parent, false);
                return new ViewHolder(view);

            }
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_staggered, parent, false);
                return new ViewHolder(view);

        }
    }

    @SuppressLint({"ResourceAsColor", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {


        ExpenseModel expenseModel = arrayList.get(position);

        holder.title.setText(expenseModel.getTitleNotes());
        holder.description.setText(expenseModel.getDescriptionNotes());



//------------------------------time to time ago set------------------------------------------------
        try {
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat format=new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
            Date past = format.parse(expenseModel.getCreateDate());
            Date now=new Date();

            long seconds= TimeUnit.MILLISECONDS.toSeconds(now.getTime() - past.getTime());
            long minutes=TimeUnit.MILLISECONDS.toMinutes(now.getTime() - past.getTime());
            long hours=TimeUnit.MILLISECONDS.toHours(now.getTime() - past.getTime());
            long days=TimeUnit.MILLISECONDS.toDays(now.getTime() - past.getTime());


            if (seconds<60)
            {
                holder.date.setText(seconds+" second ago");
            } else if (minutes<60)
            {
                holder.date.setText(minutes+" minutes ago");
            } else if (hours<24)
            {
                holder.date.setText(hours+" hours ago");
            } else
            {
                holder.date.setText(days+" days ago");
                
            }

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }



//        int a= position%4;


        if (type.equals("1")) {

            holder.deleteNotesList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onDelete(holder.getAdapterPosition());
                }
            });
            Glide.with(context).load(expenseModel.getImageUri()).into(holder.imgList);
            holder.listCardView.setCardBackgroundColor(expenseModel.getColor());

        } else if (type.equals("2")) {
            holder.deleteNotesList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onDelete(holder.getAdapterPosition());
                }
            });

            holder.gridCardView.setCardBackgroundColor(expenseModel.getColor());

        } else if (type.equals("3")) {

            holder.staggeredCardView.setCardBackgroundColor(expenseModel.getColor());

        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onClickListener.onClick(holder.getAdapterPosition(), expenseModel);
            }
        });

    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public interface OnClickListener {
        void onClick(int position, ExpenseModel model);

        void onDelete(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title, description, date;
        ImageView imgList, deleteNotesList;
        CardView listCardView;
        MaterialCardView gridCardView, staggeredCardView;

        public ViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.tvTitleRead);
            description = itemView.findViewById(R.id.tvDescriptionRead);
            listCardView = itemView.findViewById(R.id.listCard);
            gridCardView = itemView.findViewById(R.id.gridCard);
            staggeredCardView = itemView.findViewById(R.id.staggeredCard);
            date = itemView.findViewById(R.id.createDate);
            imgList = itemView.findViewById(R.id.imgList);
            deleteNotesList = itemView.findViewById(R.id.deleteNotesList);
        }
    }
}
