package com.project.sns.domain.post.infra.database;

import com.project.sns.domain.post.application.repository.ImageRepository;
import com.project.sns.domain.post.domain.Image;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ImageRepositoryImpl implements ImageRepository {

    private final JPAImageRepository jpaImageRepository;

    @Override
    public Image save(Image image) {
        return jpaImageRepository.save(image);
    }

    @Override
    public List<Image> savedAll(List<Image> images) {
        return jpaImageRepository.saveAll(images);
    }
}
