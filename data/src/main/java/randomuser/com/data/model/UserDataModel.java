package randomuser.com.data.model;

import java.io.Serializable;
import java.util.Date;

public class UserDataModel implements Serializable {
    private String  gender;
    private UserNameDataModel  name;
    private UserLocationDataModel location;
    private String email;
    private String registered;
    private String phone;
    private String cell;
    private String dob;
    private String nat;
    private LoginDataModel login;
    private UserPictureDataModel picture;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public UserNameDataModel getName() {
        return name;
    }

    public void setName(UserNameDataModel name) {
        this.name = name;
    }

    public UserLocationDataModel getLocation() {
        return location;
    }

    public void setLocation(UserLocationDataModel location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRegistered() {
        return registered;
    }

    public void setRegistered(String registered) {
        this.registered = registered;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public UserPictureDataModel getPicture() {
        return picture;
    }

    public void setPicture(UserPictureDataModel picture) {
        this.picture = picture;
    }
}
