package com.adityasharat.simpleproteusdemo;

import android.os.Bundle;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.flipkart.android.proteus.Proteus;
import com.flipkart.android.proteus.ProteusBuilder;
import com.flipkart.android.proteus.ProteusContext;
import com.flipkart.android.proteus.ProteusLayoutInflater;
import com.flipkart.android.proteus.ProteusView;
import com.flipkart.android.proteus.gson.ProteusTypeAdapterFactory;
import com.flipkart.android.proteus.value.Layout;
import com.flipkart.android.proteus.value.ObjectValue;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.StringReader;

public class MainActivity extends AppCompatActivity {

  private static final String LAYOUT = "{\n" +
    "  \"type\": \"TextView\",\n" +
    "  \"textSize\": \"28sp\",\n" +
    "  \"text\": \"Hello World!\"\n" +
    "}";
  private static final String DATA = "{}";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    ViewGroup container = findViewById(R.id.container);

    // create a new instance of proteus
    Proteus proteus = new ProteusBuilder().build();

    // register proteus with a ProteusTypeAdapterFactory to deserialize proteus jsons
    ProteusTypeAdapterFactory adapter = new ProteusTypeAdapterFactory(this);
    ProteusTypeAdapterFactory.PROTEUS_INSTANCE_HOLDER.setProteus(proteus);

    // deserialize layout and data
    Layout layout;
    ObjectValue data;
    try {
      layout = adapter.LAYOUT_TYPE_ADAPTER.read(new JsonReader(new StringReader(LAYOUT)));
      data = adapter.OBJECT_TYPE_ADAPTER.read(new JsonReader(new StringReader(DATA)));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    // create a new ProteusLayoutInflater
    ProteusContext context = proteus.createContextBuilder(this).build();
    ProteusLayoutInflater inflater = context.getInflater();

    // Inflate the layout
    ProteusView view = inflater.inflate(layout, data, container, 0);

    // Add the inflated layout into the container
    container.addView(view.getAsView());
  }
}
