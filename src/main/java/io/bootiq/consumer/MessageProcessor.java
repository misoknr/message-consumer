package io.bootiq.consumer;

import io.bootiq.consumer.persistence.UserManager;
import io.bootiq.consumer.persistence.entity.User;

import java.util.List;

public class MessageProcessor {

    private final UserManager userManager = UserManager.getInstance();

    public void processMessage(Message message) {
        switch (message.getInstruction()) {
            case CREATE:
                userManager.createUser(message.getUser());
                break;
            case LIST:
                List<User> userList = userManager.listAll();
                System.out.println("Users list:");
                userList.stream().forEach(System.out::println);
                break;
            case DELETE_ALL:
                int deletedCount = userManager.deleteAll();
                System.out.println("Deleted entries: " + deletedCount);
                break;
            default:
                break;
        }
    }

}
