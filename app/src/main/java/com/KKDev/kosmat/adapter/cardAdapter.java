package com.KKDev.kosmat.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.KKDev.kosmat.DescActivity;
import com.KKDev.kosmat.R;


public class cardAdapter extends RecyclerView.Adapter<cardAdapter.ViewHolder> {

    private Object[][] data;
    private Context context;

    public cardAdapter(Context context, Object[][] data) {
        this.context = context;
        this.data = data;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardlayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Object[] row = data[position];

        // Assuming row[0] is an integer representing the resource ID of the image
        holder.imageView.setImageResource((Integer) row[0]);

        // Assuming row[1] and row[2] are Strings for the title and description
        holder.textViewTitle.setText((String) row[1]);
        holder.textViewDescription.setText((String) row[2]);

    }

    // Tambahkan method untuk mendapatkan deskripsi dari posisi tertentu
    public Object[] getItem(int position) {
        Object[] row = data[position];
        return row;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView textViewTitle;
        TextView textViewDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textViewTitle = itemView.findViewById(R.id.Title);
            textViewDescription = itemView.findViewById(R.id.Description);

            // Menambahkan listener klik pada itemView
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();

            if (position != RecyclerView.NO_POSITION) {
                // Mengambil deskripsi dari item yang diklik
                Object[] item = getItem(position);

                // Membuat intent untuk membuka DetailActivity
                Intent intent = new Intent(context, DescActivity.class);
                intent.putExtra("image", (Integer) item[0]);
                intent.putExtra("title", (String) item[1]);
                intent.putExtra("penghuni", (String) item[2]);
                intent.putExtra("description", (String) item[3]);
                context.startActivity(intent);
            }
        }
    }

    @Override
    public int getItemCount() {
        return data.length;
    }
}
