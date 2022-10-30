package com.igor.mamba;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;


public class itemspurchasedadapter extends RecyclerView.Adapter<itemspurchasedadapter.holderproducts> {
    LayoutInflater inflater;
    List<purchaseditemsmodel> itemspurchased;

    Context context;
    public itemspurchasedadapter(Context context, List<purchaseditemsmodel> itemspurchased)
    {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.itemspurchased = itemspurchased;

    }
    @NonNull
    @Override
    public holderproducts onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_layout,parent,false);
        return new holderproducts(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holderproducts holder, int position) {

        purchaseditemsmodel purchaseditemsmodels = itemspurchased.get(position);
        Glide.with(context)
                .load("http://"+URLs.IP+"/admin_area/product_images/"+purchaseditemsmodels.getProductimage())
                .fitCenter()
                .into(holder.productimage);

        holder.price.setText("price : "+String.valueOf(purchaseditemsmodels.getPrice()));
        holder.productname.setText(purchaseditemsmodels.getTitle());
        holder.quantity.setText("quantity : "+String.valueOf(purchaseditemsmodels.getQuantity()));

    }

    @Override
    public int getItemCount() {
        return itemspurchased.size();
    }

    public class holderproducts extends  RecyclerView.ViewHolder {

        ImageView productimage;
        TextView price,productname,quantity;
        public holderproducts(@NonNull View itemView) {
         super(itemView);
            productimage = itemView.findViewById(R.id.coverImage);
            price = itemView.findViewById(R.id.price);
            quantity = itemView.findViewById(R.id.quantity);
            productname = itemView.findViewById(R.id.songTitle);

        }
    }
}
