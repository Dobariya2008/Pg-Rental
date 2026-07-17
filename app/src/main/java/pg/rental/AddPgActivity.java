package pg.rental;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddPgActivity extends AppCompatActivity {

    private EditText etTitle;
    private EditText etLocation;
    private EditText etRentFee;
    private Spinner spinnerPeriod;
    private EditText etAddress;
    private EditText etDescription;
    private EditText etNumOfBeds;
    private EditText etNumOfBaths;
    private EditText etContactName;
    private EditText etContactNumber;
    private EditText etContactEmail;
    private Button btnPost;

    SharedPreferences sp;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pg);
        getSupportActionBar().setTitle("Add PG");
        sp = getSharedPreferences(ConstantSp.PREF, MODE_PRIVATE);

        db = openOrCreateDatabase("PGRental", MODE_PRIVATE, null);

        String tableQuery = "CREATE TABLE IF NOT EXISTS USERS(ID INTEGER PRIMARY KEY AUTOINCREMENT,USERNAME TEXT,EMAIL TEXT,PASSWORD TEXT,ROLE TEXT,NAME TEXT,GENDER TEXT,PHONE TEXT,DOB TEXT,ADDRESS TEXT)";
        db.execSQL(tableQuery);

        String rentQuery = "CREATE TABLE IF NOT EXISTS RENT(ID INTEGER PRIMARY KEY AUTOINCREMENT,USERID TEXT,TITLE TEXT,LOCATION TEXT,RENTFEE TEXT,RENTTYPE TEXT,ADDRESS TEXT,DESCRIPTION TEXT,NOBED TEXT,NOBATH TEXT,CONTACTNAME TEXT,CONTACTNO TEXT,EMAIL TEXT)";
        db.execSQL(rentQuery);

        etTitle = findViewById(R.id.etTitle);
        etLocation = findViewById(R.id.etLocation);
        etRentFee = findViewById(R.id.etRentFee);
        spinnerPeriod = findViewById(R.id.spinnerPeriod);
        etAddress = findViewById(R.id.etAddress);
        etDescription = findViewById(R.id.etDescription);
        etNumOfBeds = findViewById(R.id.etNumOfBeds);
        etNumOfBaths = findViewById(R.id.etNumOfBaths);
        etContactName = findViewById(R.id.etContactName);
        etContactNumber = findViewById(R.id.etContactNumber);
        etContactEmail = findViewById(R.id.etContactEmail);
        btnPost = findViewById(R.id.btnPost);

        if (sp.getString(ConstantSp.NAME, "").equals("")) {
            etContactName.setEnabled(true);
        } else {
            etContactName.setText(sp.getString(ConstantSp.NAME, ""));
            etContactName.setEnabled(false);
        }
        etContactEmail.setText(sp.getString(ConstantSp.EMAIL, ""));
        etContactEmail.setEnabled(false);

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String adTitle = etTitle.getText().toString().trim();
                final String adLocation = etLocation.getText().toString().trim();
                final String rentFee = etRentFee.getText().toString().trim();
                final String periodOfTime;
                if (spinnerPeriod.getSelectedItem().toString().equals("Monthly")) {
                    periodOfTime = "month";
                } else {
                    periodOfTime = "year";
                }
                final String address = etAddress.getText().toString().trim();
                final String description = etDescription.getText().toString().trim();
                final String beds = etNumOfBeds.getText().toString().trim();
                final String baths = etNumOfBaths.getText().toString().trim();
                final String userName = sp.getString(ConstantSp.NAME, "");
                final String contactName = etContactName.getText().toString().trim();
                String contactNumber = etContactNumber.getText().toString().trim();
                String contactEmail = etContactEmail.getText().toString().trim();

                Calendar calendar = Calendar.getInstance();
//                SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm:ss");
                SimpleDateFormat mdformat = new SimpleDateFormat();
                final String strDate = mdformat.format(calendar.getTime());

                if (adTitle.equals("") || adLocation.equals("") || rentFee.equals("") || address.equals("") ||
                        description.equals("") || beds.equals("") || baths.equals("") || contactName.equals("") ||
                        contactNumber.equals("")) {
                    Toast.makeText(AddPgActivity.this, "Fill up all fields!", Toast.LENGTH_SHORT).show();
                } else {
                    String insertQuery = "INSERT INTO RENT VALUES(NULL,'"+sp.getString(ConstantSp.ID,"")+"','" + adTitle + "','" + adLocation + "','" + rentFee + "','" + periodOfTime + "','" + address + "','" + description + "','" + beds + "','" + baths + "','" + contactName + "','" + contactNumber + "','" + contactEmail + "')";
                    db.execSQL(insertQuery);
                    Toast.makeText(AddPgActivity.this, "Your ad has posted!", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }

            }
        });
    }
}
