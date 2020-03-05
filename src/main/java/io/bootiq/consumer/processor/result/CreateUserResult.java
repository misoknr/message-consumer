package io.bootiq.consumer.processor.result;

import io.bootiq.consumer.enums.Instruction;

public class CreateUserResult extends Result {

    public CreateUserResult() {
        this.messageType = Instruction.CREATE;
    }

}
