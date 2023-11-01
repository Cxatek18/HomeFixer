package com.team.homefixers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.team.homefixers.viewmodels.ForgotPasswordViewModel;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText editTextForgotPasswordEmail;
    private AppCompatButton buttonForgotPassword;
    private ForgotPasswordViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        initViews();
        viewModel = new ViewModelProvider(this).get(ForgotPasswordViewModel.class);
        observeViewModel();
        onClickButtons();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_forgot_password, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.itemMenuRegister){
            Intent intent = RegisterUserActivity.newIntent(ForgotPasswordActivity.this);
            startActivity(intent);
            finish();
        }
        if(item.getItemId() == R.id.itemMenuSignin){
            Intent intent = LoginUserActivity.newIntent(ForgotPasswordActivity.this);
            startActivity(intent);
            finish();
        }
        if(item.getItemId() == R.id.itemMenuLogout){
            Intent intent = MainActivity.newIntent(ForgotPasswordActivity.this);
            startActivity(intent);
            finish();
        }
        if(item.getItemId() == R.id.itemMenuSigninNoRegister){
            Intent intent = SelectionPerformersActivity.newIntent(ForgotPasswordActivity.this);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void observeViewModel(){
        viewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errorMessage) {
                Toast.makeText(
                        ForgotPasswordActivity.this,
                        errorMessage,
                        Toast.LENGTH_LONG
                ).show();
            }
        });

        viewModel.getIsReset().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isReset) {
                if(isReset){
                    Toast.makeText(
                            ForgotPasswordActivity.this,
                            "Проверьте почту",
                            Toast.LENGTH_LONG
                    ).show();
                }
            }
        });
    }

    private void onClickButtons(){
        buttonForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextForgotPasswordEmail.getText().toString().trim();
                if(email.isEmpty()){
                    Toast.makeText(
                            ForgotPasswordActivity.this,
                            "Поле email должно быть заполнено",
                            Toast.LENGTH_SHORT
                    ).show();
                }else {
                    viewModel.resetPasswordUser(email);
                }
            }
        });
    }

    public static Intent newIntent(Context context){
        Intent intent = new Intent(context, ForgotPasswordActivity.class);
        return intent;
    }

    private void initViews(){
        editTextForgotPasswordEmail = findViewById(R.id.editTextForgotPasswordEmail);
        buttonForgotPassword = findViewById(R.id.buttonForgotPassword);
    }
}