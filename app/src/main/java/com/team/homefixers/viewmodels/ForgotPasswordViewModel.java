package com.team.homefixers.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordViewModel extends ViewModel {
    private FirebaseAuth auth;
    private MutableLiveData<Boolean> isReset = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();

    public ForgotPasswordViewModel() {
        auth = FirebaseAuth.getInstance();
    }

    public LiveData<Boolean> getIsReset() {
        return isReset;
    }

    public LiveData<String> getError() {
        return error;
    }

    public void resetPasswordUser(String email){
        auth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                isReset.setValue(true);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                isReset.setValue(false);
                error.setValue("Произошла ошибка в смене пароля!");
            }
        });
    }
}
