package randomuser.com.presentation.model.mapper;

import android.net.Uri;
import com.domain.model.UserModel;
import java.util.ArrayList;
import java.util.List;
import randomuser.com.presentation.model.UserViewModel;
import rx.functions.Func1;

public class UserViewModelMapper implements Func1<List<UserModel>, List<UserViewModel>> {

  @Override
  public List<UserViewModel> call(List<UserModel> userModelCollection) {
    List<UserViewModel> userViewModelList = new ArrayList<>();

    for (UserModel userModel : userModelCollection) {

      UserViewModel userViewModel = mapUserViewModel(userModel);

      userViewModelList.add(userViewModel);
    }
    return userViewModelList;
  }

  private UserViewModel mapUserViewModel(UserModel userModel) {

    return new UserViewModel(userModel.getFirsName(), userModel.getEmail(), userModel.getPhone(),
        Uri.parse(userModel.getThumbnail()), userModel.getLastName());
  }
}
