package com.hanks.android.fypapplication.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser implements Parcelable {

    private String userId;
    private String displayName;
    private int profilePicture;

    public static final Creator<LoggedInUser> CREATOR = new Creator<LoggedInUser>() {
        @Override
        public LoggedInUser createFromParcel(Parcel in) {
            return new LoggedInUser(in);
        }

        @Override
        public LoggedInUser[] newArray(int size) {
            return new LoggedInUser[size];
        }
    };

    protected LoggedInUser(Parcel in) {
        userId = in.readString();
        displayName = in.readString();
        profilePicture = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userId);
        dest.writeString(displayName);
        dest.writeInt(profilePicture);
    }

    public LoggedInUser(String userId, String displayName, int profilePicture) {
        this.userId = userId;
        this.displayName = displayName;
        this.profilePicture = profilePicture;

    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getProfilePicture() {return profilePicture; }
}
