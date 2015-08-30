package io.github.imruahmed.noswipe;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.support.v4.app.FragmentActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(android.R.id.content, new PhotoGallery()).commit();

    }

    public void selectImage(View view) {

        Animation animation = AnimationUtils.loadAnimation(getBaseContext(), android.R.anim.fade_out);
        view.startAnimation(animation);

    }

    public void startGallery(View view) {

        Intent i = new Intent(this, GalleryPager.class);
        startActivity(i);

    }
}