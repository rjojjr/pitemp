package ks.pitemp;



import picenter.connector.driver.DatabaseResults;
import picenter.connector.driver.Transaction;
import picenter.connector.singleclient.SingleClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Update {

    private String temp = "", humidity = "";
    private SingleClient client = null;
    private String username = "", table = "";

    public Update(){
        client = new SingleClient(Main.IP, Main.HOSTNAME, Main.PORT);
        switch (Main.PI){
            case 1:
                username = "pizero1";
                table = "office";
                break;
            case 2:
                username = "pizero2";
                table = "serverroom";
                break;
            case 3:
                username = "pizero3";
                table = "livingroom";
                break;
            case 4:
                username = "pizero4";
                table = "bedroom";
                break;
        }
    }

    public void update(String temp, String humidity) throws Exception{
        Long time = System.currentTimeMillis();
        if(Main.DEBUG){
            String date = CalenderConverter.getMonthDayYearHourMinuteSecond(time, "-", "-");
            System.out.println("Update Received: Temp: " + temp + " Humidity: " + humidity + " Date: " + date);
        }
        updateDB(temp, humidity, time + "");
    }

    boolean logon() throws Exception{
        return client.logon();
    }

    private void updateDB(String temp, String humidity, String date) throws Exception{
        this.humidity = humidity;
        this.temp = temp;
        if(!logon()){
            System.out.println("Failed to logon...");
        }else{
            Transaction transaction = new Transaction();
            transaction.setRequestTime(System.currentTimeMillis());
            transaction.setUsername(username);
            List<Map<String, String>> rows = new ArrayList<>();
            Map<String, String> row = new HashMap<>();
            row.put("temp", temp);
            row.put("humidity", humidity);
            row.put("time", date);
            rows.add(row);
            transaction.setNewRows(rows);
            transaction.setOperation(table);
            System.out.println("Sending update...");
            DatabaseResults results;
            results = client.sendCommand(transaction);
            if(results.isSuccess()){
                System.out.println("Update Successful");
            }else {
                System.out.println("Update Unsuccessful");
                System.out.println(results.getMessage());
            }
        }
    }

    String getHumidity() {
        return humidity;
    }

    String getTemp() {
        return temp;
    }
}
