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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.team.homefixers.model.Executor;

public class RegisterExecutorViewModel extends ViewModel {

    private FirebaseAuth auth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference executorReference;

    private MutableLiveData<FirebaseUser> user = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();

    private static final String ERROR_MESSAGE = "Произошла ошибка во время регистрации";

    public RegisterExecutorViewModel() {
        auth = FirebaseAuth.getInstance();
        auth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() != null){
                    user.setValue(firebaseAuth.getCurrentUser());
                }
            }
        });

        firebaseDatabase = FirebaseDatabase.getInstance();
        executorReference = firebaseDatabase.getReference("Executor");
    }

    public LiveData<FirebaseUser> getUser() {
        return user;
    }

    public LiveData<String> getError() {
        return error;
    }

    public void signUpExecutor(
            String name,
            String email,
            String password,
            String numberPhone,
            String city,
            String specialization,
            String description,
            int price,
            String experience,
            String contract,
            String guarantee,
            String urgency
    ) {
        auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                FirebaseUser firebaseUser = authResult.getUser();
                if(firebaseUser == null){
                    return;
                }
                Executor executor = new Executor(
                        firebaseUser.getUid(),
                        name,
                        email,
                        password,
                        numberPhone,
                        city,
                        specialization,
                        description,
                        price,
                        experience,
                        contract,
                        guarantee,
                        urgency
                );
                executorReference.child(executor.getId()).setValue(executor);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                error.setValue(ERROR_MESSAGE);
            }
        });
    }
}
