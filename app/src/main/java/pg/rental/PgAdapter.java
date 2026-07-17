package pg.rental;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

class PgAdapter extends RecyclerView.Adapter<PgAdapter.ViewHolder> {

    private ArrayList<Rent> rent;
    Context context;
    String sPageType;

    public PgAdapter(Context context, ArrayList<Rent> rentList, String sPageType) {
        rent = rentList;
        this.context = context;
        this.sPageType = sPageType;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivHouse;
        private TextView tvTitle, tvFee, tvPeriod, tvLocationCardView;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            ivHouse = itemView.findViewById(R.id.ivHouse);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvFee = itemView.findViewById(R.id.tvFee);
            tvPeriod = itemView.findViewById(R.id.tvPeriod);
            tvLocationCardView = itemView.findViewById(R.id.tvLocationCardView);

//            not working on api that lower than 21
//            ivHouse.setImageResource(R.drawable.signboard_for_rent);
        }
    }

    @NonNull
    @Override
    public PgAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_pg, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PgAdapter.ViewHolder viewHolder, int i) {
        viewHolder.itemView.setTag(rent.get(i));
        if (rent.get(i).getTitle().length() > 24) {
            viewHolder.tvTitle.setText(rent.get(i).getTitle().substring(0, 20) + "...");
        } else {
            viewHolder.tvTitle.setText(rent.get(i).getTitle());
        }
        viewHolder.tvFee.setText(viewHolder.itemView.getContext()
                .getResources()
                .getString(R.string.bdt_sign) + " " + rent.get(i).getFee());
        viewHolder.tvPeriod.setText(" /" + rent.get(i).getPeriod());
        viewHolder.tvLocationCardView.setText(rent.get(i).getLocation());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sPageType.equalsIgnoreCase("MyPost")){
                    Intent homeDetailsIntent = new Intent(context, PgDetails.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("title", rent.get(i).getTitle());
                    bundle.putString("location", rent.get(i).getLocation());
                    bundle.putString("fee", rent.get(i).getFee());
                    bundle.putString("period", rent.get(i).getPeriod());
                    bundle.putString("address", rent.get(i).getAddress());
                    bundle.putString("description", rent.get(i).getDescription());
                    bundle.putString("beds", rent.get(i).getNumOfBeds());
                    bundle.putString("baths", rent.get(i).getNumOfBaths());

                    bundle.putString("postBy", rent.get(i).getUserName());
                    bundle.putString("contact", rent.get(i).getContact());
                    bundle.putString("email", rent.get(i).getEmail());
                    bundle.putString("key", rent.get(i).getKey());
                    homeDetailsIntent.putExtras(bundle);
                    context.startActivity(homeDetailsIntent);
                }
                else {
                    Intent homeDetailsIntent = new Intent(context, HomeDetailsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("title", rent.get(i).getTitle());
                    bundle.putString("location", rent.get(i).getLocation());
                    bundle.putString("fee", rent.get(i).getFee());
                    bundle.putString("period", rent.get(i).getPeriod());
                    bundle.putString("address", rent.get(i).getAddress());
                    bundle.putString("description", rent.get(i).getDescription());
                    bundle.putString("beds", rent.get(i).getNumOfBeds());
                    bundle.putString("baths", rent.get(i).getNumOfBaths());

                    bundle.putString("postBy", rent.get(i).getUserName());
                    bundle.putString("contact", rent.get(i).getContact());
                    bundle.putString("email", rent.get(i).getEmail());
                    bundle.putString("key", rent.get(i).getKey());

                    homeDetailsIntent.putExtras(bundle);
                    context.startActivity(homeDetailsIntent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
//        return rent.size();
        int arr = 0;
        try {
            if (rent.size() == 0) {
                arr = 0;
            } else {
                arr = rent.size();
            }
        } catch (Exception e){

        }
        return arr;
    }

    public void setFilter(ArrayList<Rent> rentList) {
        rent = new ArrayList<>();
        rent.addAll(rentList);
        notifyDataSetChanged();
    }
}