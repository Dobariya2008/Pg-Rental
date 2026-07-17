package pg.rental;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Profile extends AppCompatActivity {

    private TextView tvProfileUserRole;
    private TextView tvProfileUserName;
    private EditText tvProfileEmail;
    private EditText tvProfileFullName;
    private EditText tvProfileContact;
    private TextView tvProfileGender;
    private TextView tvProfileDateOfBirth;
    private EditText tvProfileAddress;

    private Button btnUpdate;
    private Button btnDeleteAccount;

    SharedPreferences sp;
    SQLiteDatabase db;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setTitle("Profile");
        sp = getSharedPreferences(ConstantSp.PREF, MODE_PRIVATE);

        db = openOrCreateDatabase("PGRental", MODE_PRIVATE, null);

        String tableQuery = "CREATE TABLE IF NOT EXISTS USERS(ID INTEGER PRIMARY KEY AUTOINCREMENT,USERNAME TEXT,EMAIL TEXT,PASSWORD TEXT,ROLE TEXT,NAME TEXT,GENDER TEXT,PHONE TEXT,DOB TEXT,ADDRESS TEXT)";
        db.execSQL(tableQuery);

        String rentQuery = "CREATE TABLE IF NOT EXISTS RENT(ID INTEGER PRIMARY KEY AUTOINCREMENT,USERID TEXT,TITLE TEXT,LOCATION TEXT,RENTFEE TEXT,RENTTYPE TEXT,ADDRESS TEXT,DESCRIPTION TEXT,NOBED TEXT,NOBATH TEXT,CONTACTNAME TEXT,CONTACTNO TEXT,EMAIL TEXT)";
        db.execSQL(rentQuery);

        tvProfileUserRole = findViewById(R.id.tvProfileUserRole);
        tvProfileUserName = findViewById(R.id.tvProfileUserName);
        tvProfileEmail = findViewById(R.id.tvProfileEmail);
        tvProfileFullName = findViewById(R.id.tvProfileFullName);
        tvProfileContact = findViewById(R.id.tvProfileContact);
        tvProfileGender = findViewById(R.id.tvProfileGender);
        tvProfileDateOfBirth = findViewById(R.id.tvProfileDateOfBirth);
        tvProfileAddress = findViewById(R.id.tvProfileAddress);

        btnUpdate = findViewById(R.id.btnUpdate);
        btnDeleteAccount = findViewById(R.id.btnDeleteAccount);

        tvProfileUserRole.setText("Logged in as: " + sp.getString(ConstantSp.ROLE, ""));
        tvProfileUserName.setText(sp.getString(ConstantSp.USERNAME, ""));
        tvProfileEmail.setText(sp.getString(ConstantSp.EMAIL, ""));
        tvProfileFullName.setText(sp.getString(ConstantSp.NAME, ""));
        tvProfileContact.setText(sp.getString(ConstantSp.PHONE, ""));
        tvProfileGender.setText(sp.getString(ConstantSp.GENDER, ""));
        tvProfileDateOfBirth.setText(sp.getString(ConstantSp.DOB, ""));
        tvProfileAddress.setText(sp.getString(ConstantSp.ADDRESS, ""));

        tvProfileGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] gender = {"Male", "Female"};
                AlertDialog.Builder alert = new AlertDialog.Builder(Profile.this);
                alert.setTitle("Select Gender");
                alert.setSingleChoiceItems(gender, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (gender[which] == "Male") {
                            tvProfileGender.setText("Male");
                        } else if (gender[which] == "Female") {
                            tvProfileGender.setText("Female");
                        }
                    }
                });
                alert.show();
            }
        });

        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "dd/MM/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

                tvProfileDateOfBirth.setText(sdf.format(myCalendar.getTime()));
            }
        };

        tvProfileDateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Profile.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updateQuery = "UPDATE USERS SET ADDRESS='" + tvProfileAddress.getText().toString().trim() + "',NAME='" + tvProfileFullName.getText().toString().trim() + "',EMAIL='" + tvProfileEmail.getText().toString().trim() + "',GENDER='" + tvProfileGender.getText().toString().trim() + "',PHONE='" + tvProfileContact.getText().toString().trim() + "',DOB='" + tvProfileDateOfBirth.getText().toString().trim() + "' WHERE ID='" + sp.getString(ConstantSp.ID, "") + "'";
                db.execSQL(updateQuery);
                sp.edit().putString(ConstantSp.ADDRESS, tvProfileAddress.getText().toString().trim()).commit();
                sp.edit().putString(ConstantSp.NAME, tvProfileFullName.getText().toString().trim()).commit();
                sp.edit().putString(ConstantSp.EMAIL, tvProfileEmail.getText().toString().trim()).commit();
                sp.edit().putString(ConstantSp.GENDER, tvProfileGender.getText().toString().trim()).commit();
                sp.edit().putString(ConstantSp.PHONE, tvProfileContact.getText().toString().trim()).commit();
                sp.edit().putString(ConstantSp.DOB, tvProfileDateOfBirth.getText().toString().trim()).commit();
                new AlertDialog.Builder(Profile.this)
                        .setTitle("Success")
                        .setIcon(R.drawable.ic_done_black_24dp)
                        .setMessage("Updated successfully!")
                        .show();
            }
        });

        btnDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String deleteQuery = "DELETE FROM USERS WHERE ID='" + sp.getString(ConstantSp.ID, "") + "'";
                db.execSQL(deleteQuery);

                String deleteRentQuery = "DELETE FROM RENT WHERE USERID='" + sp.getString(ConstantSp.ID, "") + "'";
                db.execSQL(deleteRentQuery);

                new AlertDialog.Builder(Profile.this)
                        .setTitle("Success")
                        .setMessage("Your account has deleted!")
                        .show();

                sp.edit().clear().commit();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
}
