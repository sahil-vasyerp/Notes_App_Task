package com.example.notesapp_task.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesapp_task.ModelClass.ExpenseModel;
import com.example.notesapp_task.R;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private ArrayList<ExpenseModel> arrayList;
    private OnClickListener onClickListener;
    String type;
    private Context context;

    public RecyclerViewAdapter(ArrayList<ExpenseModel> arrayList, String type, OnClickListener onClickListener, Context context) {

        this.arrayList = arrayList;
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

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder,  int position) {


        ExpenseModel expenseModel = arrayList.get(position);

        holder.title.setText(expenseModel.getTitleNotes());
        holder.description.setText(expenseModel.getDescriptionNotes());
        holder.date.setText(expenseModel.getCreateDate());


//        int a= position%4;


                if (type.equals("1")) {

                    holder.listCardView.setCardBackgroundColor(expenseModel.getColor());

                } else if (type.equals("2")) {

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
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title, description,date ;
        CardView listCardView;
        MaterialCardView gridCardView,staggeredCardView;
        public ViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.tvTitleRead);
            description = itemView.findViewById(R.id.tvDescriptionRead);
            listCardView = itemView.findViewById(R.id.listCard);
            gridCardView = itemView.findViewById(R.id.gridCard);
            staggeredCardView=itemView.findViewById(R.id.staggeredCard);
            date=itemView.findViewById(R.id.createDate);
        }
    }
}
