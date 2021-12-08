package com.example.assignment;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.method.KeyListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String fn, ln, email;
    private TextView errorText;
    private EditText fnameText;
    private EditText lnameText;
    private EditText emailText;
    private Button editBtn;
    private Button changePwdBtn;
    private boolean emailChanged;
    private TextView currPassText, newPassText, passConfText;
    private String currPass, newPass, passConf;
    private TextView hide1, hide2, hide3;

    class DefaultFormat{
        public Drawable back;
        public KeyListener key;
        DefaultFormat(EditText et){
            back = et.getBackground();
            key = et.getKeyListener();
        }
    }

    private DefaultFormat fnameFormat, lnameFormat, emailFormat;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        errorText = view.findViewById(R.id.profError);
        fnameText = view.findViewById(R.id.profFirst);
        lnameText = view.findViewById(R.id.profLast);
        emailText = view.findViewById(R.id.profEmail);
        editBtn = view.findViewById(R.id.profileEdit);
        changePwdBtn = view.findViewById(R.id.profileChangePwd);

        hide1 = view.findViewById(R.id.profHide1);
        hide2 = view.findViewById(R.id.profHide2);
        hide3 = view.findViewById(R.id.profHide3);
        currPassText = view.findViewById(R.id.profCurrPass);
        newPassText = view.findViewById(R.id.profNewPass);
        passConfText = view.findViewById(R.id.profPassConf);

        fnameFormat = new DefaultFormat(fnameText);
        lnameFormat = new DefaultFormat(lnameText);
        emailFormat = new DefaultFormat(emailText);
        setNotEditingMode();

        changePwdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPasswordChangeMode();
            }
        });

        FirebaseFirestore.getInstance().collection("user").document(((Global)getActivity().getApplication()).
                getLoginUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                email = ((Global)getActivity().getApplication()).getLoginUser().getEmail();
                fn = documentSnapshot.get("fname").toString();
                ln = documentSnapshot.get("lname").toString();
                displayProfile();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("Profile:Firestore:getUser:Fail", e.getLocalizedMessage(), e);
                errorText.setText("Failed to fetch your profile, please try again later");
            }
        });
    }

    private void setPasswordChangeMode() {
        displayProfile();
        setNotEditingMode();
        resetPwds();
        unhidePwds();

        changePwdBtn.setText("Submit");
        changePwdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePwds();
                if(validatePwds()){
                    ((Global)getActivity().getApplication()).getLoginUser().reauthenticate(EmailAuthProvider
                            .getCredential(email, currPass)).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            ((Global)getActivity().getApplication()).getLoginUser()
                                    .updatePassword(newPass).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    unsetPasswordChangeMode();
                                    errorText.setText("Password changed, use new password to login next time");
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    errorText.setText("Failed to change password, please try again later");
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            errorText.setText("Authenticate failed, please try again");
                        }
                    });
                }
            }
        });

        editBtn.setText("Cancel");
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unsetPasswordChangeMode();
            }
        });
    }

    private void unsetPasswordChangeMode() {
        changePwdBtn.setText("Change Password");
        changePwdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPasswordChangeMode();
            }
        });
        hidePwds();
        setNotEditingMode();
    }

    private boolean validatePwds() {
        if(currPass.equals("")){
            currPassText.setError("Cannot be empty");
            currPassText.requestFocus();
            return false;
        }
        if(newPass.equals("")){
            newPassText.setError("Cannot be empty");
            newPassText.requestFocus();
            return false;
        }
        if(passConf.equals("")){
            passConfText.setError("Cannot be empty");
            passConfText.requestFocus();
            return false;
        }
        if(!newPass.equals(passConf)){
            passConfText.setError("Must be same as the new password");
            passConfText.requestFocus();
            return false;
        }
        return true;
    }

    private void updatePwds() {
        currPass = currPassText.getText().toString();
        newPass = newPassText.getText().toString();
        passConf = passConfText.getText().toString();
    }

    private void resetPwds() {
        currPass = "";
        currPassText.setText(currPass);
        newPass = "";
        newPassText.setText(newPass);
        passConf = "";
        passConfText.setText(passConf);
    }

    private void unhidePwds() {
        hide1.setVisibility(View.VISIBLE);
        hide2.setVisibility(View.VISIBLE);
        hide3.setVisibility(View.VISIBLE);
        currPassText.setVisibility(View.VISIBLE);
        newPassText.setVisibility(View.VISIBLE);
        passConfText.setVisibility(View.VISIBLE);
    }

    private void hidePwds() {
        hide1.setVisibility(View.GONE);
        hide2.setVisibility(View.GONE);
        hide3.setVisibility(View.GONE);
        currPassText.setVisibility(View.GONE);
        newPassText.setVisibility(View.GONE);
        passConfText.setVisibility(View.GONE);
    }

    private void displayProfile() {
        fnameText.setText(fn);
        lnameText.setText(ln);
        emailText.setText(email);
        errorText.setText("");
    }

    private void setEditingMode(){
        setEditable(fnameText, fnameFormat);
        setEditable(lnameText, lnameFormat);
        setEditable(emailText, emailFormat);
        editBtn.setText("Submit");
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateInput();
                if(validateInput()){
                    Map<String, Object> map= new HashMap<>();
                    map.put("fname", fn);
                    map.put("lname", ln);
                    FirebaseFirestore.getInstance().collection("user").document(((Global)
                            getActivity().getApplication()).getLoginUser().getUid()).update(map)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            displayProfile();
                            setNotEditingMode();
                            errorText.setText("Updated successfully!");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            errorText.setText("Something went wrong! Please try again later");
                        }
                    });
                    if(emailChanged){
                        ((Global)getActivity().getApplication()).getLoginUser()
                                .updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                errorText.setText(errorText.getText() + "\nEmail changed: login with new email next time");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                errorText.setText(errorText.getText() + "\nFailed to change email");
                                email = ((Global)getActivity().getApplication()).getLoginUser().getEmail();
                                displayProfile();
                            }
                        });
                    }
                }
            }
        });
    }

    private void updateInput() {
        fn = fnameText.getText().toString();
        ln = lnameText.getText().toString();
        if(email.equals(emailText.getText().toString())){
            emailChanged = false;
        }else {
            email = emailText.getText().toString();
            emailChanged = true;
        }
    }

    private boolean validateInput() {
        if(fn.equals("")){
            fnameText.setError("Cannot be empty");
            fnameText.requestFocus();
            return false;
        }
        if(ln.equals("")){
            lnameText.setError("Cannot be empty");
            lnameText.requestFocus();
            return false;
        }
        if(email.equals("")){
            emailText.setError("Cannot be empty");
            emailText.requestFocus();
            return false;
        }
        return true;
    }

    private void setEditable(EditText text, DefaultFormat format) {
        text.setEnabled(true);
        text.setCursorVisible(true);
        text.setBackground(format.back);
        text.setKeyListener(format.key);
    }

    private void setNotEditingMode(){
        setNotEditable(fnameText);
        setNotEditable(lnameText);
        setNotEditable(emailText);
        editBtn.setText("Update");
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                errorText.setText("");
                setEditingMode();
            }
        });
    }

    private void setNotEditable(EditText text) {
        text.setEnabled(false);
        text.setCursorVisible(false);
        text.setBackgroundColor(Color.TRANSPARENT);
        text.setKeyListener(null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }
}