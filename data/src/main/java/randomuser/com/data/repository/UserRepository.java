package randomuser.com.data.repository;

import com.domain.model.UserModel;
import com.domain.model.UserModelCollection;
import randomuser.com.data.model.mapper.UserDataModelMapper;
import randomuser.com.data.repository.datasource.api.RandomUserApi;
import randomuser.com.data.repository.datasource.cache.RandomUserCache;
import randomuser.com.data.repository.datasource.sharedPreferences.RandomUserPreferences;
import rx.Observable;
import rx.functions.Func1;

public class UserRepository implements com.domain.usecases.UserRepository {

  private final RandomUserApi randomUserApi;
  private final UserDataModelMapper userDataModelMapper;
  private final RandomUserCache randomUserCache;
  private final RandomUserPreferences randomUserPreferences;

  public UserRepository(RandomUserApi randomUserApi, UserDataModelMapper userDataModelMapper,
      RandomUserCache randomUserCache, RandomUserPreferences randomUserPreferences) {
    this.randomUserApi = randomUserApi;
    this.userDataModelMapper = userDataModelMapper;
    this.randomUserCache = randomUserCache;
    this.randomUserPreferences = randomUserPreferences;
  }

  public rx.Observable<UserModelCollection> getRandomUsers() {
    return randomUserApi.getRandomUsers()
        .doOnNext(userDataModelCollection -> randomUserCache.saveUserList(
            userDataModelCollection.getResults()))
        .map(userDataModelMapper);
  }

  @Override
  public Observable<UserModel> getUserDetail(String name) {
    return randomUserCache.getUserDetail(name).map(userDataModelMapper::mapUserProperties);
  }

  @Override
  public Observable<Boolean> deleteUser(String name) {
    return randomUserCache.deleteUser(name).flatMap(new Func1<Boolean, Observable<Boolean>>() {

      @Override
      public Observable<Boolean> call(Boolean aBoolean) {
          Observable<Boolean> result = Observable.just(false);
        if (aBoolean) {
          result= randomUserPreferences.saveDeleteUser(name);
        }
        return result;
      }
    });
  }
}
