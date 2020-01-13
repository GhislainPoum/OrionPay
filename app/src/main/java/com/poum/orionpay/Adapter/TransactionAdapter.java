package com.poum.orionpay.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.poum.orionpay.Models.Transaction;
import com.poum.orionpay.R;

import java.util.List;

@SuppressWarnings("ALL")
public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {

    private Context mContext;
    private List<Transaction> mTransactions;

    public TransactionAdapter(Context mContext, List<Transaction> mTransactions, boolean b) {
        this.mContext = mContext;
        this.mTransactions = mTransactions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_transaction, parent, false);
        return new TransactionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final Transaction transaction = mTransactions.get(position);

        holder.main_no_id.setText(transaction.getMain_no_id());
        holder.main_smsField_id.setText(transaction.getMain_smsField_id());

    }

    @Override
    public int getItemCount() {
        return mTransactions.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView main_no_id;
        public TextView main_smsField_id;


        public ViewHolder(View itemView){
            super(itemView);

            main_no_id = itemView.findViewById(R.id.main_no_id);
            main_smsField_id = itemView.findViewById(R.id.main_smsField_id);



        }
    }
}
