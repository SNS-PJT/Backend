package com.project.sns.unit.comment.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.sns.ApiTest;
import com.project.sns.domain.comment.api.CommentApi;
import com.project.sns.domain.comment.application.CommentAddUseCase;
import com.project.sns.domain.comment.dto.CommentAddRequestDto;
import com.project.sns.global.config.webmvc.AuthUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CommentApi.class)
class CommentApiTest extends ApiTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CommentAddUseCase commentAddUseCase;
    private static String BASE_URL = "api/comments";

    @Test
    void addComment_ValidRequest_Success() throws Exception {
        // given
        final AuthUser authUser = new AuthUser(1L);
        final CommentAddRequestDto commentAddRequestDto = new CommentAddRequestDto(1L,
                "test content");

        final CommentAddRequestBody requestBodyObject = new CommentAddRequestBody(
                authUser.getUserId(),
                commentAddRequestDto.getPostId(), commentAddRequestDto.getContent());

        String requestBody = objectMapper.writeValueAsString(requestBodyObject);

        // when, then
        mockMvc.perform(post("/api/comments").contentType(MediaType.APPLICATION_JSON)
                                             .content(requestBody))
               .andExpect(status().isCreated());

    }


}