package com.example.service;


import com.example.repository.UserCacheRepository;
import com.example.repository.UserRepository;
import com.example.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserService
{
   private static final String USER_CREATE_TOPIC = "user_created";
    @Autowired
    UserRepository userRepository;

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    UserCacheRepository userCacheRepository;
    public void create(User user) throws JsonProcessingException {

        userRepository.save(user);

        JSONObject userObj = new JSONObject();
        userObj.put("phone",user.getPhone());

        userObj.put("email", user.getEmail());
        kafkaTemplate.send(USER_CREATE_TOPIC,this.objectMapper.writeValueAsString(userObj));

    }

    public User get(int userID) throws Exception {
        User user = userCacheRepository.get(userID);
        if(user != null){
            return user;
        }
       user = userRepository.findById(userID).orElseThrow(()-> new Exception());
        userCacheRepository.set(user);

        return user;



    }
    public User getByPhone(String phone) throws Exception {
        return userRepository.findByPhone(phone);




    }

}
