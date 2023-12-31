package com.team.homefixers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.team.homefixers.viewmodels.RegisterExecutorViewModel;

public class RegisterExecutorActivity extends AppCompatActivity {

    private EditText editTextRegisterExecutorName;
    private EditText editTextRegisterExecutorEmail;
    private EditText editTextRegisterExecutorPassword;
    private EditText editTextRegisterExecutorPhone;
    private EditText editTextRegisterExecutorCity;
    private Spinner spinnerExecutorSpecialization;
    private EditText editTextRegisterExecutorDescription;
    private EditText editTextRegisterExecutorPrice;
    private Spinner spinnerExecutorExperience;
    private Spinner spinnerExecutorContract;
    private Spinner spinnerExecutorGuarantee;
    private Spinner spinnerExecutorUrgency;
    private AppCompatButton buttonRegRegisterExecutor;

    private RegisterExecutorViewModel viewModel;

    private static final String MESSAGE_PRICE_LESS_ZERO = "Цена не может быть меньше 0";

    private static final String MESSAGE_FOR_ANONYMOUS_REGISTER_EXECUTOR = "Вы не можете " +
            "воспользоваться регистарацией, так как находитесь в анонимном аккаунте";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_executor);
        initViews();
        viewModel = new ViewModelProvider(this).get(RegisterExecutorViewModel.class);
        observeViewModel();
        onClickButtons();
        checkPriceTextEdit();
    }

    private void observeViewModel(){
        viewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String executorReference) {
                Toast.makeText(
                        RegisterExecutorActivity.this,
                        executorReference,
                        Toast.LENGTH_LONG
                ).show();
            }
        });

        viewModel.getUser().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if(firebaseUser != null && !firebaseUser.isAnonymous()){
                    observeIsExecutorOrUser();
                }else if(firebaseUser != null && firebaseUser.isAnonymous()){
                    observeIsExecutorOrAnonymous();
                    Toast.makeText(
                            RegisterExecutorActivity.this,
                            MESSAGE_FOR_ANONYMOUS_REGISTER_EXECUTOR,
                            Toast.LENGTH_LONG
                    ).show();

                }
            }
        });
    }

    private void observeIsExecutorOrUser(){
        viewModel.getIsExecutor().observe(RegisterExecutorActivity.this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean userIsExecutor) {
                Intent intent;
                if(userIsExecutor){
                    intent = SelectionPerformersActivity.newIntentExecutor(
                            RegisterExecutorActivity.this,
                            true
                    );
                }else{
                    intent = SelectionPerformersActivity.newIntent(RegisterExecutorActivity.this);
                }
                startActivity(intent);
                finish();
            }
        });
    }

    private void observeIsExecutorOrAnonymous(){
        viewModel.getIsExecutor().observe(RegisterExecutorActivity.this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean userIsExecutor) {
                Intent intent;
                if(userIsExecutor){
                    intent = SelectionPerformersActivity.newIntentExecutor(
                            RegisterExecutorActivity.this,
                            true
                    );
                }else{
                    intent = SelectionPerformersActivity.newIntentAnonymous(
                            RegisterExecutorActivity.this,
                            true
                    );
                }
                startActivity(intent);
                finish();
            }
        });
    }

    private void onClickButtons(){
        buttonRegRegisterExecutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = getFormattedStringEditText(editTextRegisterExecutorName);
                String email = getFormattedStringEditText(editTextRegisterExecutorEmail);
                String password = getFormattedStringEditText(editTextRegisterExecutorPassword);
                String phone = getFormattedStringEditText(editTextRegisterExecutorPhone);
                String city = getFormattedStringEditText(editTextRegisterExecutorCity);
                String specialization = getFormattedStringSinner(spinnerExecutorSpecialization);
                String description = getFormattedStringEditText(editTextRegisterExecutorDescription);
                int price = Integer.parseInt(
                        getFormattedStringEditText(editTextRegisterExecutorPrice)
                );
                String experience = getFormattedStringSinner(spinnerExecutorExperience);
                String contract = getFormattedStringSinner(spinnerExecutorContract);
                String guarantee = getFormattedStringSinner(spinnerExecutorGuarantee);
                String urgency = getFormattedStringSinner(spinnerExecutorUrgency);

                if(price < 0){
                    price = 0;
                }

                viewModel.signUpExecutor(
                        name,
                        email,
                        password,
                        phone,
                        city,
                        specialization,
                        description,
                        price,
                        experience,
                        contract,
                        guarantee,
                        urgency
                );


            }
        });
    }

    private void checkPriceTextEdit(){
        editTextRegisterExecutorPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int price = Integer.parseInt(
                        getFormattedStringEditText(editTextRegisterExecutorPrice)
                );
                if(price < 0){
                    Toast.makeText(
                            RegisterExecutorActivity.this,
                            MESSAGE_PRICE_LESS_ZERO,
                            Toast.LENGTH_LONG
                    ).show();
                }
            }
        });
    }

    private String getFormattedStringEditText(EditText editText){
        return editText.getText().toString().trim();
    }

    private String getFormattedStringSinner(Spinner spinner){
        return spinner.getSelectedItem().toString().trim();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_register_executor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.itemMenuRegisterUser){
            Intent intent = RegisterUserActivity.newIntent(RegisterExecutorActivity.this);
            startActivity(intent);
            finish();
        }else if(item.getItemId() == R.id.itemMenuLoginUser){
            Intent intent = LoginUserActivity.newIntent(RegisterExecutorActivity.this);
            startActivity(intent);
            finish();
        }else if(item.getItemId() == R.id.itemMenuLoginExecutor){
            Intent intent = LoginExecutorActivity.newIntent(RegisterExecutorActivity.this);
            startActivity(intent);
            finish();
        }else if(item.getItemId() == R.id.itemMenuLogout){
            Intent intent = MainActivity.newIntent(RegisterExecutorActivity.this);
            startActivity(intent);
            finish();
        }else if(item.getItemId() == R.id.itemMenuHelp){
            Intent intent = HelpActivity.newIntent(RegisterExecutorActivity.this);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void initViews(){
        editTextRegisterExecutorName = findViewById(R.id.editTextRegisterExecutorName);
        editTextRegisterExecutorEmail = findViewById(R.id.editTextRegisterExecutorEmail);
        editTextRegisterExecutorPassword = findViewById(R.id.editTextRegisterExecutorPassword);
        editTextRegisterExecutorPhone = findViewById(R.id.editTextRegisterExecutorPhone);
        editTextRegisterExecutorCity = findViewById(R.id.editTextRegisterExecutorCity);
        spinnerExecutorSpecialization = findViewById(R.id.spinnerExecutorSpecialization);
        editTextRegisterExecutorDescription = findViewById(R.id.editTextRegisterExecutorDescription);
        editTextRegisterExecutorPrice = findViewById(R.id.editTextRegisterExecutorPrice);
        spinnerExecutorExperience = findViewById(R.id.spinnerExecutorExperience);
        spinnerExecutorContract = findViewById(R.id.spinnerExecutorContract);
        spinnerExecutorGuarantee = findViewById(R.id.spinnerExecutorGuarantee);
        spinnerExecutorUrgency = findViewById(R.id.spinnerExecutorUrgency);
        buttonRegRegisterExecutor = findViewById(R.id.buttonRegRegisterExecutor);
    }

    public static Intent newIntent(Context context){
        Intent intent = new Intent(context, RegisterExecutorActivity.class);
        return intent;
    }
}