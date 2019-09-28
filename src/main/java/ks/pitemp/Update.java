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
    private SingleClient client = null;
    private String username = "", table = "";

    public Update(){
        //1
        //client = new SingleClient("192.168.1.25", " ", 4444, "pizero1", "Password44#");
        //1
        //2
        //client = new SingleClient("192.168.1.25", " ", 4444, "pizero2", "Password55#");
        //2
        //3
        //client = new SingleClient("192.168.1.25", " ", 4444, "pizero3", "Password55#");
        //3
        //4
        //client = new SingleClient("192.168.1.25", " ", 4444, "pizero41", "Password55#");
        //4
        switch (Main.PI){
            case 1:
                client = new SingleClient("192.168.1.25", " ", 4444, "pizero1", "Password44#");
                username = "pizero1";
                table = "PiTempsOffice";
                break;
            case 2:
                client = new SingleClient("192.168.1.25", " ", 4444, "pizero2", "Password55#");
                username = "pizero2";
                table = "PiTempsServerRoom";
                break;
            case 3:
                client = new SingleClient("192.168.1.25", " ", 4444, "pizero3", "Password55#");
                username = "pizero3";
                table = "PiTempsLR";
                break;
            case 4:
                client = new SingleClient("192.168.1.25", " ", 4444, "pizero41", "Password55#");
                username = "pizero41";
                table = "PiTempsBedroom";
                break;
        }
    }

    public void update(String temp, String humidity) throws Exception{
        String date = CalenderConverter.getMonthDayYearHourMinuteSecond(System.currentTimeMillis(), "-", "-");
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
            System.out.println("Failed to logon...");
        }else{
            Transaction transaction = new Transaction();
            transaction.setRequestTime(System.currentTimeMillis());
            transaction.setUsername(username);
            List<Map<String, String>> rows = new ArrayList<>();
            Map<String, String> row = new HashMap<>();
            row.put("temp", temp);
            row.put("humidity", humidity);
            row.put("date", date);
            rows.add(row);
            transaction.setNewRows(rows);
            transaction.setOperation("CREATE ROWS ADVANCED " + table);
            //transaction.setOperation("CREATE ROWS ADVANCED PiTempsOffice");
            //transaction.setOperation("CREATE ROWS ADVANCED PiTempsServerRoom");
            //transaction.setOperation("CREATE ROWS ADVANCED PiTempsLR");
            //transaction.setOperation("CREATE ROWS ADVANCED PiTempsBedroom");
            System.out.println("Sending update...");
            DatabaseResults results = client.sendCommand(transaction);
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
