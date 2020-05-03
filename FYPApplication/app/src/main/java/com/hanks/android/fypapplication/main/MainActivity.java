package com.hanks.android.fypapplication.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.hanks.android.fypapplication.R;
import com.hanks.android.fypapplication.data.LoginDataSource;
import com.hanks.android.fypapplication.data.LoginRepository;
import com.hanks.android.fypapplication.ui.login.LoginActivity;

public class MainActivity extends AppCompatActivity implements ProfileDialogFragment.ProfileDialogListener {

    //there is no state currently in the app, so view model is deemed unnecessary
    private LoginRepository loginRepository;
    //fragment manager
    private FragmentManager fm;

    private ImageView profileIcon;

    private final String PROFILE_FRAGMENT = "fragment_profile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        profileIcon = (ImageView) findViewById(R.id.main_profile_picture);

        loginRepository = LoginRepository.getInstance(new LoginDataSource(getApplication()), getApplication());
        fm = getSupportFragmentManager();

        //set drawable
        setProfilePicture();

        profileIcon.setOnClickListener((v) -> {
                //check whether the fragment is exist
                Fragment curr = fm.findFragmentByTag(PROFILE_FRAGMENT);
                if (curr != null) {
                    fm.beginTransaction().remove(curr).commit();
                }
                ProfileDialogFragment profileDialog = ProfileDialogFragment.newInstance(
                                                        loginRepository.getCurrentUser());
                profileDialog.show(fm, PROFILE_FRAGMENT);
        });
    }

    private void setProfilePicture(){
        Drawable profilePicture = ResourcesCompat.getDrawable(getResources(),
                loginRepository.getCurrentUser().getProfilePicture(), null);
        Bitmap profileBitmap = ((BitmapDrawable)profilePicture).getBitmap();
        profileBitmap = Bitmap.createScaledBitmap(profileBitmap, 150, 150, false);
        RoundedBitmapDrawable roundProfilePicture = RoundedBitmapDrawableFactory.create(getResources(), profileBitmap);
        roundProfilePicture.setCircular(true);
        profileIcon.setImageDrawable(roundProfilePicture);
    }

    @Override
    public void logOut() {
        loginRepository.logout();
        startActivity(new Intent(getApplication(), LoginActivity.class));
        finish();
    }
}
