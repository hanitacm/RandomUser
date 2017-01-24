package randomuser.com.presentation.model;

import android.net.Uri;

public class UserViewModel {

  private Uri photo;
  private String name;
  private String email;
  private String phone;

  public UserViewModel(String fullName, String email, String phone, Uri thumbnail) {
    this.photo = thumbnail;
    this.name = fullName;
    this.email = email;
    this.phone = phone;
  }

  public Uri getPhoto() {
    return photo;
  }

  public void setPhoto(Uri photo) {
    this.photo = photo;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }
}
