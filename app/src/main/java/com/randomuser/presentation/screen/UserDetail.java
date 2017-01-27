package com.randomuser.presentation.screen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.squareup.picasso.Picasso;
import com.randomuser.presentation.R;
import com.randomuser.presentation.UserServiceLocator;
import com.randomuser.presentation.model.UserDetailViewModel;
import com.randomuser.presentation.presenter.UserDetailPresenter;

public class UserDetail extends AppCompatActivity implements UserDetailPresenter.UserDetailView {

  public static final String NAME = "name";
  public static final String SURNAME = "surname";
  public static final String EMAIL = "email";
  @Bind(R.id.photo) ImageView photo;
  @Bind(R.id.gender) EditText gender;
  @Bind(R.id.fullName) EditText fullName;
  @Bind(R.id.email) EditText email;
  @Bind(R.id.street) EditText street;
  @Bind(R.id.city) EditText city;
  @Bind(R.id.registeredDate) EditText date;
  @Bind(R.id.state) EditText state;

  private UserDetailPresenter presenter;

  public static void open(Context context, String name, String surname, String email) {
    Intent intent = new Intent(context, UserDetail.class);

    intent.putExtra(NAME, name);
    intent.putExtra(SURNAME, surname);
    intent.putExtra(EMAIL, email);

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
    String name = getIntent().getStringExtra(NAME);
    String surname = getIntent().getStringExtra(SURNAME);
    String email = getIntent().getStringExtra(EMAIL);

    presenter.getUserDetail(name, surname, email);
  }

  @Override
  protected void onDestroy() {
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
