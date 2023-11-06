package com.team.homefixers.viewmodels;


import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthorizationForAnonymousViewModel extends ViewModel {

    private FirebaseAuth anonymousAuth;
    private MutableLiveData<String> error = new MutableLiveData<>();
    private MutableLiveData<Boolean> isAuthorizedAnonymous = new MutableLiveData<>();
    private MutableLiveData<FirebaseUser> anonymousUser = new MutableLiveData<>();

    public AuthorizationForAnonymousViewModel() {
        anonymousAuth = FirebaseAuth.getInstance();
        anonymousAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() != null && firebaseAuth.getCurrentUser().isAnonymous()){
                    anonymousUser.setValue(firebaseAuth.getCurrentUser());
                }
            }
        });
    }

    public LiveData<String> getError() {
        return error;
    }

    public LiveData<Boolean> getIsAuthorizedAnonymous() {
        return isAuthorizedAnonymous;
    }

    public LiveData<FirebaseUser> getAnonymousUser() {
        return anonymousUser;
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
