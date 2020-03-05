package io.bootiq.consumer.processor;

import io.bootiq.consumer.persistence.dao.UserDAO;
import io.bootiq.consumer.persistence.entity.User;
import io.bootiq.consumer.processor.result.CreateUserResult;
import io.bootiq.consumer.processor.result.DeleteUsersResult;
import io.bootiq.consumer.processor.result.ListUsersResult;
import io.bootiq.consumer.processor.result.Result;

import java.util.List;

public class MessageProcessor {

    private final UserDAO userDAO = new UserDAO();

    public Result processMessage(Message message) {
        Result result = null;

        switch (message.getInstruction()) {
            case CREATE:
                result = processCreate(message);
                break;
            case LIST:
                result = processList();
                break;
            case DELETE_ALL:
                result = processDeleteAll();
                break;
            default:
                break;
        }

        return result;
    }

    private CreateUserResult processCreate(Message message) {
        CreateUserResult result = new CreateUserResult();

        try {
            userDAO.createUser(message.getUser());
            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
        }

        return result;
    }

    private DeleteUsersResult processDeleteAll() {
        DeleteUsersResult result = new DeleteUsersResult();

        try {
            int deletedCount = userDAO.deleteAll();
            result.setSuccess(true);
            result.setDeletedCount(deletedCount);
        } catch (Exception e) {
            result.setSuccess(false);
        }

        return result;
    }

    private ListUsersResult processList() {
        ListUsersResult result = new ListUsersResult();

        try {
            List<User> userList = userDAO.listAll();
            result.setSuccess(true);
            result.setList(userList);
        } catch (Exception e) {
            result.setSuccess(false);
        }

        return result;
    }

}
