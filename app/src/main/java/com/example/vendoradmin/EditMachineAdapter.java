package com.example.vendoradmin;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import org.jetbrains.annotations.NotNull;

public class EditMachineAdapter  extends RecyclerView.Adapter<EditMachineAdapter.GithubViewHolder>{

    private Context context;
    private Schema[] schemas;

    public EditMachineAdapter(Context context, Schema[] schemas){
        this.context = context;
        this.schemas = schemas;
    }

    @NonNull
    @NotNull
    @Override
    public EditMachineAdapter.GithubViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.edit_item_layout, parent, false);
        return new GithubViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull EditMachineAdapter.GithubViewHolder holder, int position) {
        Schema schema = schemas[position];
        String machineId = schema.getMachine();
        String price = String.valueOf(schema.getPrice());
        String quantity = String.valueOf(schema.getQuantity());
        String id = schema.getId();




        Glide.with(holder.imageView.getContext()).load(schema.getImage()).override(Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL).into(holder.imageView);

        holder.priceEditText.setText((price));
        holder.quantityEditText.setText((quantity));


        holder.editProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), UpdateActivity.class);
                intent.putExtra("price", price);
                intent.putExtra("quantity", quantity);
                intent.putExtra("machineId", machineId);
                intent.putExtra("_id", id);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return schemas.length;
    }

    public class GithubViewHolder extends RecyclerView.ViewHolder{
        TextView priceEditText, quantityEditText;
        ImageView imageView;
        Button editProduct;
        public GithubViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            priceEditText = itemView.findViewById(R.id.priceEditText);
            quantityEditText = itemView.findViewById(R.id.quantityEditText);
            editProduct = itemView.findViewById(R.id.editMachine);
            imageView = itemView.findViewById(R.id.showProductImager);
        }
    }
}
