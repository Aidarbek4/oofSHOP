package com.example.offshop.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.offshop.R;
import com.example.offshop.adapters.CategoryAdapter;
import com.example.offshop.adapters.ProductAdapter;
import com.example.offshop.databinding.FragmentHomeBinding;
import com.example.offshop.models.Category;
import com.example.offshop.models.Product;
import com.example.offshop.remotedata.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements ProductAdapter.OnProductClickListener, CategoryAdapter.OnCategoryClickListener {

    private FragmentHomeBinding binding;
    private RecyclerView productRecyclerView, categoryRecyclerView;
    private ProductAdapter productAdapter;
    private CategoryAdapter categoryAdapter;
    private TextView emptyProductsView, emptyCategoriesView;
    private static final String TAG = "HomeFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Инициализация TextView для пустых списков
        emptyProductsView = root.findViewById(R.id.empty_products_view);
        emptyCategoriesView = root.findViewById(R.id.empty_categories_view);

        productRecyclerView = root.findViewById(R.id.productRecycler);
        productRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        categoryRecyclerView = root.findViewById(R.id.categoryRecycler);
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        fetchProducts();
        fetchCategories();

        return root;
    }

    private void fetchProducts() {
        Log.d(TAG, "Fetching products...");
        Call<List<Product>> call = RetrofitClient.getInstance().getApi().getStoreProducts();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "Products fetched successfully");
                    if (response.body().isEmpty()) {
                        emptyProductsView.setVisibility(View.VISIBLE);
                        productRecyclerView.setVisibility(View.GONE);
                    } else {
                        emptyProductsView.setVisibility(View.GONE);
                        productRecyclerView.setVisibility(View.VISIBLE);
                        productAdapter = new ProductAdapter(response.body(), HomeFragment.this);
                        productRecyclerView.setAdapter(productAdapter);
                    }
                } else {
                    Log.e(TAG, "Response unsuccessful: " + response.code());
                    emptyProductsView.setVisibility(View.VISIBLE);
                    productRecyclerView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e(TAG, "API call failed: ", t);
                emptyProductsView.setVisibility(View.VISIBLE);
                productRecyclerView.setVisibility(View.GONE);
            }
        });
    }

    private void fetchCategories() {
        Log.d(TAG, "Fetching categories...");
        Call<List<Category>> call = RetrofitClient.getInstance().getApi().getAllCategories();
        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "Categories fetched successfully");
                    if (response.body().isEmpty()) {
                        emptyCategoriesView.setVisibility(View.VISIBLE);
                        categoryRecyclerView.setVisibility(View.GONE);
                    } else {
                        emptyCategoriesView.setVisibility(View.GONE);
                        categoryRecyclerView.setVisibility(View.VISIBLE);
                        categoryAdapter = new CategoryAdapter(response.body(), HomeFragment.this);
                        categoryRecyclerView.setAdapter(categoryAdapter);
                    }
                } else {
                    Log.e(TAG, "Response unsuccessful: " + response.code());
                    emptyCategoriesView.setVisibility(View.VISIBLE);
                    categoryRecyclerView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Log.e(TAG, "API call failed: ", t);
                emptyCategoriesView.setVisibility(View.VISIBLE);
                categoryRecyclerView.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    // Обработка клика на продукт
    @Override
    public void onProductClick(Product product) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("product", product);
        Navigation.findNavController(requireView()).navigate(R.id.action_navigation_home_to_navigation_description, bundle);
    }

    // Обработка клика на категорию
    @Override
    public void onCategoryClick(Category category) {
        // Реализуйте логику обработки клика на категорию, например, фильтрацию продуктов
        Log.d(TAG, "Clicked category: " + category.getName());
    }
}
