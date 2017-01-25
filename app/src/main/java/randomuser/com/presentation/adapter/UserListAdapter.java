package randomuser.com.presentation.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import randomuser.com.presentation.R;
import randomuser.com.presentation.model.UserViewModel;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserLisViewHolder> {

  private final Context context;
  private ArrayList<UserViewModel> users;
  private OnItemClickListener onItemClickListener;

  public UserListAdapter(Context context) {
    this.users = new ArrayList<>();
    this.context = context;
  }

  @Override
  public UserLisViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.row_user_list, parent, false);
    return new UserLisViewHolder(view);
  }

  @Override
  public void onBindViewHolder(UserLisViewHolder holder, int position) {
    UserViewModel userData = users.get(position);
    Picasso.with(context).load(userData.getPhoto()).into(holder.photo);
    holder.photo.setOnClickListener(
        view -> UserListAdapter.this.onItemClickListener.onClickUser(userData));
    holder.name.setText(userData.getName());
    holder.email.setText(userData.getEmail());
    holder.phone.setText(userData.getPhone());
    holder.delete.setOnClickListener(view -> {
      if (onItemClickListener != null) {
        onItemClickListener.onDeleteUser(userData);
      }
    });
  }

  @Override
  public int getItemCount() {
    return users.size();
  }

  public void setUsers(List<UserViewModel> o) {
    users.clear();
    users.addAll(o);
    notifyDataSetChanged();
  }

  public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
    this.onItemClickListener = onItemClickListener;
  }

  public void deleteItem(UserViewModel userSelected) {
    users.remove(users.indexOf(userSelected));
    notifyDataSetChanged();
  }

  public interface OnItemClickListener {
    void onClickUser(UserViewModel userSelected);

    void onDeleteUser(UserViewModel userSelected);
  }

  public class UserLisViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.photo) ImageView photo;
    @Bind(R.id.name) TextView name;
    @Bind(R.id.email) TextView email;
    @Bind(R.id.phone) TextView phone;
    @Bind(R.id.bt_remove) ImageButton delete;

    public UserLisViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
