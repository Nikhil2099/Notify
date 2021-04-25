package com.example.notify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private EditText loginemailadress,loginpassword;
    private Button btnlogin;
    private ImageButton btngoogle,btnnotify;
    private FirebaseAuth mAuth;
    GoogleSignInClient mGoogleSignInClient;
    private static int RC_SIGN_IN=100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        getSupportActionBar().hide();
        castcomponents();
        if(mAuth.getCurrentUser()!=null)
        {
            startActivity(new Intent(MainActivity.this,chatactivity.class));
        }

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        //updateUI(account);
        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    signIn();
            }
        });
    btnlogin.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String emailid = loginemailadress.getText().toString().trim();
            String password = loginpassword.getText().toString().trim();
            if(emailid.equals("") && password.equals(""))
            {
                Toast.makeText(MainActivity.this,"Cannot be empty",Toast.LENGTH_SHORT).show();
            }else
            {
                loginusermethod(emailid,password);
            }
        }
    });
        ImageView notifybtn = (ImageView)findViewById(R.id.btnnotify);
        notifybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Register.class);
                startActivity(intent);

            }
        });
    }
    private void loginusermethod(String userid,String password)
    {
    mAuth.signInWithEmailAndPassword(userid, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()) {
                Toast.makeText(MainActivity.this, "logged in successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, chatactivity.class));
                finish();
            }
        }
    }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    });

    }
    private void castcomponents()
    {
        loginemailadress = (EditText)findViewById(R.id.loginemailAddress);
        loginpassword    = (EditText)findViewById(R.id.loginpassword);
        btnlogin         = (Button)findViewById(R.id.btnlogin);
        //btngoogle        = (ImageButton)findViewById(R.id.btngoogle);
        btnnotify        = (ImageButton)findViewById(R.id.btnnotify);
        mAuth            = FirebaseAuth.getInstance();

    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
            if (acct != null) {
                String personName = acct.getDisplayName();
                String personGivenName = acct.getGivenName();
                String personFamilyName = acct.getFamilyName();
                String personEmail = acct.getEmail();
                String personId = acct.getId();
                Uri personPhoto = acct.getPhotoUrl();
                Toast.makeText(this,"signed up with" +personEmail,Toast.LENGTH_SHORT).show();
            }
            //startActivity(new Intent(MainActivity.this,MapsActivity.class));

            // Signed in successfully, show authenticated UI.
            //updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
           // updateUI(null);
            Log.d("message",e.toString());
        }
    }
}