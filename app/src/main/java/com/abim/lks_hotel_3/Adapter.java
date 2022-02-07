package com.abim.lks_hotel_3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    List<Cart> carts;
    Context ctx;

    public Adapter(List<Cart> carts, Context ctx) {
        this.carts = carts;
        this.ctx = ctx;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_data, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder( Adapter.ViewHolder holder, int position) {
        Cart cart = carts.get(position);
        holder.tv_empId.setText(String.valueOf(cart.getEmp_id()));
        holder.tv_reservationId.setText(String.valueOf(cart.getReservation_id()));
        holder.tv_qty.setText(String.valueOf(cart.getQty()));
        holder.tv_fdid.setText(String.valueOf(cart.getFdid()));
        holder.tv_total.setText(String.valueOf(cart.getTotal()));
        holder.tv_fdName.setText(String.valueOf(cart.getFdName()));
    }

    @Override
    public int getItemCount() {
        return carts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
        Button btn_del;
        TextView tv_empId, tv_fdName, tv_total, tv_fdid, tv_qty, tv_reservationId;
        public ViewHolder( View v) {
            super(v);

            btn_del = v.findViewById(R.id.btn_del);
            tv_empId = v.findViewById(R.id.tv_empID);
            tv_fdName = v.findViewById(R.id.tv_fdName);
            tv_total = v.findViewById(R.id.tv_total);
            tv_fdid = v.findViewById(R.id.tv_fdid);
            tv_qty = v.findViewById(R.id.tv_qty);
            tv_reservationId = v.findViewById(R.id.tv_reserid);

            btn_del.setOnClickListener(this::onClick);
        }

        @Override
        public void onClick(View v) {
            carts.remove(getAdapterPosition());
            notifyItemRemoved(getAdapterPosition());
            notifyItemRangeRemoved(getAdapterPosition(), carts.size());
        }
    }
}
