package com.example.offshop.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.offshop.models.Product;
import com.example.offshop.remotedata.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JemRepository {
    final MutableLiveData<List<Product>> data = new MutableLiveData<>();
    public LiveData<List<Product>> getDashJeminyList(){
        Call<List<Product>> apiCall = RetrofitClient.getInstance().getApi().getStoreProducts();

        apiCall.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if(response.body() != null){
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable throwable) {
                Log.e("TAG", "NO DATA" + throwable.getLocalizedMessage());
                data.postValue(null);
            }
        });
        return data;
    }
}