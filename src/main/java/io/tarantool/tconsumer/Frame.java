package io.tarantool.tconsumer;

import org.springframework.data.annotation.Id;
import org.springframework.data.tarantool.core.mapping.Tuple;

@Tuple("frame")
public class Frame {
    //This is a stub for composite keys. They will be implemented soon..
    @Id
    FrameId frameId;

    Integer x;
    Integer y;
    String sym;
}
