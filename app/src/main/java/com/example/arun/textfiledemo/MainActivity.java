package com.example.arun.textfiledemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    EditText etname,etsurname;
    Button btnaddText,btnaddToFile;
    TextView tvresult;

    ArrayList <Person> persons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etname = findViewById(R.id.etName);
        etsurname = findViewById(R.id.etSurname);
        btnaddText = findViewById(R.id.btnAdd);
        btnaddToFile = findViewById(R.id.btnAddToFile);
        tvresult = findViewById(R.id.tvResult);

        loadData();

        persons = new ArrayList <Person>();


        btnaddText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = etname.getText().toString();
                String surname = etsurname.getText().toString();

                Person  person= new Person(name,surname);
                persons.add(person);

                setTextToTextView();


            }
        });


        btnaddToFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try
                {
                    FileOutputStream file = openFileOutput("Data.txt",MODE_PRIVATE);
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(file);

                    for(int i=0;i<persons.size();i++)
                    {
                        outputStreamWriter.write(persons.get(i).getName()+ "," + persons.get(i).getSurname() +"\n" );
                    }

                    outputStreamWriter.flush();
                    outputStreamWriter.close();

                    Toast.makeText(MainActivity.this,"Successfully Saved!",Toast.LENGTH_SHORT).show();
                }
                catch(IOException e)
                {
                    Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                }

            }
        });




    }
    

    private void loadData() {

       persons.clear();

        File file = getApplicationContext().getFileStreamPath("Data.txt");
        String lineFromFile;
        if(file.exists())
        {
            try
            {
                BufferedReader reader = new BufferedReader(new InputStreamReader(openFileInput("Data.txt")));
                while ((lineFromFile = reader.readLine()) != null)
                {
                    StringTokenizer tokenizer = new StringTokenizer(lineFromFile,",");
                    Person person = new Person(tokenizer.nextToken(),tokenizer.nextToken());
                    persons.add(person);
                }
                reader.close();
                setTextToTextView();

            }catch (IOException e)
            {
                Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setTextToTextView() {

        String text="";
        for(int i=0; i<persons.size();i++)
        {
            text = text + persons.get(i).getName() + " " + persons.get(i).getSurname() + "\n";
        }

        tvresult.setText(text);
    }


}
