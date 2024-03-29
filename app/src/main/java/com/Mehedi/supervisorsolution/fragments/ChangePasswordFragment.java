package com.mehedihasan.supervisorsolution.fragments;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mehedihasan.supervisorsolution.R;
import com.mehedihasan.supervisorsolution.interfaces.OnFragmentBackPressedListener;
import com.mehedihasan.supervisorsolution.interfaces.OnMyMessageListener;
import com.mehedihasan.supervisorsolution.models.ServerResponse;
import com.mehedihasan.supervisorsolution.preferences.UserSharedPrefManager;
import com.mehedihasan.supervisorsolution.retrofits.MyApiService;
import com.mehedihasan.supervisorsolution.retrofits.NetworkCall;
import com.mehedihasan.supervisorsolution.retrofits.ResponseCallback;
import com.mehedihasan.supervisorsolution.utils.OthersUtil;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

public class ChangePasswordFragment extends Fragment implements View.OnClickListener, OnFragmentBackPressedListener {

    private Context context;
    private AlertDialog alertDialog;
    private OnMyMessageListener onMyMessageSendListener;
    private UserSharedPrefManager sharedPrefManager;
    private TextInputLayout currentPasswordTextInputLayout, newPasswordTextInputLayout, confirmPasswordTextInputLayout;

    private String currentPassword, newPassword;

    public ChangePasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = container.getContext();
        return inflater.inflate(R.layout.fragment_password_change, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        currentPasswordTextInputLayout = view.findViewById(R.id.tilCurrentPassword);
        newPasswordTextInputLayout = view.findViewById(R.id.tilNewChangedPassword);
        confirmPasswordTextInputLayout = view.findViewById(R.id.tilChangedConfirmPassword);
        TextInputEditText changeConfirmPasswordEditText = view.findViewById(R.id.etChangeConfirmPassword);
        currentPasswordTextInputLayout.requestFocus();

        view.findViewById(R.id.btnChangePassword).setOnClickListener(this);
        view.findViewById(R.id.btnBackChangePassword).setOnClickListener(this);
        changeConfirmPasswordEditText.setOnEditorActionListener(editorActionListener);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getActivity() != null) {
            getActivity().setTitle("Change Password");
        }
        sharedPrefManager = new UserSharedPrefManager(context);

        if (getActivity() != null) {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        }
    }

    private boolean getPasswordChangeFragmentData() {
        currentPassword = Objects.requireNonNull(currentPasswordTextInputLayout.getEditText()).getText().toString();
        newPassword = Objects.requireNonNull(newPasswordTextInputLayout.getEditText()).getText().toString();
        String confirmPassword = Objects.requireNonNull(confirmPasswordTextInputLayout.getEditText()).getText().toString();

        if (newPassword.length() < 8 || newPassword.contains(" ")) {
            newPassword = "";
        }

        if (!TextUtils.isEmpty(currentPassword) && !TextUtils.isEmpty(confirmPassword) && OthersUtil.passwordPatternCheck(newPassword)) {
            if (!newPassword.equals(confirmPassword)) {
                confirmPasswordTextInputLayout.setError("New and confirm password not matches");
                return false;
            } else {
                return true;
            }
        } else {
            if (TextUtils.isEmpty(currentPassword.trim())) {
                currentPasswordTextInputLayout.setError("Current password can not be empty");
            }
            if (TextUtils.isEmpty(newPassword.trim())) {
                newPasswordTextInputLayout.setError("Password must be at least 8 characters long without a whitespace");
            }
            if (TextUtils.isEmpty(confirmPassword.trim())) {
                confirmPasswordTextInputLayout.setError("Confirm password can not be empty");
            }
            if (!OthersUtil.passwordPatternCheck(newPassword)) {
                newPasswordTextInputLayout.setError("Password must be combined as alphabets, numbers and symbols");
            }

            return false;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnChangePassword:
                if (getPasswordChangeFragmentData()) {
                    OthersUtil othersUtil = new OthersUtil(context);
                    alertDialog = othersUtil.setCircularProgressBar();
                    serverCallForPasswordChange();
                }
                break;

            case R.id.btnBackChangePassword:
                onMyMessageSendListener.onMyFragment(new ProfileFragment());
                break;
        }
    }

    private void serverCallForPasswordChange() {
        MyApiService myApiService = new NetworkCall();

        myApiService.changePassword(sharedPrefManager.getUser().getEmail(), currentPassword, newPassword, new ResponseCallback<ServerResponse>() {
            @Override
            public void onSuccess(ServerResponse data) {
                alertDialog.dismiss();

                if (data != null) {
                    if (data.getError().equals(false)) {
                        OthersUtil.closeVisibleSoftKeyBoard(Objects.requireNonNull(getActivity()));
                        onMyMessageSendListener.onMyFragment(new ProfileFragment());
                        Toast.makeText(context, data.getMessage(), Toast.LENGTH_LONG).show();
                    } else if (data.getError().equals(true)) {
                        Toast.makeText(context, data.getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Something went wrong! Try again later", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onError(Throwable th) {
                alertDialog.dismiss();
                Toast.makeText(context, "Error: "+th.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private TextView.OnEditorActionListener editorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_GO) {
                if (getPasswordChangeFragmentData()) {
                    OthersUtil othersUtil = new OthersUtil(context);
                    alertDialog = othersUtil.setCircularProgressBar();
                    serverCallForPasswordChange();
                }
            }
            return true;
        }
    };

    @Override
    public boolean onFragmentBackPressed() {
        onMyMessageSendListener.onMyFragment(new ProfileFragment());
        return true;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            onMyMessageSendListener = (OnMyMessageListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implements OnMyMessageSendListener methods.");
        }
    }
}
