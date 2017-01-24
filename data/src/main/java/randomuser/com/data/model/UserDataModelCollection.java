package randomuser.com.data.model;

import java.util.List;

public class UserDataModelCollection {
  private List<UserDataModel> results;
  private InfoDataModel info;


  public List<UserDataModel> getResults() {
    return results;
  }

  public void setResults(List<UserDataModel> results) {
    this.results = results;
  }

  public InfoDataModel getInfo() {
    return info;
  }

  public void setInfo(InfoDataModel info) {
    this.info = info;
  }
}
