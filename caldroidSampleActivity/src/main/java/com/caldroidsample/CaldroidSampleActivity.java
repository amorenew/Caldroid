package com.caldroidsample;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.Utils;
import com.hirondelle.date4j.DateTime;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@SuppressLint("SimpleDateFormat")
public class CaldroidSampleActivity extends AppCompatActivity {
    private boolean undo = false;
    private CaldroidFragment caldroidFragment;
    private CaldroidFragment dialogCaldroidFragment;

    public static long getDayInMillisSecond(int daysCount) {
        int HOURS_IN_DAY = 24;
        int MINUTES_IN_HOUR = 60;
        int SECONDS_IN_MINUTE = 60;
        int MILLIS_IN_SECOND = 1000;
        return (long) daysCount * HOURS_IN_DAY * MINUTES_IN_HOUR * SECONDS_IN_MINUTE * MILLIS_IN_SECOND;
    }

    private void setCustomResourceForDates() {
        Calendar cal = Calendar.getInstance();

        // Min date is last 7 days
        cal.add(Calendar.DATE, -7);
        Date blueDate = cal.getTime();

        // Max date is next 7 days
        cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 7);
        Date greenDate = cal.getTime();

        if (caldroidFragment != null) {
//            ColorDrawable blue = new ColorDrawable(getResources().getColor(R.color.blue));
//            ColorDrawable green = new ColorDrawable(Color.GREEN);
//            caldroidFragment.setBackgroundDrawableForDate(blue, blueDate);
//            caldroidFragment.setBackgroundDrawableForDate(green, greenDate);
//            caldroidFragment.setTextColorForDate(R.color.white, blueDate);
//            caldroidFragment.setTextColorForDate(R.color.white, greenDate);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");

        // Setup caldroid fragment
        // **** If you want normal CaldroidFragment, use below line ****
        caldroidFragment = new CaldroidFragment();
        caldroidFragment.setMinDate(new Date(Calendar.getInstance().getTimeInMillis() + Utils.getDayInMillisSecond()));
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(calendar.getTimeInMillis() + getDayInMillisSecond(365));
        caldroidFragment.setMaxDate(calendar.getTime());

        // //////////////////////////////////////////////////////////////////////
        // **** This is to show customized fragment. If you want customized
        // version, uncomment below line ****
//		 caldroidFragment = new CaldroidSampleCustomFragment();

        // Setup arguments

        // If Activity is created after rotation
        if (savedInstanceState != null) {
            caldroidFragment.restoreStatesFromKey(savedInstanceState,
                    "CALDROID_SAVED_STATE");
        }
        // If activity is created from fresh
        else {
            Bundle args = new Bundle();
            Calendar cal = Calendar.getInstance();
            args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
            args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
            args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
            args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true);

            // Uncomment this to customize startDayOfWeek
            // args.putInt(CaldroidFragment.START_DAY_OF_WEEK,
            // CaldroidFragment.TUESDAY); // Tuesday

            // Uncomment this line to use Caldroid in compact mode
            // args.putBoolean(CaldroidFragment.SQUARE_TEXT_VIEW_CELL, false);

            // Uncomment this line to use dark theme
//            args.putInt(CaldroidFragment.THEME_RESOURCE, com.caldroid.R.style.CaldroidDefaultDark);

            caldroidFragment.setArguments(args);
        }

        setCustomResourceForDates();

