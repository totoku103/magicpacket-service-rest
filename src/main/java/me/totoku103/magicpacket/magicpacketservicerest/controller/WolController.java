package me.totoku103.magicpacket.magicpacketservicerest.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.totoku103.magicpacket.magicpacketservicerest.common.MagicPacketInfo;
import me.totoku103.magicpacket.magicpacketservicerest.service.ArpService;
import me.totoku103.magicpacket.magicpacketservicerest.service.BroadcastService;
import me.totoku103.magicpacket.magicpacketservicerest.service.WolService;
import me.totoku103.magicpacket.magicpacketservicerest.util.MagicPacketUtils;
import me.totoku103.magicpacket.magicpacketservicerest.vo.ResponseVo;
import me.totoku103.magicpacket.magicpacketservicerest.vo.wol.WakeUpVo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class WolController {

    private final MagicPacketInfo magicPacketInfo;
    private final BroadcastService broadcastService;
    private final ArpService arpService;
    private final WolService wolService;

    @PostMapping("wake-up")
    public ResponseEntity<ResponseVo> wake(@RequestBody @Valid WakeUpVo wakeUpVo) throws IOException, InterruptedException {
        final String macAddress = MagicPacketUtils.generalizeDelimit(magicPacketInfo.getMacAddresses().get(wakeUpVo.getTargetName()));
        wolService.wakeup(macAddress);

        final List<String> collect =
                broadcastService
                        .check()
                        .stream()
                        .filter(responseIp -> responseIp != null && responseIp.length() > 0)
                        .filter(responseIp -> {
                            try {
                                return arpService.isEquals(responseIp, macAddress);
                            } catch (IOException | InterruptedException e) {
                                log.error(e.getMessage());
                                return false;
                            }
                        })
                        .collect(Collectors.toList());

        return ResponseEntity.ok(ResponseVo
                .builder()
                .code(HttpStatus.OK.value())
                .message("SUCCESS")
                .build());
    }
}
