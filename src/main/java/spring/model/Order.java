package spring.model;

public class Order {

    String order;
    int id;
    int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
