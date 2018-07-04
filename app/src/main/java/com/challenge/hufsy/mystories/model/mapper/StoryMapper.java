package com.challenge.hufsy.mystories.model.mapper;

import com.challenge.hufsy.mystories.model.Story;
import com.challenge.hufsy.mystories.model.entity.StoryEntity;

/**
 * MyStories
 * <p>
 * Created by Jose Monge on 7/3/18.
 * <p>
 */
public class StoryMapper extends FirebaseMapper<StoryEntity, Story> {

    @Override
    public Story map(StoryEntity from) {
        return new Story(from.getName(), from.getDownloadUrl(), from.getDate());
    }

}
