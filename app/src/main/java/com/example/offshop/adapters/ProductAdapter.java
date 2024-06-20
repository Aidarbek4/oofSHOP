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
import com.example.offshop.models.Product;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> productList;
    private List<CartItem> cartItemList = new ArrayList<>(); // Инициализация списка корзины
    private OnProductClickListener onProductClickListener;

    public ProductAdapter(List<Product> productList, OnProductClickListener listener) {
        this.productList = productList != null ? productList : new ArrayList<>(); // Проверка на null и инициализация
        this.onProductClickListener = listener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.bind(product);

        holder.itemView.setOnClickListener(v -> {
            if (onProductClickListener != null) {
                onProductClickListener.onProductClick(product);
            }
        });

        MaterialButton addButton = holder.itemView.findViewById(R.id.productCardButton);
        addButton.setOnClickListener(v -> addToCart(product));
    }

    // Метод для добавления продукта в корзину
    private void addToCart(Product product) {
        if (cartItemList == null) {
            cartItemList = new ArrayList<>();
        }

        boolean found = false;
        for (CartItem item : cartItemList) {
            if (item.getId() == product.getId()) {
                item.setQuantity(item.getQuantity() + 1);
                found = true;
                break;
            }
        }

        if (!found) {
            CartItem cartItem = new CartItem(product.getId(), product.getTitle(), product.getImage(), product.getPrice(), 1);
            cartItemList.add(cartItem);
        }

        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView nameTextView;
        private TextView priceTextView;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.productImage);
            nameTextView = itemView.findViewById(R.id.productName);
            priceTextView = itemView.findViewById(R.id.productPrice);
        }

        public void bind(Product product) {
            Glide.with(itemView.getContext())
                    .load(product.getImage())
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.error_placeholder)
                    .into(imageView);

            nameTextView.setText(product.getTitle());
            priceTextView.setText("$ " + product.getPrice());
        }
    }

    public interface OnProductClickListener {
        void onProductClick(Product product);
    }

    public List<CartItem> getCartItemList() {
        return cartItemList;
    }
}
