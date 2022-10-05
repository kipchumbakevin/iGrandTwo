package com.igrandbusiness.mybusinessplans;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class CalculatorActivity extends AppCompatActivity {
    EditText input;
    TextView results;
    ImageView arrowBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        input = findViewById(R.id.input);
        results = findViewById(R.id.result);
        arrowBack = findViewById(R.id.arrow_back);
        input.setShowSoftInputOnFocus(false);
        input.setOnClickListener(view->{
            if (input.getText().toString().equals(getString(R.string.display))) {
                input.getText().clear();
            }
        });
        arrowBack.setOnClickListener(view->onBackPressed());
    }
    private void updateText(String stringToAdd){
        String oldString = input.getText().toString();
        int cursorPos = input.getSelectionStart();
        String left = oldString.substring(0,cursorPos);
        String right = oldString.substring(cursorPos);
        if (getString(R.string.display).equals(input.getText().toString())){
            input.setText(stringToAdd);
            input.setSelection(cursorPos + 1);
        }else {
            input.setText(String.format("%s%s%s", left, stringToAdd, right));
            input.setSelection(cursorPos + 1);
        }


    }
    public void zeroBTN(View view){
        updateText("0");
    }
    public void oneBTN(View view){
        updateText("1");
    }
    public void twoBTN(View view){
        updateText("2");
    }
    public void threeBTN(View view){
        updateText("3");
    }
    public void fourBTN(View view){
        updateText("4");
    }
    public void fiveBTN(View view){
        updateText("5");
    }
    public void sixBTN(View view){
        updateText("6");
    }
    public void sevenBTN(View view){
        updateText("7");
    }
    public void eightBTN(View view){
        updateText("8");
    }
    public void nineBTN(View view){
        updateText("9");
    }
    public void multiplyBTN(View view){
        updateText("×");
    }
    public void subtractBTN(View view){
        updateText("-");
    }
    public void divideBTN(View view){
        updateText("÷");
    }
    public void percentBTN(View view){
        updateText("%");
    }
    public void addBTN(View view){
        updateText("+");
    }
    public void clearBTN(View view){
        input.setText("");
        results.setText("");
    }
    public void parBTN(View view){
        int pos = input.getSelectionStart();
        int openPar = 0;
        int closedPar = 0;
        int length = input.getText().length();
        for (int i = 0; i < pos; i ++){
            if (input.getText().toString().substring(i,i+1).equals("(")){
                openPar += 1;
            }
            if (input.getText().toString().substring(i,i+1).equals(")")){
                closedPar += 1;
            }
        }
        if (openPar == closedPar || input.getText().toString().substring(length-1,length).equals("(")){
            updateText("(");
        }
       else if (closedPar < openPar && !input.getText().toString().substring(length-1,length).equals("(")){
           updateText(")");
        }
        input.setSelection(pos +1);
    }
    public void decimalBTN(View view){
        updateText(".");
    }
    public void plusminusBTN(View view){
        updateText("+/-");
    }
    public void equalBTN(View view){
        Double result = null;
        String userExpression = input.getText().toString();
        userExpression = userExpression.replaceAll("÷","/");
        userExpression = userExpression.replaceAll("×","*");
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("rhino");
        try {
            result =(Double) engine.eval(userExpression);
        } catch (ScriptException e) {
            e.printStackTrace();
            Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT).show();
        }
        if (result != null){
            results.setText(String.valueOf(result.doubleValue()));
        }

//        Expression exp = new Expression(userExpression);
       // String result = String.valueOf(exp.calculate());
        //results.setText(result);
    }
    public void backSpaceBTN(View view){
        int pos = input.getSelectionStart();
        int length = input.getText().length();
        if (pos != 0 && length != 0){
            SpannableStringBuilder selection = (SpannableStringBuilder) input.getText();
            selection.replace(pos -1 , pos, "");
            input.setText(selection);
            input.setSelection(pos - 1);
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
}