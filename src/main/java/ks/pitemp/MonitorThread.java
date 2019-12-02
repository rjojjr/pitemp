package ks.pitemp;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MonitorThread extends Thread {

    private static boolean DEBUG = Main.DEBUG;

    public void run(){
        while(true){
            boolean found = false;
            try {
                String line;
                Process p = Runtime.getRuntime().exec("ps -e");
                BufferedReader input =
                        new BufferedReader(new InputStreamReader(p.getInputStream()));
                while ((line = input.readLine()) != null) {
                    if(DEBUG){
                        System.out.println(line); //<-- Parse data here.
                    }
                    if(line.contains("python")){
                        found = true;
                    }
                }
                input.close();
            } catch (Exception err) {
                err.printStackTrace();
            }
            if(!found){
                if(DEBUG){
                    System.out.println("Starting main.py...");
                }
                try{
                    Process p = Runtime.getRuntime().exec("python main.py");
                }catch (Exception e){
                    if(DEBUG){
                        e.printStackTrace();
                    }
                }
            }
            try{
                Thread.sleep(30000);
            }catch(Exception e){
                if(DEBUG){
                    e.printStackTrace();
                }
            }
        }
    }

}