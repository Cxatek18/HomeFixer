package com.team.homefixers.viewmodels;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.team.homefixers.model.User;

import java.util.HashMap;
import java.util.Map;

public class PersonalAccountViewModel extends ViewModel {

    private FirebaseDatabase userBase;
    private FirebaseAuth auth;
    private DatabaseReference userReference;
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private MutableLiveData<Map<String, String>> userInfoData = new MutableLiveData<>();

    public PersonalAccountViewModel() {
        auth = FirebaseAuth.getInstance();
        userBase = FirebaseDatabase.getInstance();
        userReference = userBase.getReference("Users");
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public LiveData<Map<String, String>> getUserInfoData() {
        return userInfoData;
    }

    public void getInfoUser(){
        userReference.child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<String, String> userInfo = new HashMap<>();
                User user = snapshot.getValue(User.class);
                userInfo.put("name", user.getName());
                userInfo.put("email", user.getEmail());
                userInfo.put("phone", user.getNumberPhone());
                userInfo.put("city", user.getCity());
                userInfoData.setValue(userInfo);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                errorMessage.setValue(error.getMessage());
            }
        });
    }
}
