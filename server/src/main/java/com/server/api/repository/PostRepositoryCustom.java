package com.server.api.repository;

import com.server.api.domain.Post;
import com.server.api.request.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getList(PostSearch postSearch);
}
