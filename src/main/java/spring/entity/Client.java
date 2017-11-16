package spring.entity;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@NamedQuery(name="queue_client_count", query = "SELECT count(*) from Client")
@Table(name = "car_queue", schema = "avgustbeldb")
public class Client implements Serializable{
    private int id;
    private String carN;
    private String phoneN;
    private String destination;
    private String company;
    private String regTime;
    private String callTime;
    private String stock;
    private String ramp;
    private String arrivedTime;
    private String servedTime;
    private String returnTime;
    private String enterTime;
    private String leaveTime;
    private State state;

    private String msg;

    @Transient
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getCarN() {
        return carN;
    }

    public void setCarN(String carN) {
        this.carN = carN;
    }


    public String getPhoneN() {
        return phoneN;
    }

    public void setPhoneN(String phoneN) {
        this.phoneN = phoneN;
    }


    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getRegTime() {
        return regTime;
    }

    public void setRegTime(String regTime) {
        this.regTime = regTime;
    }

    public String getCallTime() {
        return callTime;
    }

    public void setCallTime(String callTime) {
        this.callTime = callTime;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getRamp() {
        return ramp;
    }

    public void setRamp(String ramp) {
        this.ramp = ramp;
    }

    public String getArrivedTime() {
        return arrivedTime;
    }

    public void setArrivedTime(String arrivedTime) {
        this.arrivedTime = arrivedTime;
    }

    public String getServedTime() {
        return servedTime;
    }

    public void setServedTime(String servedTime) {
        this.servedTime = servedTime;
    }

    public String getReturnTime() {
        return returnTime;
    }


    public void setReturnTime(String returnTime) {
        this.returnTime = returnTime;
    }

    public String getEnterTime() {
        return enterTime;
    }

    public void setEnterTime(String enterTime) {
        this.enterTime = enterTime;
    }

    public String getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(String leaveTime) {
        this.leaveTime = leaveTime;
    }

    @OneToOne(/*fetch = FetchType.LAZY*/)
    @JoinColumn(name = "stateId")
    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
