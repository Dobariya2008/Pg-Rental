package pg.rental;

import android.content.Intent;
import android.net.Uri;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeDetailsActivity extends AppCompatActivity {

    private TextView tvPostTitle;
    private TextView tvPostBy;
    private TextView tvLocation;
    private TextView tvPostHousePrice;
    private TextView tvPostPeriod;
    private TextView tvAddress;
    private TextView tvPostDescription;
    private TextView tvNumOfBeds;
    private TextView tvNumOfBaths;
    private TextView tvContact;
    private TextView tvEmail;
    private Button btnContactPostOwner;
    private Button btnLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_details);
        getSupportActionBar().setTitle("PG Details");
        tvPostTitle = findViewById(R.id.tvPostTitle);
        tvPostBy = findViewById(R.id.tvPostBy);
        tvLocation = findViewById(R.id.tvLocation);
        tvPostHousePrice = findViewById(R.id.tvPostHousePrice);
        tvAddress = findViewById(R.id.tvAddress);
        tvPostPeriod = findViewById(R.id.tvPostPeriod);
        tvPostDescription = findViewById(R.id.tvPostDescription);
        tvNumOfBeds = findViewById(R.id.tvNumOfBeds);
        tvNumOfBaths = findViewById(R.id.tvNumOfBaths);
        tvContact = findViewById(R.id.tvContact);
        tvEmail = findViewById(R.id.tvEmail);
        btnLocation = findViewById(R.id.btnLocation);
        btnContactPostOwner = findViewById(R.id.btnContactPostOwner);

//        not working on v lower 21, they don't support png files
//        ivPostImage.setImageResource(R.drawable.signboard_for_rent);

        Bundle bundle = getIntent().getExtras();

        String title = bundle.getString("title");
        String postBy = bundle.getString("postBy");
        String location = bundle.getString("location");
        String fee = bundle.getString("fee");
        String period = bundle.getString("period");
        final String address = bundle.getString("address");
        String description = bundle.getString("description");
        int numBeds = Integer.parseInt(bundle.getString("beds"));
        int numBaths = Integer.parseInt(bundle.getString("baths"));
        final String contact = bundle.getString("contact");
        String email = bundle.getString("email");

        tvPostTitle.setText(title);
        tvPostBy.setText("Post by, " + postBy);
        tvLocation.setText("Location: " + location);
        tvPostHousePrice.setText(HomeDetailsActivity.this.getString(R.string.bdt_sign) + " " + fee);
        tvPostPeriod.setText(" /" + period);
        tvAddress.setText(address);
        tvPostDescription.setText(description);
        if (numBeds <= 1) {
            tvNumOfBeds.setText(numBeds + " Bed");
        } else {
            tvNumOfBeds.setText(numBeds + " Beds");
        }

        if (numBaths <= 1) {
            tvNumOfBaths.setText(numBaths + " Bath");
        } else {
            tvNumOfBaths.setText(numBaths + " Baths");
        }

        tvContact.setText(contact);
        tvEmail.setText(email);

        btnContactPostOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel: " + contact)));
            }
        });

        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + address)));
            }
        });

    }
}
