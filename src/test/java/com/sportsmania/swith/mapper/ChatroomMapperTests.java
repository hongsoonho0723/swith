package com.sportsmania.swith.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Log4j2
@SpringBootTest

public class ChatroomMapperTests {
    @Autowired
    private ChatroomMapper chatroomMapper;

    @Test
    public void testGetTime() throws Exception{
        log.info(chatroomMapper.getTime());
    }
}
