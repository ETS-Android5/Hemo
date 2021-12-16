package com.dev334.blood.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.dev334.blood.R;

public class HomeActivity extends AppCompatActivity {

    private HomeFragment homeFragment;
    private NotificationFragment notificationFragment;
    private ScheduleFragment scheduleFragment;
    private ProfileFragment profileFragment;
    private RequestFragment requestFragment;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        homeFragment = HomeFragment.newInstance();
        notificationFragment=NotificationFragment.newInstance();
        scheduleFragment=ScheduleFragment.newInstance();
        profileFragment=ProfileFragment.newInstance();
        requestFragment=RequestFragment.newInstance();

        fragmentManager=getSupportFragmentManager();

        replaceFragment(homeFragment);

    }

    private void replaceFragment(Fragment fragmentToShow) {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

        // Hide all of the fragments
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            transaction.hide(fragment);
        }

        if (fragmentToShow.isAdded()) {
            // When fragment was previously added - show it
            transaction.show(fragmentToShow);
        } else {
            // When fragment is adding first time - add it
            transaction.add(R.id.LoginContainer, fragmentToShow);
        }

        transaction.commit();
    }
}