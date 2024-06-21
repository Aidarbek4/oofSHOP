package com.example.offshop.ui.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.offshop.R;
import com.example.offshop.databinding.FragmentProductDesBinding;
import com.example.offshop.models.Product;

public class ProductDesFragment extends Fragment {

    private FragmentProductDesBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProductDesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
                navController.navigate(R.id.action_productDesFragment_to_navigation_home);
            }
        });
        if (getArguments() != null) {
            Product product = getArguments().getParcelable("product");
            if (product != null) {
                Glide.with(requireContext())
                        .load(product.getImage())
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.error_placeholder)
                        .into(binding.descriptionImage);
                binding.categorycr.setTextDirection(product.getCategory());
                Log.d("product", product.toString());
                binding.titledis.setText(product.getTitle());
                binding.descriptionText.setText(product.getDescription());
                binding.descriptionPrice.setText(String.valueOf(product.getPrice()));


            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}