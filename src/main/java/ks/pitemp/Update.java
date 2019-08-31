package ks.pitemp;

import kirchnersolutions.javabyte.driver.common.driver.DatabaseResults;
import kirchnersolutions.javabyte.driver.common.driver.Transaction;
import kirchnersolutions.javabyte.driver.singleclient.SingleClient;

import javax.swing.text.SimpleAttributeSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Update {

    private String temp = "", humidity = "";
    private SingleClient client = new SingleClient("192.168.1.25", " ", 4444, "pizero1", "Password44#");

    public void update(String temp, String humidity) throws Exception{
        String date = CalenderConverter.getMonthDayYearHourMinuteSecond(System.currentTimeMillis(), "~", "~");
        System.out.println("Update Received: Temp: " + temp + " Humidity: " + humidity + " Date: " + date);
        updateDB(temp, humidity, date);
    }

    boolean logon() throws Exception{
        return client.logon();
    }

    private void updateDB(String temp, String humidity, String date) throws Exception{
        this.humidity = humidity;
        this.temp = temp;
        if(!logon()){
            System.out.println("Update failed...");
            System.out.println("Failed to logon...");
        }else{
            Transaction transaction = new Transaction();
            transaction.setRequestTime(System.currentTimeMillis());
            //transaction.setUsername("pizero1");
            List<Map<String, String>> rows = new ArrayList<>();
            Map<String, String> row = new HashMap<>();
            row.put("username", "office");
            row.put("temp", temp);
            row.put("humidity", humidity);
            row.put("date", date);
            rows.add(row);
            transaction.setNewRows(rows);
            transaction.setOperation("CREATE ROWS ADVANCED PiTemps");
            System.out.println("Sending update...");
            DatabaseResults results = client.sendCommand(transaction);
            if(results.isSuccess()){
                System.out.println("Update Successful");
            }else {
                System.out.println("Update Unsuccessful");
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
