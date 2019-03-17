package com.bitirme.gitbusters.borkinroads.uiactivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.akexorcist.googledirection.model.Line;
import com.bitirme.gitbusters.borkinroads.R;
import com.bitirme.gitbusters.borkinroads.data.DoggoRecord;
import com.bitirme.gitbusters.borkinroads.data.RestRecordImpl;
import com.bitirme.gitbusters.borkinroads.data.UserRecord;
import com.bitirme.gitbusters.borkinroads.dbinterface.RestPuller;

import java.util.ArrayList;

public class FriendListActivity extends AppCompatActivity {

  private static final boolean SANDBOX = false;

  private ScrollView   sv;
  private EditText     et;

  private ArrayList<UserRecord> allUsers;
  private ArrayList<UserRecord> friends;
  private LinearLayout ll;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_friend_list);

    // Initialize UI elements
    sv = findViewById(R.id.scroll_view);
    et = findViewById(R.id.edit_text);
    et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
      @Override
      public void onFocusChange(View v, boolean hasFocus) {
      }
    });

    et.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {}

      @Override
      public void afterTextChanged(Editable s) {

      }
    });

    allUsers = new ArrayList<>();
    if(!SANDBOX)
    {
      // Initialize user list
      // TODO potential buggy behavior
      RestPuller rp = new RestPuller(new UserRecord(),getApplicationContext());
      rp.start();
      try {
        rp.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      for (RestRecordImpl rri : rp.getFetchedRecords())
        allUsers.add((UserRecord) rri);
    }else
    {
      ArrayList<Integer> petIDs = new ArrayList<>();
      petIDs.add(1);
      petIDs.add(3);
//      allUsers.add(new UserRecord("Ataberk", -1, petIDs)); //TODO: give paths if neccesary
    }

    // Display user's friends on the scrollview layout
    ll = findViewById(R.id.linear_layout);
    // displayUsersOnScreen(allUsers);
  }

  private void searchUser()
  {

  }


  private void displayUsersOnScreen(ArrayList<UserRecord> users)
  {
    for(UserRecord ur : allUsers)
    {
      LinearLayout newLL = new LinearLayout(this);
      TextView tv = new TextView(this);
      String petText = "";
      for(DoggoRecord dr : ur.getPets())
        petText += dr.getName() + " ";
      tv.setText(ur.getName()+ ": " + petText);
      newLL.addView(tv);
      Button rmButton = new Button(this);
      rmButton.setText("-");
      newLL.addView(rmButton);
      ll.addView(newLL);
    }
  }

}
