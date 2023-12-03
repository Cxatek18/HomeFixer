package com.team.homefixers.viewmodels;

import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.team.homefixers.model.Executor;
import com.team.homefixers.model.User;

import java.io.File;

public class RegisterUserViewModel extends ViewModel {

    private FirebaseAuth auth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference usersReference;
    private DatabaseReference executorReference;

    private MutableLiveData<String> error = new MutableLiveData<>();
    private MutableLiveData<FirebaseUser> user = new MutableLiveData<>();
    private MutableLiveData<Boolean> isExecutor = new MutableLiveData<>();

    public RegisterUserViewModel() {
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
                            Executor executor = snapshot.getValue(Executor.class);
                            if(executor != null){
                                isExecutor.setValue(true);
                            }else{
                                isExecutor.setValue(false);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.d("RegisterUserViewModel", "False onCancelled");
                        }
                    });
                }
            }
        });

        firebaseDatabase = FirebaseDatabase.getInstance();
        usersReference = firebaseDatabase.getReference("Users");
    }

    public LiveData<String> getError() {
        return error;
    }

    public LiveData<FirebaseUser> getUser() {
        return user;
    }

    public LiveData<Boolean> getIsExecutor() {
        return isExecutor;
    }

    public void signUpUser(
        String name,
        String email,
        String password,
        String numberPhone,
        String city
    ){
        auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                FirebaseUser firebaseUser = authResult.getUser();
                if(firebaseUser == null){
                    return;
                }
                User user = new User(
                        firebaseUser.getUid(),
                        name,
                        email,
                        password,
                        numberPhone,
                        city
                );
                usersReference.child(user.getId()).setValue(user);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                error.setValue(e.getMessage());
            }
        });
    }
}
