package randomuser.com.randomuser;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import java.util.ArrayList;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserLisViewHolder> {

  ArrayList<User> users = new ArrayList();

  @Override
  public UserLisViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_user_list, parent, false);
    return new UserLisViewHolder(view);
  }

  @Override
  public void onBindViewHolder(UserLisViewHolder holder, int position) {
    User userData = users.get(position);

    holder.photo.setImageURI(userData.getPhoto());
    holder.name.setText(userData.getName());
    holder.email.setText(userData.getEmail());
    holder.phone.setText(userData.getPhone());
  }

  @Override
  public int getItemCount() {
    return users.size();
  }

  public class UserLisViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.photo) ImageView photo;
    @BindView(R.id.name) TextView name;
    @BindView(R.id.email) TextView email;
    @BindView(R.id.phone) TextView phone;

    public UserLisViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
