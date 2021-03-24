package me.totoku103.magicpacket.magicpacketservicerest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.totoku103.magicpacket.magicpacketservicerest.vo.wol.WakeUpVo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({SpringExtension.class, RestDocumentationExtension.class})
@SpringBootTest
@AutoConfigureRestDocs
class WolControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(WebApplicationContext context, RestDocumentationContextProvider restDocumentationContextProvider) {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(documentationConfiguration(restDocumentationContextProvider))
                .build();
    }

    @ParameterizedTest
    @ValueSource(strings = {"computerA", "computerB"})
    public void Given_NormalParameter_When_WakeUpRequest_Then_Success(String target) throws Exception {
        final WakeUpVo wakeUpVo = WakeUpVo.builder()
                .targetName(target)
                .build();

        requestWakeUpMethod(wakeUpVo, status().isOk());

        //todo: restdoc 작업
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    public void Given_EmptyParameter_When_WakeUpRequest_Then_MethodArgumentNotValidException(String target) throws Exception {
        final WakeUpVo wakeUpVo = WakeUpVo.builder()
                .targetName(target)
                .build();

        final MvcResult mvcResult = requestWakeUpMethod(wakeUpVo, status().is4xxClientError());

        Assertions.assertThrows(MethodArgumentNotValidException.class, () -> {
            final Exception resolvedException = mvcResult.getResolvedException();
            throw resolvedException;
        });
    }

    @Test
    public void Given_nullParameter_When_WakeUpRequest_Then_MethodArgumentNotValidException() throws Exception {
        final WakeUpVo wakeUpVo = WakeUpVo.builder()
                .targetName(null)
                .build();

        final MvcResult mvcResult = requestWakeUpMethod(wakeUpVo, status().is4xxClientError());

        Assertions.assertThrows(MethodArgumentNotValidException.class, () -> {
            final Exception resolvedException = mvcResult.getResolvedException();
            throw resolvedException;
        });
    }

    private MvcResult requestWakeUpMethod(WakeUpVo wakeUpVo, ResultMatcher expectedStatus) throws Exception {
        return this.mockMvc
                .perform(post("/wake-up")
                        .content(objectMapper.writeValueAsString(wakeUpVo))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(expectedStatus)
                .andReturn();
    }
}