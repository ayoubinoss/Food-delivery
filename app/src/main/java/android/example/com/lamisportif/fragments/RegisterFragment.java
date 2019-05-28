package android.example.com.lamisportif.fragments;

import android.app.ProgressDialog;
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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class RegisterFragment extends Fragment {

    private TextInputEditText mEmail;
    private TextInputEditText mPassword;
    private TextInputEditText mRepeatPassword;
    private TextInputEditText mPhoneNumber;
    private TextInputEditText mName;

    private MaterialButton mButtonCancel;
    private MaterialButton mButtonRegister;
    private MaterialButton mButtonSignIn;
    private ProgressDialog progressDialog;

    private final static String TAG = "RegisterFragement";


    private FirebaseAuth mAuth;
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        // Inflate the layout for this fragment

        //initialize FirebaseAuth object
        mAuth = FirebaseAuth.getInstance();

        //initialize progressDialog object
        progressDialog = new ProgressDialog(getActivity());

        //initialize views
        mEmail = view.findViewById(R.id.email_edit_text);
        mPassword = view.findViewById(R.id.password_edit_text);
        mRepeatPassword = view.findViewById(R.id.r_password_edit_text);
        mPhoneNumber = view.findViewById(R.id.phone_number);
        mName = view.findViewById(R.id.name_edit_text);

        //initialize buttons
        mButtonCancel = view.findViewById(R.id.cancel_btn);
        mButtonRegister = view.findViewById(R.id.register_btn);
        mButtonSignIn = view.findViewById(R.id.sign_in_btn);

        //button to go to the Login fragment
        mButtonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((NavigationHost)getActivity()).navigateTo(new LoginFragment(), true);
            }
        });
        //button authentication
        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check password
                if(!isPasswordValid(mPassword.getText()))
                    mPassword.setError(getString(R.string.error_msg_password));

                //check email
                if(!isEmailValid(mEmail.getText())) {
                    mEmail.setError(getString(R.string.error_msg_email));
                }
                //check password
                if(!isRepeatPasswordValid(String.valueOf(mPassword.getText()),String.valueOf(mRepeatPassword.getText()))){
                    mRepeatPassword.setError(getString(R.string.error_msg_r_password));
                }
                //check phone number
                if(!isPhoneNumberValid(mPhoneNumber.getText())) {
                    mPhoneNumber.setError(getString(R.string.error_msg_phone));
                }
                //check name
                if(!isNameValid(mName.getText())) {
                    mName.setError(getString(R.string.name_msg_error));
                }
                else{
                    progressDialog.setMessage("Registering User ...");
                    progressDialog.show();

                    mEmail.setError(null); //clear Error
                    mPassword.setError(null); //clear Error
                    mRepeatPassword.setError(null); // clear Error
                    mName.setError(null);
                    mPhoneNumber.setError(null);

                    mAuth.createUserWithEmailAndPassword(
                            String.valueOf(mEmail.getText()),
                            String.valueOf(mPassword.getText()))
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        //Registered successfully
                                        Log.d(TAG,"Register successful");
                                        Toast.makeText(getActivity(),"Registered successfully",Toast.LENGTH_LONG ).show();
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        if(user != null)
                                            user.sendEmailVerification();
                                        updateUI(user);
                                    }
                                    else{
                                        //Register failure
                                        Log.d(TAG,"Register Failed");
                                        Toast.makeText(getActivity(),"Registration failed",Toast.LENGTH_LONG ).show();
                                        progressDialog.dismiss();
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
                mRepeatPassword.setText("");
            }
        });

        return view;
    }

    /**
     * check if user is already signed in else we display the login fragment
     * @param currentUser
     */
    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {
            storeUserInformations();
        }
    }

    /**
     * check if email is valid eg: abcd@cdefh.xyz
     * @param text email to verify
     * @return boolean
     */
    public boolean isEmailValid(@Nullable Editable text) {
        return !TextUtils.isEmpty(text) &&
                android.util.Patterns.EMAIL_ADDRESS.matcher(text).matches();
    }
    /**
     * check if the password is not empty
     * @param text password to verify
     * @return boolean
     */
    private boolean isPasswordValid(Editable text) {
        return !TextUtils.isEmpty(text);
    }

    /**
     * check if the user name is not empty
     * @param text name to verify
     * @return boolean
     */

    private boolean isNameValid(Editable text) { return !TextUtils.isEmpty(text); }

    /**
     * check if the phone number is valid
     * @param text phone to verify
     * @return boolean
     */
    private boolean isPhoneNumberValid(Editable text) { return !TextUtils.isEmpty(text) && text.length()==10;}

    /**
     * check if password match
     * @param text the password
     * @param text2 the password repeated
     * @return boolean
     */
    private boolean isRepeatPasswordValid(String text, String text2) {
        return text.equals(text2);
    }

    /**
     * store user details into FireStore
     */
    public void storeUserInformations() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> newUser = new HashMap<>();
        if(mName.getText() != null && mPhoneNumber.getText() != null && mEmail.getText() != null) {

            newUser.put("name", mName.getText().toString());
            newUser.put("phone_number", mPhoneNumber.getText().toString());
            newUser.put("email", mEmail.getText().toString());
        }

        db.collection("users").document(mEmail.getText().toString())
                .set(newUser)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                        progressDialog.dismiss();
                        FirebaseAuth.getInstance().signOut();
                        ((NavigationHost)getActivity()).navigateTo(new LoginFragment(), true);
                        Toast.makeText(getContext(), "An email was sent to your newslettre check your " +
                                "email letter to activate your account", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                        progressDialog.dismiss();
                        FirebaseAuth.getInstance().signOut();
                        Toast.makeText(getContext(), "try again", Toast.LENGTH_LONG).show();
                    }
                });


    }

}
