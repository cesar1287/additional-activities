package comcesar1287.github.www.additionalactivities.controller.domain;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {

    public String name, email, profile_pic;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String name, String email, String profile_pic) {
        this.name = name;
        this.email = email;
        this.profile_pic = profile_pic;
    }

}