package com.example.offshop.liveData;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UserViewModel extends ViewModel {
    private MutableLiveData token = new MutableLiveData<String>();

    public MutableLiveData getToken() {
        return token;
    }

    public void setToken(String tokenValue) {
        token.setValue(tokenValue);
    }
}
