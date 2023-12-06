package com.team.homefixers.viewmodels;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.team.homefixers.model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ChangeInfoUserViewModel extends ViewModel {

    private FirebaseDatabase userBase;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;

    private MutableLiveData<Boolean> isChanged = new MutableLiveData<>();
    private MutableLiveData<Map<String, String>> informationUser = new MutableLiveData();
    private MutableLiveData<String> error = new MutableLiveData<>();

    public ChangeInfoUserViewModel() {
        auth = FirebaseAuth.getInstance();
        userBase = FirebaseDatabase.getInstance();
        databaseReference = userBase.getReference("Users");
    }

    public LiveData<Boolean> getIsChanged() {
        return isChanged;
    }

    public LiveData<Map<String, String>> getInformationUser() {
        return informationUser;
    }

    public LiveData<String> getError() {
        return error;
    }

    public void getUserInfoToChange() {
        databaseReference.child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<String, String> userInfo = new HashMap<>();
                User user = snapshot.getValue(User.class);
                userInfo.put("username", user.getName());
                userInfo.put("email", user.getEmail());
                userInfo.put("city", user.getCity());
                userInfo.put("phone", user.getNumberPhone());
                informationUser.setValue(userInfo);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError e) {
                error.setValue(e.getMessage());
            }
        });
    }

    public void changeUserIngo(String name, String email, String city, String number) {
        HashMap<String, Object> userNewInfo = new HashMap<>();
        userNewInfo.put("name", name);
        userNewInfo.put("email", email);
        userNewInfo.put("city", city);
        userNewInfo.put("numberPhone", number);
        Log.d("ChangeInfoUserViewModel", String.valueOf(userNewInfo));
        databaseReference.child(auth.getCurrentUser().getUid()).updateChildren(userNewInfo)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                isChanged.setValue(true);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                isChanged.setValue(false);
            }
        });
    }
}
