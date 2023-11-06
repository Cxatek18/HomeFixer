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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.team.homefixers.viewmodels.RegisterUserViewModel;

public class RegisterUserActivity extends AppCompatActivity {

    private EditText editTextRegUserName;
    private EditText editTextRegUserEmail;
    private EditText editTextRegUserPassword;
    private EditText editTextRegUserPhone;
    private EditText editTextRegUserCity;
    private AppCompatButton buttonRegRegisterUser;

    private RegisterUserViewModel viewModel;

    private static final String MESSAGE_FOR_ANONYMOUS_REGISTER = "Вы не можете воспользоваться " +
            "регистарацией, так как находитесь в анонимном аккаунте";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        initViews();
        viewModel = new ViewModelProvider(this).get(RegisterUserViewModel.class);
        observeViewModel();
        onClickButtons();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.itemMenuLogout) {
            Intent intent = MainActivity.newIntent(RegisterUserActivity.this);
            startActivity(intent);
            finish();
        }
        if(item.getItemId() == R.id.itemMenuSignin){
            Intent intent = LoginUserActivity.newIntent(RegisterUserActivity.this);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void onClickButtons(){
        buttonRegRegisterUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = getFormattedString(editTextRegUserName);
                String email = getFormattedString(editTextRegUserEmail);
                String password = getFormattedString(editTextRegUserPassword);
                String numberPhone = getFormattedString(editTextRegUserPhone);
                String city = getFormattedString(editTextRegUserCity);
                viewModel.signUpUser(
                        name,
                        email,
                        password,
                        numberPhone,
                        city
                );
            }
        });
    }

    private void observeViewModel(){
        viewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errorMessage) {
                if(errorMessage != null){
                    Toast.makeText(
                        RegisterUserActivity.this,
                            "Произошла ошибка регистрации",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });

        viewModel.getUser().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if(firebaseUser != null){
                    Intent intent = SelectionPerformersActivity.newIntent(
                            RegisterUserActivity.this
                    );
                    startActivity(intent);
                    finish();
                }
            }
        });

        viewModel.getAnonymousUser().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if(firebaseUser != null){
                    Intent intent = SelectionPerformersActivity.newIntentAnonymous(
                            RegisterUserActivity.this,
                            true
                    );
                    Toast.makeText(
                            RegisterUserActivity.this,
                            MESSAGE_FOR_ANONYMOUS_REGISTER,
                            Toast.LENGTH_LONG
                    ).show();
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private String getFormattedString(EditText editText){
        return editText.getText().toString().trim();
    }

    private void initViews(){
        editTextRegUserName = findViewById(R.id.editTextRegUserName);
        editTextRegUserEmail = findViewById(R.id.editTextRegUserEmail);
        editTextRegUserPassword = findViewById(R.id.editTextRegUserPassword);
        editTextRegUserPhone = findViewById(R.id.editTextRegUserPhone);
        editTextRegUserCity = findViewById(R.id.editTextRegUserCity);
        buttonRegRegisterUser = findViewById(R.id.buttonRegRegisterUser);
    }

    public static Intent newIntent(Context context){
        Intent intent = new Intent(context, RegisterUserActivity.class);
        return intent;
    }
}