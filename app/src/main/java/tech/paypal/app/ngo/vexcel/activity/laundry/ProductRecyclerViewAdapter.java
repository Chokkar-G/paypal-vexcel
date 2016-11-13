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
import tech.paypal.app.ngo.vexcel.model.products.Product;

/**
 * Created by Chokkar on 9/2/2016.
 */

public class ProductRecyclerViewAdapter extends RecyclerView.Adapter<ProductRecyclerViewAdapter.ViewHolder> {
    private final TypedValue mTypedValue = new TypedValue();
    private ArrayList<Product> ProductAList;
    private int[] mMaterialColors;
    private static final Random RANDOM = new Random();
    private Context mContext;
    private static OnClickListener mListener;
    private static OnLongClickListener mLongClickListener;
    private static boolean isLongClick;
    private static final String TAG = "ProductRecyclerViewAdapter";

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public String mBoundString;
        final View mView;
        final MaterialLetterIcon mIcon;
        final TextView nameView;
        final TextView priceView;
        final TextView shelfView;
        final TextView batchView;

        public ViewHolder(View view, ProductRecyclerViewAdapter.OnClickListener mListener) {
            super(view);
            mView = view;
            mIcon = (MaterialLetterIcon) view.findViewById(R.id.thumbnail);
            nameView = (TextView) view.findViewById(R.id.name_price);
            priceView = (TextView) view.findViewById(R.id.price_id);
            shelfView = (TextView) view.findViewById(R.id.shelf_id);
            batchView = (TextView) view.findViewById(R.id.batch_id);
            mView.setOnClickListener(this);
            mView.setOnLongClickListener(this);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + nameView.getText();
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
        return ProductAList.get(position).getLabel();
    }

    public ProductRecyclerViewAdapter(Context context, List<Product> items) {
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        mMaterialColors = context.getResources().getIntArray(R.array.colors);
        ProductAList = (ArrayList<Product>) items;
        Log.i(TAG , "list" + ProductAList);
        mContext = context;
    }

    @Override
    public ProductRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_product_item_card, parent, false);
        ProductRecyclerViewAdapter.ViewHolder viewHolder = new ProductRecyclerViewAdapter.ViewHolder(view, mListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ProductRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.mIcon.setInitials(true);
        holder.mIcon.setInitialsNumber(1);
        holder.mIcon.setLetterSize(18);
        holder.mBoundString = ProductAList.get(position).getLabel();
        holder.mIcon.setShapeColor(mMaterialColors[RANDOM.nextInt(mMaterialColors.length)]);
        holder.nameView.setText(ProductAList.get(position).getLabel());
        holder.mIcon.setLetter(ProductAList.get(position).getLabel());
        holder.priceView.setText(""+ProductAList.get(position).getPrice());
        holder.shelfView.setText(""+ProductAList.get(position).getShelfLifeInDays());
        holder.batchView.setText(""+ProductAList.get(position).getBatchSize());
    }

    @Override
    public int getItemCount() {
        return ProductAList.size();
    }

    // Custom Recycler onClickListener
    public void setOnClickListener(ProductRecyclerViewAdapter.OnClickListener listener) {
        this.mListener = listener;
    }

    public void setOnLongClickListener(ProductRecyclerViewAdapter.OnLongClickListener listener) {
        this.mLongClickListener = listener;
    }

    public interface OnClickListener {
        void onClick(View view, int postion);
    }

    public interface OnLongClickListener {
        void onLongClick(View view, int position);
    }
}