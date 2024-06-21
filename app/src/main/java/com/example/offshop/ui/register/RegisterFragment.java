package com.example.offshop.ui.register;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.offshop.R;
import com.example.offshop.databinding.FragmentLoginBinding;
import com.example.offshop.databinding.FragmentRegisterBinding;
import com.example.offshop.models.User;
import com.example.offshop.remotedata.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterFragment extends Fragment {
    private FragmentRegisterBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container,
                             @NonNull Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        binding.tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
                navController.navigate(R.id.action_registerFragment_to_loginFragment);
            }
        });

        return view;
    }

    private void registerUser() {
        String username = binding.registerusernametx.getText().toString().trim();
        String email = binding.registerEmail.getText().toString().trim();
        String password = binding.registerPassword.getText().toString().trim();

        if (username.isEmpty()) {
            binding.registerusernametx.setError("Enter last name");
            return;
        }
        if (email.isEmpty()) {
            binding.registerEmail.setError("Enter email");
            return;
        }
        if (password.isEmpty()) {
            binding.registerPassword.setError("Enter password");
            return;
        }

        User user = new User(username, email, password);
        RetrofitClient.getInstance().getApi().registrationNewUser(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    // Registration successful
                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
                    navController.navigate(R.id.action_registerFragment_to_loginFragment);
                } else {
                    // Registration failed
                    binding.errorre.setText("Failed to register. Please try again.");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // Registration failed
                binding.errorre.setText("Failed to connect to the server. Please check your network connection and try again.");
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
