package com.team.homefixers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseUser;
import com.team.homefixers.viewmodels.SelectionPerformerViewModel;

public class SelectionPerformersActivity extends AppCompatActivity {

    private SelectionPerformerViewModel viewModel;
    private static final String KEY_ANONYMOUS = "anonymous";
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection_performers);
        viewModel = new ViewModelProvider(this).get(SelectionPerformerViewModel.class);
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, SelectionPerformersActivity.class);
        return intent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (currentUser.isAnonymous()) {
            getMenuInflater().inflate(R.menu.menu_anonymous_user, menu);
        } else {
            getMenuInflater().inflate(R.menu.menu_user, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (currentUser.isAnonymous()) {
            if (item.getItemId() == R.id.itemMenuLogout) {
                Intent intent = MainActivity.newIntent(SelectionPerformersActivity.this);
                startActivity(intent);
                isSignOut = true;
                viewModel.deleteUser();
                finish();
            }
        } else {
            if (item.getItemId() == R.id.itemMenuLogout) {
                Intent intent = MainActivity.newIntent(SelectionPerformersActivity.this);
                startActivity(intent);
                viewModel.logout();
                finish();
            }
            if (item.getItemId() == R.id.itemMenuChangePassword) {
                Intent intent = ForgotPasswordActivity.newIntent(SelectionPerformersActivity.this);
                startActivity(intent);
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void observeSelectionViewModel(){
        viewModel.getUser().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if(firebaseUser.isAnonymous() && firebaseUser != null){
                    currentUser = firebaseUser;
                }
            }
        });
    }
}