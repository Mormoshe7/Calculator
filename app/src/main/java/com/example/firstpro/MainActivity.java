
package com.example.firstpro;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private TextView result;
    double firstNumber;
    double secondNumber;
    double res=0;
    char ch;
    boolean isResultDisplayed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        result = findViewById(R.id.textView);
        result.setText("");
    }

    public void funcNum(View view) {
        if (isResultDisplayed) return;
        Button button = (Button) view;
        String input = button.getText().toString();
        if (input.equals(".")) {
            if (result.getText().toString().isEmpty() ||
                    !Character.isDigit(result.getText().toString().charAt(result.getText().length() - 1))) {
                result.append("0");
            }
        }
        result.append(input);
    }

    public void funcAction(View view) {
        if (isResultDisplayed) return;
        if (result.getText().toString().isEmpty()) {
            return;
        }

        char newOp = ((Button) view).getText().toString().charAt(0);
        String[] parts = result.getText().toString().split(" ");

        if (parts.length == 2) {
            String updatedResult = parts[0] + " " + newOp + " ";
            result.setText(updatedResult);
            ch = newOp;
        } else if (parts.length == 1) {
            ch = newOp;
            result.append(" " + ch + " ");
            firstNumber = Double.parseDouble(parts[0]);
        }
    }


    public void funcEqual(View view) {
        if (result.getText().toString().isEmpty() || ch == '\0') {
            result.setText("Error");
            return;
        }

        String[] parts = result.getText().toString().split(" ");
        if (parts.length < 3) {
            result.setText("Error");
            return;
        }

        try {
            secondNumber = Double.parseDouble(parts[2]);
        } catch (NumberFormatException e) {
            result.setText("Error");
            return;
        }

        res = 0;

        switch (ch) {
            case '+':
                res = firstNumber + secondNumber;
                break;
            case '-':
                res = firstNumber - secondNumber;
                break;
            case '*':
                res = firstNumber * secondNumber;
                break;
            case '/':
                if (secondNumber != 0) {
                    res = firstNumber / secondNumber;
                } else {
                    res = Double.NaN;
                }
                break;
            case '%':
                res = firstNumber % secondNumber;
                break;
            default:
                result.setText("Error");
                return;
        }
        String formattedResult;
        if (res == (int) res) {
            formattedResult = String.valueOf((int) res);
        } else {
            formattedResult = String.valueOf(res);
        }
        result.append(" = " + formattedResult);
        isResultDisplayed = true;
    }

    public void funcDelete(View view) {
        result.setText("");
        firstNumber=0;
        secondNumber=0;
        res = 0;
        isResultDisplayed = false;
    }
}


