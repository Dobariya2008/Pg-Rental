package pg.rental;

import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;

public class MyPg extends AppCompatActivity {

    private RecyclerView rvMyPosts;
    private RecyclerView.Adapter houseAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<Rent> rent;

    SharedPreferences sp;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pg);
        getSupportActionBar().setTitle("My PG");
        sp = getSharedPreferences(ConstantSp.PREF, MODE_PRIVATE);

        db = openOrCreateDatabase("PGRental", MODE_PRIVATE, null);

        String tableQuery = "CREATE TABLE IF NOT EXISTS USERS(ID INTEGER PRIMARY KEY AUTOINCREMENT,USERNAME TEXT,EMAIL TEXT,PASSWORD TEXT,ROLE TEXT,NAME TEXT,GENDER TEXT,PHONE TEXT,DOB TEXT,ADDRESS TEXT)";
        db.execSQL(tableQuery);

        String rentQuery = "CREATE TABLE IF NOT EXISTS RENT(ID INTEGER PRIMARY KEY AUTOINCREMENT,USERID TEXT,TITLE TEXT,LOCATION TEXT,RENTFEE TEXT,RENTTYPE TEXT,ADDRESS TEXT,DESCRIPTION TEXT,NOBED TEXT,NOBATH TEXT,CONTACTNAME TEXT,CONTACTNO TEXT,EMAIL TEXT)";
        db.execSQL(rentQuery);

        rvMyPosts = findViewById(R.id.rvMyPosts);
        rvMyPosts.setHasFixedSize(true);

        rent = new ArrayList<Rent>();
        String selectQuery = "SELECT * FROM RENT WHERE USERID='" + sp.getString(ConstantSp.ID, "") + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                rent.add(new Rent(cursor.getString(2), cursor.getString(3), cursor.getString(4),
                        cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8),
                        cursor.getString(9), cursor.getString(10), cursor.getString(11), cursor.getString(12), cursor.getString(0)));
            }
            layoutManager = new LinearLayoutManager(MyPg.this);
            houseAdapter = new PgAdapter(MyPg.this, rent, "MyPost");

            rvMyPosts.setLayoutManager(layoutManager);
            rvMyPosts.setItemAnimator(new DefaultItemAnimator());
            rvMyPosts.setAdapter(houseAdapter);
        }
    }
}
