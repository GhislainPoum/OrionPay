package com.poum.orionpay.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.poum.orionpay.PaymentMode.MomoActivity;
import com.poum.orionpay.PaymentMode.OrangeActivity;
import com.poum.orionpay.R;

@SuppressWarnings("ALL")
public class ActionBottomDialogFragment extends BottomSheetDialogFragment
        implements View.OnClickListener{


    public static final String TAG = "ActionBottomDialog";

    private ItemClickListener mListener;

    ImageView orange, momo;

    public static ActionBottomDialogFragment newInstance() {
        return new ActionBottomDialogFragment();
    }

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.bottom_sheet, container, false);

        orange = view.findViewById(R.id.orange);
        momo = view.findViewById(R.id.momo);

        orange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                goOrangePay();
            }
        });

        momo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                goMomoPay();
            }
        });

        return view;
    }

    private void goOrangePay() {
        Intent intent = new Intent(getContext(), OrangeActivity.class);
        startActivity(intent);
    }

    private void goMomoPay() {
        Intent intent = new Intent(getContext(), MomoActivity.class);
        startActivity(intent);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ItemClickListener) {
            mListener = (ItemClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ItemClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override public void onClick(View view) {
        TextView tvSelected = (TextView) view;
        mListener.onItemClick(tvSelected.getText().toString());
        dismiss();
    }

    public interface ItemClickListener {
        void onItemClick(String item);
    }
}
