package randomuser.com.data.repository;

import com.domain.model.UserModelCollection;
import randomuser.com.data.model.mapper.UserDataModelMapper;
import randomuser.com.data.repository.datasource.api.RandomUserApi;
import randomuser.com.data.repository.datasource.cache.RandomUserCache;

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
}
