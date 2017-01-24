package randomuser.com.data.repository.datasource.sharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;
import rx.Observable;

public class RandomUserPreferences {

  private static final String USER_ID = "UserId";
  private static final String DELETED_USER = "DeletedUser";
  private final Context context;
  private SharedPreferences preferences;

  private RandomUserPreferences(Context context) {
    this.context = context;
  }

  public static RandomUserPreferences getInstance(Context context) {
    return new RandomUserPreferences(context);
  }

  public Observable<Boolean> saveDeleteUser(String name) {
    preferences = context.getSharedPreferences(DELETED_USER, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = preferences.edit();

    editor.putString(USER_ID, name);

    return Observable.just(editor.commit());
  }
}
