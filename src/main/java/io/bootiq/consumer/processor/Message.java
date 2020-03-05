package io.bootiq.consumer.processor;

import io.bootiq.consumer.enums.Instruction;
import io.bootiq.consumer.persistence.entity.User;
import lombok.*;

@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class Message {

    @Getter @Setter @NonNull
    private Instruction instruction;

    @Getter @Setter
    private User user;

}
