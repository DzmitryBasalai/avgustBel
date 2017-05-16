package spring.DAO.DAOimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import spring.DAO.ClientDao;
import spring.model.Client;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

@Component
public class ClientDaoImpl implements ClientDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void getDataSourse(DataSource dataSourse) {
        this.jdbcTemplate = new JdbcTemplate(dataSourse);
    }

    @Autowired
    MessageSource messageSource;

    @Override
    public Client insertClient(Client client, Locale locale) throws Exception {
        try {
            client.setStateId(6);
            if (client.getCarNumber().equals("") || client.getPhoneNumber().equals("")) {
                client.setMsg(messageSource.getMessage("client.registration.emptyFields", null, locale));
                return client;
            }

            String sql = "insert into avgustbeldb.car_queue (carN, phoneN, destination,orderN," +
                    "regTime,callTime,stock,ramp,arrivedTime,servedTime,returnTime,stateId) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";

            Client clientDB;
            try {
                clientDB = getClientByCarN(client.getCarNumber(), locale);
            } catch (Exception ex) {
                client.setMsg(ex.getMessage());
                return client;
            }

            if (clientDB != null && clientDB.getCarNumber().equals(client.getCarNumber())) {
                client.setMsg(messageSource.getMessage("client.registration.sameCarNumber", new Object[]{client.getCarNumber()}, locale));
                return client;
            }
            client.setStateId(1);
            this.jdbcTemplate.update(sql,
                    client.getCarNumber(),
                    client.getPhoneNumber(),
                    client.getDestination(),
                    client.getOrderNumber(),
                    client.getRegTime(),
                    client.getCallTime(),
                    client.getStock(),
                    client.getRamp(),
                    client.getArrivedTime(),
                    client.getServedTime(),
                    client.getReturnTime(),
                    client.getStateId()
            );

            client.setMsg(messageSource.getMessage("client.registration.success", new Object[]{client.getCarNumber()}, locale));
            return client;
        } catch (Exception ex) {
            throw new Exception(messageSource.getMessage("exception.dbError", null, locale) + ex.getMessage());
        }
    }


    @Override
    public void deleteClient(Client client, Locale locale) {

        String sqlAutoIncr = "ALTER TABLE avgustbeldb.car_queue AUTO_INCREMENT = 1";
        String sqlSafeUpdate = "SET SQL_SAFE_UPDATES=0";

        String sqlDeleteClient = "DELETE FROM avgustbeldb.car_queue WHERE carN=?";

        try {
            Client clientDB = getClientByCarN(client.getCarNumber(), locale);
            if (clientDB.getCarNumber().equals(client.getCarNumber())) {
                jdbcTemplate.update(sqlDeleteClient, client.getCarNumber());
                jdbcTemplate.execute(sqlSafeUpdate);
                jdbcTemplate.update(sqlAutoIncr);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

    @Override
    public int updateClient(Client client) {
        String sql = "Update avgustbeldb.car_queue SET phoneN=?, destination=?,orderN=?," +
                "regTime=?,callTime=?,stock=?,ramp=?,arrivedTime=?,servedTime=?,returnTime=?,stateId=? where carN=?";

        Object[] params = {client.getPhoneNumber(), client.getDestination(), client.getOrderNumber(),
                client.getRegTime(), client.getCallTime(), client.getStock(), client.getRamp(), client.getArrivedTime(),
                client.getServedTime(), client.getReturnTime(), client.getStateId(), client.getCarNumber()};

        return this.jdbcTemplate.update(sql, params);
    }

    @Override
    public Client getClientByCarN(String carN, Locale locale) throws Exception {

        try {
            String sql = "SELECT * FROM avgustbeldb.car_queue inner join avgustbeldb.state\n" +
                    "on avgustbeldb.car_queue.stateId = avgustbeldb.state.id where avgustbeldb.car_queue.carN = ?;";
            List<Client> clientList = this.jdbcTemplate.query(
                    sql, new Object[]{carN}, new ClientRowMapper());
            System.out.println(clientList);
            if (clientList.size() == 0)
                return null;

            return clientList.get(0);
        } catch (Exception ex) {
            throw new Exception(messageSource.getMessage("exception.dbError", null, locale) + ex.getMessage());

        }

    }

    @Override
    public List<Client> getClientListByState(String from, String to, int stateId) {
        String sql = "SELECT * FROM avgustbeldb.car_archive \n" +
                "WHERE car_archive.regTime >= ? AND car_archive.regTime <= ? AND car_archive.stateId = ?;";
        return this.jdbcTemplate.query(sql, new Object[]{from, to, stateId}, new ClientArchiveRowMapper());
    }


    @Override
    public int getClientCount() {
        String sql = "SELECT count(*) from avgustbeldb.car_queue where avgustbeldb.car_queue.stateId<?";
        return jdbcTemplate.queryForObject(sql, new Object[]{4}, Integer.class);
    }

    @Override
    public List<Client> getClientListByPage(int pageid, int total) {
        String sql = "select * from avgustbeldb.car_queue inner JOIN  avgustbeldb.state " +
                "on avgustbeldb.car_queue.stateId = avgustbeldb.state.id" +
                " where avgustbeldb.car_queue.stateId<4" + " limit "+ (pageid - 1) + "," + total;
        return jdbcTemplate.query(sql, new ClientRowMapper());
    }

    @Override
    public String insertClientInArchive(Client client) {
        String sql = "insert into avgustbeldb.car_archive (carN, phoneN, destination,orderN," +
                "regTime,callTime,stock,ramp,arrivedTime,servedTime,returnTime,stateId) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";

        this.jdbcTemplate.update(sql,
                client.getCarNumber(),
                client.getPhoneNumber(),
                client.getDestination(),
                client.getOrderNumber(),
                client.getRegTime(),
                client.getCallTime(),
                client.getStock(),
                client.getRamp(),
                client.getArrivedTime(),
                client.getServedTime(),
                client.getReturnTime(),
                client.getStateId()
        );
        return "Информация помещена в архив";
    }
}

class ClientArchiveRowMapper implements RowMapper<Client> {
    @Override
    public Client mapRow(ResultSet rs, int rowNum) throws SQLException {
        Client aClient = new Client();
        aClient.setId(rs.getInt("car_archive.id"));
        aClient.setCarNumber(rs.getString("car_archive.carN"));
        aClient.setPhoneNumber(rs.getString("car_archive.phoneN"));
        aClient.setDestination(rs.getString("car_archive.destination"));
        aClient.setOrderNumber(rs.getString("car_archive.orderN"));
        aClient.setRegTime(rs.getString("car_archive.regTime"));
        aClient.setCallTime(rs.getString("car_archive.callTime"));
        aClient.setStock(rs.getString("car_archive.stock"));
        aClient.setRamp(rs.getString("car_archive.ramp"));
        aClient.setArrivedTime(rs.getString("car_archive.arrivedTime"));
        aClient.setServedTime(rs.getString("car_archive.servedTime"));
        aClient.setReturnTime(rs.getString("car_archive.returnTime"));
        aClient.setStateId(rs.getInt("car_archive.stateId"));
        return aClient;
    }
}

class ClientRowMapper implements RowMapper<Client> {
    @Override
    public Client mapRow(ResultSet rs, int rowNum) throws SQLException {
        Client aClient = new Client();
        aClient.setId(rs.getInt("car_queue.id"));
        aClient.setCarNumber(rs.getString("car_queue.carN"));
        aClient.setPhoneNumber(rs.getString("car_queue.phoneN"));
        aClient.setDestination(rs.getString("car_queue.destination"));
        aClient.setOrderNumber(rs.getString("car_queue.orderN"));
        aClient.setRegTime(rs.getString("car_queue.regTime"));
        aClient.setCallTime(rs.getString("car_queue.callTime"));
        aClient.setStock(rs.getString("car_queue.stock"));
        aClient.setRamp(rs.getString("car_queue.ramp"));
        aClient.setArrivedTime(rs.getString("car_queue.arrivedTime"));
        aClient.setServedTime(rs.getString("car_queue.servedTime"));
        aClient.setReturnTime(rs.getString("car_queue.returnTime"));
        aClient.setStateId(rs.getInt("car_queue.stateId"));
        aClient.setState(rs.getString("state.state"));
        return aClient;
    }
}