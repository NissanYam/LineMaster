package com.example.linemaster.Activities.Fragments.Calender;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.linemaster.Data.Appointment;
import com.example.linemaster.Data.Merchant;
import com.example.linemaster.Data.User;
import com.example.linemaster.MyRTFB;
import com.example.linemaster.MySignal;
import com.example.linemaster.R;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AdapterCalender extends RecyclerView.Adapter<AdapterCalender.AppointmentUserViewHolder> {
    private List<Appointment> appointments;
    private OnAppointmentClickListener onAppointmebtClickListener;

    public AdapterCalender() {
    }

    public void setOnAppointmebtClickListener(OnAppointmentClickListener onAppointmebtClickListener) {
        this.onAppointmebtClickListener = onAppointmebtClickListener;
    }

    public AdapterCalender setAppointment(List<Appointment> appointments) {
        List<Appointment> futureAppointment = new ArrayList<>();
        for (Appointment appointment: appointments) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                LocalDate date = LocalDate.of(appointment.getYear(),appointment.getMonth(),appointment.getDay());
                if(date.isAfter(LocalDate.now())
                    || date.equals(LocalDate.now())){
                    futureAppointment.add(appointment);
                }
            }
        }
        this.appointments = futureAppointment;
        return this;
    }

    @NonNull
    @Override
    public AppointmentUserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.appointment_item, viewGroup, false);
        return new AppointmentUserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentUserViewHolder holder, int position) {
        Appointment appointment = getItem(position);
        holder.appointment_merchant_name.setText(appointment.getMerchantName()+" "+ appointment.getService().getServiceName());
        MyRTFB.getSpecificMerchant(appointment.getMerchantName(), appointment.getMerchantOwner(), new MyRTFB.CB_Merchant() {
            @Override
            public void getMerchantData(Merchant merchant) {
                MyRTFB.getUser(appointment.getCustomerEmail(), new MyRTFB.CB_User() {
                    @Override
                    public void getUserData(User user) {
                        holder.appointment_merchant_phone.setText("Merchant Phone -> "+merchant.getMerchantPhone()+"\n"+
                                                                    "Customer Phone -> "+user.getPhoneNumber()+"\n"+
                                                                    "Customer Email -> "+user.getEmail());
                    }
                });

                MySignal.getInstance().getAddress(merchant.getAddress().getLat(),
                        merchant.getAddress().getLng(), new MySignal.Listener_String() {
                            @Override
                            public void getString(String str) {
                                holder.appointment_merchant_location.setText(str);
                            }
                        });
            }
            @Override
            public void getAllMerchants(ArrayList<Merchant> merchantArrayList) {

            }
        });

        holder.appointment_date_and_time.setText(appointment.getDay()+"/"+appointment.getMonth()+"/"+appointment.getYear()
                +" - "+
                appointment.getAppointmentTimeHour()+" : "+appointment.getAppointmentTimeMinute());

    }
    @Override
    public int getItemCount() {
        return appointments == null ? 0 : appointments.size();
    }

    private Appointment getItem(int position) {
        return appointments.get(position);
    }

    public interface OnAppointmentClickListener {
        void removeAppointment(View v, Appointment item, int adapterPosition);
    }

    public class AppointmentUserViewHolder extends RecyclerView.ViewHolder {
        private TextView appointment_merchant_name;
        private TextView appointment_remove;
        private TextView appointment_merchant_phone;
        private TextView appointment_date_and_time;
        private TextView appointment_merchant_location;

        public AppointmentUserViewHolder(@NonNull View itemView) {
            super(itemView);
            findViews(itemView);
            initViews();
        }

        private void initViews() {
            appointment_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onAppointmebtClickListener.removeAppointment(v,getItem(getAdapterPosition()), getAdapterPosition());
                }
            });
        }

        private void findViews(View itemView) {
            appointment_merchant_name = itemView.findViewById(R.id.appointment_merchant_name);
            appointment_remove = itemView.findViewById(R.id.appointment_remove);
            appointment_merchant_phone = itemView.findViewById(R.id.appointment_merchant_phone);
            appointment_date_and_time = itemView.findViewById(R.id.appointment_date_and_time);
            appointment_merchant_location = itemView.findViewById(R.id.appointment_merchant_location);
        }
    }
}
