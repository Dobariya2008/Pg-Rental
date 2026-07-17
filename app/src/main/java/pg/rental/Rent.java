package pg.rental;

public class Rent {
    private String title;
    private String location;
    private String address;
    private String fee;
    private String period;
    private String description;
    private String numOfBeds;
    private String numOfBaths;
    private String userName;
    private String contact;
    private String email;
    private String key;

    public Rent() {
    }

    public Rent(String title, String location, String fee, String period, String address, String description, String numOfBeds, String numOfBaths, String userName, String contact, String email, String key) {
        this.title = title;
        this.location = location;
        this.address = address;
        this.fee = fee;
        this.period = period;
        this.description = description;
        this.numOfBeds = numOfBeds;
        this.numOfBaths = numOfBaths;
        this.userName = userName;
        this.contact = contact;
        this.email = email;
        this.key = key;
    }

    public Rent(String title, String fee, String period) {
        this.title = title;
        this.fee = fee;
        this.period = period;
    }

    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }

    public String getAddress() {
        return address;
    }

    public String getFee() {
        return fee;
    }

    public String getPeriod() {
        return period;
    }

    public String getDescription() {
        return description;
    }

    public String getNumOfBeds() {
        return numOfBeds;
    }

    public String getNumOfBaths() {
        return numOfBaths;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setNumOfBeds(String numOfBeds) {
        this.numOfBeds = numOfBeds;
    }

    public void setNumOfBaths(String numOfBaths) {
        this.numOfBaths = numOfBaths;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
