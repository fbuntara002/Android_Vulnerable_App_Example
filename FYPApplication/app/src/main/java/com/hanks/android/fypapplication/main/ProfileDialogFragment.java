package com.hanks.android.fypapplication.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.fragment.app.DialogFragment;

import com.hanks.android.fypapplication.R;
import com.hanks.android.fypapplication.data.model.LoggedInUser;

import org.w3c.dom.Text;

import java.util.List;

public class ProfileDialogFragment extends DialogFragment {

    public interface ProfileDialogListener {
        public void logOut();
    }

    private ProfileDialogListener listener;
    private TextView displayName;
    private TextView userName;
    private ImageView userPicture;
    private ListView optionsList;

    private String[] options = {"View complete profile", LOG_OUT};

    private static final String ARGS_PROFILE = "Profile";
    private static final String LOG_OUT = "Log out";

    public ProfileDialogFragment() {}

    public static ProfileDialogFragment newInstance(LoggedInUser user) {
        ProfileDialogFragment frag = new ProfileDialogFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARGS_PROFILE, user);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        getDialog().setCanceledOnTouchOutside(true);
        return inflater.inflate(R.layout.profile_dialog_fragment, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //get the data from arguments
        Bundle args = this.getArguments();
        LoggedInUser user = args.getParcelable(ARGS_PROFILE);
        displayName = (TextView) view.findViewById(R.id.user_display_name);
        userName = (TextView) view.findViewById(R.id.user_display_username);
        userPicture = (ImageView) view.findViewById(R.id.profile_picture);
        optionsList = (ListView) view.findViewById(R.id.optionsList);


        displayName.setText(user.getDisplayName());
        userName.setText("id: " + user.getUserId());
        setProfilePicture(user);
        optionsList.setOnItemClickListener((adapter, v, position, arg3) -> {
            String value = (String)adapter.getItemAtPosition(position);
            if (value.equals(LOG_OUT)) {
                listener.logOut();
            }
        });
        optionsList.setAdapter(new ArrayAdapter<String>(view.getContext(),
                android.R.layout.simple_list_item_1,
                options) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView)super.getView(position, convertView, parent);
                if (textView.getText().equals(LOG_OUT)) {
                    textView.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                } else {
                    textView.setTextColor(getResources().getColor(android.R.color.black));
                }
                return textView;
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (ProfileDialogListener) context;
        } catch (ClassCastException e) {
            dismiss();
        }
    }
    private void setProfilePicture(LoggedInUser user){
        Drawable profilePicture = ResourcesCompat.getDrawable(getResources(),
                user.getProfilePicture(), null);
        Bitmap profileBitmap = ((BitmapDrawable)profilePicture).getBitmap();
        profileBitmap = Bitmap.createScaledBitmap(profileBitmap, 200, 200, false);
        RoundedBitmapDrawable roundProfilePicture = RoundedBitmapDrawableFactory.create(getResources(), profileBitmap);
        roundProfilePicture.setCircular(true);
        userPicture.setImageDrawable(roundProfilePicture);
    }
}