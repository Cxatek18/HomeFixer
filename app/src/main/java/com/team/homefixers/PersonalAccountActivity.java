package com.team.homefixers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.team.homefixers.viewmodels.PersonalAccountViewModel;

import java.util.Map;

public class PersonalAccountActivity extends AppCompatActivity {

    private TextView textNamePersonalAccountUser;
    private TextView textEmailPersonalAccountUser;
    private TextView textCityPersonalAccountUser;
    private TextView textPhonePersonalAccountUser;
    private AppCompatButton buttonChangeInfo;
    private AppCompatButton buttonDeleteAccount;

    private PersonalAccountViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_account);
        viewModel = new ViewModelProvider(this).get(PersonalAccountViewModel.class);
        initViews();
        viewModel.getInfoUser();
        observePersonalAccountViewModel();
        clickButtons();
    }

    public void clickButtons() {
        buttonChangeInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = ChangeInfoUserActivity.newIntent(PersonalAccountActivity.this);
                startActivity(intent);
            }
        });
    }

    public void observePersonalAccountViewModel() {
        viewModel.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String error) {
                Toast.makeText(
                        PersonalAccountActivity.this,
                        error,
                        Toast.LENGTH_LONG
                ).show();
            }
        });

        viewModel.getUserInfoData().observe(this, new Observer<Map<String, String>>() {
            @Override
            public void onChanged(Map<String, String> userInfoMap) {
                String resultName = String.format(
                        (String) textNamePersonalAccountUser.getText(),
                        userInfoMap.get("name")
                );
                textNamePersonalAccountUser.setText(resultName);
                String resultEmail = String.format(
                        (String) textEmailPersonalAccountUser.getText(),
                        userInfoMap.get("email")
                );
                textEmailPersonalAccountUser.setText(resultEmail);
                String resultCity = String.format(
                        (String) textCityPersonalAccountUser.getText(),
                        userInfoMap.get("city")
                );
                textCityPersonalAccountUser.setText(resultCity);
                String resultPhone = String.format(
                        (String) textPhonePersonalAccountUser.getText(),
                        userInfoMap.get("phone")
                );
                textPhonePersonalAccountUser.setText(resultPhone);
            }
        });
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, PersonalAccountActivity.class);
        return intent;
    }

    private void initViews() {
        textNamePersonalAccountUser = findViewById(R.id.textNamePersonalAccountUser);
        textEmailPersonalAccountUser = findViewById(R.id.textEmailPersonalAccountUser);
        textCityPersonalAccountUser = findViewById(R.id.textCityPersonalAccountUser);
        textPhonePersonalAccountUser = findViewById(R.id.textPhonePersonalAccountUser);
        buttonChangeInfo = findViewById(R.id.buttonChangeInfo);
        buttonDeleteAccount = findViewById(R.id.buttonDeleteAccount);
    }
}