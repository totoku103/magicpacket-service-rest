package me.totoku103.magicpacket.magicpacketservicerest.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.totoku103.magicpacket.magicpacketservicerest.common.MagicPacketInfo;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BroadcastService {

    private final MagicPacketInfo magicPacketInfo;
    private final ProcessService processService;
    private final Pattern ipPattern = Pattern.compile("from\\s([0-9]{1,3}\\.?){4}");

    public List<String> check() throws IOException, InterruptedException {
        return processService.run(Arrays.asList("/sbin/ping", "-c", "3", magicPacketInfo.getBroadcastIp()),
                line -> matchString(line))
                .stream()
                .distinct()
                .collect(Collectors.toList());
    }

    private String matchString(String line) {
        final Matcher matcher = ipPattern.matcher(line);
        if (matcher.find()) {
            final String matchResult = matcher.group(0).trim();
            final String result = removeSpace(removeString(matchResult));
            log.info(result);
            return result;
        } else {
            return null;
        }
    }

    private String removeString(String message) {
        return message.replaceAll("[a-zA-Z]", "");
    }

    private String removeSpace(String message) {
        return message.replaceAll("\\s", "");
    }
}
