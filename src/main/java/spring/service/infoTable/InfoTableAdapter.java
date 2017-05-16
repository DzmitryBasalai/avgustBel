package spring.service.infoTable;
import java.util.List;


public interface InfoTableAdapter {
    public void SendData(List<String> data) throws Exception;
    public void SetBrightness(int brightness) throws Exception;
}
