package me.totoku103.magicpacket.magicpacketservicerest.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class BroadcastServiceTest {
    @Autowired
    private BroadcastService broadcastService;

    @Test
    public void Given_NormalBroadcastIp_When_Check_Then_Success() throws IOException, InterruptedException {
        final List<String> responseCollect = broadcastService.check();
        Assertions.assertThat(responseCollect)
                .isNotNull();
        Assertions.assertThat(responseCollect)
                .isNotEmpty();
    }

}