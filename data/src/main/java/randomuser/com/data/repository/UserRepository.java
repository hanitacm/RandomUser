package randomuser.com.data.repository;

import com.domain.model.UserModel;
import com.domain.model.UserModelCollection;
import java.util.ArrayList;
import java.util.List;
import randomuser.com.data.model.UserDataModel;
import randomuser.com.data.model.UserDataModelCollection;
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
    Observable<List<UserDataModel>> apiUserDataModelList = randomUserApi.getRandomUsers()
        .map(UserDataModelCollection::getResults);

    Observable<List<UserDataModel>> cacheUserDataModelList = randomUserCache.getUserList()
        .map(UserDataModelCollection::getResults);

    return Observable.zip(apiUserDataModelList, cacheUserDataModelList,
        randomUserPreferences.getDeletedUser(),
        (userDataModelApiList, userDataModelCacheList, stringMap) -> {
          List<UserDataModel> users = new ArrayList<>();
          for(UserDataModel user : userDataModelApiList){
             if(!randomUserCache.isCached(user) && !stringMap.containsKey(user.getEmail())){
               users.add(user);
             }
          }
          UserDataModelCollection userDataModelCollection = new UserDataModelCollection();
          userDataModelCollection.setResults(users);

          return userDataModelCollection;
        })
        .doOnNext(userDataModelCollection -> randomUserCache.saveUserList(
            userDataModelCollection.getResults()))
        .flatMap(cachedUserDataModelList ->  randomUserCache.getUserList())
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
          result = randomUserPreferences.saveDeleteUser(name);
        }
        return result;
      }
    });
  }

  @Override
  public Observable<UserModelCollection> searchUsers(String queryText) {
    return randomUserCache.findUsers(queryText).map(userDataModelMapper);
  }
}
