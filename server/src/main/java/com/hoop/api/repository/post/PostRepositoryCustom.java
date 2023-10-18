package com.hoop.api.repository.post;

import com.hoop.api.domain.Post;
import com.hoop.api.request.post.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getList(PostSearch postSearch);
}
