package com.igor.mamba;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.igor.mamba.User.User;

import org.json.JSONException;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import p32929.androideasysql_library.Column;
import p32929.androideasysql_library.EasyDB;

import static com.android.volley.Request.*;

public class productAdapter extends RecyclerView.Adapter<productAdapter.ProductView> implements Filterable {

    ArrayList<Product> productList,filterList;
    ArrayList<Product> list;
    User user;
    private Context context;
    private FilterProductUser filter;

    public productAdapter(Context mCtx, ArrayList<Product> productList){
        this.context = mCtx;
        this.productList = productList;
        this.list = new ArrayList<>(productList);
        this.filterList = productList;
    }

    @NonNull
    @Override
    public productAdapter.ProductView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.product,null);
        return new ProductView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull productAdapter.ProductView holder, int position) {

        final Product product = productList.get(position);
        int prodStatus = Integer.parseInt(product.getUnitsStored());


        //loading the image
        Glide.with(context)
                .load("http://"+URLs.IP+"/admin_area/product_images/"+product.getProduct_img1())
                .fitCenter()
                .into(holder.imageView);
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('\'');
        symbols.setDecimalSeparator(',');
         symbols.setCurrency(Currency.getInstance(new Locale("es", "ES")));

        DecimalFormat decimalFormat = new DecimalFormat("R₣ #,###.00", symbols);
        holder.textViewTitle.setText(product.getProduct_title());
//        holder.instock.setText(prodStatus);
      holder.textViewPrice.setText(decimalFormat.format(Integer.valueOf(String.valueOf(product.getProduct_price()))));
        holder.instock.setTextColor(context.getResources().getColor(R.color.green));
        if (prodStatus > 0) {


            holder.addView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(holder.itemView.getContext(), ProductDetails.class);
                    intent.putExtra("object", productList.get(position));
                    holder.instock.setTextColor(context.getResources().getColor(R.color.green));
                    holder.itemView.getContext().startActivity(intent);
                }
            });

        }

        else if(prodStatus == 0)  {

            holder.instock.setTextColor(context.getResources().getColor(R.color.red));
            holder.instock.setText(" ( out of stock )");
            holder.addView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(holder.itemView.getContext(), ProductDetails.class);
                    intent.putExtra("object", productList.get(position));
                    holder.instock.setTextColor(context.getResources().getColor(R.color.red));
                    holder.itemView.getContext().startActivity(intent);
                }
            });
                }
        }


    @Override
    public int getItemCount() {
        return productList.size();
    }

    @Override
    public Filter getFilter() {
        if (filter == null){
            filter = new FilterProductUser(this,filterList);
        }
        return filter;
    }

    public class ProductView extends RecyclerView.ViewHolder {
        TextView textViewTitle,textViewPrice,addView,instock;
        ImageView imageView;

        public ProductView(@NonNull View itemView) {
            super(itemView);

            addView = itemView.findViewById(R.id.addView);
            textViewTitle = itemView.findViewById(R.id.titleView);
            instock = itemView.findViewById(R.id.instock);
            textViewPrice = itemView.findViewById(R.id.priceView);
            imageView = itemView.findViewById(R.id.imageprod);
        }
    }
}
