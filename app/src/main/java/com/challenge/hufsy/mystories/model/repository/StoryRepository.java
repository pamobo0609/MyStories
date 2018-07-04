package com.challenge.hufsy.mystories.model.repository;

import com.challenge.hufsy.mystories.app.MyStoriesConstants;
import com.challenge.hufsy.mystories.model.Story;
import com.challenge.hufsy.mystories.model.mapper.StoryMapper;

/**
 * MyStories
 * <p>
 * Created by Jose Monge on 7/3/18.
 * <p>
 */
public class StoryRepository extends FirebaseDatabaseRepository<Story> {

    public StoryRepository() {
        super(new StoryMapper());
    }

    @Override
    protected String getRootNode() {
        return MyStoriesConstants.IMAGES_CHILD;
    }
}
