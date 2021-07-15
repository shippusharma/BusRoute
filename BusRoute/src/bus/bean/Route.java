package bus.bean;
public class Route {
private String id;    
private String routeNo;    
private String stopage; 
private int stopageNo; 

    public int getStopageNo() {
        return stopageNo;
    }

    public void setStopageNo(int stopageNo) {
        this.stopageNo = stopageNo;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRouteNo() {
        return routeNo;
    }

    public void setRouteNo(String routeNo) {
        this.routeNo = routeNo;
    }

    public String getStopage() {
        return stopage;
    }

    public void setStopage(String stopage) {
        this.stopage = stopage;
    }

}
