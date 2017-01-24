package randomuser.com.presentation.model.mapper;

import android.net.Uri;
import com.domain.model.UserModel;
import com.domain.model.UserModelCollection;
import java.util.ArrayList;
import java.util.List;
import randomuser.com.presentation.model.UserViewModel;
import rx.functions.Func1;

public class UserViewModelMapper implements Func1<UserModelCollection, List<UserViewModel>> {

  @Override
  public List<UserViewModel> call(UserModelCollection userModelCollection) {
    List<UserViewModel> userViewModelList = new ArrayList<>();

    for (UserModel userModel : userModelCollection.getUsers()) {

      UserViewModel userViewModel = mapUserViewModel(userModel);

      userViewModelList.add(userViewModel);
    }
    return userViewModelList;
  }

  private UserViewModel mapUserViewModel(UserModel userModel) {
    String fullName = userModel.getFirsName() + " " + userModel.getLastName();

    return new UserViewModel(fullName, userModel.getEmail(), userModel.getPhone(),
        Uri.parse(userModel.getThumbnail()));

  }
}
