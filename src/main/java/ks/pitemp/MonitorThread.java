package ks.pitemp;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MonitorThread extends Thread {

    private static boolean DEBUG = Main.DEBUG;
    private static Logger logger = Logger.getLogger();

    public void run(){
        while(true){
            boolean found = false;
            try {
                String line;
                Process p = Runtime.getRuntime().exec("ps -aux");
                BufferedReader input =
                        new BufferedReader(new InputStreamReader(p.getInputStream()));
                while ((line = input.readLine()) != null) {
                    if(DEBUG){
                        System.out.println(line); //<-- Parse data here.
                    }
                    if(line.contains("main.py")){
                        found = true;
                    }
                }
                input.close();
            } catch (Exception err) {
                logger.log("Failed to read running processes", err);
                err.printStackTrace();
                Main.restartApplication();
            }
            if(!found){
                if(DEBUG){
                    System.out.println("Starting main.py...");
                }
                try{
                    Process p = Runtime.getRuntime().exec("python main.py");
                }catch (Exception e){
                    if(DEBUG){
                        logger.log("Failed to start main.py", e);
                        e.printStackTrace();
                        Main.restartApplication();
                    }
                }
            }
            try{
                Thread.sleep(Main.MONITOR_SCAN_INTERVAL);
            }catch(Exception e){
                if(DEBUG){
                    e.printStackTrace();
                    logger.log("Monitor thread sleep interrupted", e);
                }
            }
        }
    }

}