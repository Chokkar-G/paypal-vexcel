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
import tech.paypal.app.ngo.vexcel.model.inventory.Inventory;

/**
 * Created by Chokkar on 9/2/2016.
 */

public class InventoryRecyclerViewAdapter extends RecyclerView.Adapter<InventoryRecyclerViewAdapter.ViewHolder> {
    private final TypedValue mTypedValue = new TypedValue();
    private ArrayList<Inventory> inventoryArrayList;
    private int[] mMaterialColors;
    private static final Random RANDOM = new Random();
    private Context mContext;
    private static OnClickListener mListener;
    private static OnLongClickListener mLongClickListener;
    private static boolean isLongClick;
    private static final String TAG = "InventoryRecyclerViewAdapter";

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public String mBoundString;
        final View mView;
        final MaterialLetterIcon mIcon;
        final TextView mNameView;
        final TextView quantityView;

        public ViewHolder(View view, InventoryRecyclerViewAdapter.OnClickListener mListener) {
            super(view);
            mView = view;
            mIcon = (MaterialLetterIcon) view.findViewById(R.id.thumbnail);
            mNameView = (TextView) view.findViewById(R.id.name_raw);
            quantityView = (TextView) view.findViewById(R.id.quantity_id);
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
        return String.valueOf(inventoryArrayList.get(position).getRawMaterial());
    }

    public InventoryRecyclerViewAdapter(Context context, List<Inventory> items) {
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        mMaterialColors = context.getResources().getIntArray(R.array.colors);
        inventoryArrayList = (ArrayList<Inventory>) items;
        Log.i(TAG , "list" + inventoryArrayList);
        mContext = context;
    }

    @Override
    public InventoryRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_inventory_item_card, parent, false);
        InventoryRecyclerViewAdapter.ViewHolder viewHolder = new InventoryRecyclerViewAdapter.ViewHolder(view, mListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final InventoryRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.mIcon.setInitials(true);
        holder.mIcon.setInitialsNumber(1);
        holder.mIcon.setLetterSize(18);
        holder.mBoundString = String.valueOf(inventoryArrayList.get(position).getRawMaterial());
        holder.mIcon.setShapeColor(mMaterialColors[RANDOM.nextInt(mMaterialColors.length)]);
        holder.mNameView.setText(""+inventoryArrayList.get(position).getRawMaterial());
        holder.mIcon.setLetter(""+inventoryArrayList.get(position).getRawMaterial());
        holder.quantityView.setText(""+inventoryArrayList.get(position).getQuantity());
    }

    @Override
    public int getItemCount() {
        return inventoryArrayList.size();
    }

    // Custom Recycler onClickListener
    public void setOnClickListener(InventoryRecyclerViewAdapter.OnClickListener listener) {
        this.mListener = listener;
    }

    public void setOnLongClickListener(InventoryRecyclerViewAdapter.OnLongClickListener listener) {
        this.mLongClickListener = listener;
    }

    public interface OnClickListener {
        void onClick(View view, int postion);
    }

    public interface OnLongClickListener {
        void onLongClick(View view, int position);
    }
}