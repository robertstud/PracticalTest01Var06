package ro.pub.cs.systems.eim.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ButtonHandler buttonHandler = new ButtonHandler();
    private CheckBox firstNumberCheckBox;
    private CheckBox secondNumberCheckBox;
    private CheckBox thirdNumberCheckBox;
    private EditText firstNumberText;
    private EditText secondNumberText;
    private EditText thirdNumberText;
    private Integer scor;
   private Boolean wasScoreEdited = false;
    private IntentFilter intentFilter = new IntentFilter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.play).setOnClickListener(buttonHandler);
        firstNumberCheckBox = ((CheckBox) findViewById(R.id.checkBoxNr1));
        secondNumberCheckBox = ((CheckBox) findViewById(R.id.checkBoxNr2));
        thirdNumberCheckBox = ((CheckBox) findViewById(R.id.checkBoxNr3));

        firstNumberText = findViewById(R.id.textNr1);
        secondNumberText = findViewById(R.id.textNr2);
        thirdNumberText = findViewById(R.id.textNr3);
        scor = 0;
        intentFilter.addAction("test");
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                wasScoreEdited = true;
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        };
        firstNumberText.addTextChangedListener(textWatcher);
        secondNumberText.addTextChangedListener(textWatcher);
        thirdNumberText.addTextChangedListener(textWatcher);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState.containsKey("score")) {
            Integer total = savedInstanceState.getInt("score");
            scor+= total;
            Toast.makeText(getApplicationContext(), "Scorul este: " + scor, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(getApplicationContext(), "Victory", Toast.LENGTH_LONG).show();

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(getApplicationContext(), PracticalTest01Var06Service.class);
        getApplicationContext().stopService(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==200)
        {
            Integer total = data.getExtras().getInt("scor");
            Integer beforeScore = scor;
            scor+= total;
            Toast.makeText(getApplicationContext(), "Scorul este: " + scor, Toast.LENGTH_LONG).show();
            if( scor >= 10) {
                Intent intent = new Intent(getApplicationContext(), PracticalTest01Var06Service.class);
                if(beforeScore < 10 ) {
                    getApplicationContext().startService(intent);
                }
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("score", scor);
    }

    private class ButtonHandler implements View.OnClickListener {

        @Override
        public void onClick(View view) {
//            if(wasScoreEdited) {
//                Toast.makeText(getApplicationContext(), "Scorul este: " + scor, Toast.LENGTH_LONG).show();
//                return;
//            }
            String first = setFieldValue(firstNumberCheckBox, firstNumberText);
            String second = setFieldValue(secondNumberCheckBox, secondNumberText);
            String third = setFieldValue(thirdNumberCheckBox, thirdNumberText);
            Toast.makeText(getApplicationContext(), "Valorile sunt: " + first + " " + second + " " + third, Toast.LENGTH_LONG).show();

            Intent intent = new Intent(MainActivity.this, PracticalTest01Var06SecondaryActivity.class);
            intent.putExtra("firstNumber", first);
            intent.putExtra("secondNumber", second);
            intent.putExtra("thirdNumber", third);
            MainActivity.this.startActivityForResult(intent, 200);
        }

        private String setFieldValue(CheckBox checkBox, EditText text) {
            String toBeWritten = "*";
            if(!checkBox.isChecked()) {
                toBeWritten = generateRandomNumber().toString();
            }
            text.setText(toBeWritten);
            return toBeWritten;
        }

        private Integer generateRandomNumber() {
            Random rn = new Random();
            return rn.nextInt(3 - 1 + 1) + 1;
        }

    }


}