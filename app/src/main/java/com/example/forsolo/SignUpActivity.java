package com.example.forsolo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "SignUpActivity";

    public EditText id, name, emailId, passwd;
    private Button btnSignUp;

    // 추가 코드
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        id = findViewById(R.id.sign_id);
        name = findViewById(R.id.sign_name);
        emailId = findViewById(R.id.emailEditText);
        passwd = findViewById(R.id.passwordEditText);

        btnSignUp = findViewById(R.id.btn_signUp);


        firebaseAuth = FirebaseAuth.getInstance();

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        btnSignUp.setOnClickListener(this);
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        String emailID = emailId.getText().toString();
        String paswd = passwd.getText().toString();
        String userID = id.getText().toString();
        final String userName = name.getText().toString();

        if (v.getId() == R.id.btn_signUp) {
            if (!userID.equals("") && !userName.equals("") && !emailID.equals("") && !paswd.equals("")) {
//                UserData userInfor = new UserData(userID, userName, emailID, paswd);

                firebaseAuth.createUserWithEmailAndPassword(emailID, paswd)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    final FirebaseUser user;
                                    UserProfileChangeRequest profileUpdate;

                                    profileUpdate = new UserProfileChangeRequest.Builder()
                                            .setDisplayName(userName)
                                            .build();

                                    user = firebaseAuth.getCurrentUser();
                                    user.updateProfile(profileUpdate)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()) {
                                                        ManagementData.registerUser(user);
                                                    }
                                                }
                                            });

                                    //myRef.child(Constant.DB_CHILD_USER).child(userID).setValue(userInfor);
                                    toastMessage("New Information has been saved.");

                                    id.setText("");
                                    name.setText("");
                                    emailId.setText("");
                                    passwd.setText("");

                                    finish();
                                    onBackPressed();

                                }
                            }
                        });
            } else if (emailID.isEmpty()) {
                emailId.setError("Provide your Email first!");
                emailId.requestFocus();

            } else if (paswd.isEmpty()) {
                passwd.setError("Set your password");
                passwd.requestFocus();

            } else if (emailID.isEmpty() && paswd.isEmpty()) {
                Toast.makeText(SignUpActivity.this, "Fields Empty!", Toast.LENGTH_SHORT).show();

            } else if (!(emailID.isEmpty() && paswd.isEmpty())) {
                firebaseAuth.createUserWithEmailAndPassword(emailID, paswd).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this.getApplicationContext(),
                                    "SignUp unsuccessful: " + task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                        }
                    }
                });
            } else {
                Toast.makeText(SignUpActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        }
    }
}