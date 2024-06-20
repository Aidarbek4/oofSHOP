package com.example.offshop.ui.cart;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.offshop.R;
import com.example.offshop.adapters.CartAdapter;
import com.example.offshop.models.CartItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {

    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private List<CartItem> cartItemList;
    private TextView emptyCartTextView;

    public CartFragment() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        // Инициализация списка корзины
        cartItemList = new ArrayList<>();

        // Инициализация текстового поля для пустой корзины
        emptyCartTextView = view.findViewById(R.id.emptyCartTextView);

        // Установка RecyclerView
        recyclerView = view.findViewById(R.id.orderRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Инициализация и установка CartAdapter
        cartAdapter = new CartAdapter(cartItemList, new CartAdapter.OnCartEmptyListener() {
            @Override
            public void onCartEmpty(boolean isEmpty) {
                emptyCartTextView.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
            }
        });
        recyclerView.setAdapter(cartAdapter);

        // Проверка на пустоту корзины и обновление отображения
        checkCartEmpty();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.nav_view);
        bottomNavigationView.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.nav_view);
        bottomNavigationView.setVisibility(View.VISIBLE);
    }

    // Метод для обновления списка корзины в CartAdapter
    public void updateCartItemList(List<CartItem> cartItemList) {
        this.cartItemList = cartItemList;
        cartAdapter.updateCartItemList(cartItemList);
        checkCartEmpty(); // Проверка на пустоту корзины
    }

    // Метод для проверки на пустоту корзины и обновления отображения
    private void checkCartEmpty() {
        boolean isEmpty = cartItemList.isEmpty();
        emptyCartTextView.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
    }
}
