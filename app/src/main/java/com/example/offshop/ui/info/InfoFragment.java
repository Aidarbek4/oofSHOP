package com.example.offshop.ui.info;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.offshop.R;
import com.example.offshop.databinding.FragmentInfoBinding;
import com.example.offshop.liveData.Token;
import com.example.offshop.models.Product;
import com.example.offshop.remotedata.RetrofitClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoFragment extends Fragment {
    private FragmentInfoBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentInfoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        AppCompatImageButton button = root.findViewById(R.id.buttonBack);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
                navController.navigate(R.id.action_navigation_info_to_navigation_home);
            }
        });

        // Обработчики кликов для социальных сетей
        root.findViewById(R.id.instgram).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUrl("https://www.instagram.com/yourfoggymind");
            }
        });

        root.findViewById(R.id.facebook).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUrl("https://www.facebook.com/");
            }
        });

        root.findViewById(R.id.telegram).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUrl("https://t.me/yourfoggymind");
            }
        });

        root.findViewById(R.id.buttonSubmit).setOnClickListener(v -> {
            TextInputEditText textInputContent = root.findViewById(R.id.textInputContent);
            String reviewText = textInputContent.getText().toString();

            Toast.makeText(getContext(), "Click", Toast.LENGTH_SHORT).show();

            Call<Void> call = RetrofitClient.getInstance().getApi().createNewReview(Token.token, reviewText);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                    } else {
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                }
            });
        });


        return root;
    }

    private void openUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
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
