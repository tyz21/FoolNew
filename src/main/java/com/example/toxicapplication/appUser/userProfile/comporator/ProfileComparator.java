package com.example.toxicapplication.appUser.userProfile.comporator;

import com.example.toxicapplication.appUser.userProfile.ProfileUserEntity;

import java.util.Comparator;

public class ProfileComparator implements Comparator<ProfileUserEntity> {
    private final String searchQuery;

    public ProfileComparator(String searchQuery) {
        this.searchQuery = searchQuery.toLowerCase();
    }

    @Override
    public int compare(ProfileUserEntity user1, ProfileUserEntity user2) {
        String username1 = user1.getProfileName().toLowerCase();
        String username2 = user2.getProfileName().toLowerCase();

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
