package com.randomuser.data.repository;

import android.support.annotation.NonNull;
import com.domain.model.UserModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import com.randomuser.data.agent.GetRemoteUsersAgentImp;
import com.randomuser.data.model.UserDataModel;
import com.randomuser.data.model.UserDataModelCollection;
import com.randomuser.data.model.mapper.UserDataModelMapper;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class GetRemoteUsersAgentImpShould {

  @Mock private UserDataModelMapper userDataMapper;
  @Mock private UserRepository userRepository;

  private GetRemoteUsersAgentImp agent;

  @Before
  public void setUp() throws Exception {
    initMocks(this);
    agent = new GetRemoteUsersAgentImp(userRepository, userDataMapper);
  }

  @Test
  public void get_users_from_api_and_save_on_cache_if_they_were_not_deleted_previously_and_return_users_from_cache() {
    UserDataModelCollection userDataModelCollection = givenAUserDataModelCollection();
    List<UserDataModel> givenACachedUserDataModelList = givenACachedUserDataModelList();
    Map<String, ?> deletedUsers = givenDeletedUsers();
    List<UserDataModel> processedUsers = givenResultCollection();
    ArrayList<UserModel> userModelsResult = new ArrayList<>();

    given(userRepository.getRemoteUsers()).willReturn(rx.Observable.just(userDataModelCollection));
    given(userRepository.getDeletedUser()).willReturn(rx.Observable.just(deletedUsers));
    given(userRepository.getUserList()).willReturn(
        rx.Observable.just(givenACachedUserDataModelList));

    given(userDataMapper.call(Matchers.anyListOf(UserDataModel.class))).willReturn(
        userModelsResult);

    Assert.assertEquals(userModelsResult, agent.getUsers().toBlocking().first());

    verify(userRepository, times(1)).getRemoteUsers();
    verify(userRepository, times(2)).getUserList();
    verify(userRepository, times(1)).getDeletedUser();
    verify(userRepository, times(1)).saveUserList(processedUsers);

    verify(userDataMapper, times(1)).call(Matchers.anyListOf(UserDataModel.class));
  }

  @NonNull
  private Map<String, String> givenDeletedUsers() {
    Map<String, String> userDeleted = new HashMap<>();
    userDeleted.put("user8@user8.com", "");
    userDeleted.put("user11@user11.com", "");
    userDeleted.put("user12@user12.com", "");
    return userDeleted;
  }

  @NonNull
  private UserDataModelCollection givenAUserDataModelCollection() {
    UserDataModelCollection userDataModelCollection = new UserDataModelCollection();
    List<UserDataModel> userDataModelList = givenAUserDataModelList();

    userDataModelCollection.setResults(userDataModelList);
    return userDataModelCollection;
  }

  private List<UserDataModel> givenACachedUserDataModelList() {
    Integer[] userNames = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };

    return createUsers(userNames);
  }

  private List<UserDataModel> givenAUserDataModelList() {

    Integer[] userNames = { 6, 7, 8, 9, 10, 11, 12, 13, 14 };

    return createUsers(userNames);
  }

  private List<UserDataModel> givenResultCollection() {
    Integer[] userNames = { 10, 13, 14 };

    return createUsers(userNames);
  }

  @NonNull
  private List<UserDataModel> createUsers(Integer[] userNames) {
    List<UserDataModel> userDataModelList = new ArrayList<>();
    for (Integer name : userNames) {
      UserDataModel userDataModel = new UserDataModel();
      userDataModel.setEmail("user" + name + "@user" + name + ".com");
      userDataModelList.add(userDataModel);
    }
    return userDataModelList;
  }
}