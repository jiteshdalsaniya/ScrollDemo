package com.scrolldemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    private ListView listview;
    private TextView title;
    private HorizontalScrollView scroll;
    private Button btnNext, btnPrevious;
    private ArrayList<String> data;
    ArrayAdapter<String> sd;

    public int TOTAL_LIST_ITEMS = 50;
    public int NUM_ITEMS_PAGE   = 10;
    private int noOfBtns;
    private TextView[] btns;
    int pageCount = 0;
    LinearLayout ll;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ll = (LinearLayout)findViewById(R.id.btnLay);
        scroll = (HorizontalScrollView)findViewById(R.id.scroll);
        btnNext = (Button)findViewById(R.id.btnNextGallery);
        btnPrevious = (Button)findViewById(R.id.btnPreviousGallery);
        listview = (ListView)findViewById(R.id.list);
        title    = (TextView)findViewById(R.id.title);

        Btnfooter();

        data = new ArrayList<String>();

        /**
         * The ArrayList data contains all the list items
         */
        for(int i=0;i<TOTAL_LIST_ITEMS;i++)
        {
            data.add("This is Item "+(i+1));
        }

        loadList(0);

        checkBackground(0);
        int childCount = ll.getChildCount();
        int count = 0;
        for(int i = 0; i < childCount; i++) {
            if(ll.getChildAt(i).getVisibility() == View.VISIBLE) {
                count++;
            }
        }
        Log.e(TAG, "onCreate: Visible Count : " + count);
        // Next Button go through availabel pages.
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.e(TAG, "onClick: btnNext pageCount " + pageCount);
//                Log.e(TAG, "onClick: btnNext numberOfButtons " + numberOfButtons);
                if (pageCount < noOfBtns) {
                    pageCount = pageCount + 1;
                    loadList(pageCount);
                    checkBackground(pageCount);
                }
//                Log.e(TAG, "onClick: Visible Items : " + scroll.getVis);
//                Rect scrollBounds = new Rect();
//                ll.getHitRect(scrollBounds);
//                Log.e(TAG, "onCreate: scrollBounds Top : " + scrollBounds.top + "bottom : " + scrollBounds.bottom
//                        + "right : " + scrollBounds.right + "left : " + scrollBounds.left);
                if(pageCount >= 5) {
                    Log.e(TAG, "onClick: scroll.getScrollX() B " + scroll.getScrollX());
                    scroll.scrollTo(scroll.getScrollX() + 150, 0);
                    Log.e(TAG, "onClick: scroll.getScrollX() A " + scroll.getScrollX());
                }
            }
        });


        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.e(TAG, "onClick: btnPrevious pageCount " + pageCount);
                if (pageCount >= 1) {
                    pageCount = pageCount - 1;
                    loadList(pageCount);
                    checkBackground(pageCount);
                }
                if(pageCount <= ll.getChildCount() - 5){
                    scroll.scrollTo(scroll.getScrollX() - 150,0);
                }
            }
        });

        Log.e(TAG, "onCreate: Child Count " + ll.getChildCount());
//        Log.e(TAG, "onCreate: Child getFocusedChild " + ll.get);
    }

    private void Btnfooter()
    {
        int val = TOTAL_LIST_ITEMS%NUM_ITEMS_PAGE;
        val = val==0?0:1;
        noOfBtns=TOTAL_LIST_ITEMS/NUM_ITEMS_PAGE+val;


        btns    =new Button[noOfBtns];

        for(int i=0;i<noOfBtns;i++)
        {
            btns[i] =   new Button(this);
            btns[i].setText("" + (i + 1));
            btns[i].setGravity(Gravity.CENTER);
            btns[i].setTextColor(Color.parseColor("#9f2a2f"));
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(150, 150);
            ll.addView(btns[i], lp);

            final int j = i;
            btns[j].setOnClickListener(new View.OnClickListener() {

                public void onClick(View v)
                {
                    pageCount = j;
                    loadList(j);
                    checkBackground(j);
                }
            });
        }

    }
    /**
     * Method for Checking Button Backgrounds
     */
    private void checkBackground(int index)
    {
        title.setText("Page "+(index+1)+" of "+noOfBtns);
        for(int i=0;i<noOfBtns;i++)
        {
            if(i==index)
            {
//                btns[index].setBackgroundColor(Color.BLUE);
                btns[i].setTextColor(Color.BLUE);
            } else {
                btns[i].setTextColor(Color.BLACK);
//                btns[i].setBackgroundColor(Color.WHITE);
            }
        }

    }

    /**
     * Method for loading data in listview
     * @param number
     */
    private void loadList(int number)
    {
        ArrayList<String> sort = new ArrayList<String>();

        int start = number * NUM_ITEMS_PAGE;
        for(int i=start;i<(start)+NUM_ITEMS_PAGE;i++)
        {
            if(i<data.size())
            {
                sort.add(data.get(i));
            }
            else
            {
                break;
            }
        }
        sd = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                sort);
        listview.setAdapter(sd);
    }
}
