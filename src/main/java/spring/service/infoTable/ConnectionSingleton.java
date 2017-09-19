package spring.service.infoTable;

public class ConnectionSingleton {
    private static ConnectionSingleton ourInstance = new ConnectionSingleton();

    private InfoTable infoTable;
    public static ConnectionSingleton getInstance() {
        return ourInstance;
    }

    private ConnectionSingleton() {
        InfoTableAdapterTCP infoTableAdapterTCP = new InfoTableAdapterTCP("192.168.175.118", 10007, 1);
        //InfoTableAdapterTCP infoTableAdapterTCP = new InfoTableAdapterTCP("192.168.1.111", 51234, 9999);
        infoTable = new InfoTable("", infoTableAdapterTCP);
    }

    public InfoTable getInfoTable(){
       return infoTable;
    }
}
