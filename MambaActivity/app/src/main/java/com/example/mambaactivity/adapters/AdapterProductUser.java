package com.example.mambaactivity.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mambaactivity.FilterProductUser;
import com.example.mambaactivity.R;
import com.example.mambaactivity.activities.ShopDetailsActivity;
import com.example.mambaactivity.models.ModelProduct;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import p32929.androideasysql_library.Column;
import p32929.androideasysql_library.EasyDB;

public class AdapterProductUser extends RecyclerView.Adapter<AdapterProductUser.HolderProductUser> implements Filterable {

    private Context context;
    public ArrayList<ModelProduct> productsList, filterList;

    private FilterProductUser filter;

    public AdapterProductUser(Context context, ArrayList<ModelProduct> productsList) {
        this.context = context;
        this.productsList = productsList;
        this.filterList = productsList;
    }

    @NonNull
    @Override
    public HolderProductUser onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout
        View view = LayoutInflater.from(context).inflate(R.layout.row_product_user,parent,false);
        return new HolderProductUser(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderProductUser holder, int position) {
        //get data
        final ModelProduct modelProduct = productsList.get(position);
        String discountAvailable = modelProduct.getDiscountAvailable();
        String discountNote = modelProduct.getDiscountNote();
        String discountPrice = modelProduct.getDiscountPrice();
        String productCategory = modelProduct.getProductCategory();
        String originalPrice = modelProduct.getOriginalPrice();
        String productDescription = modelProduct.getProductDescription();
        String productTitle = modelProduct.getProductTitle();
        String productQuantity = modelProduct.getProductQuantity();
        String productId = modelProduct.getProductId();
        String timestamp = modelProduct.getTimestamp();
        String productIcon = modelProduct.getProductIcon();


        //set data
        holder.titleTv.setText(productTitle);
        holder.discountNoteTv.setText(discountNote+"% OFF");
        holder.descriptionTv.setText(productDescription);
        holder.originalPriceTv.setText("Rwf"+originalPrice);
        holder.discountPriceTv.setText("Rwf"+discountPrice);

        if (discountAvailable.equals("true"))
        {
            //product is on discount
            holder.discountPriceTv.setVisibility(View.VISIBLE);
            holder.discountNoteTv.setVisibility(View.VISIBLE);
            holder.originalPriceTv.setPaintFlags(holder.originalPriceTv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);//add strike through on original price
        }
        else
        {
            //product is not on discount
            holder.discountPriceTv.setVisibility(View.GONE);
            holder.discountNoteTv.setVisibility(View.GONE);
            holder.originalPriceTv.setPaintFlags(0);
        }
        try
        {
            Picasso.get().load(productIcon).placeholder(R.drawable.ic_cart2).into(holder.productIconIv);
        }
        catch (Exception e)
        {
            holder.productIconIv.setImageResource(R.drawable.ic_cart2);
        }
        holder.addToCartTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //add product to cart
                showQuantityDialog(modelProduct);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show product details

            }
        });
    }
    private double cost = 0;
    private double finalCost = 0;
    private  int quantity = 0;

    private void showQuantityDialog(ModelProduct modelProduct)
    {
        //inflate layout for dialog
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_quantity,null);
        //init layout views

        ImageView productIv = view.findViewById(R.id.productIv);
        final TextView titleTv = view.findViewById(R.id.titleTv);
        TextView pQuantityTv = view.findViewById(R.id.pQuantityTv);
        TextView descriptionTv = view.findViewById(R.id.descriptionTv);
        TextView discountNoteTv = view.findViewById(R.id.discountNoteTv);
        final TextView originalPriceTv = view.findViewById(R.id.originalPriceTv);
        TextView priceDiscountTv = view.findViewById(R.id.priceDiscountTv);
        final TextView finalPriceTv = view.findViewById(R.id.finalPriceTv);
        ImageButton decrementBtn = view.findViewById(R.id.decrementBtn);
        final TextView quantityTv = view.findViewById(R.id.quantityTv);
        ImageButton incrementBtn = view.findViewById(R.id.incrementBtn);
        Button continueBtn = view.findViewById(R.id.continueBtn);

        //get data from model
        final String productId = modelProduct.getProductId();
        String title = modelProduct.getProductTitle();
        String productQuantity = modelProduct.getProductQuantity();
        String description = modelProduct.getProductDescription();
        String discountNote = modelProduct.getDiscountNote();
        String image = modelProduct.getProductIcon();

        final String price;
        if (modelProduct.getDiscountAvailable().equals("true")){
            //product have discount
            price = modelProduct.getDiscountPrice();
            discountNoteTv.setVisibility(View.VISIBLE);
            originalPriceTv.setPaintFlags(originalPriceTv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);//add strike through on original price
        }
        else
        {
            //product don't have discount
            discountNoteTv.setVisibility(View.GONE);
            priceDiscountTv.setVisibility(View.GONE);
            price = modelProduct.getOriginalPrice();
        }

        cost = Double.parseDouble(price.replaceAll("RwF",""));
        finalCost = Double.parseDouble(price.replaceAll("RWF",""));
        quantity = 1;

        //dialog

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);

        //set data
        try{
            Picasso.get().load(image).placeholder(R.drawable.ic_cart).into(productIv);
        }
        catch (Exception e)
        {
            productIv.setImageResource(R.drawable.ic_cart);
        }
        titleTv.setText(""+title);
        pQuantityTv.setText(""+productQuantity);
        descriptionTv.setText(""+description);
        discountNoteTv.setText(""+discountNote);
        quantityTv.setText(""+quantity);
        originalPriceTv.setText("Rwf"+modelProduct.getOriginalPrice());
        priceDiscountTv.setText("Rwf"+modelProduct.getDiscountPrice());
        finalPriceTv.setText("Rwf"+finalCost);

        final AlertDialog dialog = builder.create();
        dialog.show();

        //increase quantity of the product
        incrementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                finalCost = finalCost + cost;
                quantity++;

                finalPriceTv.setText("Rwf"+finalCost);
                quantityTv.setText(""+quantity);
            }
        });
        //decrement quantity of product, only if quantity is >1
        decrementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (quantity>1) {
                    finalCost = finalCost - cost;
                    quantity--;

                    finalPriceTv.setText("Rwf" + finalCost);
                    quantityTv.setText("" + quantity);
                }
            }
        });
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String title = titleTv.getText().toString().trim();
                String priceEach = price;
                String totalprice = finalPriceTv.getText().toString().trim().replace("Rwf","");
                String quantity = quantityTv.getText().toString().trim();

                //add to db(SQLite)
                addToCart(productId,title,priceEach,totalprice,quantity);

                dialog.dismiss();


            }
        });
    }

    private int itemId =1;
    private void addToCart(String productId, String title, String priceEach, String price, String quantity)
    {
        itemId++;
        EasyDB easyDB = EasyDB.init(context,"ITEMS_DB")
                .setTableName("ITEMS TABLE")
                .addColumn(new Column("item_id", new String[]{"text","unique"}))
                .addColumn(new Column("item_PID", new String[]{"text","not null"}))
                .addColumn(new Column("item_Name", new String[]{"text","not null"}))
                .addColumn(new Column("item_Price_Each", new String[]{"text","not null"}))
                .addColumn(new Column("item_Price", new String[]{"text","not null"}))
                .addColumn(new Column("item_Quantity", new String[]{"text","not null"}))
                .doneTableColumn();
        Boolean b=easyDB.addData("item_id",itemId)
                .addData("item_PID",productId)
                .addData("item_Name",title)
                .addData("item_Price_Each",priceEach)
                .addData("item_Price",price)
                .addData("item_Quantity",quantity)
                .doneDataAdding();

        Toast.makeText(context, "Added to cart...", Toast.LENGTH_SHORT).show();

        //update cart count
        ((ShopDetailsActivity)context).cartCount();
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    @Override
    public Filter getFilter() {
        if (filter == null){
            filter = new FilterProductUser(this,filterList);
        }
        return filter;
    }

    class HolderProductUser extends RecyclerView.ViewHolder{

        //uid views
        private ImageView productIconIv;
        private TextView  discountNoteTv,titleTv,descriptionTv,addToCartTv,discountPriceTv,originalPriceTv;

        public HolderProductUser(@NonNull View itemView) {
            super(itemView);

            //init ui views
            productIconIv = itemView.findViewById(R.id.productIconIv);
            discountNoteTv = itemView.findViewById(R.id.discountNoteTv);
            titleTv = itemView.findViewById(R.id.titleTv);
            descriptionTv = itemView.findViewById(R.id.descriptionTv);
            addToCartTv = itemView.findViewById(R.id.addToCartTv);
            discountPriceTv = itemView.findViewById(R.id.discountPriceTv);
            originalPriceTv = itemView.findViewById(R.id.originalPriceTv);
        }
    }
}
