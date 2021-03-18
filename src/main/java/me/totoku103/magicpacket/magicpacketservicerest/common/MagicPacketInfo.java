package me.totoku103.magicpacket.magicpacketservicerest.common;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Getter
@Setter
@ToString
@Component
@ConfigurationProperties("wol")
public class MagicPacketInfo {
    private String broadcastIp;
    private Map<String, String> macAddresses;
    private int udpPort;
}
