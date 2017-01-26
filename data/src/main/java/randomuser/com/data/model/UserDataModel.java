package randomuser.com.data.model;

import java.io.Serializable;

public class UserDataModel implements Serializable {
  private String gender;
  private UserNameDataModel name;
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

  @Override
  public boolean equals(Object obj) {
    if (obj == null) return false;
    if (obj == this) return true;

    if (!(obj instanceof UserDataModel)) return false;

    UserDataModel userDataModel = (UserDataModel) obj;

    return (isSameEmail(userDataModel) &
        isSameName(userDataModel) & isSameLastName(userDataModel));
  }

  private boolean isSameEmail(UserDataModel userDataModel) {
    return (userDataModel.getEmail() == null && this.getEmail() == null) || userDataModel.getEmail()
        .equals(this.getEmail());
  }

  private boolean isSameName(UserDataModel userDataModel) {
    return (userDataModel.getName() == null && this.getName() == null)
        || (userDataModel.getName().getFirst() == null && this.getName().getFirst() == null)
        || (userDataModel.getName().getFirst().equals(this.getName().getFirst()));
  }

  private boolean isSameLastName(UserDataModel userDataModel) {
    return (userDataModel.getName() == null && this.getName() == null)
        || (userDataModel.getName().getLast() == null && this.getName().getLast() == null)
        || (userDataModel.getName().getLast().equals(this.getName().getLast()));
  }
}
