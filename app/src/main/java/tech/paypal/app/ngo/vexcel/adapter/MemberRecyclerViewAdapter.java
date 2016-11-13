package tech.paypal.app.ngo.vexcel.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import tech.paypal.app.ngo.vexcel.R;
import tech.paypal.app.ngo.vexcel.model.member.MemberData;

/**
 * Created by Ravikumar on 9/2/2016.
 */

public class MemberRecyclerViewAdapter extends RecyclerView.Adapter<MemberRecyclerViewAdapter.ViewHolder> {
    private final TypedValue mTypedValue = new TypedValue();
    private ArrayList<MemberData> memberArrayList;
    private static final Random RANDOM = new Random();
    private Context mContext;
    private static OnClickListener mListener;
    private static OnLongClickListener mLongClickListener;
    private static boolean isLongClick;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        final View mView;
        final TextView memberNameView;
        final TextView adminView;

        public ViewHolder(View view, MemberRecyclerViewAdapter.OnClickListener mListener) {
            super(view);
            mView = view;
            memberNameView = (TextView) view.findViewById(R.id.memberNameView);
            adminView = (TextView) view.findViewById(R.id.adminView);
            mView.setOnClickListener(this);
            mView.setOnLongClickListener(this);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + memberNameView.getText();
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
        return memberArrayList.get(position).getMember();
    }

    public MemberRecyclerViewAdapter(Context context, ArrayList<MemberData> items) {
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        memberArrayList = items;
        mContext = context;
    }

    @Override
    public MemberRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.member_recycle_card, parent, false);
        MemberRecyclerViewAdapter.ViewHolder viewHolder = new MemberRecyclerViewAdapter.ViewHolder(view, mListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MemberRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.memberNameView.setText(memberArrayList.get(position).getMember());
        String role;

        if (Integer.parseInt(memberArrayList.get(position).getRole()) == 0) {
            role = "Director";
        } else
            role = "Secretary";
        holder.adminView.setText(role);
    }

    @Override
    public int getItemCount() {
        return memberArrayList.size();
    }

    // Custom Recycler onClickListener
    public void setOnClickListener(MemberRecyclerViewAdapter.OnClickListener listener) {
        this.mListener = listener;
    }

    public void setOnLongClickListener(MemberRecyclerViewAdapter.OnLongClickListener listener) {
        this.mLongClickListener = listener;
    }

    public interface OnClickListener {
        void onClick(View view, int postion);
    }

    public interface OnLongClickListener {
        void onLongClick(View view, int position);
    }
}