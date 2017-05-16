package spring.service.infoTable;

public class ConnectionSingleton {
    private static ConnectionSingleton ourInstance = new ConnectionSingleton();

    InfoTable infoTable;
    public static ConnectionSingleton getInstance() {
        return ourInstance;
    }

    private ConnectionSingleton() {
        InfoTableAdapterTCP infoTableAdapterTCP = new InfoTableAdapterTCP("192.168.175.118 ", 10007, 1);
        infoTable = new InfoTable("", infoTableAdapterTCP);
    }

    public InfoTable getInfoTable(){
       return infoTable;
    }
}
