package com.letsgoapp.Views;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.letsgoapp.R;
import com.letsgoapp.ViewModels.LoginViewModel;
import com.letsgoapp.databinding.ActivityLoginBinding;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;
import com.vk.sdk.util.VKUtil;

import static com.letsgoapp.Utils.ContextUtill.SetTopContext;

public class LoginActivity extends AppCompatActivity {
    private LoginViewModel loginViewModel;
    public static final int MY_PERMISSIONS = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SetTopContext(this);
        ActivityLoginBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_login);
        loginViewModel = new LoginViewModel();
        binding.setLoginVM(loginViewModel);
        String[] fingerprints = VKUtil.getCertificateFingerprint(this, this.getPackageName());
//        loginViewModel.goVk();
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        SetTopContext(this);
        Intent answerIntent = new Intent();
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                requestPermissions();
                loginViewModel.setUserVk(res);
            }

            @Override
            public void onError(VKError error) {
                Log.d("LoginActivity: ", "onError"+error.errorMessage);
                Log.d("LoginActivity: ", "onError"+error.toString());
                answerIntent.putExtra("auth", false);
                loginViewModel.retry();
                //finish();
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void requestPermissions(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS);
        }
    }

}
