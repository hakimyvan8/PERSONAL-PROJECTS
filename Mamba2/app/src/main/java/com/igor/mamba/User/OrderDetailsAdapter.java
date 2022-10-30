package com.igor.mamba.User;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.igor.mamba.CartListAdapter;
import com.igor.mamba.ItemsPurchased;
import com.igor.mamba.ModelCart;
import com.igor.mamba.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;

public class OrderDetailsAdapter extends RecyclerView.Adapter<OrderDetailsAdapter.orderHolder> {
    ArrayList<Order> orderList;
    private Context context;

    View view;
    public OrderDetailsAdapter(Context mCtx, ArrayList<Order> orderList){
        this.orderList = orderList;
        this.context = mCtx;
    }
    @NonNull
    @Override
    public OrderDetailsAdapter.orderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.row_ordereditem, null);
        return new OrderDetailsAdapter.orderHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailsAdapter.orderHolder holder, int position) {
        final Order order = orderList.get(position);
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('\'');
        symbols.setDecimalSeparator(',');
       symbols.setCurrency(Currency.getInstance(new Locale("es", "ES")));
        DecimalFormat decimalFormat = new DecimalFormat("R₣ #,###.00", symbols);

        String id = String.valueOf(order.getOrderid());

        holder.orderIdTv.setText(id);

        holder.dateTv.setText(order.getCreateAt());
        holder.amountTv.setText(decimalFormat.format(order.getGrandtotal()));
        holder.nextIv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ItemsPurchased.class);
                intent.putExtra("id",id);
                intent.putExtra("payment",order.getPaymentsecreat());
                context.startActivity(intent);

            }
        });

        if(order.getStatus() == 0) {
            holder.statusTv.setText("Pending verification");
        }
        if(order.getStatus() == 2) {
            holder.statusTv.setText("Order Declined");
            holder.statusTv.setTextColor(Color.RED);
            holder.amountTv.setText(decimalFormat.format(order.getGrandtotal())+"(Refund)");
            holder.amountTv.setPaintFlags(holder.amountTv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.orderIdTv.setPaintFlags(holder.orderIdTv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        else if(order.getStatus() == 1)
        {

            holder.statusTv.setText("Order Approved");
        }


        else if(order.getStatus() == 4)
        {

            holder.statusTv.setText("Order Complete");
        }


        else if(order.getStatus() == 3)
        {

            holder.statusTv.setText("Driver En Route");
        }
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }


    public class orderHolder extends RecyclerView.ViewHolder {
        TextView orderIdTv,dateTv,shopNameTv,amountTv,statusTv;
        ImageView nextIv;
        public orderHolder(@NonNull View itemView) {
            super(itemView);
            //init ui views
            orderIdTv = itemView.findViewById(R.id.orderIdTver);
            dateTv = itemView.findViewById(R.id.dateTv);
            shopNameTv = itemView.findViewById(R.id.shopNameTv);
            amountTv = itemView.findViewById(R.id.amountTv);
            statusTv = itemView.findViewById(R.id.statusTv);
            nextIv   = itemView.findViewById(R.id.nextIv);

        }
    }
}
