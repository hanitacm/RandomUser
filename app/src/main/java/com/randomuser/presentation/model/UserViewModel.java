package com.randomuser.presentation.model;

import android.net.Uri;
import java.io.Serializable;

public class UserViewModel implements Serializable {

  private Uri photo;
  private String name;
  private String email;
  private String phone;
  private String surname;

  public UserViewModel(String fullName, String email, String phone, Uri thumbnail, String surname) {
    this.photo = thumbnail;
    this.name = fullName;
    this.email = email;
    this.phone = phone;
    this.surname = surname;
  }

  public UserViewModel() {

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

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }
}
