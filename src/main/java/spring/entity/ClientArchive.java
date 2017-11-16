package spring.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "car_archive", schema = "avgustbeldb")
public class ClientArchive implements Serializable{
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

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "carN")
    public String getCarN() {
        return carN;
    }

    public void setCarN(String carN) {
        this.carN = carN;
    }

    @Basic
    @Column(name = "phoneN")
    public String getPhoneN() {
        return phoneN;
    }

    public void setPhoneN(String phoneN) {
        this.phoneN = phoneN;
    }

    @Basic
    @Column(name = "destination")
    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    @Basic
    @Column(name = "regTime")
    public String getRegTime() {
        return regTime;
    }

    public void setRegTime(String regTime) {
        this.regTime = regTime;
    }

    @Basic
    @Column(name = "callTime")
    public String getCallTime() {
        return callTime;
    }

    public void setCallTime(String callTime) {
        this.callTime = callTime;
    }

    @Basic
    @Column(name = "stock")
    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    @Basic
    @Column(name = "ramp")
    public String getRamp() {
        return ramp;
    }

    public void setRamp(String ramp) {
        this.ramp = ramp;
    }

    @Basic
    @Column(name = "arrivedTime")
    public String getArrivedTime() {
        return arrivedTime;
    }

    public void setArrivedTime(String arrivedTime) {
        this.arrivedTime = arrivedTime;
    }

    @Basic
    @Column(name = "servedTime")
    public String getServedTime() {
        return servedTime;
    }

    public void setServedTime(String servedTime) {
        this.servedTime = servedTime;
    }

    @Basic
    @Column(name = "returnTime")
    public String getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(String returnTime) {
        this.returnTime = returnTime;
    }

    @Basic
    @Column(name = "company")
    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Basic
    @Column(name = "enterTime")
    public String getEnterTime() {
        return enterTime;
    }

    public void setEnterTime(String enterTime) {
        this.enterTime = enterTime;
    }

    @Basic
    @Column(name = "leaveTime")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientArchive that = (ClientArchive) o;

        if (id != that.id) return false;
        if (carN != null ? !carN.equals(that.carN) : that.carN != null) return false;
        if (phoneN != null ? !phoneN.equals(that.phoneN) : that.phoneN != null) return false;
        if (destination != null ? !destination.equals(that.destination) : that.destination != null) return false;
        if (regTime != null ? !regTime.equals(that.regTime) : that.regTime != null) return false;
        if (callTime != null ? !callTime.equals(that.callTime) : that.callTime != null) return false;
        if (stock != null ? !stock.equals(that.stock) : that.stock != null) return false;
        if (ramp != null ? !ramp.equals(that.ramp) : that.ramp != null) return false;
        if (arrivedTime != null ? !arrivedTime.equals(that.arrivedTime) : that.arrivedTime != null) return false;
        if (servedTime != null ? !servedTime.equals(that.servedTime) : that.servedTime != null) return false;
        if (returnTime != null ? !returnTime.equals(that.returnTime) : that.returnTime != null) return false;
        if (company != null ? !company.equals(that.company) : that.company != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (carN != null ? carN.hashCode() : 0);
        result = 31 * result + (phoneN != null ? phoneN.hashCode() : 0);
        result = 31 * result + (destination != null ? destination.hashCode() : 0);
        result = 31 * result + (regTime != null ? regTime.hashCode() : 0);
        result = 31 * result + (callTime != null ? callTime.hashCode() : 0);
        result = 31 * result + (stock != null ? stock.hashCode() : 0);
        result = 31 * result + (ramp != null ? ramp.hashCode() : 0);
        result = 31 * result + (arrivedTime != null ? arrivedTime.hashCode() : 0);
        result = 31 * result + (servedTime != null ? servedTime.hashCode() : 0);
        result = 31 * result + (returnTime != null ? returnTime.hashCode() : 0);
        result = 31 * result + (company != null ? company.hashCode() : 0);
        return result;
    }
}
