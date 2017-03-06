package edu.cqu.wwt.acountcompute;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String amount;
    EditText totalAmount,nbrPers;
    TextView display;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        totalAmount=(EditText)findViewById(R.id.etAmount);
        nbrPers=(EditText)findViewById(R.id.etNbr);
        display=(TextView)findViewById(R.id.tvAmountPerPerson);
    }

    public void compute(View v){
        if(totalAmount.getText().toString().equals("")||nbrPers.getText().toString().equals("")) {
            Toast.makeText(getBaseContext(), "Please Input Some Data", Toast.LENGTH_SHORT).show();
            return;
        }
        Double total=Double.parseDouble(totalAmount.getText().toString());
        Double pers=Double.parseDouble(nbrPers.getText().toString());
        if(pers.equals(Double.valueOf(0))){
            Toast.makeText(getBaseContext(), "The Number Of Person Must Not Be Zero!!!Crash!!!", Toast.LENGTH_SHORT).show();
            return;
        }
        double share=total/pers;
        share= Math.round(share*100);
        share=share/100;
        display.setText(String.valueOf(share));
        amount=String.valueOf(share);
        Toast.makeText(getBaseContext(), "The Amount to share is:"+amount, Toast.LENGTH_SHORT).show();
    }
}
