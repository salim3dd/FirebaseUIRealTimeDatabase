package com.salim3dd.chatroom;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ChateRoom extends AppCompatActivity {

    private DatabaseReference root;
    private String temp_key;
    private Button btn_send_msg;
    private EditText input_msg;
    private ListView listView_chat;
    ArrayList<String> list_chat = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chate_room);

        root = FirebaseDatabase.getInstance().getReference().child("MainChatRoom");

        listView_chat = (ListView) findViewById(R.id.listView_chat);
        input_msg = (EditText) findViewById(R.id.input_msg);
        btn_send_msg = (Button) findViewById(R.id.btn_send_msg);

        name = getIntent().getExtras().getString("Name");

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list_chat);
        listView_chat.setAdapter(arrayAdapter);

        btn_send_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Map<String, Object> map = new HashMap<String, Object>();
                temp_key = root.push().getKey();
                root.updateChildren(map);

                DatabaseReference message_root = root.child(temp_key);
                Map<String, Object> map2 = new HashMap<String, Object>();
                map2.put("name", name);
                map2.put("msg", input_msg.getText().toString());

                message_root.updateChildren(map2);
            }
        });


        root.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Add_Chat(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Add_Chat(dataSnapshot);

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    private String chat_msg, chat_user_name;

    private void Add_Chat(DataSnapshot dataSnapshot) {

        Iterator i = dataSnapshot.getChildren().iterator();
        input_msg.setText("");
        while (i.hasNext()) {

            chat_msg = (String) ((DataSnapshot) i.next()).getValue();
            chat_user_name = (String) ((DataSnapshot) i.next()).getValue();

            list_chat.add(chat_user_name + " : " + chat_msg);
            arrayAdapter.notifyDataSetChanged();
            listView_chat.setSelection(list_chat.size());
        }


    }
}
