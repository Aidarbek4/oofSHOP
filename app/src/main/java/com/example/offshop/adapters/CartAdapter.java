package com.example.offshop.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.offshop.R;
import com.example.offshop.models.CartItem;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<CartItem> cartItemList;
    private OnCartEmptyListener onCartEmptyListener;

    public CartAdapter(List<CartItem> cartItemList, OnCartEmptyListener listener) {
        this.cartItemList = cartItemList;
        this.onCartEmptyListener = listener;
        notifyCartEmpty();
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem cartItem = cartItemList.get(position);
        holder.bind(cartItem);
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    public void updateCartItemList(List<CartItem> cartItemList) {
        this.cartItemList = cartItemList;
        notifyDataSetChanged();
        notifyCartEmpty();
    }

    private void notifyCartEmpty() {
        if (onCartEmptyListener != null) {
            onCartEmptyListener.onCartEmpty(cartItemList.isEmpty());
        }
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView nameTextView;
        private TextView priceTextView;
        private TextView quantityTextView;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.orderImage);
            nameTextView = itemView.findViewById(R.id.orderName);
            priceTextView = itemView.findViewById(R.id.orderPrice);
            quantityTextView = itemView.findViewById(R.id.orderItemQuantity);
        }

        public void bind(CartItem cartItem) {
            Glide.with(itemView.getContext())
                    .load(cartItem.getImage())
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.error_placeholder)
                    .into(imageView);

            nameTextView.setText(cartItem.getName());
            priceTextView.setText("$ " + cartItem.getPrice());
            quantityTextView.setText("Quantity: " + cartItem.getQuantity());
        }
    }

    // Интерфейс для уведомления о пустоте корзины
    public interface OnCartEmptyListener {
        void onCartEmpty(boolean isEmpty);
    }
}
