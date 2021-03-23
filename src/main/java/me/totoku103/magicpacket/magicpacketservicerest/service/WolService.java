package me.totoku103.magicpacket.magicpacketservicerest.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.totoku103.magicpacket.magicpacketservicerest.common.MagicPacketInfo;
import me.totoku103.magicpacket.magicpacketservicerest.util.MagicPacketUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

@Slf4j
@Service
@AllArgsConstructor
public class WolService {

    private final MagicPacketInfo magicPacketInfo;

    public void wakeup(String macAddress) throws IOException {
        log.info("wake up: {}", macAddress);
        final byte[] magicPacket = MagicPacketUtils.getMagicPacket(macAddress);

        InetAddress address = InetAddress.getByName(magicPacketInfo.getBroadcastIp());
        DatagramPacket packet = new DatagramPacket(magicPacket, magicPacket.length, address, magicPacketInfo.getUdpPort());
        log.debug("packet data: {}", packet.getData());
        try (DatagramSocket socket = new DatagramSocket()) {
            socket.send(packet);
        } finally {
            log.info("wol send to {}", macAddress);
        }
    }
}
