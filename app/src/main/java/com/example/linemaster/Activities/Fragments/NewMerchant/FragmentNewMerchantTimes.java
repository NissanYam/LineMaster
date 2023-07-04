package com.example.linemaster.Activities.Fragments.NewMerchant;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.linemaster.Activities.Fragments.NewMerchant.CallbacksMerchant.CallBackFragmentNewMerchantTimes;
import com.example.linemaster.Data.BusinessDay;
import com.example.linemaster.Data.Merchant;
import com.example.linemaster.Data.TimeRange;
import com.example.linemaster.MySignal;
import com.example.linemaster.R;
import com.google.android.material.button.MaterialButton;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.HashMap;


public class FragmentNewMerchantTimes extends Fragment implements CurrentFragmentNewMerchant {

    private CallBackFragmentNewMerchantTimes callBackFragmentNewMerchantTimes;

    private MaterialButton new_merchant_time_startTime_Sunday;
    private MaterialButton new_merchant_time_endTime_Sunday;
    private SwitchCompat new_merchant_time_work_Sunday;

    private MaterialButton new_merchant_time_startTime_Monday;
    private MaterialButton new_merchant_time_endTime_Monday;
    private SwitchCompat new_merchant_time_work_Monday;

    private MaterialButton new_merchant_time_startTime_Tuesday;
    private MaterialButton new_merchant_time_endTime_Tuesday;
    private SwitchCompat new_merchant_time_work_Tuesday;

    private MaterialButton new_merchant_time_startTime_Wednesday;
    private MaterialButton new_merchant_time_endTime_Wednesday;
    private SwitchCompat new_merchant_time_work_Wednesday;

    private MaterialButton new_merchant_time_startTime_Thursday;
    private MaterialButton new_merchant_time_endTime_Thursday;
    private SwitchCompat new_merchant_time_work_Thursday;

    private MaterialButton new_merchant_time_startTime_Friday;
    private MaterialButton new_merchant_time_endTime_Friday;
    private SwitchCompat new_merchant_time_work_Friday;

    private MaterialButton new_merchant_time_startTime_Saturday;
    private MaterialButton new_merchant_time_endTime_Saturday;
    private SwitchCompat new_merchant_time_work_Saturday;
    private HashMap<DayOfWeek, BusinessDay> dayOfWeekBusinessDayHashMap;
    private final String START = "start";
    private final String END = "end";
    private Context context;
    private Merchant merchant;


    public FragmentNewMerchantTimes setMerchant(Merchant merchant) {
        this.merchant = merchant;
        return this;
    }

    public FragmentNewMerchantTimes() {
    }

