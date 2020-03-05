package io.bootiq.consumer.processor.result;

import io.bootiq.consumer.enums.Instruction;
import lombok.Getter;
import lombok.Setter;

public class DeleteUsersResult extends Result {

    @Getter
    @Setter
    private int deletedCount;

    public DeleteUsersResult() {
        this.messageType = Instruction.DELETE_ALL;
    }

}
