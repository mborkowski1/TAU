package pl.edu.pjatk.tau.cars.domain;

public class Car {
    
    private long id;
    private String model;

    public void setId(long id) {
        this.id  = id;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public long getId() {
        return id;
    }

    public String getModel() {
        return model;
    }
    
}
