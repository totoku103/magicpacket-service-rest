package me.totoku103.magicpacket.magicpacketservicerest.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Slf4j
@Service
public class ProcessService {

    public <R> List<R> run(List<String> commands, Function<String, R> function) throws IOException, InterruptedException {
        final List<R> resultList = new ArrayList<>();

        final ProcessBuilder processBuilder = new ProcessBuilder(commands);
        processBuilder.redirectErrorStream(true);
        final Process process = processBuilder.start();
        log.info("process start");

        try (BufferedReader inputStreamReader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line = null;
            while ((line = inputStreamReader.readLine()) != null) {
                log.debug("line: {}", line);
                resultList.add(function.apply(line));
            }
        }

        final int exitCode = process.waitFor();
        log.info("Exited code : " + exitCode);
        if (exitCode != 0) {
            log.error("process exited with error code: {}", exitCode);
            throw new RuntimeException(String.format("command: %s, exitCode: %d", commands, exitCode));
        } else {
            log.info("process completed");
        }
        return resultList;
    }
}
