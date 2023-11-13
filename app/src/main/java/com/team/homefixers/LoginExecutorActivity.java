package com.team.homefixers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.team.homefixers.viewmodels.LoginExecutorViewModel;

public class LoginExecutorActivity extends AppCompatActivity {

    private EditText editTextLoginEmailExecutor;
    private EditText editTextLoginPasswordExecutor;
    private TextView textViewLoginForgotPasswordExecutor;
    private AppCompatButton buttonLoginExecutor;

    private LoginExecutorViewModel viewModel;

    private static final String MESSAGE_FOR_ANONYMOUS_LOGIN = "Вы не можете войти, " +
            "так как находитесь в анонимном аккаунте";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_executor);
        viewModel = new ViewModelProvider(this).get(LoginExecutorViewModel.class);
        initViews();
        observeViewModel();
        onClockButtons();
    }

    private void observeViewModel(){
        viewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errorMessage) {
                Toast.makeText(
                        LoginExecutorActivity.this,
                        errorMessage,
                        Toast.LENGTH_LONG
                ).show();
            }
        });

        viewModel.getExecutor().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if(firebaseUser != null && !firebaseUser.isAnonymous()){
                    observeIsExecutorOrUser();
                } else if(firebaseUser != null && firebaseUser.isAnonymous()){
                    observeIsExecutorOrAnonymous();
                    Toast.makeText(
                            LoginExecutorActivity.this,
                            MESSAGE_FOR_ANONYMOUS_LOGIN,
                            Toast.LENGTH_LONG
                    ).show();
                    finish();
                }
            }
        });
    }

    private void observeIsExecutorOrUser(){
        viewModel.getIsExecutor().observe(LoginExecutorActivity.this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean userIsExecutor) {
                Intent intent;
                if(userIsExecutor){
                    intent = SelectionPerformersActivity.newIntentExecutor(
                            LoginExecutorActivity.this,
                            true
                    );
                }else{
                    intent = SelectionPerformersActivity.newIntent(LoginExecutorActivity.this);
                }
                startActivity(intent);
                finish();
            }
        });
    }

    private void observeIsExecutorOrAnonymous(){
        viewModel.getIsExecutor().observe(LoginExecutorActivity.this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean userIsExecutor) {
                Intent intent;
                if(userIsExecutor){
                    intent = SelectionPerformersActivity.newIntentExecutor(
                            LoginExecutorActivity.this,
                            true
                    );
                }else{
                    intent = SelectionPerformersActivity.newIntentAnonymous(
                            LoginExecutorActivity.this,
                            true
                    );
                }
                startActivity(intent);
                finish();
            }
        });
    }

    private void onClockButtons(){
        buttonLoginExecutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextLoginEmailExecutor.getText().toString().trim();
                String password = editTextLoginPasswordExecutor.getText().toString().trim();
                viewModel.loginExecutor(email, password);
            }
        });

        textViewLoginForgotPasswordExecutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = ForgotPasswordExecutorActivity.newIntent(LoginExecutorActivity.this);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login_executor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.itemMenuLogout){
            Intent intent = MainActivity.newIntent(LoginExecutorActivity.this);
            startActivity(intent);
            finish();
        }else if(item.getItemId() == R.id.itemMenuRegisterExecutor){
            Intent intent = RegisterExecutorActivity.newIntent(LoginExecutorActivity.this);
            startActivity(intent);
            finish();
        }else if(item.getItemId() == R.id.itemMenuRegisterUser){
            Intent intent = RegisterUserActivity.newIntent(LoginExecutorActivity.this);
            startActivity(intent);
            finish();
        }else if(item.getItemId() == R.id.itemMenuLoginUser){
            Intent intent = LoginUserActivity.newIntent(LoginExecutorActivity.this);
            startActivity(intent);
            finish();
        }else if(item.getItemId() == R.id.itemMenuHelp){
            Intent intent = HelpActivity.newIntent(LoginExecutorActivity.this);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void initViews(){
        editTextLoginEmailExecutor = findViewById(R.id.editTextLoginEmailExecutor);
        editTextLoginPasswordExecutor = findViewById(R.id.editTextLoginPasswordExecutor);
        textViewLoginForgotPasswordExecutor = findViewById(R.id.textViewLoginForgotPasswordExecutor);
        buttonLoginExecutor = findViewById(R.id.buttonLoginExecutor);
    }

    public static Intent newIntent(Context context){
        Intent intent = new Intent(context, LoginExecutorActivity.class);
        return intent;
    }
}