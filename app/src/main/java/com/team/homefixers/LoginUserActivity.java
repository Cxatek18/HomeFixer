package com.team.homefixers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.team.homefixers.viewmodels.LoginUserViewModel;

public class LoginUserActivity extends AppCompatActivity {

    private EditText editTextLoginEmail;
    private EditText editTextLoginPassword;
    private TextView textViewLoginForgotPassword;
    private AppCompatButton buttonLoginUser;

    private LoginUserViewModel viewModel;

    private final static String IS_NO_USER_IN_SYSTEM = "NO_USER_IN_SYSTEM";
    private boolean isNoUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);
        initViews();
        viewModel = new ViewModelProvider(this).get(LoginUserViewModel.class);
        isNoUser = getIntent().getBooleanExtra(IS_NO_USER_IN_SYSTEM, true);
        observeViewModel();
        onClickButtons();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.itemMenuLogout) {
            Intent intent = MainActivity.newIntent(LoginUserActivity.this);
            startActivity(intent);
            finish();
        }
        if(item.getItemId() == R.id.itemMenuRegister){
            Intent intent = RegisterUserActivity.newIntent(LoginUserActivity.this);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void observeViewModel(){
        viewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errorMessage) {
                Toast.makeText(
                        LoginUserActivity.this,
                        errorMessage,
                        Toast.LENGTH_LONG
                ).show();
            }
        });

        viewModel.getUser().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if(firebaseUser != null && !isNoUser){
                    
                }else{
                    if(firebaseUser != null && !firebaseUser.isAnonymous()){
                        observeIsExecutorOrUser();
                    }else if(firebaseUser != null && firebaseUser.isAnonymous()){
                        observeIsExecutorOrAnonymous();
                    }
                }
            }
        });
    }

    private void observeIsExecutorOrUser(){
        viewModel.getIsExecutor().observe(LoginUserActivity.this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean userIsExecutor) {
                Intent intent;
                if(userIsExecutor){
                    intent = SelectionPerformersActivity.newIntentExecutor(
                            LoginUserActivity.this,
                            true
                    );
                }else{
                    intent = SelectionPerformersActivity.newIntent(LoginUserActivity.this);
                }
                startActivity(intent);
                finish();
            }
        });
    }

    private void observeIsExecutorOrAnonymous(){
        viewModel.getIsExecutor().observe(LoginUserActivity.this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean userIsExecutor) {
                Intent intent;
                if(userIsExecutor){
                    intent = SelectionPerformersActivity.newIntentExecutor(
                            LoginUserActivity.this,
                            true
                    );
                }else{
                    intent = SelectionPerformersActivity.newIntentAnonymous(
                            LoginUserActivity.this,
                            true
                    );
                }
                startActivity(intent);
                finish();
            }
        });
    }

    private void onClickButtons(){
        buttonLoginUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailUser = editTextLoginEmail.getText().toString().trim();
                String passwordUser = editTextLoginPassword.getText().toString().trim();
                if(emailUser.isEmpty() || passwordUser.isEmpty()){
                    Toast.makeText(
                            LoginUserActivity.this,
                            "Поля не должны быть пустыми",
                            Toast.LENGTH_SHORT
                    ).show();
                }else{
                    viewModel.loginUser(emailUser, passwordUser);
                }
            }
        });

        textViewLoginForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = ForgotPasswordActivity.newIntent(LoginUserActivity.this);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initViews(){
        editTextLoginEmail = findViewById(R.id.editTextLoginEmail);
        editTextLoginPassword = findViewById(R.id.editTextLoginPassword);
        textViewLoginForgotPassword = findViewById(R.id.textViewLoginForgotPassword);
        buttonLoginUser = findViewById(R.id.buttonLoginUser);
    }

    public static Intent newIntent(Context context){
        Intent intent = new Intent(context, LoginUserActivity.class);
        return intent;
    }

    public static Intent newIntent(Context context, boolean isNoUser){
        Intent intent = new Intent(context, LoginUserActivity.class);
        intent.putExtra(IS_NO_USER_IN_SYSTEM, isNoUser);
        return intent;
    }
}