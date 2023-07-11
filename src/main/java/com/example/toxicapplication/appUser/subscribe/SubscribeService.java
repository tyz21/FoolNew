package com.example.toxicapplication.appUser.subscribe;

import com.example.toxicapplication.appUser.userDetails.entity.AppUser;
import com.example.toxicapplication.appUser.userDetails.repository.AppUserRepository;
import com.example.toxicapplication.appUser.userProfile.ProfileUserEntity;
import com.example.toxicapplication.appUser.userProfile.ProfileUserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

@Service
@AllArgsConstructor
@Slf4j
public class SubscribeService {
    private ProfileUserRepository profileUserRepository;
    private SubscribeRepository subscribeRepository;
    private AppUserRepository appUserRepository;
    private final TransactionTemplate transactionTemplate;
    public void subscribe(Long idProfile, Long idUser) {
        ProfileUserEntity profileUser = profileUserRepository.findById(idProfile).orElse(null);
        if (profileUser == null) {
            return;
        }

        AppUser appUser = appUserRepository.findById(idUser).orElse(null);
        if (appUser == null) {
            return;
        }

        Subscribe subscribe = new Subscribe();
        subscribe.setProfileUserEntity(profileUser);
        subscribe.setAppUser(appUser);
        subscribeRepository.save(subscribe);
    }
}

//    public List<ProfileUserEntity> getSubscriber(Long idUser) {
//        return transactionTemplate.execute(status -> {
//            List<ProfileUserEntity> profileUserEntities = new ArrayList<>();
//            List<Subscribe> subscribes = subscribeRepository.findAll();
//            for (Subscribe subscribe : subscribes) {
//                if (subscribe.getAppUser() != null && subscribe.getProfileUserEntity() != null) {
//                    if (subscribe.getAppUser().getId().equals(idUser)) {
//                        ProfileUserEntity profileUser = subscribe.getProfileUserEntity();
//                        if (!profileUserEntities.contains(profileUser)) {
//                            profileUserEntities.add(profileUser);
//                        }
//                    }
//                }
//            }
//            return profileUserEntities;
//        });
//    }
//
//}
