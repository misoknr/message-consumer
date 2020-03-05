package io.bootiq.consumer.processor.result;

import io.bootiq.consumer.enums.Instruction;
import io.bootiq.consumer.persistence.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class ListUsersResult extends Result {

    @Getter
    @Setter
    private List<User> list;

    public ListUsersResult() {
        this.messageType = Instruction.LIST;
    }

}
