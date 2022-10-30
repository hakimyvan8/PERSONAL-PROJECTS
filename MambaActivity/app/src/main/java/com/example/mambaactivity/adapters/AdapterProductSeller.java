package com.example.mambaactivity.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mambaactivity.FilterProduct;
import com.example.mambaactivity.FilterProductUser;
import com.example.mambaactivity.R;
import com.example.mambaactivity.activities.EditProductActivity;
import com.example.mambaactivity.models.ModelProduct;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterProductSeller extends RecyclerView.Adapter<AdapterProductSeller.HolderProductSeller> implements Filterable {

    private Context context;
    public ArrayList<ModelProduct> productList,filterList ;
    private FilterProduct filter;

    public AdapterProductSeller(Context context, ArrayList<ModelProduct> productList) {
        this.context = context;
        this.productList = productList;
        this.filterList = productList;
    }

    @NonNull
    @Override
    public HolderProductSeller onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout
        View view = LayoutInflater.from(context).inflate(R.layout.row_product_seller,parent,false);
        return new HolderProductSeller(view);
    }

    @Override
    public void onBindViewHolder(  @NonNull HolderProductSeller holder, int position) {
        //get data
        final ModelProduct modelProduct = productList.get(position);
        String id = modelProduct.getProductId();
        String uid = modelProduct.getUid();
        String discountNote = modelProduct.getDiscountNote();
        String discountAvailable = modelProduct.getDiscountAvailable();
        String discountPrice = modelProduct.getDiscountPrice();
        String productCategory = modelProduct.getProductCategory();
        String icon = modelProduct.getProductIcon();
        String quantity = modelProduct.getProductQuantity();
        String title = modelProduct.getProductTitle();
        String timestamp = modelProduct.getTimestamp();
        String originalPrice = modelProduct.getOriginalPrice();

        //set data
        holder.titleTv.setText(title);
        holder.quantityTv.setText(quantity);
        holder.discountNoteTv.setText(discountNote+"% OFF");
        holder.discountPriceTv.setText("RwF"+discountPrice);
        holder.originalPriceTv.setText("RwF"+originalPrice);
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
            Picasso.get().load(icon).placeholder(R.drawable.ic_cart2).into(holder.productIconIv);
        }
        catch (Exception e)
        {
            holder.productIconIv.setImageResource(R.drawable.ic_cart2);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //handle item clicks,show item details in bottom
                detailsBottomSheet(modelProduct);//here modelProduct contains details of clicked product
            }
        });
    }

    private void detailsBottomSheet(ModelProduct modelProduct)
    {
        //bottom sheet
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        //inflate view for bottomSheet
        View view = LayoutInflater.from(context).inflate(R.layout.bs_product_details_seller,null);
        //set view to bottomSheet
        bottomSheetDialog.setContentView(view);



        //init views of bottomSheet
        ImageButton backBtn = view.findViewById(R.id.backBtn);
        ImageButton deleteBtn = view.findViewById(R.id.deleteBtn);
        ImageButton editBtn = view.findViewById(R.id.editBtn);
        ImageView  productIconIv = view.findViewById(R.id.productIconIv);
        TextView  discountNoteTv = view.findViewById(R.id.discountNoteTv);
        TextView  titleTv = view.findViewById(R.id.titleTv);
        TextView  descriptionTv = view.findViewById(R.id.descriptionTv);
        TextView  categoryTv = view.findViewById(R.id.categoryTv);
        TextView quantityTv = view.findViewById(R.id.quantityTv);
        TextView discountPriceTv = view.findViewById(R.id.discountPriceTv);
        TextView originalPriceTv = view.findViewById(R.id.originalPriceTv);

        //get data
        final String id = modelProduct.getProductId();
        String uid = modelProduct.getUid();
        String productDescription = modelProduct.getProductDescription();
        String discountNote = modelProduct.getDiscountNote();
        String discountAvailable = modelProduct.getDiscountAvailable();
        String discountPrice = modelProduct.getDiscountPrice();
        String productCategory = modelProduct.getProductCategory();
        String icon = modelProduct.getProductIcon();
        String quantity = modelProduct.getProductQuantity();
        final String title = modelProduct.getProductTitle();
        String timestamp = modelProduct.getTimestamp();
        String originalPrice = modelProduct.getOriginalPrice();

        //set data
        titleTv.setText(title);
        descriptionTv.setText(productDescription);
        categoryTv.setText(productCategory);
        quantityTv.setText(quantity);
        discountNoteTv.setText(quantity);
        discountPriceTv.setText(""+ discountPrice);
        originalPriceTv.setText(""+ originalPrice);
        if (discountAvailable.equals("true"))
        {
            //product is on discount
           discountPriceTv.setVisibility(View.VISIBLE);
           discountNoteTv.setVisibility(View.VISIBLE);
           originalPriceTv.setPaintFlags(originalPriceTv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);//add strike through on original price
        }
        else
        {
            //product is not on discount
           discountPriceTv.setVisibility(View.GONE);
           discountNoteTv.setVisibility(View.GONE);
        }
        try
        {
            Picasso.get().load(icon).placeholder(R.drawable.ic_cart2).into(productIconIv);
        }
        catch (Exception e)
        {
            productIconIv.setImageResource(R.drawable.ic_cart2);
        }

        //show dialog
        bottomSheetDialog.show();

        //edit click
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                bottomSheetDialog.dismiss();
                //open edit product activity,pass id of product
                Intent intent = new Intent(context, EditProductActivity.class);
                intent.putExtra("productId",id);
                context.startActivity(intent);
            }
        });
        //delete click
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
               //show delete confirm dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete");
                builder.setMessage("Are you sure you want to delete product "+title+"?");
                builder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                //delete
                                deleteProduct(id);
                            }
                        });
                        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                //cancel, dismiss dialog
                                dialog.dismiss();
                            }
                        });
                      builder.show();
            }
        });
        //back click
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //dismiss bottom sheet
                bottomSheetDialog.dismiss();
            }
        });
    }

    private void deleteProduct(String id)
    {
        //delete product using its id
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(firebaseAuth.getUid()).child("Products").child(id).removeValue()
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid)
                {
                    //product deleted
                    Toast.makeText(context, "product deleted....", Toast.LENGTH_SHORT).show();
                }
            })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                //failed deleting product
                Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    @Override
    public Filter getFilter()
    {
        if (filter==null)
        {
            filter = new FilterProduct(this,filterList);
        }
        return filter;
    }

    class HolderProductSeller extends RecyclerView.ViewHolder{
        /*holds views of recyclerview*/
        private ImageView productIconIv;
        private TextView discountNoteTv,titleTv,quantityTv,discountPriceTv,originalPriceTv;

        public HolderProductSeller(@NonNull View itemView) {
            super(itemView);

            productIconIv = itemView.findViewById(R.id.productIconIv);
            discountNoteTv = itemView.findViewById(R.id.discountNoteTv);
            titleTv = itemView.findViewById(R.id.titleTv);
            quantityTv = itemView.findViewById(R.id.quantityTv);
            discountPriceTv = itemView.findViewById(R.id.discountPriceTv);
            originalPriceTv = itemView.findViewById(R.id.originalPriceTv);

        }
    }
}
