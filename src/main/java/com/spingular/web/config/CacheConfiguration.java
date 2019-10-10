package com.spingular.web.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.spingular.web.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.spingular.web.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.spingular.web.domain.User.class.getName());
            createCache(cm, com.spingular.web.domain.Authority.class.getName());
            createCache(cm, com.spingular.web.domain.User.class.getName() + ".authorities");
            createCache(cm, com.spingular.web.domain.PersistentToken.class.getName());
            createCache(cm, com.spingular.web.domain.User.class.getName() + ".persistentTokens");
            createCache(cm, com.spingular.web.domain.Blog.class.getName());
            createCache(cm, com.spingular.web.domain.Blog.class.getName() + ".posts");
            createCache(cm, com.spingular.web.domain.Post.class.getName());
            createCache(cm, com.spingular.web.domain.Post.class.getName() + ".comments");
            createCache(cm, com.spingular.web.domain.Post.class.getName() + ".proposals");
            createCache(cm, com.spingular.web.domain.Post.class.getName() + ".tags");
            createCache(cm, com.spingular.web.domain.Post.class.getName() + ".topics");
            createCache(cm, com.spingular.web.domain.Topic.class.getName());
            createCache(cm, com.spingular.web.domain.Topic.class.getName() + ".posts");
            createCache(cm, com.spingular.web.domain.Tag.class.getName());
            createCache(cm, com.spingular.web.domain.Tag.class.getName() + ".posts");
            createCache(cm, com.spingular.web.domain.Comment.class.getName());
            createCache(cm, com.spingular.web.domain.Cmessage.class.getName());
            createCache(cm, com.spingular.web.domain.Message.class.getName());
            createCache(cm, com.spingular.web.domain.Notification.class.getName());
            createCache(cm, com.spingular.web.domain.Appphoto.class.getName());
            createCache(cm, com.spingular.web.domain.Appprofile.class.getName());
            createCache(cm, com.spingular.web.domain.Community.class.getName());
            createCache(cm, com.spingular.web.domain.Community.class.getName() + ".blogs");
            createCache(cm, com.spingular.web.domain.Community.class.getName() + ".csenders");
            createCache(cm, com.spingular.web.domain.Community.class.getName() + ".creceivers");
            createCache(cm, com.spingular.web.domain.Community.class.getName() + ".cfolloweds");
            createCache(cm, com.spingular.web.domain.Community.class.getName() + ".cfollowings");
            createCache(cm, com.spingular.web.domain.Community.class.getName() + ".cblockedusers");
            createCache(cm, com.spingular.web.domain.Community.class.getName() + ".cblockingusers");
            createCache(cm, com.spingular.web.domain.Community.class.getName() + ".calbums");
            createCache(cm, com.spingular.web.domain.Community.class.getName() + ".cinterests");
            createCache(cm, com.spingular.web.domain.Community.class.getName() + ".cactivities");
            createCache(cm, com.spingular.web.domain.Community.class.getName() + ".ccelebs");
            createCache(cm, com.spingular.web.domain.Follow.class.getName());
            createCache(cm, com.spingular.web.domain.Blockuser.class.getName());
            createCache(cm, com.spingular.web.domain.Album.class.getName());
            createCache(cm, com.spingular.web.domain.Album.class.getName() + ".photos");
            createCache(cm, com.spingular.web.domain.Calbum.class.getName());
            createCache(cm, com.spingular.web.domain.Calbum.class.getName() + ".photos");
            createCache(cm, com.spingular.web.domain.Photo.class.getName());
            createCache(cm, com.spingular.web.domain.Interest.class.getName());
            createCache(cm, com.spingular.web.domain.Interest.class.getName() + ".appusers");
            createCache(cm, com.spingular.web.domain.Activity.class.getName());
            createCache(cm, com.spingular.web.domain.Activity.class.getName() + ".appusers");
            createCache(cm, com.spingular.web.domain.Celeb.class.getName());
            createCache(cm, com.spingular.web.domain.Celeb.class.getName() + ".appusers");
            createCache(cm, com.spingular.web.domain.Cinterest.class.getName());
            createCache(cm, com.spingular.web.domain.Cinterest.class.getName() + ".communities");
            createCache(cm, com.spingular.web.domain.Cactivity.class.getName());
            createCache(cm, com.spingular.web.domain.Cactivity.class.getName() + ".communities");
            createCache(cm, com.spingular.web.domain.Cceleb.class.getName());
            createCache(cm, com.spingular.web.domain.Cceleb.class.getName() + ".communities");
            createCache(cm, com.spingular.web.domain.Urllink.class.getName());
            createCache(cm, com.spingular.web.domain.Frontpageconfig.class.getName());
            createCache(cm, com.spingular.web.domain.Vtopic.class.getName());
            createCache(cm, com.spingular.web.domain.Vtopic.class.getName() + ".vquestions");
            createCache(cm, com.spingular.web.domain.Vquestion.class.getName());
            createCache(cm, com.spingular.web.domain.Vquestion.class.getName() + ".vanswers");
            createCache(cm, com.spingular.web.domain.Vquestion.class.getName() + ".vthumbs");
            createCache(cm, com.spingular.web.domain.Vanswer.class.getName());
            createCache(cm, com.spingular.web.domain.Vanswer.class.getName() + ".vthumbs");
            createCache(cm, com.spingular.web.domain.Vthumb.class.getName());
            createCache(cm, com.spingular.web.domain.Newsletter.class.getName());
            createCache(cm, com.spingular.web.domain.Feedback.class.getName());
            createCache(cm, com.spingular.web.domain.ConfigVariables.class.getName());
            createCache(cm, com.spingular.web.domain.Proposal.class.getName());
            createCache(cm, com.spingular.web.domain.Proposal.class.getName() + ".proposalVotes");
            createCache(cm, com.spingular.web.domain.ProposalVote.class.getName());
            createCache(cm, com.spingular.web.domain.Appuser.class.getName());
            createCache(cm, com.spingular.web.domain.Appuser.class.getName() + ".communities");
            createCache(cm, com.spingular.web.domain.Appuser.class.getName() + ".blogs");
            createCache(cm, com.spingular.web.domain.Appuser.class.getName() + ".notifications");
            createCache(cm, com.spingular.web.domain.Appuser.class.getName() + ".albums");
            createCache(cm, com.spingular.web.domain.Appuser.class.getName() + ".comments");
            createCache(cm, com.spingular.web.domain.Appuser.class.getName() + ".posts");
            createCache(cm, com.spingular.web.domain.Appuser.class.getName() + ".senders");
            createCache(cm, com.spingular.web.domain.Appuser.class.getName() + ".receivers");
            createCache(cm, com.spingular.web.domain.Appuser.class.getName() + ".followeds");
            createCache(cm, com.spingular.web.domain.Appuser.class.getName() + ".followings");
            createCache(cm, com.spingular.web.domain.Appuser.class.getName() + ".blockedusers");
            createCache(cm, com.spingular.web.domain.Appuser.class.getName() + ".blockingusers");
            createCache(cm, com.spingular.web.domain.Appuser.class.getName() + ".vtopics");
            createCache(cm, com.spingular.web.domain.Appuser.class.getName() + ".vquestions");
            createCache(cm, com.spingular.web.domain.Appuser.class.getName() + ".vanswers");
            createCache(cm, com.spingular.web.domain.Appuser.class.getName() + ".vthumbs");
            createCache(cm, com.spingular.web.domain.Appuser.class.getName() + ".proposals");
            createCache(cm, com.spingular.web.domain.Appuser.class.getName() + ".proposalVotes");
            createCache(cm, com.spingular.web.domain.Appuser.class.getName() + ".interests");
            createCache(cm, com.spingular.web.domain.Appuser.class.getName() + ".activities");
            createCache(cm, com.spingular.web.domain.Appuser.class.getName() + ".celebs");
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cm.destroyCache(cacheName);
        }
        cm.createCache(cacheName, jcacheConfiguration);
    }

}
