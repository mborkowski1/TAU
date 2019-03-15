package pl.edu.pjatk.tau.domain;

public class Car {
    
    private Long id;
    private String brand;
    private String model;
    private int manufactureYear;
    private int mileage;

    public Car() { }

    public Car(Car car) {
        id = car.id;
        brand = car.brand;
        model = car.model;
        manufactureYear = car.manufactureYear;
        mileage = car.mileage;
    }
    
    public Car(Long id, String brand, String model, int manufactureYear, int mileage) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.manufactureYear = manufactureYear;
        this.mileage = mileage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getManufactureYear() {
        return manufactureYear;
    }

    public void setManufactureYear(int manufactureYear) {
        this.manufactureYear = manufactureYear;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

}
