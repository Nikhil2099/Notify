package com.example.notify;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class Register extends AppCompatActivity {
    private EditText username;
    private EditText registeremailadress;
    private EditText registerpassword;
    private EditText registerconfirmpassword;
    private Button btnregister;
    private FirebaseAuth registerauth;
    private FirebaseDatabase registerdatabase;
    private EditText registerCity;
    private EditText phNo;
    private  DatabaseReference myuserref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Objects.requireNonNull(getSupportActionBar()).hide();
//        castcomponents();
//        references();
        btnregister = findViewById(R.id.btnregister);
        username = findViewById(R.id.username);
        registeremailadress = findViewById(R.id.registeremailAddress);
        registerpassword = findViewById(R.id.registerpassword);
        registerconfirmpassword = findViewById(R.id.registerconfirmpassword);
        registerCity = (EditText) findViewById(R.id.citytext);
        phNo = (EditText) findViewById(R.id.editTextNumber);
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Username = username.getText().toString().toLowerCase();
                String emailid = registeremailadress.getText().toString().toLowerCase();
                Integer phno = phNo.getText().length();
                String password = registerpassword.getText().toString();
                String confirmpassword = registerconfirmpassword.getText().toString();


               if(Username.equals(""))
               {
                  username.setError("username please");
               }
               else if (emailid.equals(""))
               {
                   registeremailadress.setError("emailid please");
               }
               else if(password.equals(""))
               {
                   registerpassword.setError("password please");
                }
               else if(confirmpassword.equals(""))
               {
                   registerconfirmpassword.setError("confirm the password");
               }
               else if (!confirmpassword.equals(password))
                {
                    Toast.makeText(Register.this, "Password do not match", Toast.LENGTH_SHORT).show();
                }


                if (checkusername(Username)&&checkpassword(password)){
                    emailid = registeremailadress.getText().toString().trim();
                    password = registerpassword.getText().toString().trim();
                    signupusermethod(emailid,password);
                }
            }
        });
    }


    private void signupusermethod(String emailid, String password) {
        registerauth = FirebaseAuth.getInstance();
        registerauth.createUserWithEmailAndPassword(emailid,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                   // Toast.makeText(Register.this,"User created Succeessfully",Toast.LENGTH_SHORT).show();
                    uploaduserdata();
                    Toast.makeText(Register.this,"User registered Succeessfully",Toast.LENGTH_SHORT).show();
                 signinmethod();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Register.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void signinmethod() {
        Toast.makeText(this,"x",Toast.LENGTH_LONG);
        String emailid = registeremailadress.getText().toString().trim();
        String password = registerpassword.getText().toString().trim();
        registerauth.signInWithEmailAndPassword(emailid, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
               uploaduserdata();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(
Register.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploaduserdata() {
        String user_name = registeremailadress.getText().toString().trim().toLowerCase();
        String password = registerpassword.getText().toString().trim().toLowerCase();
        String mCity = registerCity.getText().toString().toLowerCase().trim();
        Integer phno = phNo.getText().length();
//        uploaduserdataclass userdata = new uploaduserdataclass(user_name,password,city, phNo);

//        myuserref.push().setValue(userdata);
        //Toast.makeText(this, "entered uploaduserdata", Toast.LENGTH_SHORT).show();


        String current_user=FirebaseAuth.getInstance().getCurrentUser().getUid();


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("users");
        Log.d("myref", "myRef created");
        //uploaduserdataclass user_details=new uploaduserdataclass(user_name, password, mCity);
        HashMap<String,String> user_details=new HashMap<>();


        String data=current_user.toString().trim();

//                user_details.put("id",id);
        user_details.put("mail",user_name);
//                user_details.put("mail",mail);
        user_details.put("password",password);
        user_details.put("city",mCity);
//                user_details.put("taskcompleted","0");
//                user_details.put("timecredits","10");
//                user_details.put("profilepic","null");
//                user_details.put("status","Hey,i am using time bank");
//                user_details.put("mid",data);
                Log.d("values", user_name + " " + password + " " + mCity);
                myRef.child(current_user).setValue(user_details).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Registration Success",Toast.LENGTH_SHORT).show();
                           startActivity(new Intent(getApplicationContext(),MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                        }else{
                            Toast.makeText(getApplicationContext(),"please,try again",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


//    private void castcomponents() {
//        registeremailadress = (EditText)findViewById(R.id.registeremailAddress);
//        registerpassword = (EditText)findViewById(R.id.registerpassword);
//        btnregister = (Button)findViewById(R.id.btnregister);
//
//        registerauth = FirebaseAuth.getInstance();
//        registerdatabase = FirebaseDatabase.getInstance();
//    }
//    private void references()
//    {
//        if (registerauth != null) {
//            myuserref = registerdatabase.getReference("users")
//                    .child(registerauth.getCurrentUser().getUid().toString().trim());
//        }
//    }


    private boolean checkusername(String username){
        if (username.equals("")&& username.length()<2){
            return false;

        }else{
            return  true;
        }
    }
    private boolean checkpassword(String password)
    {
        if (password.equals("")&& password.length()<8){
            return false;
        }else
        {
            return  true;
        }
        }

    public void setUsername(EditText username) {
        this.username = username;
    }
}