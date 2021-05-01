package com.example.android.intentsplayground;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.android.intentsplayground.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding bind;
    int qty = 0;
    int minValue,maxValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());

        setUpEventHandlers();
        getInitialCount();
    }

    //getting data from intentPlayground
    private void getInitialCount() {
        //getting data in bundles form
        Bundle bundle = getIntent().getExtras();
        qty = bundle.getInt(Constants.INITIAL_COUNT,0);
        minValue = bundle.getInt(Constants.MIN_VALUE,-100);
        maxValue = bundle.getInt(Constants.MAX_VALUE,100);
        bind.quantity.setText(""+qty);

        if(qty != 0){
            bind.sendBack.setVisibility(View.VISIBLE);
        }
    }

    //Method to set functionality of both the buttons
    private void setUpEventHandlers() {

        //setting onClickListener on decrement button
        bind.decButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qty--;
                //decrementing quantity and setting text
                bind.quantity.setText(""+qty);
            }
        });

        //setting onClickListener on increment button
        bind.incButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qty++;
                //incrementing quantity and setting text
                bind.quantity.setText(""+qty);
            }
        });
    }

    // for sending back new data
    public void sendBack(View view) {

        //setting range
        if(qty>=minValue && qty<=maxValue){
            Intent intent = new Intent();
            intent.putExtra(Constants.FINAL_VALUE,qty);
            setResult(RESULT_OK,intent);
            finish();
        }
        //if not in range
        else{
            Toast.makeText(this, "Not in range", Toast.LENGTH_SHORT).show();
        }
    }
}