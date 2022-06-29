package com.kandyvilla.kandyvillathefashionstore.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.kandyvilla.kandyvillathefashionstore.R;
import com.kandyvilla.kandyvillathefashionstore.admin.activities.AdminActivity;

import java.util.Arrays;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LoginActivity extends AppCompatActivity {
    EditText email,password;
    TextView signuptxt;
    Button signinbtn;
    FirebaseAuth auth;
    String user_type;
    TextView forgetpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Spinner spinner = findViewById(R.id.usertype);
        final List<String>[] usertype = new List[]{Arrays.asList("User", "Admin")};

        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, usertype[0]);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        auth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email_log);
        password = findViewById(R.id.password_log);
        signinbtn = findViewById(R.id.signinbtn_log);
        signuptxt = findViewById(R.id.signuptxt);
        forgetpassword = findViewById(R.id.forgetpassword);




        
        forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgetpassword();
            }
        });
        
        
        signuptxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(LoginActivity.this,RegistrationActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_type = spinner.getSelectedItem().toString();
                if(user_type.equals("User"))
                {
                    userlogin();
                }
                if(user_type.equals("Admin"))
                {
                    adminlogin();
                }
            }
        });
    }

    private void forgetpassword() {

        AlertDialog.Builder alert = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        final EditText edittext = new EditText(getApplicationContext());
        alert.setMessage("Enter Your Email");
        alert.setTitle("Reset Password");
        alert.setView(edittext);

        alert.setPositiveButton("Send Link", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Editable email = edittext.getText();
                FirebaseAuth.getInstance().setLanguageCode("en");// Set to English
                FirebaseAuth.getInstance()
                        .sendPasswordResetEmail(String.valueOf(email))
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(LoginActivity.this,"Sending....",Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Oops...")
                                        .setContentText("Something went wrong!")
                                        .show();
                            }
                        }).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                new SweetAlertDialog(LoginActivity.this,SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Email has been sent !")
                                        .setContentText("Click on the link given in the mail to rest the password")
                                        .show();
                            }
                        });
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
            }
        });

        alert.show();

    }

    public void userlogin() {
        //fetching data from page

        String Email= email.getText().toString();
        String Password= password.getText().toString();

        if(Email.isEmpty())
        {
            email.setError("Email cannot be empty !");
        }

        if(Password.isEmpty())
        {
            password.setError("Password cannot be empty !");
        }
        auth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Login Successful !!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, "Eroor:" + task.getException(), Toast.LENGTH_SHORT).show();

                    }
                }
            });
    }

    public void adminlogin() {
        //fetching data from page

        String Email= email.getText().toString();
        String Password= password.getText().toString();

        if(Email.isEmpty())
        {
            email.setError("Email cannot be empty !");
        }

        if(Password.isEmpty())
        {
            password.setError("Password cannot be empty !");
        }

        if(Email.equals("admin@gmail.com") && Password.equals("admin123"))
        {
            Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            Toast.makeText(this, "Login Success.", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Login Failed.", Toast.LENGTH_SHORT).show();
        }
    }
}