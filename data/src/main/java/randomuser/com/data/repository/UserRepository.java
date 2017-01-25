package randomuser.com.data.repository;

import java.util.List;
import java.util.Map;
import randomuser.com.data.model.UserDataModel;
import randomuser.com.data.model.UserDataModelCollection;
import randomuser.com.data.repository.datasource.api.RandomUserApi;
import randomuser.com.data.repository.datasource.cache.RandomUserCache;
import randomuser.com.data.repository.datasource.sharedPreferences.RandomUserPreferences;
import rx.Observable;

public class UserRepository {

  private final RandomUserApi randomUserApi;
  private final RandomUserCache randomUserCache;
  private final RandomUserPreferences randomUserPreferences;

  public UserRepository(RandomUserApi randomUserApi, RandomUserCache randomUserCache,
      RandomUserPreferences randomUserPreferences) {

    this.randomUserApi = randomUserApi;
    this.randomUserCache = randomUserCache;
    this.randomUserPreferences = randomUserPreferences;
  }

  public Observable<UserDataModelCollection> getRemoteUsers() {
    return randomUserApi.getRandomUsers();
  }

  public Observable<UserDataModel> getUserDetail(String nameUser, String surname, String email) {
    return randomUserCache.getUserDetail(nameUser,surname,email);
  }

  public Observable<Boolean> deleteUser(String nameUser, String surname, String email) {
    return randomUserCache.deleteUser(nameUser, surname, email).flatMap(isDeletedOnCache -> {
      Observable<Boolean> result = Observable.just(false);
      if (isDeletedOnCache) {
        result = randomUserPreferences.saveDeleteUser(email);
      }
      return result;
    });
  }

  public Observable<List<UserDataModel>> searchUsers(String queryText) {
    return randomUserCache.findUsers(queryText);
  }

  public Observable<List<UserDataModel>> getUserList() {
    return randomUserCache.getUserList();
  }

  public Observable<Map<String, ?>> getDeletedUser() {
    return randomUserPreferences.getDeletedUser();
  }

  public Observable<Boolean> saveUserList(List<UserDataModel> users) {
    return randomUserCache.saveUserList(users);
  }
}
