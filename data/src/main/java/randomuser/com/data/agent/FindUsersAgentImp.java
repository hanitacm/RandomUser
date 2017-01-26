package randomuser.com.data.agent;

import com.domain.model.UserModel;
import com.domain.respository.FindUsersAgent;
import java.util.List;
import randomuser.com.data.model.mapper.UserDataModelMapper;
import randomuser.com.data.repository.UserRepository;
import rx.Observable;

public class FindUsersAgentImp implements FindUsersAgent {
  private final UserRepository userRepository;
  private final UserDataModelMapper userDataModelMapper;

  public FindUsersAgentImp(UserRepository userRepository, UserDataModelMapper userDataModelMapper) {
    this.userRepository = userRepository;
    this.userDataModelMapper = userDataModelMapper;
  }

  @Override
  public Observable<List<UserModel>> searchUsers(String queryText) {
    return userRepository.searchUsers(queryText).map(userDataModelMapper);
  }
}
