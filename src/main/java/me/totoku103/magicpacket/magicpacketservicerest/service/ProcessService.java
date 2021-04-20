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
    private Process process;

    private void setProcess(List<String> commands) throws IOException {
        log.info("set process");
        final ProcessBuilder processBuilder = new ProcessBuilder(commands);
        processBuilder.redirectErrorStream(true);
        process = processBuilder.start();
        log.info("set process completed");
    }

    private <R> List<R> functionApplyToResult(Function<String, R> function) throws IOException {
        log.info("process start");
        final List<R> resultList = new ArrayList<>();

        try (BufferedReader inputStreamReader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line = null;
            while ((line = inputStreamReader.readLine()) != null) {
                log.debug("line: {}", line);
                resultList.add(function.apply(line));
            }
        }
        return resultList;
    }

    private void shutdownProcess() throws InterruptedException {
        if (process == null) return;

        final int exitCode = process.waitFor();
        log.info("Exited code : {}", exitCode);
        if (exitCode < 0) {
            log.error("process exited with error code: {}", exitCode);
            throw new RuntimeException(String.format("exitCode: %d", exitCode));
        } else {
            log.info("process completed");
        }
    }

    public <R> List<R> run(List<String> commands, Function<String, R> function) throws IOException, InterruptedException {
        setProcess(commands);
        final List<R> rs = functionApplyToResult(function);
        shutdownProcess();
        return rs;
    }
}
