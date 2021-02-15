package io.tarantool.tconsumer;

import org.springframework.data.tarantool.repository.Query;
import org.springframework.data.tarantool.repository.TarantoolRepository;

import java.util.List;

public interface FrameRepository extends TarantoolRepository<Frame, FrameId> {

    @Query(function="get_frames")
    List<Frame> getFrames(Integer from, Integer to);
}
