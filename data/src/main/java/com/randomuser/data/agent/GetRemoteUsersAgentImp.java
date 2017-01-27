package com.randomuser.data.agent;

import android.support.annotation.NonNull;
import com.domain.model.UserModel;
import com.domain.respository.GetRemoteUsersAgent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.randomuser.data.model.UserDataModel;
import com.randomuser.data.model.UserDataModelCollection;
import com.randomuser.data.model.mapper.UserDataModelMapper;
import com.randomuser.data.repository.UserRepository;
import rx.Observable;

public class GetRemoteUsersAgentImp implements GetRemoteUsersAgent {
  private final UserRepository userRepository;
  private final UserDataModelMapper userDataModelMapper;

  public GetRemoteUsersAgentImp(UserRepository userRepository, UserDataModelMapper userDataModelMapper) {
    this.userRepository = userRepository;
    this.userDataModelMapper = userDataModelMapper;
  }

  @Override
  public Observable<List<UserModel>> getUsers() {
    Observable<List<UserDataModel>> apiUserModelList =
        userRepository.getRemoteUsers().map(UserDataModelCollection::getResults);

    Observable<List<UserDataModel>> cacheUserModelList = userRepository.getUserList();

    return Observable.zip(apiUserModelList, cacheUserModelList, userRepository.getDeletedUser(),
        this::processUsersCollections)
        .doOnNext(userRepository::saveUserList)
        .flatMap(cachedUserDataModelList -> userRepository.getUserList())
        .map(userDataModelMapper);
  }

  @NonNull
  private List<UserDataModel> processUsersCollections(List<UserDataModel> userDataModelApiList,
      List<UserDataModel> userDataModelCacheList, Map<String, ?> stringMap) {
    List<UserDataModel> users = new ArrayList<>();
    for (UserDataModel user : userDataModelApiList) {
      if (!userDataModelCacheList.contains(user) && !stringMap.containsKey(user.getEmail())) {
        users.add(user);
      }
    }
    return users;
  }
}
