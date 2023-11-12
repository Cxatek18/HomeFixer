package com.team.homefixers.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SelectionPerformerViewModel extends ViewModel {

    private FirebaseAuth auth;
    private MutableLiveData<FirebaseUser> user = new MutableLiveData<>();

    public SelectionPerformerViewModel() {
        auth = FirebaseAuth.getInstance();
    }

    public LiveData<FirebaseUser> getUser() {
        return user;
    }

    public void logout(){
        user.setValue(auth.getCurrentUser());
        auth.signOut();
    }
}
