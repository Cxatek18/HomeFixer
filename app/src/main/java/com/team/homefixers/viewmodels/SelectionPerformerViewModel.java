package com.team.homefixers.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SelectionPerformerViewModel extends ViewModel {

    private FirebaseAuth auth;

    private MutableLiveData<FirebaseUser> user = new MutableLiveData<>();

    public SelectionPerformerViewModel() {
        auth = FirebaseAuth.getInstance();
        user.setValue(auth.getCurrentUser());
    }

    public void logout(){
        auth.signOut();
    }

    public void deleteUser(){
        auth.signOut();
        auth.getCurrentUser().delete();
    }
}
