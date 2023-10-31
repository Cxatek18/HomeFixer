package com.team.homefixers.viewmodels;

import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;

public class SelectionPerformerViewModel extends ViewModel {


    private FirebaseAuth auth;
    public SelectionPerformerViewModel() {
        auth = FirebaseAuth.getInstance();
    }

    public void logout(){
        auth.signOut();
    }
}
