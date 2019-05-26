package android.example.com.lamisportif.fragments;

import android.content.Intent;

import android.example.com.lamisportif.MainActivity;

import android.example.com.lamisportif.R;
import android.example.com.lamisportif.helpful.NavigationHost;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Fragment representing the login screen for our application
 */
public class LoginFragment extends Fragment {

    private TextInputEditText mEmail;
    private TextInputEditText mPassword;
    private MaterialButton mButtonAuth;
    private MaterialButton mButtonCancel;
    private MaterialButton mButtonRegister;
    private FirebaseAuth mAuth;
    private final static String TAG = "LoginFragment";

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        //initialize FirebaseAuth object
        mAuth = FirebaseAuth.getInstance();

        //initialize views
        mEmail = view.findViewById(R.id.email_edit_text);
        mPassword = view.findViewById(R.id.password_edit_text);
        mButtonAuth = view.findViewById(R.id.next_button);
        mButtonCancel = view.findViewById(R.id.cancel_button);
        mButtonRegister = view.findViewById(R.id.register_button);



        //button to go to the register fragment
        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((NavigationHost)getActivity()).navigateTo(new RegisterFragment(), true);
            }
        });
        //button authentication
        mButtonAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check password
                if(!isPasswordValid(mPassword.getText()))
                    mPassword.setError(getString(R.string.error_msg_password));

                //check email
                if(!isEmailValid(mEmail.getText())) {
                    mEmail.setError(getString(R.string.error_msg_email));
                } else if(isPasswordValid(mPassword.getText())){

                    mEmail.setError(null); //clear Error
                    mPassword.setError(null); //clear Error

                    mAuth.signInWithEmailAndPassword(String.valueOf(mEmail.getText()),
                            String.valueOf(mPassword.getText()))
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "signInWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        updateUI(user);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                                        Toast.makeText(getActivity(), "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                        updateUI(null);
                                    }

                                }
                            });
                }
            }
        });
        //button cancel
        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEmail.setText("");
                mPassword.setText("");
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        //check if the user is currently signed in
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    /**
     * check if user is already signed in else we display the login fragment
     * @param currentUser
     */
    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {
            //the user is already authenticated we display the catalog
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
    }

    /**
     * check if email is valid eg: abcd@cdefh.xyz
     * @param text
     * @return boolean
     */
    public boolean isEmailValid(@Nullable Editable text) {
        return !TextUtils.isEmpty(text) &&
                android.util.Patterns.EMAIL_ADDRESS.matcher(text).matches();
    }

    /**
     * check if the password is not empty
     * @param text
     * @return boolean
     */
    private boolean isPasswordValid(Editable text) {
        return !TextUtils.isEmpty(text);
    }

}