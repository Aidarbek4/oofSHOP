package com.example.offshop.ui.login;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.offshop.R;
import com.example.offshop.databinding.FragmentLoginBinding;
import com.example.offshop.liveData.Token;
import com.example.offshop.liveData.UserViewModel;
import com.example.offshop.models.CurrentUser;
import com.example.offshop.models.LoginResponse;
import com.example.offshop.remotedata.RetrofitClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {
    private FragmentLoginBinding binding;
    private String email, password;
//    private UserViewModel viewModel = new ViewModelProvider(this).get(UserViewModel.class);

    NavController navController;

    private TextInputEditText emailTextView, passwordTextView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container,
                             @NonNull Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        emailTextView = view.findViewById(R.id.loginEmail);
        passwordTextView = view.findViewById(R.id.loginPassword);

        binding.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get email and password from input fields
                email = emailTextView.getText().toString().trim();
                password = passwordTextView.getText().toString().trim();

                // Check if email and password are not empty
                if (email.isEmpty()) {
                    emailTextView.setError("Введите email");
                    return;
                }
                if (password.isEmpty()) {
                    passwordTextView.setError("Введите пароль");
                    return;
                }

                // Perform login
                login(email, password);
            }
        });

        binding.tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to register fragment
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
                navController.navigate(R.id.action_loginFragment_to_registerFragment);
            }
        });

        return view;
    }

    private void login(String email, String password) {
        // Create CurrentUser object with email and password
        CurrentUser currentUser = new CurrentUser(email, password);

        // Call login API
        RetrofitClient.getInstance().getApi().checkLoginUser(currentUser).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    // Handle successful login
                    LoginResponse loginResponse = response.body();
                    if (loginResponse != null) {
                        Log.d("token", loginResponse.access_token);
//                        viewModel.setToken(loginResponse.access_token);

                        Token.token = loginResponse.access_token;

                        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
                        navController.navigate(R.id.action_loginFragment_to_homeFragment);
                    } else {
                        // Show error message if login response is null
                        Log.e("API", "Login response is null");
                        binding.error.setText("Failed to login. Please try again.");
                    }
                } else {
                    // Handle unsuccessful login
                    Log.e("API", "Login request failed: " + response.code());
                    binding.error.setText("Failed to login. Please check your credentials.");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                // Handle login failure
                Log.e("API", "Login request failed", t);
                binding.error.setText("Failed to connect to the server. Please check your network connection and try again.");
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.nav_view);
        bottomNavigationView.setVisibility(View.VISIBLE);
        binding = null;
    }


    @Override
    public void onViewCreated(@androidx.annotation.NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.nav_view);
        bottomNavigationView.setVisibility(View.INVISIBLE);
    }
}