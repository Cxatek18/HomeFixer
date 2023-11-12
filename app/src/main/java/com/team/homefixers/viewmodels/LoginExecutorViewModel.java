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
import com.team.homefixers.model.Executor;

public class LoginExecutorViewModel extends ViewModel {

    private FirebaseAuth authExecutor;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference executorReference;

    private MutableLiveData<FirebaseUser> executor = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();
    private MutableLiveData<Boolean> isExecutor = new MutableLiveData<>();

    public LoginExecutorViewModel() {
        authExecutor = FirebaseAuth.getInstance();
        authExecutor.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() != null){
                    executor.setValue(firebaseAuth.getCurrentUser());
                    firebaseDatabase = FirebaseDatabase.getInstance();
                    executorReference = firebaseDatabase.getReference("Executor");
                    String userUid = firebaseAuth.getCurrentUser().getUid();

                    executorReference.child(userUid).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Executor executor = snapshot.getValue(Executor.class);
                            if(executor != null){
                                isExecutor.setValue(true);
                            }else{
                                isExecutor.setValue(false);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.d("LoginExecutorViewModel", "False onCancelled");
                        }
                    });
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

    public LiveData<Boolean> getIsExecutor() {
        return isExecutor;
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
