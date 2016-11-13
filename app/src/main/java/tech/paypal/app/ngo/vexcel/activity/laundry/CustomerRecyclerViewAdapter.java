package tech.paypal.app.ngo.vexcel.activity.laundry;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.ivbaranov.mli.MaterialLetterIcon;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import tech.paypal.app.ngo.vexcel.R;
import tech.paypal.app.ngo.vexcel.model.customers.Customer;

/**
 * Created by Chokkar on 9/2/2016.
 */

public class CustomerRecyclerViewAdapter extends RecyclerView.Adapter<CustomerRecyclerViewAdapter.ViewHolder> {
    private final TypedValue mTypedValue = new TypedValue();
    private ArrayList<Customer> customerAList;
    private int[] mMaterialColors;
    private static final Random RANDOM = new Random();
    private Context mContext;
    private static OnClickListener mListener;
    private static OnLongClickListener mLongClickListener;
    private static boolean isLongClick;
    private static final String TAG = "CustomerRecyclerViewAdapter";

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public String mBoundString;
        final View mView;
        final MaterialLetterIcon mIcon;
        final TextView mNameView;
        final TextView mMobileView;
        final TextView mAddressView;
        final TextView emailView;

        public ViewHolder(View view, CustomerRecyclerViewAdapter.OnClickListener mListener) {
            super(view);
            mView = view;
            mIcon = (MaterialLetterIcon) view.findViewById(R.id.thumbnail);
            mNameView = (TextView) view.findViewById(R.id.nameAdapter);
            mMobileView = (TextView) view.findViewById(R.id.mobileNumber);
            mAddressView = (TextView) view.findViewById(R.id.addressid);
            emailView = (TextView) view.findViewById(R.id.emailid);
            mView.setOnClickListener(this);
            mView.setOnLongClickListener(this);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNameView.getText();
        }

        @Override
        public void onClick(View view) {
            if (mListener != null) {
                if (isLongClick) {
                    isLongClick = false;
                    return;
                }
                mListener.onClick(view, getAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick(View view) {
            if (mLongClickListener != null) {
                mLongClickListener.onLongClick(view, getAdapterPosition());
                isLongClick = true;
            }
            return false;
        }
    }

    public String getValueAt(int position) {
        return customerAList.get(position).getName();
    }

    public CustomerRecyclerViewAdapter(Context context, List<Customer> items) {
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        mMaterialColors = context.getResources().getIntArray(R.array.colors);
        customerAList = (ArrayList<Customer>) items;
        Log.i(TAG , "list" + customerAList);
        mContext = context;
    }

    @Override
    public CustomerRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_customer_item_card, parent, false);
        CustomerRecyclerViewAdapter.ViewHolder viewHolder = new CustomerRecyclerViewAdapter.ViewHolder(view, mListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CustomerRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.mIcon.setInitials(true);
        holder.mIcon.setInitialsNumber(1);
        holder.mIcon.setLetterSize(18);
        holder.mBoundString = customerAList.get(position).getName();
        holder.mIcon.setShapeColor(mMaterialColors[RANDOM.nextInt(mMaterialColors.length)]);
        holder.mNameView.setText(customerAList.get(position).getName());
        holder.mIcon.setLetter(customerAList.get(position).getName());
        holder.mMobileView.setText(customerAList.get(position).getPhoneNumber());
        holder.mAddressView.setText(customerAList.get(position).getAddress());
        holder.emailView.setText(customerAList.get(position).getEmailId());
    }

    @Override
    public int getItemCount() {
        return customerAList.size();
    }

    // Custom Recycler onClickListener
    public void setOnClickListener(CustomerRecyclerViewAdapter.OnClickListener listener) {
        this.mListener = listener;
    }

    public void setOnLongClickListener(CustomerRecyclerViewAdapter.OnLongClickListener listener) {
        this.mLongClickListener = listener;
    }

    public interface OnClickListener {
        void onClick(View view, int postion);
    }

    public interface OnLongClickListener {
        void onLongClick(View view, int position);
    }
}