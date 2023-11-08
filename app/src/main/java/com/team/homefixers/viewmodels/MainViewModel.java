package com.team.homefixers.viewmodels;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainViewModel extends ViewModel {

    private FirebaseAuth auth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference executorReference;
    private MutableLiveData<FirebaseUser> user = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();
    private MutableLiveData<Boolean> isExecutor = new MutableLiveData<>();

    public MainViewModel() {
        auth = FirebaseAuth.getInstance();

        auth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() != null){
                    user.setValue(firebaseAuth.getCurrentUser());
                    firebaseDatabase = FirebaseDatabase.getInstance();
                    executorReference = firebaseDatabase.getReference("Executor");
                    String userUid = firebaseAuth.getCurrentUser().getUid();
                    executorReference.child(userUid).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            isExecutor.setValue(true);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            isExecutor.setValue(false);
                        }
                    });
                }
            }
        });
    }

    public LiveData<FirebaseUser> getUser() {
        return user;
    }

    public LiveData<Boolean> getIsExecutor() {
        return isExecutor;
    }

    public LiveData<String> getError() {
        return error;
    }

    public void signInAnonymousUser(){
        auth.signInAnonymously().addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                error.setValue("Произошла ошибка, попробуйте заново");
            }
        });
    }
}
