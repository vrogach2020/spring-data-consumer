package io.tarantool.tconsumer;

import org.springframework.data.annotation.Id;
import org.springframework.data.tarantool.core.mapping.Tuple;

@Tuple("frame")
public class Frame
{
    @Id
    FrameId id; //x,y
    String sym;
}
