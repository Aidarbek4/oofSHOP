package com.example.offshop.ui.cart;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.offshop.R;
import com.example.offshop.adapters.CartAdapter;
import com.example.offshop.databinding.FragmentCartBinding;
import com.example.offshop.models.CartItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {
    private FragmentCartBinding binding;
    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private List<CartItem> cartItemList = new ArrayList<>();
    private TextView emptyCartTextView;

    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(inflater, container, false);
        // Генерация фейковых данных
//        double sum = generateFakeData();
//
//        // Инициализация текстового поля для пустой корзины
//        emptyCartTextView = binding.emptyCartTextView;
//
//        // Установка RecyclerView
//        recyclerView = binding.orderRecycler;
//        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
//
//        // Инициализация и установка CartAdapter
//        cartAdapter = new CartAdapter(cartItemList, isEmpty -> {
//            emptyCartTextView.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
//        });
//        recyclerView.setAdapter(cartAdapter);
//
//        ImageButton button = binding.buttonBack;
//        button.setOnClickListener(v -> {
//            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
//            navController.navigate(R.id.action_navigation_cart_to_navigation_home);
//        });
//
//        // Проверка на пустоту корзины и обновление отображения
//        checkCartEmpty();

        return binding.getRoot();
    }

    // Метод для генерации фейковых данных корзины
    private double generateFakeData() {
        String[] applianceNames = {"Haier 489L French Door Frost Free Fridge with Water Dispenser Black HRF520FHC",
                "Samsung 427L Bottom Mount Fridge SRL456LS",
                "Haier 574L S+ Three Door Side by Side Fridge Non-Plumbed Water Dispenser Black HRF575XHC",
                "CHiQ 348L Top Mount Fridge White CTM348NW5E"};
        String[] applianceImages = {
                "https://www.appliancesonline.com.au/ak/b/6/9/a/b69a0113bf29363a032bbcfd231c27bfbb7f1b9e_haier_492l_french_door_fridge_with_water_dispenser_black_hrf520fhc_1_288f9b45_high-high.jpeg",
                "https://www.appliancesonline.com.au/ak/f/9/a/3/f9a38254ac5c6ce1b27d0482cbf29fad90a59486_Samsung_458L_Bottom_Mount_Fridge_BSRL456LS_angle_high-high.jpeg",
                "https://www.appliancesonline.com.au/ak/2/f/2/b/2f2baa74237ec5825c854e285c61432be4c30574_HRF575XHC_2-high.jpeg",
                "https://www.appliancesonline.com.au/ak/9/4/f/f/94ff36eb4a1e28e9a51901455810b9527b6d0d76_CTM348NW5E_2-high.jpeg"
        };
        double[] appliancePrices = {1417.0, 1060.0, 1396.0, 647.0};
        int[] applianceQuantities = {50, 10, 15, 20};

        double sum = 0.0;
        for (double price : appliancePrices) {
            sum += price;
        }

        double tax = sum * 0.01;
        binding.subtotalSum.setText(String.valueOf(sum));
        binding.taxSum.setText(String.valueOf(tax));
        binding.totalSum.setText(String.valueOf(sum + tax));

        for (int i = 0; i < applianceNames.length; i++) {
            cartItemList.add(new CartItem(i, applianceNames[i], applianceImages[i], appliancePrices[i], applianceQuantities[i]));
        }

        return sum;
    }

    // Метод для проверки на пустоту корзины и обновления отображения
    private void checkCartEmpty() {
        boolean isEmpty = cartItemList.isEmpty();
        emptyCartTextView.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
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
}
