package pg.rental;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;

import android.view.MenuInflater;
import android.view.View;

import com.google.android.material.navigation.NavigationView;

import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SearchView.OnQueryTextListener {

    private TextView tvHeaderUserName;
    private TextView tvHeaderEmail;

    private RecyclerView rvHouses;
    private PgAdapter houseAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<Rent> rent;

    SharedPreferences sp;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        sp = getSharedPreferences(ConstantSp.PREF, MODE_PRIVATE);

        db = openOrCreateDatabase("PGRental", MODE_PRIVATE, null);
        String tableQuery = "CREATE TABLE IF NOT EXISTS USERS(ID INTEGER PRIMARY KEY AUTOINCREMENT,USERNAME TEXT,EMAIL TEXT,PASSWORD TEXT,ROLE TEXT,NAME TEXT,GENDER TEXT,PHONE TEXT,DOB TEXT,ADDRESS TEXT)";
        db.execSQL(tableQuery);

        String rentQuery = "CREATE TABLE IF NOT EXISTS RENT(ID INTEGER PRIMARY KEY AUTOINCREMENT,USERID TEXT,TITLE TEXT,LOCATION TEXT,RENTFEE TEXT,RENTTYPE TEXT,ADDRESS TEXT,DESCRIPTION TEXT,NOBED TEXT,NOBATH TEXT,CONTACTNAME TEXT,CONTACTNO TEXT,EMAIL TEXT)";
        db.execSQL(rentQuery);



        if (sp.getString(ConstantSp.ROLE, "").equals("Renter")) {
            NavigationView navigationView = findViewById(R.id.nav_view);
            navigationView.getMenu().findItem(R.id.itemAddRent).setVisible(false);
            navigationView.getMenu().findItem(R.id.itemMyPosts).setVisible(false);
        } else if (sp.getString(ConstantSp.ROLE, "").equals("Owner")) {
            NavigationView navigationView = findViewById(R.id.nav_view);
            navigationView.getMenu().findItem(R.id.itemAddRent).setVisible(true);
            navigationView.getMenu().findItem(R.id.itemMyPosts).setVisible(true);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        tvHeaderUserName = headerView.findViewById(R.id.tvHeaderUserName);
        tvHeaderEmail = headerView.findViewById(R.id.tvHeaderEmail);

        tvHeaderUserName.setText(sp.getString(ConstantSp.NAME, ""));
        tvHeaderEmail.setText(sp.getString(ConstantSp.EMAIL, ""));

        rvHouses = findViewById(R.id.rvHouses);
        rvHouses.setHasFixedSize(true);

        rent = new ArrayList<Rent>();

        String selectQuery = "SELECT * FROM RENT";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                rent.add(new Rent(cursor.getString(2), cursor.getString(3), cursor.getString(4),
                        cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8),
                        cursor.getString(9), cursor.getString(10), cursor.getString(11),cursor.getString(12), cursor.getString(0)));
            }
            layoutManager = new LinearLayoutManager(Dashboard.this);
            houseAdapter = new PgAdapter(Dashboard.this, rent,"Home");

            rvHouses.setLayoutManager(layoutManager);
            rvHouses.setItemAnimator(new DefaultItemAnimator());
            rvHouses.setAdapter(houseAdapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menusearch, menu);
        MenuItem item = menu.findItem(R.id.menuSearch);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);

        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        newText = newText.toLowerCase();
        ArrayList<Rent> newList = new ArrayList<>();
        for (Rent rents : rent) {

            String title = rents.getTitle().toLowerCase();
            String location = rents.getLocation().toLowerCase();
            String price = rents.getFee().toLowerCase();
            String address = rents.getAddress().toLowerCase();

            if (location.contains(newText) || title.contains(newText) ||
                    price.contains(newText) || address.contains(newText)) {
                newList.add(rents);
            }
        }
        houseAdapter.setFilter(newList);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.itemHome) {
            Intent homeIntent = new Intent(Dashboard.this, Dashboard.class);
            startActivity(homeIntent);
        } else if (id == R.id.itemAddRent) {
            Intent postAdIntent = new Intent(Dashboard.this, AddPgActivity.class);
            startActivity(postAdIntent);
        } else if (id == R.id.itemMyPosts) {
            Intent myPostIntent = new Intent(Dashboard.this, MyPg.class);
            startActivity(myPostIntent);
        } else if (id == R.id.itemProfile) {
            Intent profileIntent = new Intent(Dashboard.this, Profile.class);
            startActivity(profileIntent);
        } else if (id == R.id.itemSignOut) {
            super.onBackPressed();
            Intent loginIntent = new Intent(getApplicationContext(), MainActivity.class);
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(loginIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
