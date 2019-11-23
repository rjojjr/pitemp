package ks.pitemp;

import py4j.GatewayServer;

public class Main {

    public static final int PI = 1;
    public static final String IP = "192.168.1.25";
    public static final String HOSTNAME = " ";
    public static final int PORT = 7773;
    public static final boolean DEBUG = false;

    private Update app;

    public Main(){
        app = new Update();
    }

    public static void main(String[] args) throws  Exception{
        System.out.println("Starting gateway server...");
        GatewayServer server = null;
        switch (PI){
            case 1:
                server = new GatewayServer(new Main());
                break;
            case 2:
                server = new GatewayServer(new Main(), 25331);
                break;
            case 3:
                server = new GatewayServer(new Main(), 25332);
                break;
            case 4:
                server = new GatewayServer(new Main(), 25333);
                break;
            case 5:
                server = new GatewayServer(new Main(), 25331);
                break;
            case 6:
                server = new GatewayServer(new Main(), 25334);
                break;
        }
        server.start();
        System.out.println("Gateway server started");
    }

    public Update getApp() throws Exception{
        System.out.println("Python connected to server");
        System.out.println("Logging on...");
        try{
            if(app.logon()){
                System.out.println("Logged on");
            }else{
                while(app.logon() != true){
                    System.out.println("Failed to logon..." + System.lineSeparator() + "Retrying in 60 seconds...");
                    Thread.sleep(60 * 1000);
                    if(app.logon()){
                        System.out.println("Logged on");
                    }else{
                        System.out.println("Failed to connect");
                    }
                }
            }
        }catch (Exception e){
            System.out.println("Failed to logon..." + System.lineSeparator() + "Retrying in 60 seconds...");
            Thread.sleep(60 * 1000);
            try{
                if(app.logon()){
                    System.out.println("Logged on");
                }else{
                    System.out.println("Failed to logon..." + System.lineSeparator() + "Retrying in 60 seconds...");
                    Thread.sleep(60 * 1000);
                }
            }catch (Exception ex){
                System.out.println("Failed to connect");
            }
        }
        return app;
    }
}
