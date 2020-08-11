package com.szubov.android_hw_131;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private EditText mEditTextLogin;
    private EditText mEditTextPassword;
    public static final String TAG = "my app";
    public static final String LOGIN_FILE_NAME = "UserLogin.txt";
    public static final String PASSWORD_FILE_NAME = "UserPassword.txt";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditTextLogin = findViewById(R.id.editTextLogin);
        mEditTextPassword = findViewById(R.id.ediTextPassword);

        findViewById(R.id.btnOk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "MainActivity -> BtnOk -> OnClick");
                String stringLogin = mEditTextLogin.getText().toString();
                String stringPassword = mEditTextPassword.getText().toString();
                if (stringLogin.length() > 0 && stringPassword.length() > 0) {
                    if (compareLoginAndPassword(stringLogin, stringPassword)) {
                        Toast.makeText(MainActivity.this, R.string.log_pass_available,
                                Toast.LENGTH_LONG).show();
                        mEditTextLogin.setText("");
                        mEditTextPassword.setText("");
                    } else {
                        Toast.makeText(MainActivity.this, R.string.log_pass_not_available,
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Enter login and password!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        findViewById(R.id.btnRegistration).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "MainActivity -> BtnRegistration -> OnClick");
                String stringLogin = mEditTextLogin.getText().toString();
                String stringPassword = mEditTextPassword.getText().toString();
                if(stringLogin.length() > 0 && stringPassword.length() > 0) {
                    if (saveLoginAndPassword(stringLogin, stringPassword)) {
                        Toast.makeText(MainActivity.this, R.string.user_registered,
                                Toast.LENGTH_LONG).show();
                        mEditTextLogin.setText("");
                        mEditTextPassword.setText("");
                    }
                } else {
                    Toast.makeText(MainActivity.this, R.string.login_or_password_is_empty,
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public boolean compareLoginAndPassword(String login, String password) {
        Log.d(TAG, "MainActivity -> BtnOk -> OnClick -> compareLoginAndPassword");
        if (login != null && password != null) {
            String storedLogin = loadValue(LOGIN_FILE_NAME);
            String storedPassword = loadValue(PASSWORD_FILE_NAME);
            if (storedLogin.length() > 0 && storedPassword.length() > 0) {
                return storedLogin.equals(login) && storedPassword.equals(password);
            }
        }
        return false;
    }

    public boolean saveLoginAndPassword(String login, String password) {
        Log.d(TAG, "MainActivity -> BtnRegistration -> OnClick -> saveLoginAndPassword");
        if (login != null && password != null) {
            return saveValue(login, LOGIN_FILE_NAME) && saveValue(password, PASSWORD_FILE_NAME);
        }
        return false;
    }

    public boolean saveValue(String value, String fileName) {
        Log.d(TAG, "MainActivity -> BtnOk -> OnClick -> saveLoginAndPassword -> saveValue");
        try {
            FileOutputStream fileOutputStream = openFileOutput(fileName, MODE_PRIVATE);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            BufferedWriter bw = new BufferedWriter(outputStreamWriter);
            bw.write(value);
            bw.close();
            return true;
        } catch (IOException ex){
            Log.e(TAG, "MainActivity -> btnRegistration -> onClick -> " +
                    "saveLoginAndPassword -> saveValue", ex);
            ex.getStackTrace();
        }
        return false;
    }

    public String loadValue(String fileName) {
        Log.d(TAG, "MainActivity -> BtnOk -> OnClick -> compareLoginAndPassword -> loadValue");
        String value = "";
        try {
            FileInputStream fileInputStream = openFileInput(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);

            BufferedReader br = new BufferedReader(inputStreamReader);
            value = br.readLine();
            br.close();
        } catch (IOException ex) {
            Log.e(TAG, "MainActivity -> btnOk -> onClick -> " +
                    "compareLoginAndPassword -> loadValue", ex);
            ex.getStackTrace();
        }
        return value;
    }
}