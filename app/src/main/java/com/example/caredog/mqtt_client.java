//package com.example.caredog;
//
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import org.eclipse.paho.android.service.MqttAndroidClient;
//import org.eclipse.paho.client.mqttv3.IMqttActionListener;
//import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
//import org.eclipse.paho.client.mqttv3.IMqttToken;
//import org.eclipse.paho.client.mqttv3.MqttCallback;
//import org.eclipse.paho.client.mqttv3.MqttException;
//import org.eclipse.paho.client.mqttv3.MqttMessage;
//
//public class mqtt_client extends AppCompatActivity {
//
//
//
//    private MqttAndroidClient mqttAndroidClient;
//
//    private Button button;
//
//    @Override
//
//    protected void onCreate(Bundle savedInstanceState) {
//
//        super.onCreate(savedInstanceState);
//
//        setContentView(R.layout.activity_main);
//
//
//
//        button = (Button)findViewById(R.id.button);
//
//
//
//        mqttAndroidClient = new MqttAndroidClient(this,  "tcp://" + "192.168.219.103" + ":1883", MqttClient.generateClientId());
//
//        // 2번째 파라메터 : 브로커의 ip 주소 , 3번째 파라메터 : client 의 id를 지정함 여기서는 paho 의 자동으로 id를 만들어주는것
//
//
//
//        try {
//
//            IMqttToken token = mqttAndroidClient.connect(getMqttConnectionOption());    //mqtttoken 이라는것을 만들어 connect option을 달아줌
//
//            token.setActionCallback(new IMqttActionListener() {
//
//                @Override
//
//                public void onSuccess(IMqttToken asyncActionToken) {
//
//                    mqttAndroidClient.setBufferOpts(getDisconnectedBufferOptions());    //연결에 성공한경우
//
//                    Log.e("Connect_success", "Success");
//
//
//
//                    try {
//
//                        mqttAndroidClient.subscribe("jmlee", 0 );   //연결에 성공하면 jmlee 라는 토픽으로 subscribe함
//
//                    } catch (MqttException e) {
//
//                        e.printStackTrace();
//
//                    }
//
//                }
//
//
//
//                @Override
//
//                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {   //연결에 실패한경우
//
//                    Log.e("connect_fail", "Failure " + exception.toString());
//
//                }
//
//            });
//
//        } catch (
//
//                MqttException e)
//
//
//
//        {
//
//            e.printStackTrace();
//
//        }
//
//
//
//        /*
//
//         *   subscribe 할때 3번째 파라메터에 익명함수 리스너를 달아줄수도있음
//
//         * */
//
//        /*try {
//
//            mqttAndroidClient.subscribe("jmlee!!", 0, new IMqttMessageListener() {
//
//                @Override
//
//                public void messageArrived(String topic, MqttMessage message) throws Exception {
//
//
//
//                }
//
//            });
//
//        } catch (MqttException e) {
//
//            e.printStackTrace();
//
//        }*/
//
//
//
//        button.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//
//            public void onClick(View view) {
//
//                try {
//
//                    mqttAndroidClient.publish("jmlee", "hello , my name is jmlee !".getBytes(), 0 , false );
//
//                    //버튼을 클릭하면 jmlee 라는 토픽으로 메시지를 보냄
//
//                } catch (MqttException e) {
//
//                    e.printStackTrace();
//
//                }
//
//            }
//
//        });
//
//
//
//
//
//
//
//        mqttAndroidClient.setCallback(new MqttCallback() {  //클라이언트의 콜백을 처리하는부분
//
//            @Override
//
//            public void connectionLost(Throwable cause) {
//
//
//
//            }
//
//
//
//            @Override
//
//            public void messageArrived(String topic, MqttMessage message) throws Exception {    //모든 메시지가 올때 Callback method
//
//                if (topic.equals("jmlee")){     //topic 별로 분기처리하여 작업을 수행할수도있음
//
//                    String msg = new String(message.getPayload());
//
//                    Log.e("arrive message : ", msg);
//
//                }
//
//            }
//
//
//
//            @Override
//
//            public void deliveryComplete(IMqttDeliveryToken token) {
//
//
//
//            }
//
//        });
//
//
//
//    }
//
//
//
//
//
//    private DisconnectedBufferOptions getDisconnectedBufferOptions() {
//
//        DisconnectedBufferOptions disconnectedBufferOptions = new DisconnectedBufferOptions();
//
//        disconnectedBufferOptions.setBufferEnabled(true);
//
//        disconnectedBufferOptions.setBufferSize(100);
//
//        disconnectedBufferOptions.setPersistBuffer(true);
//
//        disconnectedBufferOptions.setDeleteOldestMessages(false);
//
//        return disconnectedBufferOptions;
//
//    }
//
//
//
//    private MqttConnectOptions getMqttConnectionOption() {
//
//        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
//
//        mqttConnectOptions.setCleanSession(false);
//
//        mqttConnectOptions.setAutomaticReconnect(true);
//
//        mqttConnectOptions.setWill("aaa", "I am going offline".getBytes(), 1, true);
//
//        return mqttConnectOptions;
//
//    }
//
//}
//
