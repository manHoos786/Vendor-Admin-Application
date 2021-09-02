package com.example.vendoradmin;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class MachineAdapter extends RecyclerView.Adapter<MachineAdapter.GithubViewHolder> {

    private Context context;
    private Schema[] schemas;

    public MachineAdapter(Context context, Schema[] schemas){
        this.context = context;
        this.schemas = schemas;
    }

    @NonNull
    @NotNull
    @Override
    public MachineAdapter.GithubViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.machine_list_layout, parent, false);
        return new GithubViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MachineAdapter.GithubViewHolder holder, int position) {
        Schema schema = schemas[position];
        String machine = schema.getMachine();
        if(schema.getStatus()){
            holder.showMachine.setText(machine);
            holder.showMachine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context.getApplicationContext(), EditItemsInMachineActivity.class);
                    intent.putExtra("machineId", machine);
                    intent.putExtra("key", schema.getKey_razorpay());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return schemas.length;
    }

    public class GithubViewHolder extends RecyclerView.ViewHolder{
        TextView showMachine;
        public GithubViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            showMachine = itemView.findViewById(R.id.showMachine);
        }
    }

}
