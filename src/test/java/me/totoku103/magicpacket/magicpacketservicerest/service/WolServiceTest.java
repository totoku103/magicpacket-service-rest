package me.totoku103.magicpacket.magicpacketservicerest.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class WolServiceTest {

    @Autowired
    WolService wolService;

    @Test
    public void Given_NormalMacAddress_When_WakeUpServiceCall_Then_Success() throws IOException {
        wolService.wakeup("D0-50-99-97-36-D0");
    }

    @Test
    public void Given_InvalidMacAddress_When_WakeUpServiceCall_Then_IllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> wolService.wakeup("D050999736D0"));
    }
}