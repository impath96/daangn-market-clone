package com.zerobase.daangnmarketclone.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zerobase.daangnmarketclone.domain.repository.ArticleRepository;
import com.zerobase.daangnmarketclone.dto.CreateArticle;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class ArticleControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("중고 거래 게시글 작성")
    void 게시글_작성() throws Exception {
        // given
        CreateArticle request = CreateArticle.builder()
            .title("제목입니다.")
            .content("내용입니다.")
            .price(1000)
            .categoryId(1L)
            .build();

        String json = objectMapper.writeValueAsString(request);

        // when
        mockMvc.perform(post("/articles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
            )
            .andExpect(status().isOk())
            .andDo(print());

//        // then
//        assertThat(postRepository.count()).isEqualTo(1L);
//
//        Post post = postRepository.findAll().get(0);
//        assertThat(post.getTitle()).isEqualTo("제목입니다.");
//        assertThat(post.getContent()).isEqualTo("내용입니다.");

        // given
        // when
        // then
    }
}



