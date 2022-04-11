package ro.pub.cs.systems.eim.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class PracticalTest01Var06SecondaryActivity extends AppCompatActivity {
    Integer points = 0;
    ButtonHandler buttonHandler = new ButtonHandler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var06_secondary);
        Intent intent = getIntent();
        String firstNumber = intent.getExtras().getString("firstNumber");
        String secondNumber = intent.getExtras().getString("secondNumber");
        String thirdNumber = intent.getExtras().getString("thirdNumber");
        List<String> numbers = Arrays.asList(firstNumber, secondNumber, thirdNumber);
        TextView textView = findViewById(R.id.gainedText);
        String point = "0";
        long count = numbers.stream().filter(n -> n.equals("*")).count();
        if(count == 2) {
            point = "10";
            textView.setText("Gained" + point);
        }
        if( count == 0 ) {
            if(firstNumber.equals(secondNumber) && secondNumber.equals(thirdNumber)) {
                point = "100";
                textView.setText("Gained" + point);

            }
        }
        if(count == 1) {
            if(firstNumber.equals(secondNumber) || firstNumber.equals(thirdNumber) || secondNumber.equals(thirdNumber)) {
                point = "50";
                textView.setText("Gained" + point);
            }
        }

        points = Integer.valueOf(point);
        findViewById(R.id.okButton).setOnClickListener(buttonHandler);
    }

    private class ButtonHandler implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Intent newIntent = new Intent();
            newIntent.putExtra("scor", points);
            setResult(200, newIntent);
            finish();
        }
    }
}