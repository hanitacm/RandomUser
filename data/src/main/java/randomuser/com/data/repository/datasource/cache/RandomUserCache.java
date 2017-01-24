package randomuser.com.data.repository.datasource.cache;

import android.util.Log;
import com.domain.model.UserModel;
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
        fileManager.write(user.getEmail(), user);
      }
    } catch (Exception e) {
      result =false;
    }

    return Observable.just(result);
  }

  public Observable<UserDataModel> getUserDetail(String name) {

    UserDataModel userData = (UserDataModel) fileManager.read(name);

    return Observable.just(userData);
  }
}
