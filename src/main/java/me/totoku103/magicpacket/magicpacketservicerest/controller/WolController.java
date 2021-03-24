package me.totoku103.magicpacket.magicpacketservicerest.controller;

import me.totoku103.magicpacket.magicpacketservicerest.vo.wol.WakeUpVo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class WolController {

    @PostMapping("wake-up")
    public ResponseEntity wake(@RequestBody @Valid WakeUpVo wakeUpVo) {
        return ResponseEntity.ok().build();
    }
}
