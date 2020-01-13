package com.poum.orionpay;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.poum.orionpay.Adapter.TransactionAdapter;
import com.poum.orionpay.Models.Transaction;

import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;

@SuppressWarnings("ALL")
public class PaymentTransactionActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private TransactionAdapter transactionAdapter;
    private List<Transaction> mTransactions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_transaction);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mTransactions = new ArrayList<>();

        readTransactions();
    }

    private void readTransactions() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("DataTransaction");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mTransactions.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                    Transaction transaction = snapshot.getValue(Transaction.class);
                    mTransactions.add(transaction);
                }

                transactionAdapter = new TransactionAdapter(getApplicationContext(), mTransactions, false);
                recyclerView.setAdapter(transactionAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
