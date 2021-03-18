package me.totoku103.magicpacket.magicpacketservicerest.common;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class MagicPacketInfoTest {

    @Autowired
    MagicPacketInfo magicPacketInfo;

    @Test
    @DisplayName("TargetProperties Bean 생성 여부")
    public void given_setAutowired_when_applicationLoaded_then_isNotNull() {
        assertThat(magicPacketInfo, is(not(nullValue())));
    }

    @Test
    @DisplayName("broadcastIp 속성 값 존재 여부")
    public void given_broadcastIp_when_applicationLoaded_then_isNotNull() {
        final String broadcastIp = magicPacketInfo.getBroadcastIp();
        assertThat(broadcastIp, is(not(nullValue())));
        assertThat(broadcastIp, is("192.168.0.255"));
    }

    @Test
    @DisplayName("macAddress 리스트 값 존재 여부")
    public void given_macAddress_when_applicationLoaded_then_isNotNullAndSizeIsNotZero() {
        final Map<String, String> macAddresses = magicPacketInfo.getMacAddresses();
        assertThat(macAddresses, is(not(nullValue())));
        assertThat(macAddresses.size(), is(greaterThan(1)));
    }

    @Test
    @DisplayName("udpPort 값 존재 여부")
    public void given_udpPort_when_applicationLoaded_then_greaterThanZeroAndIs9() {
        final int udpPort = magicPacketInfo.getUdpPort();
        assertThat(udpPort, greaterThan(0));
        assertThat(udpPort, is(9));
    }

}