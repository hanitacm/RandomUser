package randomuser.com.data.repository;

import com.domain.model.UserModelCollection;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import randomuser.com.data.model.UserDataModel;
import randomuser.com.data.model.UserDataModelCollection;
import randomuser.com.data.model.mapper.UserDataModelMapper;
import randomuser.com.data.repository.datasource.api.RandomUserApi;
import randomuser.com.data.repository.datasource.cache.RandomUserCache;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserRepositoryShould {

  @Mock private RandomUserApi randomUserApi;
  @Mock private UserDataModelMapper userDataMapper;
  @Mock private RandomUserCache randomUserCache;
  @Mock private UserRepository repository;

  @Before
  public void setUp() throws Exception {
    initMocks(this);
    repository = new UserRepository(randomUserApi, userDataMapper, randomUserCache);
  }

  @Test
  public void get_users_from_api_and_save_on_cache() {
    UserDataModelCollection userDataModelCollection = new UserDataModelCollection();
    List<UserDataModel> userDataModelList = new ArrayList<>();
    userDataModelList.add(new UserDataModel());

    userDataModelCollection.setResults(userDataModelList);

    given(randomUserApi.getRandomUsers()).willReturn(rx.Observable.just(userDataModelCollection));
    given(randomUserCache.saveUserList(userDataModelCollection.getResults())).willReturn(rx.Observable.just(true));
    given(userDataMapper.call(userDataModelCollection)).willReturn(new UserModelCollection());

    repository.getRandomUsers();

    verify(randomUserApi, times(1)).getRandomUsers();
    verify(randomUserCache, times(1)).saveUserList(userDataModelCollection.getResults());
    verify(userDataMapper.call(userDataModelCollection));
  }
}