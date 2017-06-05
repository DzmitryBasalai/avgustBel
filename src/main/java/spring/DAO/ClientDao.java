package spring.dao;

import spring.entity.Client;

import java.util.List;
import java.util.Locale;

public interface ClientDao {

    Client getClientByCarN(String carN, Locale locale) throws Exception;

    void insertClient(Client client, Locale locale) throws Exception;

    int getTotalClientCount(Locale locale) throws Exception;

    List<Client> getClientList(int offset, int total,String destination,  Locale locale) throws Exception;


    void updateClient(Client client) throws Exception;

    void deleteClient(Client client, Locale locale) throws Exception;

    //List<ClientArchive> getClientListArch(String from, String to, int state, Locale locale) throws Exception;

    //void insertClientIntoArch(ClientArchive clientArchive, Locale locale) throws Exception;
}
