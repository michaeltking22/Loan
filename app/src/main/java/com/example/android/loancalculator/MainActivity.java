package com.example.android.loancalculator;

import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView cost;
    private TextView apr;
    private TextView down;
    private EditText inputDown;
    private SeekBar Bar;
    private TextView length;
    private EditText inputCost;
    private EditText inputAPR;
    private RadioButton lease;
    private RadioButton loan;
    private RadioGroup type;
    private RadioListener listenRadio;
    private TextView out;


    @Override
        protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cost = findViewById(R.id.view1);
        down = findViewById(R.id.view2);
        Bar = findViewById(R.id.lengthBar);
        apr = findViewById(R.id.inputAPR)
        ;
        inputCost = findViewById(R.id.inputCost);
        inputDown = findViewById(R.id.inputDown);
        inputAPR = findViewById(R.id.inputAPR)
        ;
        length = findViewById(R.id.viewProg);
        lease = findViewById(R.id.lease);
        loan = findViewById(R.id.loan);
        out = findViewById(R.id.monthOut);
        listenRadio = new RadioListener();
        type = findViewById(R.id.radGroup);
        type.setOnCheckedChangeListener(listenRadio);

        apr.setOnEditorActionListener(
                new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                        if(lease.isChecked()) {
                            setMonthlyLease();
                        }
                        else{
                            setMonthlyLoan();
                        }
                        return false;
                    }

                }
        );

        Bar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                        int progress = Bar.getProgress();
                        length.setText(progress + " month plan");
                        setMonthlyLoan();
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                }
        );

        if (savedInstanceState != null) {
            if(lease.isChecked()) {
                cost.setText(savedInstanceState.getString("COST"));
                down.setText(savedInstanceState.getString("DOWN"));
                apr.setText(savedInstanceState.getString("APR"));
                out.setText(savedInstanceState.getString("OUT"));
                lease.setChecked(savedInstanceState.getBoolean("LEASE"));
            }
            else
                cost.setText(savedInstanceState.getString("COST"));
                down.setText(savedInstanceState.getString("DOWN"));
                apr.setText(savedInstanceState.getString("APR"));
                out.setText(savedInstanceState.getString("OUT"));
                loan.setChecked(savedInstanceState.getBoolean("LOAN"));
        }
    }

        public void onSaveInstanceState(Bundle savedInstanceState) {
        if(loan.isChecked()) {
            super.onSaveInstanceState(savedInstanceState);
            savedInstanceState.putString("COST", cost.getText().toString());
            savedInstanceState.putString("DOWN", down.getText().toString());
            savedInstanceState.putString("APR", apr.getText().toString());
            savedInstanceState.putBoolean("LOAN", loan.isChecked());
            savedInstanceState.putString("OUT", out.getText().toString());
        }
        else{
            super.onSaveInstanceState(savedInstanceState);
            savedInstanceState.putString("COST", cost.getText().toString());
            savedInstanceState.putString("DOWN", down.getText().toString());
            savedInstanceState.putString("APR", apr.getText().toString());
            savedInstanceState.putBoolean("LEASE", lease.isChecked());
            savedInstanceState.putString("OUT", out.getText().toString());
        }


    }






    private class RadioListener implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {
        public void onCheckedChanged(RadioGroup g, int button) {
            if (lease.isChecked()) {
                setMonthlyLease();

            } else {
                setMonthlyLoan();
            }
        }

        @Override
        public void onClick(View v) {

        }

    }

        private void setMonthlyLoan(){
        String c = inputCost.getText().toString();
        double L = Double.parseDouble(c);
        String d = inputDown.getText().toString();
        double down = Double.parseDouble(d);
        L=L-down;
        String a = inputAPR.getText().toString();
        double apr = Double.parseDouble(a);
        int n = Bar.getProgress();
        double mr = (apr/100)/12;
        double P = mr*L/(1-(Math.pow(1+mr, -n)));
        out.setText("$" +String.format("%.2f", P));
        }

        private void setMonthlyLease(){
            String c = inputCost.getText().toString();
            double L = Double.parseDouble(c);
            String d = inputDown.getText().toString();
            double down = Double.parseDouble(d);
            L=L-down;
            String a = inputAPR.getText().toString();
            double apr = Double.parseDouble(a);
            double mr = (apr/100)/12;
            L = L/3;
            double P = (mr*L)/(1-(Math.pow(1+mr, -36)));
            out.setText("$" +String.format("%.2f", P));
        }


}
