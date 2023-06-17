package com.example.toxicapplication.appUser.userDetails.entity.comporator;

import com.example.toxicapplication.appUser.userDetails.entity.AppUser;

import java.util.Comparator;

public class UsernameComparator implements Comparator<AppUser> {
    private final String searchQuery;

    public UsernameComparator(String searchQuery) {
        this.searchQuery = searchQuery.toLowerCase();
    }

    @Override
    public int compare(AppUser user1, AppUser user2) {
        String username1 = user1.getUsername().toLowerCase();
        String username2 = user2.getUsername().toLowerCase();

        boolean containsQuery1 = username1.contains(searchQuery);
        boolean containsQuery2 = username2.contains(searchQuery);

        if (containsQuery1 && containsQuery2) {
            int index1 = username1.indexOf(searchQuery);
            int index2 = username2.indexOf(searchQuery);
            return Integer.compare(index1, index2);
        } else if (containsQuery1) {
            return -1;
        } else if (containsQuery2) {
            return 1;
        } else {
            return 0;
        }
    }
}
