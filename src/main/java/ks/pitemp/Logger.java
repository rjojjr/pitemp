package ks.pitemp;

import picenter.connector.common.common.utilities.ByteTools;

import java.io.File;
import java.io.IOException;

import static ks.pitemp.Main.restartApplication;

class Logger {

    private static Logger single_instance = new Logger();

    private File dir = new File("pitemp/logs");
    private File logFile = new File(dir,"/log.txt");

    private Logger(){
        if(!dir.exists()){
            dir.mkdirs();
            createLog(logFile);
        }
    }

    static Logger getLogger(){
        if(single_instance == null){
            single_instance = new Logger();
        }
        return single_instance;
    }

    private void log(String msg, Exception e){
        if(!logFile.exists()){
            createLog(logFile);
        }
        String out = readFile(logFile) + "\r\n" + CalenderConverter.getMonthDayYearHourMinuteSecond(System.currentTimeMillis(), "-", "-") + msg + "\r\n" + getStack(e);
        try{
            ByteTools.writeBytesToFile(logFile, out.getBytes("UTF-8"));
        }catch (Exception ex){
            e.printStackTrace();
            System.out.println();
            ex.printStackTrace();
        }
    }

    private static void createLog(File logFile){
        try{
            logFile.createNewFile();
        }catch (IOException e){
            e.printStackTrace();
            restartApplication();
        }
    }

    private String getStack(Exception e){
        String out = e.getMessage();
        boolean first = true;
        for(StackTraceElement el : e.getStackTrace()){
            if (first) {
                out = el.toString();
                first = false;
            }else {
                out+= "\r\n" + el.toString();
            }
        }
        return out;
    }

    private String readFile(File file){
        if (!file.exists()){
            createLog(file);
            return "";
        }
        try{
            return new String(ByteTools.readBytesFromFile(file), "UTF-8");
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }

}
