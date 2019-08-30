package ks.pitemp;

import py4j.GatewayServer;

public class Main {

    private Update app;

    public Main(){
        app = new Update();
    }

    public static void main(String[] args) throws  Exception{

        // app is now the gateway.entry_point
        System.out.println("Starting gateway server");
        GatewayServer server = new GatewayServer(new Main());
        server.start();
    }

    public Update getApp() throws Exception{
        System.out.println("Python connected to server");
        System.out.println("Logging on...");
        if(app.logon()){
            System.out.println("Logged on");
        }else{
            System.out.println("Failed to logon...");
        }
        return app;
    }
}