        // Attach to the activity
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.calendar1, caldroidFragment);
        t.commit();

        // Setup listener
        final CaldroidListener listener = new CaldroidListener() {

            @Override
            public void onSelectDate(Date date, View view) {
                Toast.makeText(getApplicationContext(), formatter.format(date),
                        Toast.LENGTH_SHORT).show();
                DateTime startSelectedDate = caldroidFragment.getDatePagerAdapters().get(0).getStartSelectedDate();
                Calendar startCalendar = Calendar.getInstance();
                if (startSelectedDate != null)
                startCalendar.setTimeInMillis(startSelectedDate.getMilliseconds(TimeZone.getDefault()));
                if (caldroidFragment.getDatePagerAdapters().get(0).isSingleSelectedDate()) {
                } else {
                    DateTime endSelectedDate = caldroidFragment.getDatePagerAdapters().get(0).getEndSelectedDate();
                    Calendar endCalendar = Calendar.getInstance();
                    if (endSelectedDate != null)
                    endCalendar.setTimeInMillis(endSelectedDate.getMilliseconds(TimeZone.getDefault()));
                }
            }

            @Override
            public void onChangeMonth(int month, int year) {
                String text = "month: " + month + " year: " + year;
                Toast.makeText(getApplicationContext(), text,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClickDate(Date date, View view) {
                Toast.makeText(getApplicationContext(),
                        "Long click " + formatter.format(date),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCaldroidViewCreated() {
                if (caldroidFragment.getLeftArrowButton() != null) {
                    Toast.makeText(getApplicationContext(),
                            "Caldroid view is created", Toast.LENGTH_SHORT)
                            .show();
                }
            }

        };

        // Setup Caldroid
        caldroidFragment.setCaldroidListener(listener);

        final TextView textView = (TextView) findViewById(R.id.textview);

        final Button customizeButton = (Button) findViewById(R.id.customize_button);

        // Customize the calendar
        customizeButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                if (undo) {
                    customizeButton.setText(getString(R.string.customize));
                    textView.setText("");

                    // Reset calendar
                    caldroidFragment.clearDisableDates();
                    caldroidFragment.clearSelectedDates();
                    caldroidFragment.setMinDate(null);
                    caldroidFragment.setMaxDate(null);
                    caldroidFragment.setShowNavigationArrows(true);
                    caldroidFragment.setEnableSwipe(true);
                    caldroidFragment.refreshView();
                    undo = false;
                    return;
                }

                // Else
                undo = true;
                customizeButton.setText(getString(R.string.undo));
                Calendar cal = Calendar.getInstance();

                // Min date is last 7 days
//                cal.add(Calendar.DATE, -7);
                Date minDate = cal.getTime();

                // Max date is next 7 days
                cal = Calendar.getInstance();
                cal.add(Calendar.DATE, 28);
                Date maxDate = cal.getTime();

                // Set selected dates
                // From Date
                cal = Calendar.getInstance();
                cal.add(Calendar.DATE, 2);
                Date fromDate = cal.getTime();

                // To Date
                cal = Calendar.getInstance();
                cal.add(Calendar.DATE, 5);
                Date toDate = cal.getTime();

                // Set disabled dates
                ArrayList<Date> disabledDates = new ArrayList<>();
                for (int i = 5; i < 8; i++) {
                    cal = Calendar.getInstance();
                    cal.add(Calendar.DATE, i);
                    disabledDates.add(cal.getTime());
                }

                // Customize
                caldroidFragment.setMinDate(minDate);
                caldroidFragment.setMaxDate(maxDate);
//                caldroidFragment.setDisableDates(disabledDates);
                caldroidFragment.setSelectedDates(fromDate, toDate);
                caldroidFragment.setShowNavigationArrows(true);
                caldroidFragment.setEnableSwipe(true);
                caldroidFragment.refreshView();

                // Move to date
                // cal = Calendar.getInstance();
                // cal.add(Calendar.MONTH, 12);
                // caldroidFragment.moveToDate(cal.getTime());

                String text = "Today: " + formatter.format(new Date()) + "\n";
                text += "Min Date: " + formatter.format(minDate) + "\n";
                text += "Max Date: " + formatter.format(maxDate) + "\n";
                text += "Select From Date: " + formatter.format(fromDate)
                        + "\n";
                text += "Select To Date: " + formatter.format(toDate) + "\n";
                for (Date date : disabledDates) {
                    text += "Disabled Date: " + formatter.format(date) + "\n";
                }

                textView.setText(text);
            }
        });

        Button showDialogButton = (Button) findViewById(R.id.show_dialog_button);

        final Bundle state = savedInstanceState;
        showDialogButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // Setup caldroid to use as dialog
                dialogCaldroidFragment = new CaldroidFragment();
                dialogCaldroidFragment.setCaldroidListener(listener);
                // If activity is recovered from rotation
                final String dialogTag = "CALDROID_DIALOG_FRAGMENT";
                if (state != null) {
                    dialogCaldroidFragment.restoreDialogStatesFromKey(
                            getSupportFragmentManager(), state,
                            "DIALOG_CALDROID_SAVED_STATE", dialogTag);
                    Bundle args = dialogCaldroidFragment.getArguments();
                    if (args == null) {
                        args = new Bundle();
                        dialogCaldroidFragment.setArguments(args);
                    }
                } else {
                    // Setup arguments
                    Bundle bundle = new Bundle();
                    // Setup dialogTitle
                    dialogCaldroidFragment.setArguments(bundle);
                }
                dialogCaldroidFragment.setBackground(R.drawable.background);
                dialogCaldroidFragment.show(getSupportFragmentManager(),
                        dialogTag);

            }
        });
    }

    /**
     * Save current states of the Caldroid here
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);

        if (caldroidFragment != null) {
            caldroidFragment.saveStatesToKey(outState, "CALDROID_SAVED_STATE");
        }

        if (dialogCaldroidFragment != null) {
            dialogCaldroidFragment.saveStatesToKey(outState,
                    "DIALOG_CALDROID_SAVED_STATE");
        }
    }

}
