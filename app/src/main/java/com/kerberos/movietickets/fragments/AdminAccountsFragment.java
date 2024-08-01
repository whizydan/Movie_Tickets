package com.kerberos.movietickets.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.kerberos.movietickets.R;
import com.kerberos.movietickets.activities.UserHomeActivity;
import com.kerberos.movietickets.dbhandler.DbHandler;
import com.kerberos.movietickets.models.Users;
import com.kerberos.movietickets.utils.TinyDB;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminAccountsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminAccountsFragment extends Fragment {

    public AdminAccountsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdminAccountsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminAccountsFragment newInstance(String param1, String param2) {
        AdminAccountsFragment fragment = new AdminAccountsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_accounts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextInputLayout email = view.findViewById(R.id.email);
        TextInputLayout address = view.findViewById(R.id.address);
        TextInputLayout phone = view.findViewById(R.id.phone);
        TextInputLayout password = view.findViewById(R.id.password);
        TextInputLayout name = view.findViewById(R.id.name);
        MaterialButton save = view.findViewById(R.id.save);
        TextInputLayout answer = view.findViewById(R.id.answer);
        DbHandler dbHandler = new DbHandler(requireContext());
        TinyDB tinyDB = new TinyDB(requireContext());
        Users user = dbHandler.getUser(tinyDB.getInt("id"));

        //set the values to the views
        email.getEditText().setText(user.getEmail());
        address.getEditText().setText(user.getAddress());
        phone.getEditText().setText(user.getPhone());
        password.getEditText().setText(user.getPassword());
        name.getEditText().setText(user.getName());
        answer.getEditText().setText(user.getSecurityAnswer());
        password.getEditText().setText(user.getPassword());

        save.setOnClickListener(v -> {
            if (TextUtils.isEmpty(email.getEditText().getText().toString())){
                email.setError("Enter Email");
            }else if (TextUtils.isEmpty(address.getEditText().getText().toString())){
                address.setError("Enter Address");
            }else if (TextUtils.isEmpty(phone.getEditText().getText().toString())){
                phone.setError("Enter Phone");
            }else if (TextUtils.isEmpty(password.getEditText().getText().toString())){
                password.setError("Enter Password");
            }else if (TextUtils.isEmpty(name.getEditText().getText().toString())){
                name.setError("Enter Name");
            }else if (TextUtils.isEmpty(answer.getEditText().getText().toString())){
                name.setError("Enter a security code you will use to reset password");
            }else{
                Users userData = new Users();
                userData.setSecurityAnswer(answer.getEditText().getText().toString());
                userData.setAddress(address.getEditText().getText().toString());
                userData.setEmail(email.getEditText().getText().toString());
                userData.setName(name.getEditText().getText().toString());
                userData.setPassword(password.getEditText().getText().toString());
                userData.setRole("0");
                userData.setPhone(phone.getEditText().getText().toString());
                dbHandler.updateUser(userData);
            }

        });
    }
}