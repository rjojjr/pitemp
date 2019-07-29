package ks.pitemp;

import py4j.GatewayServer;

public class Main {

    public static void main(String[] args) throws  Exception{
        Update app = new Update();
        // app is now the gateway.entry_point
        GatewayServer server = new GatewayServer(app);
        server.start();
    }
}
