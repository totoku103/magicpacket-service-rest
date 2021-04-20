package me.totoku103.magicpacket.magicpacketservicerest.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.totoku103.magicpacket.magicpacketservicerest.util.MagicPacketUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArpService {

    private final ProcessService processService;
    private final Pattern pattern = Pattern.compile("at\\s(([0-9a-zA-Z]{1,2}:?){6})\\son");

    public boolean isEquals(String ip, String expectedMacAddress) throws IOException, InterruptedException {
        final String macAddress = MagicPacketUtils.generalizeDelimit(getMacAddress(ip));
        return MagicPacketUtils.generalizeDelimit(expectedMacAddress).equalsIgnoreCase(macAddress);
    }

    public String getMacAddress(String ip) throws IOException, InterruptedException {
        return processService.run(Arrays.asList("/usr/sbin/arp", ip),
                line -> {
                    if (line.contains("no entry")) return null;
                    return match(line);
                })
                .stream()
                .findAny()
                .orElseGet(() -> null);
    }

    private String match(String line) {
        final Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            final String matchResult = matcher.group(1).trim();
            log.info("original: {}, match: {}", line, matchResult);
            return matchResult;
        } else {
            return null;
        }
    }
}
