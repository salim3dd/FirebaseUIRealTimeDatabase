package com.salim3dd.chatroom;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AlertDialog dialog = new AlertDialog.Builder(this).create();
        final EditText editText = new EditText(this);
        dialog.setTitle("Enter Your Name");
        dialog.setView(editText);
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = editText.getText().toString();
                if (!TextUtils.isEmpty(name)) {
                    Intent intent = new Intent(MainActivity.this, ChateRoom.class);
                    intent.putExtra("Name", name);
                    startActivity(intent);
                }else{
                    Toast.makeText(MainActivity.this, "Enter Your Name", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }
}
