package randomuser.com.data.repository.datasource.sharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.Map;
import rx.Observable;

public class RandomUserPreferences {

  private static final String DELETED_USER = "DeletedUser";
  private static RandomUserPreferences instance;
  private SharedPreferences preferences;

  private RandomUserPreferences(Context context) {
    this.preferences = context.getSharedPreferences(DELETED_USER, Context.MODE_PRIVATE);
  }

  public static RandomUserPreferences getInstance(Context context) {
    if (instance == null) {
      instance = new RandomUserPreferences(context);
    }
    return instance;
  }

  public Observable<Boolean> saveDeleteUser(String email) {

    SharedPreferences.Editor editor = preferences.edit();

    editor.putString(email, "");

    return Observable.just(editor.commit());
  }

  public Observable<Map<String, ?>> getDeletedUser() {
       return rx.Observable.just(preferences.getAll());
  }
}
