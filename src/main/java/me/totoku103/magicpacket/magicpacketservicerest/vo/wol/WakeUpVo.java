package me.totoku103.magicpacket.magicpacketservicerest.vo.wol;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WakeUpVo implements Serializable {

    @JsonIgnore
    private final long serialVersionUID = 1L;

    @NotBlank
    private String targetName;

}
