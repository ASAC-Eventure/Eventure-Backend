//package com.LTUC.Eventure.config;
//
//import com.LTUC.Eventure.models.AppUserEntity;
//import com.LTUC.Eventure.repositories.AppUserJPARepository;
//import com.LTUC.Eventure.repositories.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class UserServiceImpl implements UserService {
//
//    @Autowired
//    private AppUserJPARepository userRepository; // Assume you have a UserRepository interface
//
//    @Override
//    public List<AppUserEntity> getAllUsers() {
//        return userRepository.findAll();
//    }
//
//    @Override
//    public AppUserEntity getUserById(Long userId) {
//        return userRepository.findById(userId).orElse(null);
//    }
//
////    @Override
////    public void deleteUserTicket(Long userId, Long ticketId) {
////        AppUserEntity user = userRepository.findById(userId).orElse(null);
////        if (user != null) {
////            user.getTickets().removeIf(ticket -> ticket.getId().equals(ticketId));
////            userRepository.save(user);
////        }
////    }
//}