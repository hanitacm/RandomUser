package randomuser.com.data.agent;

import com.domain.model.UserModel;
import randomuser.com.data.model.mapper.UserDataModelMapper;
import randomuser.com.data.repository.UserRepository;
import rx.Observable;

public class GetUserDetailAgentImp implements com.domain.usecases.GetUserDetailAgent {
  private final UserRepository userRepository;
  private final UserDataModelMapper userDataModelMapper;

  public GetUserDetailAgentImp(UserRepository userRepository,
      UserDataModelMapper userDataModelMapper) {
    this.userRepository = userRepository;
    this.userDataModelMapper = userDataModelMapper;
  }

  @Override
  public Observable<UserModel> getUserDetail(String name) {
    return userRepository.getUserDetail(name).map(userDataModelMapper::mapUserProperties);
  }
}
