package com.team.homefixers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.team.homefixers.viewmodels.ForgotPasswordExecutorViewModel;

public class ForgotPasswordExecutorActivity extends AppCompatActivity {

    private EditText editTextForgotPasswordExecutorEmail;
    private AppCompatButton buttonForgotPasswordExecutor;

    private ForgotPasswordExecutorViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_executor);
        viewModel = new ViewModelProvider(this).get(ForgotPasswordExecutorViewModel.class);
        initViews();
        observeForgotPasswordExecutorViewModel();
        onClickButtons();
    }

    private void observeForgotPasswordExecutorViewModel(){
        viewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(
                        ForgotPasswordExecutorActivity.this,
                        "Произошла ошибка востановления пароля",
                        Toast.LENGTH_LONG
                ).show();
            }
        });

        viewModel.getIsReset().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isReset) {
                if(isReset){
                    Toast.makeText(
                            ForgotPasswordExecutorActivity.this,
                            "Проверьте свою почту",
                            Toast.LENGTH_LONG
                    ).show();
                }
            }
        });
    }

    private void onClickButtons(){
        buttonForgotPasswordExecutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextForgotPasswordExecutorEmail.getText().toString().trim();
                viewModel.forgotPasswordExecutor(email);
            }
        });
    }

    private void initViews(){
        editTextForgotPasswordExecutorEmail = findViewById(R.id.editTextForgotPasswordExecutorEmail);
        buttonForgotPasswordExecutor = findViewById(R.id.buttonForgotPasswordExecutor);
    }

    public static Intent newIntent(Context context){
        Intent intent = new Intent(context, ForgotPasswordActivity.class);
        return intent;
    }
}