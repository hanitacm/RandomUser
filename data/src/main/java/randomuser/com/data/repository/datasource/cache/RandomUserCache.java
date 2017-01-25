package randomuser.com.data.repository.datasource.cache;

import android.support.annotation.NonNull;
import java.util.List;
import randomuser.com.data.model.UserDataModel;
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
    return user.getName().getFirst() + "_" + user.getName().getLast() + "_" + user.getEmail();
  }

  public Observable<UserDataModel> getUserDetail(String name) {

    UserDataModel userData = (UserDataModel) fileManager.read(name);

    return Observable.just(userData);
  }

  public Observable<Boolean> deleteUser(String name) {

    return fileManager.delete(name);
  }

  public Observable<List<UserDataModel>> findUsers(String queryText) {
    return fileManager.findUsers(queryText);
  }

  public Observable<List<UserDataModel>> getUserList() {
    return fileManager.readAllUsers();
  }


}
