package com.kandyvilla.kandyvillathefashionstore.activities;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kandyvilla.kandyvillathefashionstore.R;
import com.kandyvilla.kandyvillathefashionstore.models.userModel;

import java.text.SimpleDateFormat;
import java.util.HashMap;

public class RegistrationActivity extends AppCompatActivity {
    EditText name,email,password,retypepassword;
    TextView signintxt;
    Button signupbtn;
    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        auth = FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        firestore =FirebaseFirestore.getInstance();
        name = findViewById(R.id.name_reg);
        email = findViewById(R.id.email_reg);
        password = findViewById(R.id.password_reg);
        retypepassword = findViewById(R.id.retypepassword_reg);
        signintxt = findViewById(R.id.signintxt);
        signupbtn = findViewById(R.id.signupbtn_reg);

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userregistration();
            }
        });

        signintxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(RegistrationActivity.this,LoginActivity.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                // set the new task and clear flags
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

    }

    public void userregistration() {
        // fetching values from the page

        String username= name.getText().toString();
        String Email= email.getText().toString();
        String Password= password.getText().toString();
        String rePassword = retypepassword.getText().toString();

        if(username.isEmpty())
        {
            name.setError("Username cannot be empty !");
        }

        if(Email.isEmpty())
        {
            email.setError("Email cannot be empty !");
        }

        if(Password.isEmpty())
        {
            password.setError("Password cannot be empty !");
        }
        if(Password.length() < 8)
        {
            password.setError("Password length must be greater then 8");
        }

        if(rePassword.length() < 8)
        {
            password.setError("Password length must be greater then 8");
        }

        if(!Password.equals(rePassword))
        {
            retypepassword.setError("Password does not match !");
        }

        if(rePassword.isEmpty())
        {
            retypepassword.setError("Password cannot be empty !");
        }

        // User Registration in Firebase
        auth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    userModel usermodel = new userModel(username,Email,Password);
                    String id = task.getResult().getUser().getUid();
                    database.getReference().child("Users").child(id).setValue(usermodel);

                    String currentDate,currentTime;
                    Calendar calendar = Calendar.getInstance();

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    currentDate = dateFormat.format(calendar.getTime());

                    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                    currentTime = timeFormat.format(calendar.getTime());

                    final HashMap<String,Object> userMap = new HashMap<>();
                    userMap.put("userName",username);
                    userMap.put("password",Password);
                    userMap.put("email",Email);
                    userMap.put("currentDate",currentDate);
                    userMap.put("currentTime",currentTime);


                    firestore.collection("users").document(auth.getCurrentUser().getUid())
                            .collection("user data").add(userMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    Intent intent= new Intent(RegistrationActivity.this, LoginActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }
                            });

                }
                else
                {
                    Toast.makeText(RegistrationActivity.this, "Error :"+task.getException(), Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}