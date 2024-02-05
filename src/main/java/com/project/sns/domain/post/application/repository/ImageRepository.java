package com.project.sns.domain.post.application.repository;

import com.project.sns.domain.post.domain.Image;
import java.util.List;

public interface ImageRepository {

    Image save(Image image);

    List<Image> savedAll(List<Image> images);


}
