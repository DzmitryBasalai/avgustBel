package spring.dao;

import spring.entity.ClientArchive;

import java.util.List;
import java.util.Locale;

public interface ClientArchDao  {
    void addClientToArch(ClientArchive clientArchive, Locale locale) throws Exception;

    List<ClientArchive> getClientListfromArch(String from, String to, Locale locale) throws Exception;
}
