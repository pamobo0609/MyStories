package com.challenge.hufsy.mystories.app.di.scope;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * MyStories
 * <p>
 * Created by Jose Monge on 7/3/18.
 * <p>
 */
@Scope
@Retention(RUNTIME)
public @interface ActivityScope {
}