    public FragmentNewMerchantTimes setCallBackFragmentNewMerchantTimes(CallBackFragmentNewMerchantTimes callBackFragmentNewMerchantTimes) {
        this.callBackFragmentNewMerchantTimes = callBackFragmentNewMerchantTimes;
        return this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_merchant_times, container, false);
        findViews(view);
        context = view.getContext();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startHashMap();
        }
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startHashMap() {
        dayOfWeekBusinessDayHashMap = new HashMap<DayOfWeek, BusinessDay>();
        dayOfWeekBusinessDayHashMap.put(DayOfWeek.SUNDAY, new BusinessDay().setDayOfWeek(DayOfWeek.SUNDAY).setTimeRanges(new TimeRange()).setActive(false));
        dayOfWeekBusinessDayHashMap.put(DayOfWeek.MONDAY, new BusinessDay().setDayOfWeek(DayOfWeek.MONDAY).setTimeRanges(new TimeRange()).setActive(false));
        dayOfWeekBusinessDayHashMap.put(DayOfWeek.TUESDAY, new BusinessDay().setDayOfWeek(DayOfWeek.TUESDAY).setTimeRanges(new TimeRange()).setActive(false));
        dayOfWeekBusinessDayHashMap.put(DayOfWeek.WEDNESDAY, new BusinessDay().setDayOfWeek(DayOfWeek.WEDNESDAY).setTimeRanges(new TimeRange()).setActive(false));
        dayOfWeekBusinessDayHashMap.put(DayOfWeek.THURSDAY, new BusinessDay().setDayOfWeek(DayOfWeek.THURSDAY).setTimeRanges(new TimeRange()).setActive(false));
        dayOfWeekBusinessDayHashMap.put(DayOfWeek.FRIDAY, new BusinessDay().setDayOfWeek(DayOfWeek.FRIDAY).setTimeRanges(new TimeRange()).setActive(false));
        dayOfWeekBusinessDayHashMap.put(DayOfWeek.SATURDAY, new BusinessDay().setDayOfWeek(DayOfWeek.SATURDAY).setTimeRanges(new TimeRange()).setActive(false));

    }

    private void findViews(View view) {
        new_merchant_time_startTime_Sunday = view.findViewById(R.id.new_merchant_time_startTime_Sunday);
        new_merchant_time_endTime_Sunday = view.findViewById(R.id.new_merchant_time_endTime_Sunday);
        new_merchant_time_work_Sunday = view.findViewById(R.id.new_merchant_time_work_Sunday);

        new_merchant_time_startTime_Monday = view.findViewById(R.id.new_merchant_time_startTime_Monday);
        new_merchant_time_endTime_Monday = view.findViewById(R.id.new_merchant_time_endTime_Monday);
        new_merchant_time_work_Monday = view.findViewById(R.id.new_merchant_time_work_Monday);

        new_merchant_time_startTime_Tuesday = view.findViewById(R.id.new_merchant_time_startTime_Tuesday);
        new_merchant_time_endTime_Tuesday = view.findViewById(R.id.new_merchant_time_endTime_Tuesday);
        new_merchant_time_work_Tuesday = view.findViewById(R.id.new_merchant_time_work_Tuesday);

        new_merchant_time_startTime_Wednesday = view.findViewById(R.id.new_merchant_time_startTime_Wednesday);
        new_merchant_time_endTime_Wednesday = view.findViewById(R.id.new_merchant_time_endTime_Wednesday);
        new_merchant_time_work_Wednesday = view.findViewById(R.id.new_merchant_time_work_Wednesday);

        new_merchant_time_startTime_Thursday = view.findViewById(R.id.new_merchant_time_startTime_Thursday);
        new_merchant_time_endTime_Thursday = view.findViewById(R.id.new_merchant_time_endTime_Thursday);
        new_merchant_time_work_Thursday = view.findViewById(R.id.new_merchant_time_work_Thursday);

        new_merchant_time_startTime_Friday = view.findViewById(R.id.new_merchant_time_startTime_Friday);
        new_merchant_time_endTime_Friday = view.findViewById(R.id.new_merchant_time_endTime_Friday);
        new_merchant_time_work_Friday = view.findViewById(R.id.new_merchant_time_work_Friday);

        new_merchant_time_startTime_Saturday = view.findViewById(R.id.new_merchant_time_startTime_Saturday);
        new_merchant_time_endTime_Saturday = view.findViewById(R.id.new_merchant_time_endTime_Saturday);
        new_merchant_time_work_Saturday = view.findViewById(R.id.new_merchant_time_work_Saturday);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            initViews();
        }
        if(merchant != null)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                setTimes(merchant);
            }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initViews() {
        new_merchant_time_startTime_Sunday.setOnClickListener(v -> getTime(new_merchant_time_startTime_Sunday, DayOfWeek.SUNDAY, START));
        new_merchant_time_endTime_Sunday.setOnClickListener(v -> getTime(new_merchant_time_endTime_Sunday, DayOfWeek.SUNDAY, END));

        new_merchant_time_startTime_Monday.setOnClickListener(v -> getTime(new_merchant_time_startTime_Monday, DayOfWeek.MONDAY, START));
        new_merchant_time_endTime_Monday.setOnClickListener(v -> getTime(new_merchant_time_endTime_Monday, DayOfWeek.MONDAY, END));

        new_merchant_time_startTime_Tuesday.setOnClickListener(v -> getTime(new_merchant_time_startTime_Tuesday, DayOfWeek.TUESDAY, START));
        new_merchant_time_endTime_Tuesday.setOnClickListener(v -> getTime(new_merchant_time_endTime_Tuesday, DayOfWeek.TUESDAY, END));

        new_merchant_time_startTime_Wednesday.setOnClickListener(v -> getTime(new_merchant_time_startTime_Wednesday, DayOfWeek.WEDNESDAY, START));
        new_merchant_time_endTime_Wednesday.setOnClickListener(v -> getTime(new_merchant_time_endTime_Wednesday, DayOfWeek.WEDNESDAY, END));

        new_merchant_time_startTime_Thursday.setOnClickListener(v -> getTime(new_merchant_time_startTime_Thursday, DayOfWeek.THURSDAY, START));
        new_merchant_time_endTime_Thursday.setOnClickListener(v -> getTime(new_merchant_time_endTime_Thursday, DayOfWeek.THURSDAY, END));

        new_merchant_time_startTime_Friday.setOnClickListener(v -> getTime(new_merchant_time_startTime_Friday, DayOfWeek.FRIDAY, START));
        new_merchant_time_endTime_Friday.setOnClickListener(v -> getTime(new_merchant_time_endTime_Friday, DayOfWeek.FRIDAY, END));

        new_merchant_time_startTime_Saturday.setOnClickListener(v -> getTime(new_merchant_time_startTime_Saturday, DayOfWeek.SATURDAY, START));
        new_merchant_time_endTime_Saturday.setOnClickListener(v -> getTime(new_merchant_time_endTime_Saturday, DayOfWeek.SATURDAY, END));

        new_merchant_time_work_Saturday.setOnClickListener(v -> setActivity(new_merchant_time_work_Saturday, DayOfWeek.SATURDAY));
        new_merchant_time_work_Friday.setOnClickListener(v -> setActivity(new_merchant_time_work_Friday, DayOfWeek.FRIDAY));
        new_merchant_time_work_Thursday.setOnClickListener(v -> setActivity(new_merchant_time_work_Thursday, DayOfWeek.THURSDAY));
        new_merchant_time_work_Wednesday.setOnClickListener(v -> setActivity(new_merchant_time_work_Wednesday, DayOfWeek.WEDNESDAY));
        new_merchant_time_work_Tuesday.setOnClickListener(v -> setActivity(new_merchant_time_work_Tuesday, DayOfWeek.TUESDAY));
        new_merchant_time_work_Monday.setOnClickListener(v -> setActivity(new_merchant_time_work_Monday, DayOfWeek.MONDAY));
        new_merchant_time_work_Sunday.setOnClickListener(v -> setActivity(new_merchant_time_work_Sunday, DayOfWeek.SUNDAY));
    }

    private void setActivity(SwitchCompat switchCompat, DayOfWeek day) {
        if (switchCompat.isChecked()) {
            dayOfWeekBusinessDayHashMap.get(day).setActive(true);
        } else {
            dayOfWeekBusinessDayHashMap.get(day).setActive(false);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getTime(MaterialButton btn, DayOfWeek day, String type) {
        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinutes = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this.context, (view, hourOfDay, minute) -> {
            String text = "";
            if (type.equals(START)) {
                dayOfWeekBusinessDayHashMap.get(day).getTimeRanges().setStartHour(hourOfDay).setStartMinutes(minute);
                text = LocalTime.of(dayOfWeekBusinessDayHashMap.get(day).getTimeRanges().getStartHour()
                        ,dayOfWeekBusinessDayHashMap.get(day).getTimeRanges().getStartMinutes()).toString();
            } else if (type.equals(END)) {
                BusinessDay businessDay = dayOfWeekBusinessDayHashMap.get(day);
                int startHour = businessDay.getTimeRanges().getStartHour();
                int startMinutes = businessDay.getTimeRanges().getStartMinutes();

                if (hourOfDay < startHour || (hourOfDay == startHour && minute < startMinutes)) {
                    // Set end time to midnight
                    businessDay.getTimeRanges().setEndHour(0).setEndMinutes(0);
                    text = LocalTime.of(0,0).toString(); // Display midnight as the text
                } else {
                    businessDay.getTimeRanges().setEndHour(hourOfDay).setEndMinutes(minute);
                    text = LocalTime.of(hourOfDay, minute).toString();
                }
            }
            btn.setText(text);
        }, currentHour, currentMinutes, true);
        timePickerDialog.show();
    }


    @Override
    public boolean getAllowToContinue() {
        for (BusinessDay businessDay : dayOfWeekBusinessDayHashMap.values()) {
            if (businessDay.isActive()) {
                TimeRange timeRanges = businessDay.getTimeRanges();
                int startHour = timeRanges.getStartHour();
                int startMinutes = timeRanges.getStartMinutes();
                int endHour = timeRanges.getEndHour();
                int endMinutes = timeRanges.getEndMinutes();

                if (startHour > endHour || (startHour == endHour && startMinutes > endMinutes)) {
                    MySignal.getInstance().toast("Business Day "
                            +businessDay.getDayOfWeek().toString()+" its start time is after the end time");
                    return false; // Invalid time range
                }
            }
        }
        return true; // All time ranges are valid
    }

    @Override
    public void clearAll() {
        new_merchant_time_startTime_Sunday.setText(R.string.startTime);
        new_merchant_time_endTime_Sunday.setText(R.string.endTime);
        new_merchant_time_work_Sunday.setActivated(false);

        new_merchant_time_startTime_Monday.setText(R.string.startTime);
        new_merchant_time_endTime_Monday.setText(R.string.endTime);
        new_merchant_time_work_Monday.setActivated(false);

        new_merchant_time_startTime_Tuesday.setText(R.string.startTime);
        new_merchant_time_endTime_Tuesday.setText(R.string.endTime);
        new_merchant_time_work_Tuesday.setActivated(false);

        new_merchant_time_startTime_Wednesday.setText(R.string.startTime);
        new_merchant_time_endTime_Wednesday.setText(R.string.endTime);
        new_merchant_time_work_Wednesday.setActivated(false);

        new_merchant_time_startTime_Thursday.setText(R.string.startTime);
        new_merchant_time_endTime_Thursday.setText(R.string.endTime);
        new_merchant_time_work_Thursday.setActivated(false);

        new_merchant_time_startTime_Friday.setText(R.string.startTime);
        new_merchant_time_endTime_Friday.setText(R.string.endTime);
        new_merchant_time_work_Friday.setActivated(false);

        new_merchant_time_startTime_Saturday.setText(R.string.startTime);
        new_merchant_time_endTime_Saturday.setText(R.string.endTime);
        new_merchant_time_work_Saturday.setActivated(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startHashMap();
        }
    }

    public HashMap<DayOfWeek, BusinessDay> getDayOfWeekBusinessDayHashMap() {
        return dayOfWeekBusinessDayHashMap;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setTimes(Merchant merchant) {
        //SUNDAY
        new_merchant_time_work_Sunday.setChecked(merchant.getBusinessDay(DayOfWeek.SUNDAY).isActive());
        if(new_merchant_time_work_Sunday.isChecked()){
            new_merchant_time_startTime_Sunday.setText(MySignal.getInstance().formatTime(
                    merchant.getBusinessDay(DayOfWeek.SUNDAY).getTimeRanges().getStartHour()
                    ,merchant.getBusinessDay(DayOfWeek.SUNDAY).getTimeRanges().getStartMinutes()));
            new_merchant_time_endTime_Sunday.setText(MySignal.getInstance().formatTime(
                    merchant.getBusinessDay(DayOfWeek.SUNDAY).getTimeRanges().getEndHour()
                    ,merchant.getBusinessDay(DayOfWeek.SUNDAY).getTimeRanges().getEndMinutes()));
        }else {
            new_merchant_time_startTime_Sunday.setText(R.string.startTime);
            new_merchant_time_endTime_Sunday.setText(R.string.endTime);
        }

        //MONDAY
        new_merchant_time_work_Monday.setChecked(merchant.getBusinessDay(DayOfWeek.MONDAY).isActive());
        if(new_merchant_time_work_Monday.isChecked()){
            new_merchant_time_startTime_Monday.setText(MySignal.getInstance().formatTime(
                    merchant.getBusinessDay(DayOfWeek.MONDAY).getTimeRanges().getStartHour()
                    ,merchant.getBusinessDay(DayOfWeek.MONDAY).getTimeRanges().getStartMinutes()));
            new_merchant_time_endTime_Monday.setText(MySignal.getInstance().formatTime(
                    merchant.getBusinessDay(DayOfWeek.MONDAY).getTimeRanges().getEndHour()
                    ,merchant.getBusinessDay(DayOfWeek.MONDAY).getTimeRanges().getEndMinutes()));
        }else {
            new_merchant_time_startTime_Monday.setText(R.string.startTime);
            new_merchant_time_endTime_Monday.setText(R.string.endTime);
        }

        //TUESDAY
        new_merchant_time_work_Tuesday.setChecked(merchant.getBusinessDay(DayOfWeek.TUESDAY).isActive());
        if(new_merchant_time_work_Tuesday.isChecked()){
            new_merchant_time_startTime_Tuesday.setText(MySignal.getInstance().formatTime(
                    merchant.getBusinessDay(DayOfWeek.TUESDAY).getTimeRanges().getStartHour()
                    ,merchant.getBusinessDay(DayOfWeek.TUESDAY).getTimeRanges().getStartMinutes()));
            new_merchant_time_endTime_Tuesday.setText(MySignal.getInstance().formatTime(
                    merchant.getBusinessDay(DayOfWeek.TUESDAY).getTimeRanges().getEndHour()
                    ,merchant.getBusinessDay(DayOfWeek.TUESDAY).getTimeRanges().getEndMinutes()));
        }else {
            new_merchant_time_startTime_Tuesday.setText(R.string.startTime);
            new_merchant_time_endTime_Tuesday.setText(R.string.endTime);
        }

        //WEDNESDAY
        new_merchant_time_work_Wednesday.setChecked(merchant.getBusinessDay(DayOfWeek.WEDNESDAY).isActive());
        if(new_merchant_time_work_Wednesday.isChecked()){
            new_merchant_time_startTime_Wednesday.setText(MySignal.getInstance().formatTime(
                    merchant.getBusinessDay(DayOfWeek.WEDNESDAY).getTimeRanges().getStartHour()
                    ,merchant.getBusinessDay(DayOfWeek.WEDNESDAY).getTimeRanges().getStartMinutes()));
            new_merchant_time_endTime_Wednesday.setText(MySignal.getInstance().formatTime(
                    merchant.getBusinessDay(DayOfWeek.WEDNESDAY).getTimeRanges().getEndHour()
                    ,merchant.getBusinessDay(DayOfWeek.WEDNESDAY).getTimeRanges().getEndMinutes()));
        }else {
            new_merchant_time_startTime_Wednesday.setText(R.string.startTime);
            new_merchant_time_endTime_Wednesday.setText(R.string.endTime);
        }

        //THURSDAY
        new_merchant_time_work_Thursday.setChecked(merchant.getBusinessDay(DayOfWeek.THURSDAY).isActive());
        if(new_merchant_time_work_Thursday.isChecked()){
            new_merchant_time_startTime_Thursday.setText(MySignal.getInstance().formatTime(
                    merchant.getBusinessDay(DayOfWeek.THURSDAY).getTimeRanges().getStartHour()
                    ,merchant.getBusinessDay(DayOfWeek.THURSDAY).getTimeRanges().getStartMinutes()));
            new_merchant_time_endTime_Thursday.setText(MySignal.getInstance().formatTime(
                    merchant.getBusinessDay(DayOfWeek.THURSDAY).getTimeRanges().getEndHour()
                    ,merchant.getBusinessDay(DayOfWeek.THURSDAY).getTimeRanges().getEndMinutes()));
        }else {
            new_merchant_time_startTime_Thursday.setText(R.string.startTime);
            new_merchant_time_endTime_Thursday.setText(R.string.endTime);
        }

        //FRIDAY
        new_merchant_time_work_Friday.setChecked(merchant.getBusinessDay(DayOfWeek.FRIDAY).isActive());
        if(new_merchant_time_work_Friday.isChecked()){
            new_merchant_time_startTime_Friday.setText(MySignal.getInstance().formatTime(
                    merchant.getBusinessDay(DayOfWeek.FRIDAY).getTimeRanges().getStartHour()
                    ,merchant.getBusinessDay(DayOfWeek.FRIDAY).getTimeRanges().getStartMinutes()));
            new_merchant_time_endTime_Friday.setText(MySignal.getInstance().formatTime(
                    merchant.getBusinessDay(DayOfWeek.FRIDAY).getTimeRanges().getEndHour()
                    ,merchant.getBusinessDay(DayOfWeek.FRIDAY).getTimeRanges().getEndMinutes()));
        }else {
            new_merchant_time_startTime_Friday.setText(R.string.startTime);
            new_merchant_time_endTime_Friday.setText(R.string.endTime);
        }

        //SATURDAY
        new_merchant_time_work_Saturday.setChecked(merchant.getBusinessDay(DayOfWeek.SATURDAY).isActive());
        if(new_merchant_time_work_Saturday.isChecked()){
            new_merchant_time_startTime_Saturday.setText(MySignal.getInstance().formatTime(
                    merchant.getBusinessDay(DayOfWeek.SATURDAY).getTimeRanges().getStartHour()
                    ,merchant.getBusinessDay(DayOfWeek.SATURDAY).getTimeRanges().getStartMinutes()));
            new_merchant_time_endTime_Saturday.setText(MySignal.getInstance().formatTime(
                    merchant.getBusinessDay(DayOfWeek.SATURDAY).getTimeRanges().getEndHour()
                    ,merchant.getBusinessDay(DayOfWeek.SATURDAY).getTimeRanges().getEndMinutes()));
        }else {
            new_merchant_time_startTime_Saturday.setText(R.string.startTime);
            new_merchant_time_endTime_Saturday.setText(R.string.endTime);
        }
    }
}