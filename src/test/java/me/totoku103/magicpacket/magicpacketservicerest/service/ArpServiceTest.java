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
class ArpServiceTest {

    @Autowired
    private ArpService arpService;

    @Test
    public void Given_NormalIp_When_ArpCacheCheck_Then_Success() throws IOException, InterruptedException {
        final List<String> checkCollect = arpService.check("192.168.0.2");
        Assertions.assertThat(checkCollect).isNotNull();
        Assertions.assertThat(checkCollect).isNotEmpty();
        checkCollect
                .stream()
                .forEach(macAddress -> Assertions.assertThat(macAddress).isNotBlank());
    }
}