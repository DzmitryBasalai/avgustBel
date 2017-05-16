package spring.DAO;

import spring.model.Client;
import java.util.List;
import java.util.Locale;


public interface ClientDao {

    int getClientCount();
    List<Client> getClientListByPage(int page, int total);

    Client insertClient(Client client, Locale locale) throws Exception;

    void deleteClient(Client client, Locale locale);

    int updateClient(Client client);

    Client getClientByCarN(String carN, Locale locale) throws Exception;

    List<Client> getClientListByState(String from, String to, int state);

    String insertClientInArchive(Client client);
}
