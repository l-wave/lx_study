package com.example.lx;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class InputBookActivity extends AppCompatActivity {

    public static final int RESULT_CODE_SUCCESS = 666;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_edit);

        position= this.getIntent().getIntExtra("position",0);
        String title=this.getIntent().getStringExtra("title");
        String author=this.getIntent().getStringExtra("authors");
        String translator=this.getIntent().getStringExtra("translators");
        String publisher=this.getIntent().getStringExtra("publisher");
        String isbn=this.getIntent().getStringExtra("isbn");
        String pubTime=this.getIntent().getStringExtra("pubTime");


        EditText editTextTitle=findViewById(R.id.book_title_edit_text);
        EditText editTextauthor=findViewById(R.id.book_author_edit_text);
        EditText editTexttranslator=findViewById(R.id.book_translator_edit_text);
        EditText editTextpublisher=findViewById(R.id.book_publisher_edit_text);
        EditText editTextisbn=findViewById(R.id.book_isbn_edit_text);
        EditText editTextpubTime=findViewById(R.id.book_pubyear_edit_text);


        if(null!=title)
        {
            editTextTitle.setText(title);
            editTextauthor.setText(author);
            editTexttranslator.setText(translator);
            editTextpublisher.setText(publisher);
            editTextisbn.setText(isbn);
            editTextpubTime.setText(pubTime);
        }


        Button buttonOk=findViewById(R.id.button_ok);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                Bundle bundle=new Bundle();
                bundle.putString("title",editTextTitle.getText().toString());
                bundle.putString("author",editTextauthor.getText().toString());
                bundle.putString("translator",editTexttranslator.getText().toString());
                bundle.putString("publisher",editTextpublisher.getText().toString());
                bundle.putString("isbn",editTextisbn.getText().toString());
                bundle.putString("isbn",editTextpubTime.getText().toString());
                bundle.putInt("position",position);

                intent.putExtras(bundle);
                setResult(RESULT_CODE_SUCCESS,intent);
                InputBookActivity.this.finish();
            }
        });

        Button buttoncancel=findViewById(R.id.button_cancel);
        buttoncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputBookActivity.this.finish();
            }
        });
    }
}