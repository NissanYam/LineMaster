package com.example.linemaster.Activities.Fragments.AllMerchantsUser;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.linemaster.Data.Merchant;
import com.example.linemaster.MyRTFB;
import com.example.linemaster.MySignal;
import com.example.linemaster.R;
import java.util.List;

public class AdapterAllMerchantsUser extends RecyclerView.Adapter<AdapterAllMerchantsUser.MerchantsUserViewHolder> {
    private List<Merchant> merchants;
    private OnMerchantClickListener onMerchantClickListener;

    public AdapterAllMerchantsUser() {
    }

    public void setOnMerchantClickListener(OnMerchantClickListener onMerchantClickListener) {
        this.onMerchantClickListener = onMerchantClickListener;
    }

    public AdapterAllMerchantsUser setMerchants(List<Merchant> merchants) {
        this.merchants = merchants;
        return this;
    }

    @NonNull
    @Override
    public MerchantsUserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.business_item, viewGroup, false);
        return new MerchantsUserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MerchantsUserViewHolder holder, int position) {
        try {
            Merchant merchant = getItem(position);
            holder.business_TXT_name.setText(merchant.getMerchantName());
            if(merchant.getLogo() == null
                    ||
                    merchant.getLogo().isEmpty()
                    || merchant.getLogo().equals("")){
                holder.business_IMG_logo.setImageResource(R.drawable.noun_merchant_5111948);
            }else {
                MySignal.getInstance().putImgGlide(MyRTFB.getImg(merchant.getLogo()), holder.business_IMG_logo);
            }
        }catch (Exception e){
            return;
        }

    }


    @Override
    public int getItemCount() {
        return merchants == null ? 0 : merchants.size();
    }

    private Merchant getItem(int position) {
        return merchants.get(position);
    }

    public interface OnMerchantClickListener {
        void openMerchant(View v, Merchant item, int adapterPosition);
    }

    public class MerchantsUserViewHolder extends RecyclerView.ViewHolder {
        private ImageView business_IMG_logo;
        private TextView business_TXT_name;

        private LinearLayoutCompat linearLayoutCompat_info;

        public MerchantsUserViewHolder(@NonNull View itemView) {
            super(itemView);
            findViews(itemView);
            initViews();
        }

        private void initViews() {
            linearLayoutCompat_info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onMerchantClickListener.openMerchant(v,getItem(getAdapterPosition()), getAdapterPosition());
                }
            });
        }

        private void findViews(View itemView) {
            linearLayoutCompat_info = itemView.findViewById(R.id.linearLayoutCompat_info);
            business_IMG_logo = itemView.findViewById(R.id.business_IMG_logo);
            business_TXT_name = itemView.findViewById(R.id.business_TXT_name);
        }
    }
}
