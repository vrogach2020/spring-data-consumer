package io.tarantool.tconsumer;

import org.springframework.data.annotation.Id;
import org.springframework.data.tarantool.core.mapping.Tuple;

@Tuple("frame")
public class Frame
{
    // This is a stub (composite keys will be implemented soon..)
    @Id
    FrameId id; //x,y

    String sym;
}
