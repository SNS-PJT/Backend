package com.project.sns.domain.comment.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Embeddable
public class Comments {

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @Getter
    private List<Comment> comments;

    public Comments() {
        this(new ArrayList<>());
    }

    public Comments(List<Comment> comments) {
        this.comments = comments;
    }

}
