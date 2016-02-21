package com.alleywayconsulting.piggygraph;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class GraphActivity extends BluetoothActivityBase {

    public static String EXTRA_DEVICE_ADDRESS = "device_address";

    private LineChart mChart;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_graph, menu);
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_graph);

        mChart = (LineChart) findViewById(R.id.chart);
        //mChart.setOnChartValueSelectedListener(this);

        // no description text
        mChart.setDescription("");
        mChart.setNoDataTextDescription("You need to provide data for the chart.");

        // enable touch gestures
        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setDrawGridBackground(true);
        mChart.setGridBackgroundColor(getResources().getColor(R.color.pg_topaz));
        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(true);

        // set an alternative background color
//        mChart.setBackgroundColor(Color.LTGRAY);



        LineData data = new LineData();
        data.setValueTextColor(Color.WHITE);

        // add empty data
        mChart.setData(data);

        Typeface tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

        // get the legend (only possible after setting data)
        Legend l = mChart.getLegend();
        l.setEnabled(false);
        // modify the legend ...
//        l.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
//        l.setForm(Legend.LegendForm.LINE);
//        l.setTypeface(tf);
//        l.setTextColor(Color.WHITE);


        XAxis xl = mChart.getXAxis();
        xl.setTypeface(tf);
        xl.setTextSize(13f);
        xl.setTextColor(Color.WHITE);
        xl.setDrawGridLines(false);
        xl.setAvoidFirstLastClipping(true);
        xl.setSpaceBetweenLabels(5);
        xl.setEnabled(true);
        xl.setAxisLineColor(Color.TRANSPARENT);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setAxisLineColor(Color.TRANSPARENT);
        leftAxis.setTypeface(tf);
        leftAxis.setTextColor(Color.WHITE);
        leftAxis.setGridColor(Color.argb(128, 255, 255, 255));
        leftAxis.setAxisMaxValue(100f);
        leftAxis.setAxisMinValue(0f);
        leftAxis.setDrawGridLines(true);
        leftAxis.setTextSize(15f);

        //remove the decimals from axis values
        leftAxis.setValueFormatter(new YAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, YAxis yAxis) {
                return Math.round(value) + "";
            }
        });
        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setEnabled(false);

    }

    @Override
    protected void onResume() {
        super.onResume();

        feedMultiple();

//        String address = getIntent().getExtras().getString(EXTRA_DEVICE_ADDRESS);
//
//        BluetoothDevice device = getBluetoothAdapter().getRemoteDevice(address);
//        // Attempt to connect to the device
//        connectDevice(device);

    }

    protected String[] mDays = new String[]{"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};


    @Override
    protected void receiveBtMessage(String message) {

        Long number = Long.valueOf(message);

        addEntry(number);

    }


    private void addEntry(Long number) {

        LineData data = mChart.getData();

        if (data != null) {

            ILineDataSet set = data.getDataSetByIndex(0);
            // set.addEntry(...); // can be called as well

            if (set == null) {
                set = createSet();
                data.addDataSet(set);
            }

            // add a new x-value first
            data.addXValue(mDays[data.getXValCount() % 7]);

            data.addEntry(new Entry(number, set.getEntryCount()), 0);


            // let the chart know it's data has changed
            mChart.notifyDataSetChanged();

            // limit the number of visible entries
            mChart.setVisibleXRangeMaximum(120);
            // mChart.setVisibleYRange(30, AxisDependency.LEFT);

            // move to the latest entry
            mChart.moveViewToX(data.getXValCount() - 121);

            // this automatically refreshes the chart (calls invalidate())
            // mChart.moveViewTo(data.getXValCount()-7, 55f,
            // AxisDependency.LEFT);
        }
    }

    private LineDataSet createSet() {

        LineDataSet set = new LineDataSet(null, "Dynamic Data");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setLineWidth(2.5f);
        set.setColor(Color.argb(128, 240, 240, 240));
        set.setCircleColor(getResources().getColor(R.color.pg_gold));
        set.setCircleRadius(8f);
        //set.setCircleColorHole(Color.BLACK);
//        set.setFillAlpha(30);
//        set.setFillColor(Color.RED);
        set.setHighLightColor(Color.rgb(244, 0, 0));
        set.setValueTextColor(Color.WHITE);
        set.setValueTextSize(13f);
        set.setDrawValues(true);
        set.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return Math.round(value) + "";
            }
        });
        return set;
    }

    private void feedMultiple() {

        new Thread(new Runnable() {

            @Override
            public void run() {
                for (int i = 0; i < 7; i++) {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            addEntry(Math.round((Math.random() * 100)));
                        }
                    });

                    try {
                        Thread.sleep(35);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

}
