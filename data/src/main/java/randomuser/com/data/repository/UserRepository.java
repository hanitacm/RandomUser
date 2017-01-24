package randomuser.com.data.repository;

import com.domain.model.UserModel;
import com.domain.model.UserModelCollection;
import randomuser.com.data.model.mapper.UserDataModelMapper;
import randomuser.com.data.repository.datasource.api.RandomUserApi;
import randomuser.com.data.repository.datasource.cache.RandomUserCache;
import rx.Observable;

public class UserRepository implements com.domain.usecases.UserRepository {

  private final RandomUserApi randomUserApi;
  private final UserDataModelMapper userDataModelMapper;
  private final RandomUserCache randomUserCache;

  public UserRepository(RandomUserApi randomUserApi, UserDataModelMapper userDataModelMapper,
      RandomUserCache randomUserCache) {
    this.randomUserApi = randomUserApi;
    this.userDataModelMapper = userDataModelMapper;
    this.randomUserCache = randomUserCache;
  }

  public rx.Observable<UserModelCollection> getRandomUsers() {
    return randomUserApi.getRandomUsers()
        .doOnNext(userDataModelCollection -> randomUserCache.saveUserList(
            userDataModelCollection.getResults()))
        .map(userDataModelMapper);
  }

  @Override
  public Observable<UserModel> getUserDetail(String name) {
    return randomUserCache.getUserDetail(name)
        .map(userDataModelMapper::mapUserProperties);
  }
}
