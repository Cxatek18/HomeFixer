package com.team.homefixers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.team.homefixers.viewmodels.SelectionPerformerViewModel;

public class SelectionPerformersActivity extends AppCompatActivity {

    private SelectionPerformerViewModel viewModel;
    private static final String KEY_ANONYMOUS = "anonymous";
    private boolean anonymous;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection_performers);
        viewModel = new ViewModelProvider(this).get(SelectionPerformerViewModel.class);
        anonymous = getIntent().getBooleanExtra(KEY_ANONYMOUS, false);
    }

    public static Intent newIntent(Context context){
        Log.d("SelectionPerformersActivity", "asdadadadIntent1");
        Intent intent = new Intent(context, SelectionPerformersActivity.class);
        return intent;
    }

    public static Intent newIntentAnonymous(Context context, boolean isAnonymous){
        Intent intent = new Intent(context, SelectionPerformersActivity.class);
        intent.putExtra(KEY_ANONYMOUS, isAnonymous);
        return intent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(anonymous){
            getMenuInflater().inflate(R.menu.menu_anonymous_user, menu);
        }else{
            getMenuInflater().inflate(R.menu.menu_user, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(anonymous){
            if(item.getItemId() == R.id.itemMenuLogout){
                Intent intent = MainActivity.newIntent(SelectionPerformersActivity.this);
                startActivity(intent);
                viewModel.logout();
                finish();
            }
        }else{
            if (item.getItemId() == R.id.itemMenuLogout) {
                Intent intent = MainActivity.newIntent(SelectionPerformersActivity.this);
                startActivity(intent);
                viewModel.logout();
                finish();
            }
            if(item.getItemId() == R.id.itemMenuChangePassword){
                Intent intent = ForgotPasswordActivity.newIntent(SelectionPerformersActivity.this);
                startActivity(intent);
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}