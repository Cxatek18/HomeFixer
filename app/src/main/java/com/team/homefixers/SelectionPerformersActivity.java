package com.team.homefixers;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseUser;
import com.team.homefixers.viewmodels.SelectionPerformerViewModel;

public class SelectionPerformersActivity extends AppCompatActivity {

    private SelectionPerformerViewModel viewModel;
    private static final String KEY_ANONYMOUS = "anonymous";

    private static final String KEY_EXECUTOR = "executor";
    private boolean anonymous;
    private boolean executor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection_performers);
        viewModel = new ViewModelProvider(this).get(SelectionPerformerViewModel.class);
        anonymous = getIntent().getBooleanExtra(KEY_ANONYMOUS, false);
        executor = getIntent().getBooleanExtra(KEY_EXECUTOR, false);
        observeSelectionPerformerViewModel();
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, SelectionPerformersActivity.class);
        return intent;
    }

    public static Intent newIntentAnonymous(Context context, boolean isAnonymous) {
        Intent intent = new Intent(context, SelectionPerformersActivity.class);
        intent.putExtra(KEY_ANONYMOUS, isAnonymous);
        return intent;
    }

    public static Intent newIntentExecutor(Context context, boolean isExecutor) {
        Intent intent = new Intent(context, SelectionPerformersActivity.class);
        intent.putExtra(KEY_EXECUTOR, isExecutor);
        return intent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (anonymous) {
            getMenuInflater().inflate(R.menu.menu_anonymous_user, menu);
        } else if (executor) {
            getMenuInflater().inflate(R.menu.menu_executor, menu);
        } else {
            getMenuInflater().inflate(R.menu.menu_user, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (anonymous) {
            if (item.getItemId() == R.id.itemMenuLogout) {
                Intent intent = MainActivity.newIntent(SelectionPerformersActivity.this);
                startActivity(intent);
                viewModel.logout();
                finish();
            }else if(item.getItemId() == R.id.itemMenuHelp){
                Intent intent = HelpActivity.newIntent(SelectionPerformersActivity.this);
                startActivity(intent);
            }
        } else if (executor) {
            if (item.getItemId() == R.id.itemMenuLogout) {
                Intent intent = MainActivity.newIntent(SelectionPerformersActivity.this);
                startActivity(intent);
                viewModel.logout();
                finish();
            } else if (item.getItemId() == R.id.itemMenuChangePassword) {
                Intent intent = ForgotPasswordExecutorActivity.newIntent(SelectionPerformersActivity.this);
                startActivity(intent);
            }else if(item.getItemId() == R.id.itemMenuHelp){
                Intent intent = HelpActivity.newIntent(SelectionPerformersActivity.this);
                startActivity(intent);
            }
        } else {
            if (item.getItemId() == R.id.itemMenuLogout) {
                Intent intent = MainActivity.newIntent(SelectionPerformersActivity.this);
                startActivity(intent);
                viewModel.logout();
                finish();
            }else if(item.getItemId() == R.id.itemMenuChangePassword){
                Intent intent = ForgotPasswordActivity.newIntent(SelectionPerformersActivity.this);
                startActivity(intent);
            }else if(item.getItemId() == R.id.itemMenuHelp){
                Intent intent = HelpActivity.newIntent(SelectionPerformersActivity.this);
                startActivity(intent);
            }else if(item.getItemId() == R.id.itemMenuPersonalCabinet){
                Intent intent = PersonalAccountActivity.newIntent(SelectionPerformersActivity.this);
                startActivity(intent);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void observeSelectionPerformerViewModel() {
        if (anonymous) {
            viewModel.getUser().observe(this, new Observer<FirebaseUser>() {
                @Override
                public void onChanged(FirebaseUser firebaseUser) {
                    firebaseUser.delete();
                }
            });
        } else {
            viewModel.getUser().observe(this, new Observer<FirebaseUser>() {
                @Override
                public void onChanged(FirebaseUser firebaseUser) {

                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        finish();
        super.onDestroy();
    }
}