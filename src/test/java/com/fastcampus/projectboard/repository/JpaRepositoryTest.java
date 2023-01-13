package com.fastcampus.projectboard.repository;

import com.fastcampus.projectboard.config.JpaConfig;
import com.fastcampus.projectboard.domain.Article;
import com.fastcampus.projectboard.domain.UserAccount;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("JPA 연결 테스트")
@Import(JpaConfig.class)
@DataJpaTest
class JpaRepositoryTest {

    private ArticleRepository articleRepository;
    private ArticleCommentRepository articleCommentRepository;
    private final UserAccountRepository userAccountRepository;
    public JpaRepositoryTest(
            @Autowired ArticleRepository articleRepository,
            @Autowired ArticleCommentRepository articleCommentRepository,
            @Autowired UserAccountRepository userAccountRepository
    ) {
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
        this.userAccountRepository = userAccountRepository;
    }
    @DisplayName("select 테스트")
    @Test
    void givenTestData_whenSelection_thenWorksFine(){
        //given
        long previousCount = articleRepository.count();
        UserAccount userAccount = userAccountRepository.save(UserAccount.of("seol", "pw", null, null, null));
        Article article = Article.of(userAccount, "new article", "new content", "#spring");

        //then
        articleRepository.save(article);
        assertThat(articleRepository.count()).isEqualTo(previousCount + 1);

    }

    @DisplayName("insert 테스트")
    @Test
    void givenTestData_whenSelection_thenWorksFine_insert(){
        //given
        long previousCount = articleRepository.count();

        //when
        UserAccount userAccount = userAccountRepository.save(UserAccount.of("seol", "pw", null, null, null));
        Article savedArticle = articleRepository.save(Article.of(userAccount,"new article", "new content", "#spring"));

        //then
        assertThat(articleRepository.count())
                .isEqualTo(previousCount+1);


    }

    @DisplayName("update 테스트")
    @Test
    void givenTestData_whenSelection_thenWorksFine_update(){

        //given
        Article article = articleRepository.findById(1L).orElseThrow();
        String updatedHashtag = "#springboot";
        article.setHashtag(updatedHashtag);

        //when
        Article savedArticle = articleRepository.save(article);

        //then
        assertThat(savedArticle)
                .hasFieldOrPropertyWithValue("hashtag",updatedHashtag);

    }
    @DisplayName("delete 테스트")
    @Test
    void givenTestData_whenSelection_thenWorksFine_delete(){

        //given
        Article article = articleRepository.findById(1L).orElseThrow();
        long previousArticleCount = articleRepository.count();
        long previousArticleCommontCount = articleCommentRepository.count();
        int  deletedCommentsSize = article.getArticleComments().size();



        //when
        articleRepository.delete(article);

        //then
        assertThat(articleRepository.count()) .isEqualTo(previousArticleCount-1);
        assertThat(articleCommentRepository.count()).isEqualTo(previousArticleCommontCount-deletedCommentsSize);

    }

}