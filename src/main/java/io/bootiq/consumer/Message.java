package io.bootiq.consumer;

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
    private String userName;

}
