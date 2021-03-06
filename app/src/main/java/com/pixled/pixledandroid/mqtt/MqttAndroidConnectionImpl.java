package com.pixled.pixledandroid.mqtt;

import android.content.Context;
import android.util.Log;

import com.pixled.pixledandroid.deviceGroup.mainActivity.GroupSelectionActivity;
import com.pixled.pixledandroid.utils.ServerConfig;
import com.pixled.pixledandroid.welcome.MqttConnectionStatusHandler;
import com.pixled.pixledandroid.welcome.WelcomeActivity;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

public class MqttAndroidConnectionImpl implements  MqttAndroidConnection {

    private MqttAndroidClient mqttAndroidClient;

    private static final String clientId = "PixLedAndroid";

    private GroupSelectionActivity groupSelectionActivity;
    private WelcomeActivity welcomeActivity;
    private MqttAndroidConnectionCallback callback;

    public MqttAndroidConnectionImpl(WelcomeActivity welcomeActivity) {
        this.welcomeActivity = welcomeActivity;
    }

    public void setGroupSelectionActivity(GroupSelectionActivity groupSelectionActivity) {
        this.groupSelectionActivity = groupSelectionActivity;
        callback.setGroupSelectionActivity(groupSelectionActivity);
    }

    @Override
    public void connect(Context context) {
        mqttAndroidClient = new MqttAndroidClient(context, ServerConfig.URI, clientId);
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(true);
        options.setConnectionTimeout(10);
        callback = new MqttAndroidConnectionCallback(mqttAndroidClient);
        mqttAndroidClient.setCallback(callback);
        Log.i("MQTT", "Connecting...");
        try {
            mqttAndroidClient.connect(options, context, new MqttConnectionStatusHandler(welcomeActivity));

        } catch (MqttException e) {
            e.printStackTrace();
        }

    }
}
