package com.randomuser.data.repository.datasource.cache;

import android.support.annotation.NonNull;
import java.util.List;
import com.randomuser.data.model.UserDataModel;
import rx.Observable;

public class RandomUserCache {
  private final FileManager fileManager;

  public RandomUserCache(FileManager fileManager) {
    this.fileManager = fileManager;
  }

  public Observable<Boolean> saveUserList(List<UserDataModel> userDataModelCollection) {
    boolean result = true;
    try {
      for (UserDataModel user : userDataModelCollection) {
        String fileName = getKey(user);
        fileManager.write(fileName, user);
      }
    } catch (Exception e) {
      result = false;
    }

    return Observable.just(result);
  }

  @NonNull
  private String getKey(UserDataModel user) {
    return getKey(user.getName().getFirst(), user.getName().getLast(), user.getEmail());
  }

  private String getKey(String nameUser, String surname, String email) {
    return nameUser + "_" + surname + "_" + email;
  }

  public Observable<UserDataModel> getUserDetail(String nameUser, String surname, String email) {

    UserDataModel userData = (UserDataModel) fileManager.read(getKey(nameUser, surname, email));

    return Observable.just(userData);
  }

  public Observable<Boolean> deleteUser(String nameUser, String surname, String email) {

    return fileManager.delete(getKey(nameUser, surname, email));
  }

  public Observable<List<UserDataModel>> findUsers(String queryText) {
    return fileManager.findUsers(queryText);
  }

  public Observable<List<UserDataModel>> getUserList() {
    return fileManager.readAllUsers();
  }
}
