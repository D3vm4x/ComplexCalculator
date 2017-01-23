package com.example.a3_pc.complex_calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.view.View;
import android.widget.AdapterView;


public class MainActivity extends AppCompatActivity {
    String operator = "";
    Spinner spinner;
    Spinner form;
    String complex_form = "";
    ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // dropdown menu to select which operator to apply
        spinner = (Spinner) findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.operator_arrays, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long l) {
                operator = parent.getItemAtPosition(pos).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // dropdown menu to select in which form to output the complex result
        // options are: "Rectangular", "Exponential", "Polar"
        form = (Spinner) findViewById(R.id.spinner2);
        adapter = ArrayAdapter.createFromResource(this, R.array.complex_form, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        form.setAdapter(adapter);
        form.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long l) {
                complex_form = parent.getItemAtPosition(pos).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    // On button click applies chosen operator to the two given complex expressions
    //  and outputs result in chosen complex form
    public void OnClick(View v) {
        EditText v1 = (EditText) findViewById(R.id.v1);
        EditText v2 = (EditText) findViewById(R.id.v2);
        TextView result = (TextView) findViewById(R.id.textView);
        complex c1 = new complex(v1.getText().toString());
        complex c2 = new complex(v2.getText().toString());
        complex ans = new complex();
        if(operator.equals("Addition")) {
            ans = complex.add(c1, c2);
            result.setText(complex.to_String(ans, complex_form));
        }
        else if(operator.equals("Subtraction")) {
            ans = complex.subtract(c1, c2);
            result.setText(complex.to_String(ans, complex_form));
        }
        else if(operator.equals("Multiplication")) {
            ans = complex.multiply(c1, c2);
            result.setText(complex.to_String(ans, complex_form));
        }
        else if(operator.equals("Division")) {
            ans = complex.divide(c1, c2);
            result.setText(complex.to_String(ans, complex_form));
        }
        else
            result.setText("ERROR: Operation not selected");
    }
}

