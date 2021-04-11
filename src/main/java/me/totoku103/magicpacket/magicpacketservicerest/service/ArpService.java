package me.totoku103.magicpacket.magicpacketservicerest.service;

import lombok.RequiredArgsConstructor;
import me.totoku103.magicpacket.magicpacketservicerest.common.NotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class ArpService {

    private final ProcessService processService;
    private final Pattern pattern = Pattern.compile("at\\s(([0-9a-zA-Z]{1,2}:?){6})\\son");

    public List<String> check(String ip) throws IOException, InterruptedException {
        return processService.run(Arrays.asList("/usr/sbin/arp", ip),
                line -> {
                    final Matcher matcher = pattern.matcher(line);
                    if (matcher.find()) return matcher.group(1).trim();
                    else throw new NotFoundException(String.format("ARP: MAC Address of ip(%s)", ip));
                });
    }
}
