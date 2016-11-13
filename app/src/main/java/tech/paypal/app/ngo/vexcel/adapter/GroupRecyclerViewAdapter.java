package tech.paypal.app.ngo.vexcel.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.ivbaranov.mli.MaterialLetterIcon;

import java.util.ArrayList;
import java.util.Random;

import tech.paypal.app.ngo.vexcel.R;
import tech.paypal.app.ngo.vexcel.model.group.Groups;

/**
 * Created by Ravikumar on 9/2/2016.
 */

public class GroupRecyclerViewAdapter extends RecyclerView.Adapter<GroupRecyclerViewAdapter.ViewHolder> {
    private final TypedValue mTypedValue = new TypedValue();
    private ArrayList<Groups> groupCardArrayList;
    private int[] mMaterialColors;
    private static final Random RANDOM = new Random();
    private Context mContext;
    private static OnClickListener mListener;
    private static OnLongClickListener mLongClickListener;
    private static boolean isLongClick;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public String mBoundString;
        final View mView;
        final MaterialLetterIcon mIcon;
        final TextView mNameView;
        final TextView mPhoneView;
        final TextView mPublicView;

        public ViewHolder(View view, GroupRecyclerViewAdapter.OnClickListener mListener) {
            super(view);
            mView = view;
            mIcon = (MaterialLetterIcon) view.findViewById(R.id.thumbnail);
            mNameView = (TextView) view.findViewById(R.id.nameAdapter);
            mPhoneView = (TextView) view.findViewById(R.id.phoneNumberAdapter);
            mPublicView = (TextView) view.findViewById(R.id.publicView);
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
        return groupCardArrayList.get(position).getGroupName();
    }

    public GroupRecyclerViewAdapter(Context context, ArrayList<Groups> items) {
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        mMaterialColors = context.getResources().getIntArray(R.array.colors);
        groupCardArrayList = items;
        mContext = context;
    }

    @Override
    public GroupRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_card, parent, false);
        GroupRecyclerViewAdapter.ViewHolder viewHolder = new GroupRecyclerViewAdapter.ViewHolder(view, mListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final GroupRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.mIcon.setInitials(true);
        holder.mIcon.setInitialsNumber(1);
        holder.mIcon.setLetterSize(18);
        holder.mBoundString = groupCardArrayList.get(position).getGroupName();
        holder.mIcon.setShapeColor(mMaterialColors[RANDOM.nextInt(mMaterialColors.length)]);
        holder.mNameView.setText(groupCardArrayList.get(position).getGroupName());
        holder.mIcon.setLetter(groupCardArrayList.get(position).getGroupName());
        holder.mPhoneView.setText(groupCardArrayList.get(position).getDesc());
        String publicText;
        if (groupCardArrayList.get(position).getIsPublic().equals("true")) {
            publicText = "Public";
        } else
            publicText = "Private";
        holder.mPublicView.setText(publicText);
    }

    @Override
    public int getItemCount() {
        return groupCardArrayList.size();
    }

    // Custom Recycler onClickListener
    public void setOnClickListener(GroupRecyclerViewAdapter.OnClickListener listener) {
        this.mListener = listener;
    }

    public void setOnLongClickListener(GroupRecyclerViewAdapter.OnLongClickListener listener) {
        this.mLongClickListener = listener;
    }

    public interface OnClickListener {
        void onClick(View view, int postion);
    }

    public interface OnLongClickListener {
        void onLongClick(View view, int position);
    }
}