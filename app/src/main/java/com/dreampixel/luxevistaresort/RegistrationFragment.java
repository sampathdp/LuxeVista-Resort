package com.dreampixel.luxevistaresort;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.regex.Pattern;

public class RegistrationFragment extends Fragment {

    private EditText etFullName, etEmail, etContact, etPassword, etDob;
    private RadioGroup rgGender;
    private CheckBox cbTerms;
    private Button btnRegister;
    private ImageView ivProfile;
    private DatabaseHelper dbHelper;

    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[A-Z]).{6,}$");

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registration, container, false);

        etFullName = view.findViewById(R.id.et_full_name);
        etEmail = view.findViewById(R.id.et_email);
        etContact = view.findViewById(R.id.et_contact);
        etDob = view.findViewById(R.id.et_dob);
        rgGender = view.findViewById(R.id.rg_gender);
        etPassword = view.findViewById(R.id.et_password);
        btnRegister = view.findViewById(R.id.btn_register);
        ivProfile = view.findViewById(R.id.iv_profile_picture);
        cbTerms = view.findViewById(R.id.cb_terms);

        dbHelper = new DatabaseHelper(getActivity());

        etDob.setOnClickListener(v -> showDatePicker());
        btnRegister.setOnClickListener(v -> registerUser());

        return view;
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(getActivity(),
                (view, year, month, day) -> etDob.setText(day + "/" + (month + 1) + "/" + year),
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    private void registerUser() {
        String fullName = etFullName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String contact = etContact.getText().toString().trim();
        String dob = etDob.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String gender = null;
        if (rgGender.getCheckedRadioButtonId() != -1) {
            gender = ((RadioButton) getView().findViewById(rgGender.getCheckedRadioButtonId())).getText().toString();
        }

        if (fullName.isEmpty() || email.isEmpty() || contact.isEmpty() || dob.isEmpty() || password.isEmpty() || gender == null) {
            Toast.makeText(getActivity(), "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(getActivity(), "Enter a valid email address", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!contact.matches("\\d{10}")) {
            Toast.makeText(getActivity(), "Contact number must be exactly 10 digits", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            Toast.makeText(getActivity(), "Password must have at least 6 characters and one uppercase letter", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!cbTerms.isChecked()) {
            Toast.makeText(getActivity(), "Please accept the terms and conditions", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get profile image
        byte[] profileImage = imageToByteArray();
        if (profileImage == null) {
            Toast.makeText(getActivity(), "Please select a profile picture", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = new User(fullName, email, contact, dob, gender, password, profileImage);

        if (dbHelper.registerUser(user)) {
            Toast.makeText(getActivity(), "Registration Successful!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Email already exists!", Toast.LENGTH_SHORT).show();
        }
    }

    private byte[] imageToByteArray() {
        if (ivProfile.getDrawable() == null) {
            return null;
        }

        Bitmap bitmap = ((BitmapDrawable) ivProfile.getDrawable()).getBitmap();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        return outputStream.toByteArray();
    }
}
