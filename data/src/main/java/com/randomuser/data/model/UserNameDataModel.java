package com.randomuser.data.model;

import java.io.Serializable;

public class UserNameDataModel implements Serializable {
  String title;
  String first;
  String last;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getFirst() {
    return first;
  }

  public void setFirst(String first) {
    this.first = first;
  }

  public String getLast() {
    return last;
  }

  public void setLast(String last) {
    this.last = last;
  }
}
