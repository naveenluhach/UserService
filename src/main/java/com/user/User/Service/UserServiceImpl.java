package com.user.User.Service;

import com.user.User.Cache.CacheImpl;
import com.user.User.Model.User;
import com.user.User.Repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private CacheImpl cacheClient;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findUserById(int id) {
        try {
            String key = "users"+id;
            //check cache and find the user and return it
            Jedis jedis = cacheClient.getJedis();
            Map<String, String> userMapFromCache =  jedis.hgetAll(key);
            if(userMapFromCache.size()!=0){
                //construct the user object
                User user = new User();
                user.setId(id);
                user.setEmail(userMapFromCache.get("email"));
                user.setMobile(userMapFromCache.get("mobile"));
                user.setName(userMapFromCache.get("name"));
                user.setKyc_flag(Boolean.valueOf(userMapFromCache.get("kyc_flag")));
                LOGGER.info("Data is returned from cache : {}", user.toString());
                return Optional.of(user);
            }else {
                //user not found in cache
                //go to db-mysql
                // add this entry to cache
                // return it
                Optional<User> user = userRepository.findById(id);
                if (user != null) {
                    Map<String, String> userMap = new HashMap<>();
                    userMap.put("name", user.get().getName());
                    userMap.put("email", user.get().getEmail());
                    userMap.put("mobile", user.get().getMobile());
                    userMap.put("kyc_flag", user.get().getKyc_flag().toString());
                    jedis.hset(key, userMap);
                }
                LOGGER.info("Data is returned from MySQL : {}", user.toString());
                return user;
            }
        }catch(Exception exception){
            //new UserNotFoundException()
           LOGGER.error("User not found with request id : {}", id);
           return null;
        }
    }


}
