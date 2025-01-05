package com.example.calculation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private ImageView historyBtn, saveBtn;
    private EditText result, newNumber;
    private Button buttonAdd, buttonMinus, buttonMultiply, buttonDevide, buttonEquals, buttonDot, negSymbol;
    private Button[] numberButtons;
    private Button clearText, buttonBracket, percentage;

    private double operand1 = Double.NaN;
    private double operand2;
    private String pendingOperation = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializing EditTexts
        result = findViewById(R.id.result);
        newNumber = findViewById(R.id.newNumber);

        // Initializing Operation Buttons
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonMinus = findViewById(R.id.buttonMinus);
        buttonMultiply = findViewById(R.id.buttonMultiply);
        buttonDevide = findViewById(R.id.buttonDevide);
        buttonEquals = findViewById(R.id.buttonEquals);
        buttonDot = findViewById(R.id.buttonDot);
        negSymbol = findViewById(R.id.negSymobol);
        historyBtn = findViewById(R.id.historyBtn);
        saveBtn = findViewById(R.id.saveBtn);

        // Initializing Number Buttons
        numberButtons = new Button[]{
                findViewById(R.id.button0),
                findViewById(R.id.button1),
                findViewById(R.id.button2),
                findViewById(R.id.button3),
                findViewById(R.id.button4),
                findViewById(R.id.button5),
                findViewById(R.id.button6),
                findViewById(R.id.button7),
                findViewById(R.id.button8),
                findViewById(R.id.button9)
        };

        // Initializing Special Buttons
        clearText = findViewById(R.id.clearText);
        buttonBracket = findViewById(R.id.buttonBracket);
        percentage = findViewById(R.id.percentage);

        // Setting up number button listeners
        for (Button button : numberButtons) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Button btn = (Button) view;
                    newNumber.append(btn.getText().toString());
                }
            });
        }

        // Setting up operation button listeners
        View.OnClickListener operationListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button btn = (Button) view;
                String op = btn.getText().toString();
                performOperation(op);
            }
        };

        buttonAdd.setOnClickListener(operationListener);
        buttonMinus.setOnClickListener(operationListener);
        buttonMultiply.setOnClickListener(operationListener);
        buttonDevide.setOnClickListener(operationListener);

        // Equals button
        buttonEquals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performOperation(pendingOperation);
                pendingOperation = "";
            }
        });

        //Save Btn
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Record Saved", Toast.LENGTH_LONG).show();
            }
        });
        //Records Saved
        historyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Records.class);
                startActivity(intent);
            }
        });

        // Clear button
        clearText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result.setText("");
                newNumber.setText("");
                operand1 = Double.NaN;
                pendingOperation = "";
            }
        });

        // Negation button
        negSymbol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value = newNumber.getText().toString();
                if (value.isEmpty()) return;
                if (value.startsWith("-")) {
                    newNumber.setText(value.substring(1));
                } else {
                    newNumber.setText("-" + value);
                }
            }
        });

        // Dot button
        buttonDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value = newNumber.getText().toString();
                if (!value.contains(".")) {
                    newNumber.append(".");
                }
            }
        });
    }

    private void performOperation(String operation) {
        String newNumberStr = newNumber.getText().toString();
        if (!newNumberStr.isEmpty()) {
            try {
                operand2 = Double.parseDouble(newNumberStr);
                if (!Double.isNaN(operand1)) {
                    switch (pendingOperation) {
                        case "+":
                            operand1 += operand2;
                            break;
                        case "-":
                            operand1 -= operand2;
                            break;
                        case "×":
                            operand1 *= operand2;
                            break;
                        case "÷":
                            if (operand2 != 0) {
                                operand1 /= operand2;
                            } else {
                                result.setText("Error");
                                return;
                            }
                            break;
                        case "%":
                            operand1 = operand1 % operand2;
                            break;
                    }
                } else {
                    operand1 = operand2;
                }
                result.setText(String.valueOf(operand1));
                newNumber.setText("");
            } catch (NumberFormatException e) {
                newNumber.setText("");
            }
        }
        pendingOperation = operation;
    }
}
