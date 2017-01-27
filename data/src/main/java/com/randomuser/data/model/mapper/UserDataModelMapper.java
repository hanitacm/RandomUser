package com.randomuser.data.model.mapper;

import android.support.annotation.NonNull;
import com.domain.model.UserModel;
import java.util.ArrayList;
import java.util.List;
import com.randomuser.data.model.UserDataModel;
import rx.functions.Func1;

public class UserDataModelMapper implements Func1<List<UserDataModel>, List<UserModel>> {
  @Override
  public List<UserModel> call(List<UserDataModel> userDataModelCollection) {
    List<UserModel> usersModelList = new ArrayList<>();

    for (UserDataModel user : userDataModelCollection) {
      usersModelList.add(mapUserProperties(user));
    }

    return usersModelList;
  }

  @NonNull
  public UserModel mapUserProperties(UserDataModel user) {
    UserModel userModel = new UserModel();
    userModel.setGender(user.getGender());
    userModel.setFirsName(user.getName().getFirst());
    userModel.setLastName(user.getName().getLast());
    userModel.setCity(user.getLocation().getCity());
    userModel.setState(user.getLocation().getState());
    userModel.setPostCode(user.getLocation().getPostcode());
    userModel.setStreet(user.getLocation().getStreet());
    userModel.setEmail(user.getEmail());
    userModel.setPhone(user.getPhone());
    userModel.setRegistered(user.getRegistered());
    userModel.setThumbnail(user.getPicture().getMedium());
    userModel.setPhoto(user.getPicture().getLarge());
    return userModel;
  }
}
