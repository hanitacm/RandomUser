package randomuser.com.presentation.screen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.squareup.picasso.Picasso;
import randomuser.com.presentation.R;
import randomuser.com.presentation.UserServiceLocator;
import randomuser.com.presentation.model.UserDetailViewModel;
import randomuser.com.presentation.model.UserViewModel;
import randomuser.com.presentation.presenter.UserDetailPresenter;

public class UserDetail extends AppCompatActivity implements UserDetailPresenter.UserDetailView {

  private static final String USER = "user";
  @Bind(R.id.photo) ImageView photo;
  @Bind(R.id.gender) TextView gender;
  @Bind(R.id.fullName) TextView fullName;
  @Bind(R.id.email) TextView email;
  @Bind(R.id.street) TextView street;
  @Bind(R.id.city) TextView city;
  @Bind(R.id.registeredDate) TextView date;
  @Bind(R.id.state) TextView state;

  private UserDetailPresenter presenter;

  public static void open(Context context, UserViewModel user) {
    Intent intent = new Intent(context, UserDetail.class);

    intent.putExtra(USER, user);

    context.startActivity(intent);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initUI();
    createPresenter();
  }

  private void initUI() {
    setContentView(R.layout.activity_user_detail);
    ButterKnife.bind(this);
  }

  private void createPresenter() {
    UserServiceLocator userServiceLocator = new UserServiceLocator(getApplicationContext());
    presenter = userServiceLocator.getUserDetailPresenter();
  }

  @Override
  protected void onStart() {
    super.onStart();
    presenter.onStart(this);
  }

  @Override
  protected void onResume() {
    super.onResume();
    String userId = getIntent().getStringExtra(USER);
    presenter.getUserDetail(userId);
  }

  @Override
  protected void onStop() {
    super.onStop();
    presenter.onStop();
  }

  @Override
  public void renderUserDetail(UserDetailViewModel user) {
    gender.setText(user.getGender());
    fullName.setText(user.getName());
    email.setText(user.getEmail());
    street.setText(user.getStreet());
    state.setText(user.getState());
    city.setText(user.getCity());
    date.setText(user.getRegisteredDate());
    Picasso.with(this).load(user.getPhoto()).into(photo);
  }
}
