package com.team.homefixers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.team.homefixers.viewmodels.MainViewModel;

public class MainActivity extends AppCompatActivity {

    private AppCompatButton buttonRegisterUser;
    private AppCompatButton buttonSignInUser;
    private AppCompatButton buttonSignInAnonimUser;
    private TextView textViewRegisterExecutor;
    private TextView textViewSigninExecutor;

    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        onClickButtons();
    }

    private void onClickButtons(){
        buttonRegisterUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = RegisterUserActivity.newIntent(MainActivity.this);
                startActivity(intent);
                finish();
            }
        });

        buttonSignInUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = LoginUserActivity.newIntent(MainActivity.this);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.getUser().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if(firebaseUser != null){
                    Intent intent = SelectionPerformersActivity.newIntent(MainActivity.this);
                    startActivity(intent);
                    finish();
                }
            }
        });
        super.onResume();
    }

    public static Intent newIntent(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    private void initViews(){
        buttonRegisterUser = findViewById(R.id.buttonRegisterUser);
        buttonSignInUser = findViewById(R.id.buttonSignInUser);
        buttonSignInAnonimUser = findViewById(R.id.buttonSignInAnonimUser);
        textViewRegisterExecutor = findViewById(R.id.textViewRegisterExecutor);
        textViewSigninExecutor = findViewById(R.id.textViewSigninExecutor);
    }
}