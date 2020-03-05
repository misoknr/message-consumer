package io.bootiq.consumer.processor.result;

import io.bootiq.consumer.enums.Instruction;
import lombok.Getter;
import lombok.Setter;

public class Result {

    @Getter
    protected Instruction messageType;

    @Getter
    @Setter
    protected boolean success;

}
