package com.backend.happening;

import com.backend.shared.Location;
import com.backend.user.Comment;
import com.backend.user.User;

import java.util.List;

public interface Happening {
    String title();
    String description();
    User user();
    Location location();
    List<Comment> comments();
    int likes();
    int dislikes();
}
