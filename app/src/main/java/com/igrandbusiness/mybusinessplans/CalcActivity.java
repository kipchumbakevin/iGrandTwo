package com.igrandbusiness.mybusinessplans;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.mariuszgromada.math.mxparser.Expression;

import java.math.BigDecimal;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class CalcActivity extends AppCompatActivity implements View.OnClickListener {

    Button zeroBtn, oneBtn, twoBtn, threeBtn, fourBtn, fiveBtn, sixBtn, sevenBtn, eightBtn, nineBtn;
    Button equalsBtn, dotBtn, clearBtn, backspaceBtn, addBtn, subBtn, multiplyBtn, multiplyBy100Btn, divideBtn, percentageBtn;
    private TextView resultTxtView;
    private EditText expTxtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);

        resultTxtView = findViewById(R.id.out);
        expTxtView = findViewById(R.id.exp);

        zeroBtn = findViewById(R.id.zero);
        oneBtn = findViewById(R.id.one);
        twoBtn = findViewById(R.id.two);
        threeBtn = findViewById(R.id.three);
        fourBtn = findViewById(R.id.four);
        fiveBtn = findViewById(R.id.five);
        sixBtn = findViewById(R.id.six);
        sevenBtn = findViewById(R.id.seven);
        eightBtn = findViewById(R.id.eight);
        nineBtn = findViewById(R.id.nine);

        equalsBtn = findViewById(R.id.equals);
        dotBtn = findViewById(R.id.dot);
        clearBtn = findViewById(R.id.clear);
        backspaceBtn = findViewById(R.id.backspace);
        addBtn = findViewById(R.id.plus);
        subBtn = findViewById(R.id.minus);
        multiplyBtn = findViewById(R.id.multiply);
        multiplyBy100Btn = findViewById(R.id.mul100times);
        divideBtn = findViewById(R.id.divide);
        percentageBtn = findViewById(R.id.percentage);

        zeroBtn.setOnClickListener(this);
        oneBtn.setOnClickListener(this);
        twoBtn.setOnClickListener(this);
        threeBtn.setOnClickListener(this);
        fourBtn.setOnClickListener(this);
        fiveBtn.setOnClickListener(this);
        sixBtn.setOnClickListener(this);
        sevenBtn.setOnClickListener(this);
        eightBtn.setOnClickListener(this);
        nineBtn.setOnClickListener(this);

        equalsBtn.setOnClickListener(this);
        dotBtn.setOnClickListener(this);
        clearBtn.setOnClickListener(this);
        backspaceBtn.setOnClickListener(this);
        addBtn.setOnClickListener(this);
        subBtn.setOnClickListener(this);
        multiplyBtn.setOnClickListener(this);
        multiplyBy100Btn.setOnClickListener(this);
        divideBtn.setOnClickListener(this);
        percentageBtn.setOnClickListener(this);

        expTxtView.setShowSoftInputOnFocus(false);
        expTxtView.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() != 0)
                    calculate();
            }
        });

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        String exp = expTxtView.getText().toString();

        if (R.id.equals == id) {
            calculate();
        } else if (R.id.zero == id) {
            generateExpression("0");
        } else if (R.id.one == id) {
            generateExpression("1");
        } else if (R.id.two == id) {
            generateExpression("2");
        } else if (R.id.three == id) {
            generateExpression("3");
        } else if (R.id.four == id) {
            generateExpression("4");
        } else if (R.id.five == id) {
            generateExpression("5");
        } else if (R.id.six == id) {
            generateExpression("6");
        } else if (R.id.seven == id) {
            generateExpression("7");
        } else if (R.id.eight == id) {
            generateExpression("8");
        } else if (R.id.nine == id) {
            generateExpression("9");
        } else if (R.id.plus == id) {
            generateExpression("+");
        } else if (R.id.minus == id) {
            generateExpression("-");
        } else if (R.id.multiply == id) {
            generateExpression("*");
        } else if (R.id.mul100times == id) {
            if (!exp.equals("")) {
                Expression expression = new Expression(exp + "*100");
                String result = String.valueOf(expression.calculate());
                resultTxtView.setText(result);
                expTxtView.setText(result);
            }
        } else if (R.id.divide == id) {
            generateExpression("/");
        } else if (R.id.clear == id) {
            expTxtView.setText("");
            resultTxtView.setText("");
        } else if (R.id.backspace == id) {
            int pos = expTxtView.getSelectionStart();
            int length = expTxtView.getText().length();
            if (pos != 0 && length != 0){
                SpannableStringBuilder selection = (SpannableStringBuilder) expTxtView.getText();
                selection.replace(pos -1 , pos, "");
                expTxtView.setText(selection);
                expTxtView.setSelection(pos - 1);
            }
        } else if (R.id.percentage == id) {
            if (exp.equals("") || isSpecialChar("" + exp.charAt(exp.length() - 1))) {
                return;
            }
            Expression expression = new Expression(exp + "/100");
            String result = String.valueOf(expression.calculate());

            expTxtView.setText(result);
            resultTxtView.setText(result);
        } else if (R.id.dot == id) {
            if (!Double.isNaN(new Expression(exp + ".0").calculate())) generateExpression(".");
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,MainActivity.class));
        finish();

    }

    private void calculate() {
        String exp = expTxtView.getText().toString();
        String result;
        if (exp.equals("") || exp.equals(".") || isSpecialChar("" + exp.charAt(exp.length() - 1))) {
            return;
        }
        Expression expression = new Expression(exp);
        result = String.valueOf(expression.calculate());
        resultTxtView.setText(result);
    }

    public void generateExpression(String val) {
//        String exp = expTxtView.getText().toString();
//        if (isSpecialChar(val)) {
//            if (exp.equals("") || isSpecialChar("" + exp.charAt(exp.length() - 1))) {
//                return;
//            }
//        }
//        expTxtView.setText(String.format("%s%s", expTxtView.getText().toString(), val));
        String oldString = expTxtView.getText().toString();
        int cursorPos = expTxtView.getSelectionStart();
        String left = oldString.substring(0,cursorPos);
        String right = oldString.substring(cursorPos);
        if (getString(R.string.display).equals(expTxtView.getText().toString())){
            expTxtView.setText(val);
            expTxtView.setSelection(cursorPos + 1);
        }else {
            expTxtView.setText(String.format("%s%s%s", left, val, right));
            expTxtView.setSelection(cursorPos + 1);
        }
    }

    public boolean isSpecialChar(String str) {
        return Pattern.compile("[$&+,:;=\\\\?@#|/'<>.^*()%!-]").matcher(str).find();
    }
}






















































