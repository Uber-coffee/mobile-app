package com.example.ubercoffee;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableString;
import android.view.View;
import android.widget.AdapterView;;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderActivity extends AppCompatActivity {

    private static final int DIALOG_GOOD_ENTRY = 1;
    private GridView gridView;

    private ImageButton bMinus;
    private ImageButton bPlus;
    private ImageButton bDelete;

    private TextView textTotal;
    private Button bSubmit;

    private TextView tvMins;
    private double mins = 15;
    private double totalPrice = 0;

    private TextView text_quantity;
    private TextView text_price;

    private String phone;

    /*int count_of_order = 0;
    HashMap<Integer,LinearLayout> layouts_with_ingredients = new HashMap<Integer, LinearLayout>();
    HashMap<Integer, LinearLayout> layouts_with_steps= new HashMap<Integer, LinearLayout>();
    HashMap<Integer,AutoCompleteTextView> list_with_names_ingredients = new HashMap<Integer,AutoCompleteTextView>();
    HashMap<Integer, EditText> list_with_names_steps = new HashMap<Integer,EditText>();*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        phone = getIntent().getStringExtra("phone_number");

        SharedPreferences order_saving = getSharedPreferences("com.example.app", Context.MODE_PRIVATE);
        Map<String, ?> allEntries = order_saving.getAll();
        List<Order> list = new ArrayList<>();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            Gson gson = new Gson();
            String json = entry.getValue().toString();
            Order obj = gson.fromJson(json, Order.class);
            list.add(obj);
        }
        //double size1 = 0.2, size2 = 0.4;
        //int count1 = 1, count2 = 2;
        //double price1 = 120, price2 = 140;
      /* Order order = new Order("Cappuccino", 0.2, 1, 120);
        list.add(order);
        list.add(order);
        list.add(order);
        list.add(order);
        list.add(order);
        order = new Order("Latte", 0.4, 2,140);
        list.add(order);
        list.add(order);
        list.add(order);
        list.add(order);
        list.add(order);
        //order = new Order("Latte", 0.4, 2,140);
        list.add(order);
        //HARDCOOOOODE*/

        for(int i = 0; i < list.size(); i++){
            Order item = list.get(i);
            totalPrice += item.getPriceDrinkables() * item.getCountDrinkables();
        }



        tvMins = findViewById(R.id.textMinutes);
        final SpannableString textMin=  new SpannableString("in " + mins + " mins");
        tvMins.setText(textMin);

        textTotal = findViewById(R.id.total);
        final SpannableString total=  new SpannableString(totalPrice + "₽");
        textTotal.append(total);


        bSubmit = findViewById(R.id.submit);


        bSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext(), R.style.AlertDialogTheme);
                builder.setTitle("Thank you!");
                builder.setMessage("We wait you again!");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Ð—Ð°ÐºÑ€Ñ‹Ð²Ð°ÐµÐ¼ Ð¾ÐºÐ½Ð¾
                        dialog.cancel();
                        SharedPreferences order_saving = getSharedPreferences("com.example.app", Context.MODE_PRIVATE);
                        SharedPreferences.Editor ed = order_saving.edit();
                        ed.clear().apply();
                        Intent intent = new Intent(OrderActivity.this, ListOfShopsActivity.class);
                        intent.putExtra("phone_number", phone);
                        startActivity(intent);
                    }
                });

                final Dialog dlg = builder.create();

                dlg.show();

            }
        });


        gridView = findViewById(R.id.gridListOrder);
        GridLoad(gridView, list);

        // When the user clicks on the GridItem
        // When the user clicks on the GridItem
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = gridView.getItemAtPosition(position);
                final Order order = (Order) o;
                //?????????????????????????????????????
                bMinus = (ImageButton) findViewById(R.id.minus);
                bPlus = (ImageButton) findViewById(R.id.plus);
                bDelete = (ImageButton) findViewById(R.id.delete);

                bMinus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int count = order.getCountDrinkables() - 1;
                        if (count < 0)
                            count = 0;
                        order.setCountDrinkables(count);
                    }
                });

                bPlus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int count = order.getCountDrinkables() + 1;
                        order.setCountDrinkables(count);
                    }
                });

                bDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });


                Toast.makeText(OrderActivity.this, "Selected :"
                        + " " + order, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void GridLoad(GridView gridView, List<Order> list){
        //List<Order> details = new ArrayList<>();
        //details = getListData();
        gridView.setAdapter(new OrderAdapter(this, list));
    }

    /*private  List<Order> getListData() {
        List<Order> list = new ArrayList<>();
        Order Cappuccino = new Order("Cappuccino", 0.2, 1, 120);
        list.add(Cappuccino);
        Order Latte = new Order("Latte", 0.4, 2,140);
        list.add(Latte);
        return list;
    }*/

    /*private void createOrder(){
        ImageButton DefaultBrushBt = new ImageButton(this);
        DefaultBrushBt.setImageResource(R.drawable.delete);
        DefaultBrushBt.setBackgroundColor(Color.parseColor("@drawable/rounded_textview_background"));
        //DefaultBrushBt.setLayoutParams(lp_for_nested_view_components2);
        DefaultBrushBt.setId(count_of_order);
        DefaultBrushBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gridView.removeView(layouts_with_ingredients.get(v.getId()));
                layouts_with_ingredients.remove(v.getId());
                list_with_names_ingredients.remove(v.getId());
            }
        });
        AutoCompleteTextView defIngr = new AutoCompleteTextView(this);
        if(num_of_theme == 2) {
            defIngr.setHintTextColor(getResources().getColor(R.color.my_textColorPrimary));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,ingredients_list_from_db);
        defIngr.setAdapter(adapter);
        //defIngr.setLayoutParams(lp_for_nested_view_components1);
        defIngr.setId(count_of_order);
        defIngr.setHint("Введите название ингредиента");
        list_with_names_ingredients.put(defIngr.getId(),defIngr);
        LinearLayout nested_for_ingredients = new LinearLayout(this);
        //nested_for_ingredients.setLayoutParams(lp_for_nested_layout);
        nested_for_ingredients.setOrientation(LinearLayout.HORIZONTAL);
        nested_for_ingredients.setId(count_of_order);
        nested_for_ingredients.addView(defIngr);
        nested_for_ingredients.addView(DefaultBrushBt);
        gridView.addView(nested_for_ingredients);
        count_of_order++;
        layouts_with_ingredients.put(nested_for_ingredients.getId(),nested_for_ingredients);
    }*/
}

