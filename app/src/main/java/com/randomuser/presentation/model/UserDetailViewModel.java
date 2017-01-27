package com.randomuser.presentation.model;

import android.net.Uri;

public class UserDetailViewModel {
  private String gender;
  private Uri photo;
  private String name;
  private String email;
  private String street;
  private String city;
  private String state;
  private String registeredDate;

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
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

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getRegisteredDate() {
    return registeredDate;
  }

  public void setRegisteredDate(String registeredDate) {
    this.registeredDate = registeredDate;
  }

  public UserDetailViewModel(String gender, Uri photo, String name, String email, String street,
      String city, String state, String registeredDate) {
    this.gender = gender;
    this.photo = photo;
    this.name = name;
    this.email = email;
    this.street = street;
    this.city = city;
    this.state = state;
    this.registeredDate = registeredDate;
  }
}