//        extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener
//{
//    private int openParenthesis = 0;
//
//    private boolean dotUsed = false;
//
//    private boolean equalClicked = false;
//    private String lastExpression = "";
//
//    private final static int EXCEPTION = -1;
//    private final static int IS_NUMBER = 0;
//    private final static int IS_OPERAND = 1;
//    private final static int IS_OPEN_PARENTHESIS = 2;
//    private final static int IS_CLOSE_PARENTHESIS = 3;
//    private final static int IS_DOT = 4;
//
//    Button buttonNumber0;
//    Button buttonNumber1;
//    Button buttonNumber2;
//    Button buttonNumber3;
//    Button buttonNumber4;
//    Button buttonNumber5;
//    Button buttonNumber6;
//    Button buttonNumber7;
//    Button buttonNumber8;
//    Button buttonNumber9;
//
//    Button buttonClear;
//    Button buttonParentheses;
//    Button buttonPercent;
//    Button buttonDivision;
//    Button buttonMultiplication;
//    Button buttonSubtraction;
//    Button buttonAddition;
//    Button buttonEqual;
//    Button buttonDot;
//
//    TextView textViewInputNumbers;
//
//    ScriptEngine scriptEngine;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_calc);
//        scriptEngine = new ScriptEngineManager().getEngineByName("rhino");
//
//        initializeViewVariables();
//        setOnClickListeners();
//        setOnTouchListener();
//    }
//
//    private void initializeViewVariables()
//    {
//        buttonNumber0 = (Button) findViewById(R.id.button_zero);
//        buttonNumber1 = (Button) findViewById(R.id.button_one);
//        buttonNumber2 = (Button) findViewById(R.id.button_two);
//        buttonNumber3 = (Button) findViewById(R.id.button_three);
//        buttonNumber4 = (Button) findViewById(R.id.button_four);
//        buttonNumber5 = (Button) findViewById(R.id.button_five);
//        buttonNumber6 = (Button) findViewById(R.id.button_six);
//        buttonNumber7 = (Button) findViewById(R.id.button_seven);
//        buttonNumber8 = (Button) findViewById(R.id.button_eight);
//        buttonNumber9 = (Button) findViewById(R.id.button_nine);
//
//        buttonClear = (Button) findViewById(R.id.button_clear);
//        buttonParentheses = (Button) findViewById(R.id.button_parentheses);
//        buttonPercent = (Button) findViewById(R.id.button_percent);
//        buttonDivision = (Button) findViewById(R.id.button_division);
//        buttonMultiplication = (Button) findViewById(R.id.button_multiplication);
//        buttonSubtraction = (Button) findViewById(R.id.button_subtraction);
//        buttonAddition = (Button) findViewById(R.id.button_addition);
//        buttonEqual = (Button) findViewById(R.id.button_equal);
//        buttonDot = (Button) findViewById(R.id.button_dot);
//        textViewInputNumbers = (TextView) findViewById(R.id.textView_input_numbers);
//    }
//
//    private void setOnClickListeners()
//    {
//        buttonNumber0.setOnClickListener(this);
//        buttonNumber1.setOnClickListener(this);
//        buttonNumber2.setOnClickListener(this);
//        buttonNumber3.setOnClickListener(this);
//        buttonNumber4.setOnClickListener(this);
//        buttonNumber5.setOnClickListener(this);
//        buttonNumber6.setOnClickListener(this);
//        buttonNumber7.setOnClickListener(this);
//        buttonNumber8.setOnClickListener(this);
//        buttonNumber9.setOnClickListener(this);
//
//        buttonClear.setOnClickListener(this);
//        buttonParentheses.setOnClickListener(this);
//        buttonPercent.setOnClickListener(this);
//        buttonDivision.setOnClickListener(this);
//        buttonMultiplication.setOnClickListener(this);
//        buttonSubtraction.setOnClickListener(this);
//        buttonAddition.setOnClickListener(this);
//        buttonEqual.setOnClickListener(this);
//        buttonDot.setOnClickListener(this);
//    }
//
//    private void setOnTouchListener()
//    {
//        buttonNumber0.setOnTouchListener(this);
//        buttonNumber1.setOnTouchListener(this);
//        buttonNumber2.setOnTouchListener(this);
//        buttonNumber3.setOnTouchListener(this);
//        buttonNumber4.setOnTouchListener(this);
//        buttonNumber5.setOnTouchListener(this);
//        buttonNumber6.setOnTouchListener(this);
//        buttonNumber7.setOnTouchListener(this);
//        buttonNumber8.setOnTouchListener(this);
//        buttonNumber9.setOnTouchListener(this);
//
//        buttonClear.setOnTouchListener(this);
//        buttonParentheses.setOnTouchListener(this);
//        buttonPercent.setOnTouchListener(this);
//        buttonDivision.setOnTouchListener(this);
//        buttonMultiplication.setOnTouchListener(this);
//        buttonSubtraction.setOnTouchListener(this);
//        buttonAddition.setOnTouchListener(this);
//        buttonDot.setOnTouchListener(this);
//    }
//
//    @Override
//    public void onClick(View view)
//    {
//        switch (view.getId())
//        {
//            case R.id.button_zero:
//                if (addNumber("0")) equalClicked = false;
//                break;
//            case R.id.button_one:
//                if (addNumber("1")) equalClicked = false;
//                break;
//            case R.id.button_two:
//                if (addNumber("2")) equalClicked = false;
//                break;
//            case R.id.button_three:
//                if (addNumber("3")) equalClicked = false;
//                break;
//            case R.id.button_four:
//                if (addNumber("4")) equalClicked = false;
//                break;
//            case R.id.button_five:
//                if (addNumber("5")) equalClicked = false;
//                break;
//            case R.id.button_six:
//                if (addNumber("6")) equalClicked = false;
//                break;
//            case R.id.button_seven:
//                if (addNumber("7")) equalClicked = false;
//                break;
//            case R.id.button_eight:
//                if (addNumber("8")) equalClicked = false;
//                break;
//            case R.id.button_nine:
//                if (addNumber("9")) equalClicked = false;
//                break;
//            case R.id.button_addition:
//                if (addOperand("+")) equalClicked = false;
//                break;
//            case R.id.button_subtraction:
//                if (addOperand("-")) equalClicked = false;
//                break;
//            case R.id.button_multiplication:
//                if (addOperand("x")) equalClicked = false;
//                break;
//            case R.id.button_division:
//                if (addOperand("\u00F7")) equalClicked = false;
//                break;
//            case R.id.button_percent:
//                if (addOperand("%")) equalClicked = false;
//                break;
//            case R.id.button_dot:
//                if (addDot()) equalClicked = false;
//                break;
//            case R.id.button_parentheses:
//                if (addParenthesis()) equalClicked = false;
//                break;
//            case R.id.button_clear:
//                textViewInputNumbers.setText("");
//                openParenthesis = 0;
//                dotUsed = false;
//                equalClicked = false;
//                break;
//            case R.id.button_equal:
//                if (textViewInputNumbers.getText().toString() != null && !textViewInputNumbers.getText().toString().equals(""))
//                    calculate(textViewInputNumbers.getText().toString());
//                break;
//        }
//
//    }
//
//    @Override
//    public boolean onTouch(View view, MotionEvent motionEvent)
//    {
//        switch (motionEvent.getAction())
//        {
//            case MotionEvent.ACTION_DOWN:
//            {
//                view.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
//                view.invalidate();
//                break;
//            }
//            case MotionEvent.ACTION_UP:
//            {
//                view.getBackground().clearColorFilter();
//                view.invalidate();
//                break;
//            }
//        }
//        return false;
//    }
//
//    private boolean addDot()
//    {
//        boolean done = false;
//
//        if (textViewInputNumbers.getText().length() == 0)
//        {
//            textViewInputNumbers.setText("0.");
//            dotUsed = true;
//            done = true;
//        } else if (dotUsed == true)
//        {
//        } else if (defineLastCharacter(textViewInputNumbers.getText().charAt(textViewInputNumbers.getText().length() - 1) + "") == IS_OPERAND)
//        {
//            textViewInputNumbers.setText(textViewInputNumbers.getText() + "0.");
//            done = true;
//            dotUsed = true;
//        } else if (defineLastCharacter(textViewInputNumbers.getText().charAt(textViewInputNumbers.getText().length() - 1) + "") == IS_NUMBER)
//        {
//            textViewInputNumbers.setText(textViewInputNumbers.getText() + ".");
//            done = true;
//            dotUsed = true;
//        }
//        return done;
//    }
//
//    private boolean addParenthesis()
//    {
//        boolean done = false;
//        int operationLength = textViewInputNumbers.getText().length();
//
//        if (operationLength == 0)
//        {
//            textViewInputNumbers.setText(textViewInputNumbers.getText() + "(");
//            dotUsed = false;
//            openParenthesis++;
//            done = true;
//        } else if (openParenthesis > 0 && operationLength > 0)
//        {
//            String lastInput = textViewInputNumbers.getText().charAt(operationLength - 1) + "";
//            switch (defineLastCharacter(lastInput))
//            {
//                case IS_NUMBER:
//                    textViewInputNumbers.setText(textViewInputNumbers.getText() + ")");
//                    done = true;
//                    openParenthesis--;
//                    dotUsed = false;
//                    break;
//                case IS_OPERAND:
//                    textViewInputNumbers.setText(textViewInputNumbers.getText() + "(");
//                    done = true;
//                    openParenthesis++;
//                    dotUsed = false;
//                    break;
//                case IS_OPEN_PARENTHESIS:
//                    textViewInputNumbers.setText(textViewInputNumbers.getText() + "(");
//                    done = true;
//                    openParenthesis++;
//                    dotUsed = false;
//                    break;
//                case IS_CLOSE_PARENTHESIS:
//                    textViewInputNumbers.setText(textViewInputNumbers.getText() + ")");
//                    done = true;
//                    openParenthesis--;
//                    dotUsed = false;
//                    break;
//            }
//        } else if (openParenthesis == 0 && operationLength > 0)
//        {
//            String lastInput = textViewInputNumbers.getText().charAt(operationLength - 1) + "";
//            if (defineLastCharacter(lastInput) == IS_OPERAND)
//            {
//                textViewInputNumbers.setText(textViewInputNumbers.getText() + "(");
//                done = true;
//                dotUsed = false;
//                openParenthesis++;
//            } else
//            {
//                textViewInputNumbers.setText(textViewInputNumbers.getText() + "x(");
//                done = true;
//                dotUsed = false;
//                openParenthesis++;
//            }
//        }
//        return done;
//    }
//
//    private boolean addOperand(String operand)
//    {
//        boolean done = false;
//        int operationLength = textViewInputNumbers.getText().length();
//        if (operationLength > 0)
//        {
//            String lastInput = textViewInputNumbers.getText().charAt(operationLength - 1) + "";
//
//            if ((lastInput.equals("+") || lastInput.equals("-") || lastInput.equals("*") || lastInput.equals("\u00F7") || lastInput.equals("%")))
//            {
//                Toast.makeText(getApplicationContext(), "Wrong format", Toast.LENGTH_LONG).show();
//            } else if (operand.equals("%") && defineLastCharacter(lastInput) == IS_NUMBER)
//            {
//                textViewInputNumbers.setText(textViewInputNumbers.getText() + operand);
//                dotUsed = false;
//                equalClicked = false;
//                lastExpression = "";
//                done = true;
//            } else if (!operand.equals("%"))
//            {
//                textViewInputNumbers.setText(textViewInputNumbers.getText() + operand);
//                dotUsed = false;
//                equalClicked = false;
//                lastExpression = "";
//                done = true;
//            }
//        } else
//        {
//            Toast.makeText(getApplicationContext(), "Wrong Format. Operand Without any numbers?", Toast.LENGTH_LONG).show();
//        }
//        return done;
//    }
//
//    private boolean addNumber(String number)
//    {
//        boolean done = false;
//        int operationLength = textViewInputNumbers.getText().length();
//        if (operationLength > 0)
//        {
//            String lastCharacter = textViewInputNumbers.getText().charAt(operationLength - 1) + "";
//            int lastCharacterState = defineLastCharacter(lastCharacter);
//
//            if (operationLength == 1 && lastCharacterState == IS_NUMBER && lastCharacter.equals("0"))
//            {
//                textViewInputNumbers.setText(number);
//                done = true;
//            } else if (lastCharacterState == IS_OPEN_PARENTHESIS)
//            {
//                textViewInputNumbers.setText(textViewInputNumbers.getText() + number);
//                done = true;
//            } else if (lastCharacterState == IS_CLOSE_PARENTHESIS || lastCharacter.equals("%"))
//            {
//                textViewInputNumbers.setText(textViewInputNumbers.getText() + "x" + number);
//                done = true;
//            } else if (lastCharacterState == IS_NUMBER || lastCharacterState == IS_OPERAND || lastCharacterState == IS_DOT)
//            {
//                textViewInputNumbers.setText(textViewInputNumbers.getText() + number);
//                done = true;
//            }
//        } else
//        {
//            textViewInputNumbers.setText(textViewInputNumbers.getText() + number);
//            done = true;
//        }
//        return done;
//    }
//
//
//    private void calculate(String input)
//    {
//        String result = "";
//        try
//        {
//            String temp = input;
//            if (equalClicked)
//            {
//                temp = input + lastExpression;
//            } else
//            {
//                saveLastExpression(input);
//            }
//            result = scriptEngine.eval(temp.replaceAll("%", "/100").replaceAll("x", "*").replaceAll("[^\\x00-\\x7F]", "/")).toString();
//            BigDecimal decimal = new BigDecimal(result);
//            result = decimal.setScale(8, BigDecimal.ROUND_HALF_UP).toPlainString();
//            equalClicked = true;
//
//        } catch (Exception e)
//        {
//            Toast.makeText(getApplicationContext(), "Wrong Format", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        if (result.equals("Infinity"))
//        {
//            Toast.makeText(getApplicationContext(), "Division by zero is not allowed", Toast.LENGTH_SHORT).show();
//            textViewInputNumbers.setText(input);
//
//        } else if (result.contains("."))
//        {
//            result = result.replaceAll("\\.?0*$", "");
//            textViewInputNumbers.setText(result);
//        }
//    }
//
//    private void saveLastExpression(String input)
//    {
//        String lastOfExpression = input.charAt(input.length() - 1) + "";
//        if (input.length() > 1)
//        {
//            if (lastOfExpression.equals(")"))
//            {
//                lastExpression = ")";
//                int numberOfCloseParenthesis = 1;
//
//                for (int i = input.length() - 2; i >= 0; i--)
//                {
//                    if (numberOfCloseParenthesis > 0)
//                    {
//                        String last = input.charAt(i) + "";
//                        if (last.equals(")"))
//                        {
//                            numberOfCloseParenthesis++;
//                        } else if (last.equals("("))
//                        {
//                            numberOfCloseParenthesis--;
//                        }
//                        lastExpression = last + lastExpression;
//                    } else if (defineLastCharacter(input.charAt(i) + "") == IS_OPERAND)
//                    {
//                        lastExpression = input.charAt(i) + lastExpression;
//                        break;
//                    } else
//                    {
//                        lastExpression = "";
//                    }
//                }
//            } else if (defineLastCharacter(lastOfExpression + "") == IS_NUMBER)
//            {
//                lastExpression = lastOfExpression;
//                for (int i = input.length() - 2; i >= 0; i--)
//                {
//                    String last = input.charAt(i) + "";
//                    if (defineLastCharacter(last) == IS_NUMBER || defineLastCharacter(last) == IS_DOT)
//                    {
//                        lastExpression = last + lastExpression;
//                    } else if (defineLastCharacter(last) == IS_OPERAND)
//                    {
//                        lastExpression = last + lastExpression;
//                        break;
//                    }
//                    if (i == 0)
//                    {
//                        lastExpression = "";
//                    }
//                }
//            }
//        }
//    }
//
//    private int defineLastCharacter(String lastCharacter)
//    {
//        try
//        {
//            Integer.parseInt(lastCharacter);
//            return IS_NUMBER;
//        } catch (NumberFormatException e)
//        {
//        }
//
//        if ((lastCharacter.equals("+") || lastCharacter.equals("-") || lastCharacter.equals("x") || lastCharacter.equals("\u00F7") || lastCharacter.equals("%")))
//            return IS_OPERAND;
//
//        if (lastCharacter.equals("("))
//            return IS_OPEN_PARENTHESIS;
//
//        if (lastCharacter.equals(")"))
//            return IS_CLOSE_PARENTHESIS;
//
//        if (lastCharacter.equals("."))
//            return IS_DOT;
//
//        return -1;
//    }
//
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        startActivity(new Intent(this,MainActivity.class));
//        finish();
//    }
//}
