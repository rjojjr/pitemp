package ks.pitemp;

import kirchnersolutions.javabyte.driver.common.driver.Transaction;
import kirchnersolutions.javabyte.driver.singleclient.SingleClient;

import java.util.HashMap;
import java.util.Map;

public class Update {

    private String temp = "", humidity = "";
    private SingleClient client = new SingleClient("192.168.1.25", "", 4444, "pizero1", "Password44#");

    public void update(String temp, String humidity) throws Exception{
        updateDB(temp, humidity);
    }

    private void updateDB(String temp, String humidity) throws Exception{
        this.humidity = humidity;
        this.temp = temp;
        client.logon();
        Transaction transaction = new Transaction();
        transaction.setRequestTime(System.currentTimeMillis());
        transaction.setUsername("pizero1");
        Map<String, String> row = new HashMap<>();
        row.put("user", "pizero1");
        row.put("temp", temp);
        row.put("humidity", humidity);
        row.put("date", CalenderConverter.getMonthDayYearHourMinuteSecond(System.currentTimeMillis(), ":", "~"));
        transaction.setPut(row);
        client.sendCommand(transaction);
    }

    public String getHumidity() {
        return humidity;
    }

    public String getTemp() {
        return temp;
    }
}
