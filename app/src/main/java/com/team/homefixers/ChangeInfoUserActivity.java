package com.team.homefixers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.team.homefixers.viewmodels.ChangeInfoUserViewModel;

import java.util.Map;

public class ChangeInfoUserActivity extends AppCompatActivity {

    private EditText editChangeNameUser;
    private EditText editChangeEmailUser;
    private EditText editChangeCityUser;
    private EditText editChangePhoneUser;
    private AppCompatButton buttonChangeInfoUser;

    private ChangeInfoUserViewModel viewModel;

    private static final String IS_CHANGED_TRUE = "Вы успешно изменили данные!";
    private static final String IS_CHANGED_FALSE = "Произошла ошибка при изменение данных.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_info_user);
        viewModel = new ViewModelProvider(this).get(ChangeInfoUserViewModel.class);
        initViews();
        viewModel.getUserInfoToChange();
        observerChangeInfoUserViewModel();
        setUpOnClickButton();
    }

    public void observerChangeInfoUserViewModel(){
        viewModel.getInformationUser().observe(this, new Observer<Map<String, String>>() {
            @Override
            public void onChanged(Map<String, String> stringStringMap) {
                String resultName = String.format(
                        String.valueOf(editChangeNameUser.getText()),
                        stringStringMap.get("username")
                );
                editChangeNameUser.setText(resultName);
                String resultEmail = String.format(
                        String.valueOf(editChangeEmailUser.getText()),
                        stringStringMap.get("email")
                );
                editChangeEmailUser.setText(resultEmail);
                String resultCity = String.format(
                        String.valueOf(editChangeCityUser.getText()),
                        stringStringMap.get("city")
                );
                editChangeCityUser.setText(resultCity);
                String resultPhone = String.format(
                        String.valueOf(editChangePhoneUser.getText()),
                        stringStringMap.get("phone")
                );
                editChangePhoneUser.setText(resultPhone);
            }
        });

        viewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(
                        ChangeInfoUserActivity.this,
                        s,
                        Toast.LENGTH_SHORT
                ).show();
            }
        });

        viewModel.getIsChanged().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isChanged) {
                if(isChanged){
                    Toast.makeText(
                            ChangeInfoUserActivity.this,
                            IS_CHANGED_TRUE,
                            Toast.LENGTH_SHORT
                    ).show();
                }else{
                    Toast.makeText(
                            ChangeInfoUserActivity.this,
                            IS_CHANGED_FALSE,
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });
    }

    private void setUpOnClickButton(){
        buttonChangeInfoUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newName = String.valueOf(editChangeNameUser.getText());
                String newEmail = String.valueOf(editChangeEmailUser.getText());
                String newCity = String.valueOf(editChangeCityUser.getText());
                String newPhone = String.valueOf(editChangePhoneUser.getText());
                viewModel.changeUserIngo(newName, newEmail, newCity, newPhone);
                Intent intent = PersonalAccountActivity.newIntent(ChangeInfoUserActivity.this);
                startActivity(intent);
                finish();
            }
        });
    }

    public static Intent newIntent(Context context){
        Intent intent = new Intent(context, ChangeInfoUserActivity.class);
        return intent;
    }

    private void initViews(){
        editChangeNameUser = findViewById(R.id.editChangeNameUser);
        editChangeEmailUser = findViewById(R.id.editChangeEmailUser);
        editChangeCityUser = findViewById(R.id.editChangeCityUser);
        editChangePhoneUser = findViewById(R.id.editChangePhoneUser);
        buttonChangeInfoUser = findViewById(R.id.buttonChangeInfoUser);
    }
}