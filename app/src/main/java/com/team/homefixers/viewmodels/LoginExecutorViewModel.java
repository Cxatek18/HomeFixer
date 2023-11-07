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

public class LoginExecutorViewModel extends ViewModel {

    private FirebaseAuth authExecutor;

    private MutableLiveData<FirebaseUser> executor = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();
    private MutableLiveData<FirebaseUser> anonymousUser = new MutableLiveData<>();

    public LoginExecutorViewModel() {
        authExecutor = FirebaseAuth.getInstance();
        authExecutor.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() != null && firebaseAuth.getCurrentUser().isAnonymous()){
                    anonymousUser.setValue(firebaseAuth.getCurrentUser());
                }
                if(firebaseAuth.getCurrentUser() != null && !firebaseAuth.getCurrentUser().isAnonymous()){
                    executor.setValue(firebaseAuth.getCurrentUser());
                }
            }
        });
    }

    public LiveData<FirebaseUser> getExecutor() {
        return executor;
    }

    public LiveData<String> getError() {
        return error;
    }

    public LiveData<FirebaseUser> getAnonymousUser() {
        return anonymousUser;
    }

    public void loginExecutor(String email, String password){
        authExecutor.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                error.setValue(e.getMessage());
            }
        });
    }
}
