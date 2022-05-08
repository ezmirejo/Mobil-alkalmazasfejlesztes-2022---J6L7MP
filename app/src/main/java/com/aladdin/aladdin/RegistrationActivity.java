package com.aladdin.aladdin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {
    private static final String LOG_TAG = RegistrationActivity.class.getName();
    private static final String PREF_KEY = MainActivity.class.getPackage().toString();

    EditText email;
    EditText password;
    EditText passwordAgain;

    private SharedPreferences preferences;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        email = findViewById(R.id.RegistrationEmailAddress);
        password = findViewById(R.id.RegistrationPassword);
        passwordAgain = findViewById(R.id.RegistrationPasswordAgain);

        preferences = getSharedPreferences(PREF_KEY, MODE_PRIVATE);
        String emailStr = preferences.getString("email","");
        String passwordStr = preferences.getString("password", "");

        email.setText(emailStr);
        password.setText(passwordStr);
        passwordAgain.setText(passwordStr);

        mAuth = FirebaseAuth.getInstance();

    }

    public void didTapButton(Button button) {
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        button.startAnimation(myAnim);
    }

    public void registration(View view) {
        didTapButton(findViewById(R.id.RegistrationButton));
        String emailStr = email.getText().toString();
        String passwordStr = password.getText().toString();
        String passwordAgainStr = passwordAgain.getText().toString();


        if (!passwordStr.equals(passwordAgainStr)) {
            Log.e(LOG_TAG,"Nem egyezik a jelszó!");
        }

        Log.i(LOG_TAG, "Regisztrált: " + emailStr);

        mAuth.createUserWithEmailAndPassword(emailStr, passwordStr).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.d(LOG_TAG, "User created");
                    startShopping();
                } else {
                    Log.d(LOG_TAG, "User not created");
                    Toast.makeText(RegistrationActivity.this,"User wasn't created succesfully:" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void cancel(View view) {
        didTapButton(findViewById(R.id.CancelButton));
        finish();
    }


    private void startShopping(){
        Intent intent = new Intent(this, ShopListActivity.class);
        startActivity(intent);
    }
}