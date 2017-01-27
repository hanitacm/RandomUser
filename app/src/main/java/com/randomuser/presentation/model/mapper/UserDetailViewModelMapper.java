package com.randomuser.presentation.model.mapper;

import android.net.Uri;
import com.domain.model.UserModel;
import com.randomuser.presentation.model.UserDetailViewModel;
import rx.functions.Func1;

public class UserDetailViewModelMapper implements Func1<UserModel, UserDetailViewModel> {
  @Override
  public UserDetailViewModel call(UserModel userModel) {
    String fullName = userModel.getFirsName() + " " + userModel.getLastName();
    return new UserDetailViewModel(userModel.getGender(), Uri.parse(userModel.getPhoto()), fullName, userModel.getEmail(),
        userModel.getStreet(), userModel.getCity(), userModel.getState(),
        userModel.getRegistered());
  }
}
