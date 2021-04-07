package me.totoku103.magicpacket.magicpacketservicerest.service;

import lombok.RequiredArgsConstructor;
import me.totoku103.magicpacket.magicpacketservicerest.common.MagicPacketInfo;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BroadcastService {

    private final MagicPacketInfo magicPacketInfo;
    private final ProcessService processService;
    private final Pattern ipPattern = Pattern.compile("\\s([0-9]{1,3}\\.?){4}");

    public List<String> check() throws IOException, InterruptedException {
        return processService.run(Arrays.asList("/sbin/ping", "-c", "3", magicPacketInfo.getBroadcastIp()),
                line -> {
                    final Matcher matcher = ipPattern.matcher(line);
                    if (matcher.find()) return matcher.group(0).trim();
                    else return null;
                })
                .stream()
                .distinct()
                .collect(Collectors.toList());
    }
}
