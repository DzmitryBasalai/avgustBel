package spring.model;

import java.io.Serializable;

public class Client implements Serializable{

    private int id, stateId;
    private String orderNumber;
    private String carNumber;
    private String phoneNumber;
    private String msg;
    private String destination;
    private String regTime;
    private String callTime;
    private String stock;
    private String ramp;
    private String arrivedTime;
    private String servedTime;
    private String returnTime;
    private String state;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    public int getStateId() {
        return stateId;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", stateId=" + stateId +
                ", orderNumber='" + orderNumber + '\'' +
                ", carNumber='" + carNumber + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", msg='" + msg + '\'' +
                ", destination='" + destination + '\'' +
                ", regTime='" + regTime + '\'' +
                ", callTime='" + callTime + '\'' +
                ", stock='" + stock + '\'' +
                ", ramp='" + ramp + '\'' +
                ", arrivedTime='" + arrivedTime + '\'' +
                ", servedTime='" + servedTime + '\'' +
                ", returnTime='" + returnTime + '\'' +
                '}';
    }
}
