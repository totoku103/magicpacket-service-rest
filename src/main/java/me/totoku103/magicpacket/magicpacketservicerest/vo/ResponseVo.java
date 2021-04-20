package me.totoku103.magicpacket.magicpacketservicerest.vo;

import lombok.Builder;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Builder
public class ResponseVo<R extends Serializable> implements Serializable {
    private final long serialVersionUID = 1L;

    private int code;
    private String message;
    private Exception exception;
    private R result;
}
