package randomuser.com.data.repository;

import com.domain.model.UserModelCollection;
import randomuser.com.data.model.mapper.UserDataModelMapper;
import randomuser.com.data.repository.datasource.api.RandomUserApi;

public class UserRepository implements com.domain.usecases.UserRepository {

  private final RandomUserApi randomUserApi;
  private final UserDataModelMapper userDataModelMapper;

  public UserRepository(RandomUserApi randomUserApi, UserDataModelMapper userDataModelMapper) {
    this.randomUserApi = randomUserApi;
    this.userDataModelMapper = userDataModelMapper;
  }

  public rx.Observable<UserModelCollection> getRandomUsers() {
    return randomUserApi.getRandomUsers().map(userDataModelMapper);
  }
}
