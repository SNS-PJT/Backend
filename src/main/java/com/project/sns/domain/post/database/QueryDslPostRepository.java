package com.project.sns.domain.post.database;

import com.project.sns.domain.post.domain.Post;
import com.project.sns.domain.post.domain.QImage;
import com.project.sns.domain.post.domain.QPost;
import com.project.sns.domain.user.domain.QUser;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class QueryDslPostRepository {

    private final JPAQueryFactory jpaQueryFactory;

    private QPost post = QPost.post;
    private QUser user = QUser.user;
    private QImage image = QImage.image;

    public List<Post> findAllByKeyword(String keyword) {
        return jpaQueryFactory.select(post)
                              .from(post)
                              .distinct()
                              .innerJoin(post.user, user)
                              .fetchJoin()
                              .innerJoin(post.images,
                                      image)
                              .fetchJoin()
                              .where(contentLikeKeyword(keyword))
                              .fetch();
    }

    private BooleanExpression contentLikeKeyword(String keyword) {
        if (keyword == null) {
            return null;
        }
        return post.content.contains(keyword);

    }
}
