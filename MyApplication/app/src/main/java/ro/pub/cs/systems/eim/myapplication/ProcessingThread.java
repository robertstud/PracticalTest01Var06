package ro.pub.cs.systems.eim.myapplication;

import java.util.Date;
import java.util.Random;

import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class ProcessingThread extends Thread {

    private Context context = null;
    private boolean isRunning = true;


    public ProcessingThread(Context context) {
        this.context = context;

    }

    @Override
    public void run() {
        while (isRunning) {
            sendMessage();
            sleep();
        }
    }

    private void sendMessage() {
        Intent intent = new Intent();
        intent.setAction("test");
        intent.putExtra("victory", "Victory");
        context.sendBroadcast(intent);
    }

    private void sleep() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    public void stopThread() {
        isRunning = false;
    }
}