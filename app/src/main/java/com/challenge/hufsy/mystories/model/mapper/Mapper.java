package com.challenge.hufsy.mystories.model.mapper;

/**
 * MyStories
 * <p>
 * Created by Jose Monge on 7/3/18.
 * <p>
 */
public interface Mapper<F, T> {

    T map(F from);

}
