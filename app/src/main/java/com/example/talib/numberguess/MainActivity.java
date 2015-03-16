package com.example.talib.numberguess;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;


public class MainActivity extends ActionBarActivity {

    int myNumber;
    int userNumber, guessCount=0;//aby wartośc na starcie była równa 0
    TextView textView; //deklarujemy jak w c na początku
    TextView textView3;
    SharedPreferences sharedPreferences; //deklarujemy obiekt sharedPreferences
    SharedPreferences.Editor editor; //pozwala nam na zapisywanie wartości w shared preferences

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


         textView = (TextView)findViewById(R.id.textView2); //podczas uruchamiania aplikacji ma się wywoływać
         textView3 = (TextView)findViewById(R.id.textView3);
         sharedPreferences = getSharedPreferences("com.example.talib.numberguess", Context.MODE_PRIVATE); //pobiera sharedpreferences, pobuera 2 argumenty
         editor = sharedPreferences.edit(); //ten editor edytuje sharedprfwerences

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void newGame(View view) {
        Random rand = new Random ();//tworzenie owych obiektów random
        myNumber = rand.nextInt((100-0)+1)+0; //((górna granica-dolna granica)+1)+dolna granica
        guessCount = 0; //zerujemy aby po nowej grze się zerowało
        textView.setText("Time guessed: " + guessCount); //wpisujemy, aby za kazdym razem się nie jebać i żeby na początku zawsze było 0
        textView3.setText("Best score: " + sharedPreferences.getInt("BestScore",100));
        //textView3.setText("Best Score: " + sharedPreferences);
    }

    public void takeTheGuess(View view) {
        guessCount++; //zwiększamy o 1

        EditText editText = (EditText)findViewById(R.id.editText); // pobieramy wartośc z pola do wpisywania tekstu
        userNumber = Integer.parseInt(editText.getText().toString()); // zmiennej userNumber przypisujemy wartość z pola wyżej (string) i zamieniamy na integer ze względu na różnice typów
        String message = ""; //wywala chmurkę z informacją o liczbie//pusty string, zeby toast się nie czepiał

        if(userNumber>myNumber){
            message = "My number is less than yours";
        }
        else if (userNumber<myNumber){
            message =  "My number is bigger than yours";
        }
        else if (userNumber==myNumber){
            message = "Congrats! You guessed my number!";

            if (guessCount < sharedPreferences.getInt("BestScore", 100)) {
                editor.putInt("BestScore", guessCount);
                editor.commit();

            }

        }

        Context context = getApplicationContext(); //pobiera to co się dieje w plikacji aktualnie
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, message, duration);//potrzebuje 3 wartości: context, wiadmości i czasu
        toast.show(); //pokazuje toast
        textView3.setText("Best score: " + sharedPreferences.getInt("BestScore",100));
        textView.setText("Time guessed: " + guessCount); //wypisujemy ile razy już zgadywaliśmy

    }
}
