package comcesar1287.github.www.additionalactivities.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import comcesar1287.github.www.additionalactivities.R;
import comcesar1287.github.www.additionalactivities.controller.domain.Category;
import comcesar1287.github.www.additionalactivities.controller.fragment.CategoryFragment;
import comcesar1287.github.www.additionalactivities.controller.util.Utility;
import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        verifyIfUserIsLogged();

        setContentView(R.layout.activity_main);

        CategoryFragment frag = (CategoryFragment) getSupportFragmentManager().findFragmentByTag(Utility.KEY_MAIN_FRAGMENT);
        if(frag == null) {
            frag = new CategoryFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.categories_fragment_container, frag, Utility.KEY_MAIN_FRAGMENT);
            ft.commit();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_menu_logout:
                logout();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setMessage(R.string.message_logout)
                .setPositiveButton(R.string.yes_logout, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toasty.success(MainActivity.this, getResources().getString(R.string.msg_logout), Toast.LENGTH_SHORT).show();
                        Thread mThread = new Thread(){
                            @Override
                            public void run() {
                                FirebaseAuth.getInstance().signOut();
                                LoginManager.getInstance().logOut();
                            }
                        };
                        mThread.start();
                    }
                })
                .setNegativeButton(R.string.no_logout, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // UserFirebase cancelled the dialog
                    }
                });
        builder.show();
    }

    private void verifyIfUserIsLogged() {
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    startActivity(new Intent(MainActivity.this, SignWithActivity.class));
                    finish();
                }
            }
        };
    }

    public List<Category> getCategoriesList() {

        ArrayList<Category> mListCategories = new ArrayList<>();

        Category category = new Category();
        category.setName("Iniciação à Docência, à Pesquisa e/ou Extensão");
        mListCategories.add(category);

        Category category1 = new Category();
        category1.setName("Atividades artísticas, culturais e/ou esportivas");
        mListCategories.add(category1);

        Category category2 = new Category();
        category2.setName("Atividades de participação e/ou organização de eventos");
        mListCategories.add(category2);

        Category category3 = new Category();
        category3.setName("Atividades de iniciação profissional e/ou correlatas");
        mListCategories.add(category3);

        Category category4 = new Category();
        category4.setName("Produção técnica e/ou científica");
        mListCategories.add(category4);

        Category category5 = new Category();
        category5.setName("Vivências ou experiências de gestão");
        mListCategories.add(category5);

        Category category6 = new Category();
        category6.setName("Outras atividades relacionadas à Universidade ou ao Curso");
        mListCategories.add(category6);

        return mListCategories;
    }
}
