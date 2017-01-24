package randomuser.com.data.model.mapper;

import android.support.annotation.NonNull;
import com.domain.model.UserModel;
import com.domain.model.UserModelCollection;
import java.util.ArrayList;
import java.util.List;
import randomuser.com.data.model.UserDataModel;
import randomuser.com.data.model.UserDataModelCollection;
import rx.functions.Func1;

public class UserDataModelMapper implements Func1<UserDataModelCollection, UserModelCollection> {
  @Override
  public UserModelCollection call(UserDataModelCollection userDataModelCollection) {
    UserModelCollection userModelCollection = new UserModelCollection();

    userModelCollection.setUsers(mapUserData(userDataModelCollection));

    return userModelCollection;
  }

  private List<UserModel> mapUserData(UserDataModelCollection userDataModelCollection) {
    List<UserModel> usersModelList = new ArrayList<>();

    for (UserDataModel user : userDataModelCollection.getResults()) {
      usersModelList.add(mapUserProperties(user));
    }

    return usersModelList;
  }

  @NonNull
  private UserModel mapUserProperties(UserDataModel user) {
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
    return userModel;
  }
}
