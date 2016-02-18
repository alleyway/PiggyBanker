package com.alleywayconsulting.scantograph;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.lang.ref.WeakReference;

/**
 * Created by Michael Lake on 2/17/16.
 * Copyright (c) Alleyway Consulting, LLC
 */
public abstract class BluetoothActivityBase extends ActivityBase {

    private static final String TAG = BluetoothActivityBase.class.getSimpleName();

    private BluetoothService mBluetoothGraphService = null;

    private Handler mBtHandler;

    private BluetoothAdapter mBluetoothAdapter = null;


    // Intent request codes
    protected static final int REQUEST_ENABLE_BT = 3;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBtHandler = new BtServiceHandler(this);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // If the adapter is null, then Bluetooth is not supported
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        // Performing this check in onResume() covers the case in which BT was
        // not enabled during onStart(), so we were paused to enable it...
        // onResume() will be called when ACTION_REQUEST_ENABLE activity returns.
        if (mBluetoothGraphService != null) {
            // Only if the state is STATE_NONE, do we know that we haven't started already
            if (mBluetoothGraphService.getState() == BluetoothService.STATE_NONE) {
                // Start the Bluetooth chat services
                mBluetoothGraphService.start();
            }
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        // If BT is not on, request that it be enabled.
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
            // Otherwise, setup the chat session
        } else if (mBluetoothGraphService == null) {
            setupBluetooth();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBluetoothGraphService != null) {
            mBluetoothGraphService.stop();
        }
    }


    protected void sendBtMessage(String message) {
        if (mBluetoothGraphService.getState() != BluetoothService.STATE_CONNECTED) {
            //Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT).show();
            return;
        }

        // Check that there's actually something to send
        if (message.length() > 0) {
            // Get the message bytes and tell the BluetoothChatService to write
            byte[] send = message.getBytes();
            mBluetoothGraphService.write(send);
        }
    }

    protected void receiveBtMessage(String message) {

    }

    protected void setupBluetooth() {
        mBluetoothGraphService = new BluetoothService(mBtHandler);
    }

    protected void connectDevice(BluetoothDevice device) {
        mBluetoothGraphService.connect(device, false);

    }

    protected BluetoothAdapter getBluetoothAdapter() {
        return mBluetoothAdapter;
    }

    private static class BtServiceHandler extends Handler {

        private WeakReference<BluetoothActivityBase> mActivity;

        public BtServiceHandler(BluetoothActivityBase activity) {
            mActivity = new WeakReference<BluetoothActivityBase>(activity);
        }

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case Constants.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case BluetoothService.STATE_CONNECTED:
                            Log.i(TAG, "Device connected");
                            //setStatus(getString(R.string.title_connected_to, mConnectedDeviceName));
                            break;
                        case BluetoothService.STATE_CONNECTING:
                            Log.i(TAG, "Connecting...");
                            //setStatus(R.string.title_connecting);
                            break;
                        case BluetoothService.STATE_LISTEN:
                        case BluetoothService.STATE_NONE:
                            Log.i(TAG, "Not connected");
                            //setStatus(R.string.title_not_connected);
                            break;
                    }
                    break;
                case Constants.MESSAGE_WRITE:
                    byte[] writeBuf = (byte[]) msg.obj;
                    // construct a string from the buffer
                    String writeMessage = new String(writeBuf);

                    //mConversationArrayAdapter.add("Me:  " + writeMessage);
                    break;
                case Constants.MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    // construct a string from the valid bytes in the buffer
                    String readMessage = new String(readBuf, 0, msg.arg1);
                    mActivity.get().receiveBtMessage(readMessage);
                    //mConversationArrayAdapter.add(mConnectedDeviceName + ":  " + readMessage);
                    break;
                case Constants.MESSAGE_DEVICE_NAME:
                    // save the connected device's name
                    String connectedDeviceName = msg.getData().getString(Constants.DEVICE_NAME);
                    if (null != mActivity) {
                        Toast.makeText(mActivity.get(), "Connected to "
                                + connectedDeviceName, Toast.LENGTH_SHORT).show();
                    }
                    break;
                case Constants.MESSAGE_TOAST:
                    if (null != mActivity) {
                        Toast.makeText(mActivity.get(), msg.getData().getString(Constants.TOAST),
                                Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    }


}
