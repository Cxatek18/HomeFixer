package com.team.homefixers.viewmodels;


import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AuthorizationForAnonymousViewModel extends ViewModel {

    private FirebaseAuth anonymousAuth;
    private MutableLiveData<String> error = new MutableLiveData<>();
    private MutableLiveData<Boolean> isAuthorizedAnonymous = new MutableLiveData<>();

    public AuthorizationForAnonymousViewModel() {
        anonymousAuth = FirebaseAuth.getInstance();
    }

    public LiveData<String> getError() {
        return error;
    }

    public LiveData<Boolean> getIsAuthorizedAnonymous() {
        return isAuthorizedAnonymous;
    }

    public void signInAnonymousUser(){
        anonymousAuth.signInAnonymously().addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                isAuthorizedAnonymous.setValue(true);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                error.setValue("Произошла ошибка, попробуйте заново");
            }
        });
    }
}