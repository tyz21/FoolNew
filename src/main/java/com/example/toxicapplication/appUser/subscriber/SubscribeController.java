package com.example.toxicapplication.appUser.subscriber;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/subscribe")
public class SubscribeController {
    private SubscribeService subscribeService;

    @PostMapping("/{idProfile}/{idUser}")
    public void subscribe(@PathVariable Long idProfile, @PathVariable Long idUser) {
        subscribeService.subscribe(idProfile, idUser);
    }

//
//    @GetMapping("/{idUser}")
//    public List<ProfileUserEntity> getSubscriber(@PathVariable Long idUser){
//       return subscribeService.getSubscriber(idUser);
//    }
}
