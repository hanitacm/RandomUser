package randomuser.com.data.agent;

import com.domain.model.UserModel;
import com.domain.respository.GetUsersAgent;
import java.util.List;
import randomuser.com.data.model.mapper.UserDataModelMapper;
import randomuser.com.data.repository.UserRepository;
import rx.Observable;

public class GetUsersAgentImp implements GetUsersAgent {
  private final UserDataModelMapper userDataModelMapper;
  private final UserRepository userRepository;

  public GetUsersAgentImp(UserRepository userRepository, UserDataModelMapper userDataModelMapper) {
    this.userRepository = userRepository;
    this.userDataModelMapper = userDataModelMapper;
  }

  @Override
  public Observable<List<UserModel>> getUsers() {
    return userRepository.getUserList().map(userDataModelMapper);
  }
}
