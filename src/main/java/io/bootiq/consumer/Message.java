package io.bootiq.consumer;

import io.bootiq.consumer.persistence.entity.User;
import lombok.*;

@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class Message {

    public enum Instruction {
        CREATE, LIST, DELETE_ALL
    }

    @Getter @Setter @NonNull
    private Instruction instruction;

    @Getter @Setter
    private User user;

}
