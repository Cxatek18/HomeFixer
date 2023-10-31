package com.team.homefixers.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainViewModel extends ViewModel {

    private FirebaseAuth auth;
    private MutableLiveData<FirebaseUser> user = new MutableLiveData<>();

    public MainViewModel() {
        auth = FirebaseAuth.getInstance();
        auth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() != null){
                    user.setValue(firebaseAuth.getCurrentUser());
                }
            }
        });
    }

    public LiveData<FirebaseUser> getUser() {
        return user;
    }
}
