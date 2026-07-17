package pg.rental;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;
    private Button btnSignIn;
    private TextView tvSignUp;

    // firebase
    /*private String USER_TABLE = "User";

    private FirebaseHandler firebaseHandler;
    private DatabaseReference userDatabase;*/

    SQLiteDatabase db;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Login");
        sp = getSharedPreferences(ConstantSp.PREF, MODE_PRIVATE);
        db = openOrCreateDatabase("PGRental", MODE_PRIVATE, null);
        String tableQuery = "CREATE TABLE IF NOT EXISTS USERS(ID INTEGER PRIMARY KEY AUTOINCREMENT,USERNAME TEXT,EMAIL TEXT,PASSWORD TEXT,ROLE TEXT,NAME TEXT,GENDER TEXT,PHONE TEXT,DOB TEXT,ADDRESS TEXT)";
        db.execSQL(tableQuery);

        String rentQuery = "CREATE TABLE IF NOT EXISTS RENT(ID INTEGER PRIMARY KEY AUTOINCREMENT,USERID TEXT,TITLE TEXT,LOCATION TEXT,RENTFEE TEXT,RENTTYPE TEXT,ADDRESS TEXT,DESCRIPTION TEXT,NOBED TEXT,NOBATH TEXT,CONTACTNAME TEXT,CONTACTNO TEXT,EMAIL TEXT)";
        db.execSQL(rentQuery);
        /*firebaseHandler = new FirebaseHandler();
        userDatabase = firebaseHandler.getFirebaseConnection(USER_TABLE);*/

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnSignIn = findViewById(R.id.btnSignIn);
        tvSignUp = findViewById(R.id.tvSignUp);

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUpIntent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(signUpIntent);
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userName = etUsername.getText().toString().trim();
                final String password = etPassword.getText().toString().trim();

                if (userName.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter your Username and Password", Toast.LENGTH_SHORT).show();
                } else {
                    String selectQuery = "SELECT * FROM USERS WHERE USERNAME='" + userName + "' AND PASSWORD='" + password + "'";
                    Cursor cursor = db.rawQuery(selectQuery, null);
                    if (cursor.getCount() > 0) {
                        while (cursor.moveToNext()) {
                            sp.edit().putString(ConstantSp.ID, cursor.getString(0)).commit();
                            sp.edit().putString(ConstantSp.USERNAME, cursor.getString(1)).commit();
                            sp.edit().putString(ConstantSp.EMAIL, cursor.getString(2)).commit();
                            sp.edit().putString(ConstantSp.PASSWORD, cursor.getString(3)).commit();
                            sp.edit().putString(ConstantSp.ROLE, cursor.getString(4)).commit();
                            sp.edit().putString(ConstantSp.NAME, cursor.getString(5)).commit();
                            sp.edit().putString(ConstantSp.GENDER, cursor.getString(6)).commit();
                            sp.edit().putString(ConstantSp.PHONE, cursor.getString(7)).commit();
                            sp.edit().putString(ConstantSp.DOB, cursor.getString(8)).commit();
                            sp.edit().putString(ConstantSp.ADDRESS, cursor.getString(9)).commit();

                            Toast.makeText(MainActivity.this, "Sign in success", Toast.LENGTH_SHORT).show();
                            Intent dashboardIntent = new Intent(MainActivity.this, Dashboard.class);
                            startActivity(dashboardIntent);
                            finish();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Invalid Credential", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
