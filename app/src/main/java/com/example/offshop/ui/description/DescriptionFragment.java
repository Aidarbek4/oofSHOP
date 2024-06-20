package com.example.offshop.ui.description;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.offshop.R;
import com.example.offshop.databinding.FragmentDescriptionBinding;
import com.example.offshop.databinding.ItemDescBinding;
import com.example.offshop.models.Product;
import com.example.offshop.remotedata.Api;
import com.example.offshop.remotedata.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DescriptionFragment extends Fragment {

    private ItemDescBinding binding;
    private ImageView imageView;
    private TextView nameTextView;
    private TextView priceTextView;

    private Api productService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ItemDescBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        imageView = binding.descriptionImage;
        nameTextView = binding.descriptionText;
        priceTextView = binding.descriptionPrice;

        productService = RetrofitClient.getInstance().getApi();

        // Получение аргумента (id продукта) из Bundle
        if (getArguments() != null) {
            String productId = getArguments().getString("productId");
            if (productId != null) {
                loadProduct(productId);
            }
        }

        return root;
    }

    private void loadProduct(String productId) {
        // Запрос к API для получения продукта по его id
        Call<Product> call = productService.getProductById(productId);
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Product product = response.body();
                    bindProductData(product);
                } else {
                    // Обработка неуспешного ответа или ошибки
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                // Обработка ошибки при выполнении запроса
            }
        });
    }

    private void bindProductData(Product product) {
        // Отображение данных продукта в интерфейсе фрагмента
        Glide.with(requireContext())
                .load(product.getImage())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error_placeholder)
                .into(imageView);

        nameTextView.setText(product.getTitle());
        priceTextView.setText("$ " + product.getPrice());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
