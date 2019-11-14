package com.spingular.web.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Appuser.
 */
@Entity
@Table(name = "appuser")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Appuser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "creation_date", nullable = false)
    private Instant creationDate;

    @Column(name = "assigned_votes_points")
    private Long assignedVotesPoints;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private User user;

    @OneToOne(mappedBy = "appuser", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private Appprofile appprofile;

    @OneToOne(mappedBy = "appuser", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private Appphoto appphoto;

    @OneToMany(mappedBy = "appuser", cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Community> communities = new HashSet<>();

    @OneToMany(mappedBy = "appuser", cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Blog> blogs = new HashSet<>();

    @OneToMany(mappedBy = "appuser", cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Notification> notifications = new HashSet<>();

    @OneToMany(mappedBy = "appuser", cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Album> albums = new HashSet<>();

    @OneToMany(mappedBy = "appuser", cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Comment> comments = new HashSet<>();

    @OneToMany(mappedBy = "appuser", cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Post> posts = new HashSet<>();

    @OneToMany(mappedBy = "sender", cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Message> senders = new HashSet<>();

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Message> receivers = new HashSet<>();

    @OneToMany(mappedBy = "followed", cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Follow> followeds = new HashSet<>();

    @OneToMany(mappedBy = "following", cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Follow> followings = new HashSet<>();

    @OneToMany(mappedBy = "blockeduser", cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Blockuser> blockedusers = new HashSet<>();

    @OneToMany(mappedBy = "blockinguser", cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Blockuser> blockingusers = new HashSet<>();

    @OneToMany(mappedBy = "appuser", cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Vtopic> vtopics = new HashSet<>();

    @OneToMany(mappedBy = "appuser", cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Vquestion> vquestions = new HashSet<>();

    @OneToMany(mappedBy = "appuser", cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Vanswer> vanswers = new HashSet<>();

    @OneToMany(mappedBy = "appuser", cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Vthumb> vthumbs = new HashSet<>();

    @OneToMany(mappedBy = "appuser", cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Proposal> proposals = new HashSet<>();

    @OneToMany(mappedBy = "appuser", cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ProposalVote> proposalVotes = new HashSet<>();

    @ManyToMany(mappedBy = "appusers", cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Interest> interests = new HashSet<>();

    @ManyToMany(mappedBy = "appusers", cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Activity> activities = new HashSet<>();

    @ManyToMany(mappedBy = "appusers", cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Celeb> celebs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public Appuser creationDate(Instant creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public Long getAssignedVotesPoints() {
        return assignedVotesPoints;
    }

    public Appuser assignedVotesPoints(Long assignedVotesPoints) {
        this.assignedVotesPoints = assignedVotesPoints;
        return this;
    }

    public void setAssignedVotesPoints(Long assignedVotesPoints) {
        this.assignedVotesPoints = assignedVotesPoints;
    }

    public User getUser() {
        return user;
    }

    public Appuser user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Appprofile getAppprofile() {
        return appprofile;
    }

    public Appuser appprofile(Appprofile appprofile) {
        this.appprofile = appprofile;
        return this;
    }

    public void setAppprofile(Appprofile appprofile) {
        this.appprofile = appprofile;
    }

    public Appphoto getAppphoto() {
        return appphoto;
    }

    public Appuser appphoto(Appphoto appphoto) {
        this.appphoto = appphoto;
        return this;
    }

    public void setAppphoto(Appphoto appphoto) {
        this.appphoto = appphoto;
    }

    public Set<Community> getCommunities() {
        return communities;
    }

    public Appuser communities(Set<Community> communities) {
        this.communities = communities;
        return this;
    }

    public Appuser addCommunity(Community community) {
        this.communities.add(community);
        community.setAppuser(this);
        return this;
    }

    public Appuser removeCommunity(Community community) {
        this.communities.remove(community);
        community.setAppuser(null);
        return this;
    }

    public void setCommunities(Set<Community> communities) {
        this.communities = communities;
    }

    public Set<Blog> getBlogs() {
        return blogs;
    }

    public Appuser blogs(Set<Blog> blogs) {
        this.blogs = blogs;
        return this;
    }

    public Appuser addBlog(Blog blog) {
        this.blogs.add(blog);
        blog.setAppuser(this);
        return this;
    }

    public Appuser removeBlog(Blog blog) {
        this.blogs.remove(blog);
        blog.setAppuser(null);
        return this;
    }

    public void setBlogs(Set<Blog> blogs) {
        this.blogs = blogs;
    }

    public Set<Notification> getNotifications() {
        return notifications;
    }

    public Appuser notifications(Set<Notification> notifications) {
        this.notifications = notifications;
        return this;
    }

    public Appuser addNotification(Notification notification) {
        this.notifications.add(notification);
        notification.setAppuser(this);
        return this;
    }

    public Appuser removeNotification(Notification notification) {
        this.notifications.remove(notification);
        notification.setAppuser(null);
        return this;
    }

    public void setNotifications(Set<Notification> notifications) {
        this.notifications = notifications;
    }

    public Set<Album> getAlbums() {
        return albums;
    }

    public Appuser albums(Set<Album> albums) {
        this.albums = albums;
        return this;
    }

    public Appuser addAlbum(Album album) {
        this.albums.add(album);
        album.setAppuser(this);
        return this;
    }

    public Appuser removeAlbum(Album album) {
        this.albums.remove(album);
        album.setAppuser(null);
        return this;
    }

    public void setAlbums(Set<Album> albums) {
        this.albums = albums;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public Appuser comments(Set<Comment> comments) {
        this.comments = comments;
        return this;
    }

    public Appuser addComment(Comment comment) {
        this.comments.add(comment);
        comment.setAppuser(this);
        return this;
    }

    public Appuser removeComment(Comment comment) {
        this.comments.remove(comment);
        comment.setAppuser(null);
        return this;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public Appuser posts(Set<Post> posts) {
        this.posts = posts;
        return this;
    }

    public Appuser addPost(Post post) {
        this.posts.add(post);
        post.setAppuser(this);
        return this;
    }

    public Appuser removePost(Post post) {
        this.posts.remove(post);
        post.setAppuser(null);
        return this;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }

    public Set<Message> getSenders() {
        return senders;
    }

    public Appuser senders(Set<Message> messages) {
        this.senders = messages;
        return this;
    }

    public Appuser addSender(Message message) {
        this.senders.add(message);
        message.setSender(this);
        return this;
    }

    public Appuser removeSender(Message message) {
        this.senders.remove(message);
        message.setSender(null);
        return this;
    }

    public void setSenders(Set<Message> messages) {
        this.senders = messages;
    }

    public Set<Message> getReceivers() {
        return receivers;
    }

    public Appuser receivers(Set<Message> messages) {
        this.receivers = messages;
        return this;
    }

    public Appuser addReceiver(Message message) {
        this.receivers.add(message);
        message.setReceiver(this);
        return this;
    }

    public Appuser removeReceiver(Message message) {
        this.receivers.remove(message);
        message.setReceiver(null);
        return this;
    }

    public void setReceivers(Set<Message> messages) {
        this.receivers = messages;
    }

    public Set<Follow> getFolloweds() {
        return followeds;
    }

    public Appuser followeds(Set<Follow> follows) {
        this.followeds = follows;
        return this;
    }

    public Appuser addFollowed(Follow follow) {
        this.followeds.add(follow);
        follow.setFollowed(this);
        return this;
    }

    public Appuser removeFollowed(Follow follow) {
        this.followeds.remove(follow);
        follow.setFollowed(null);
        return this;
    }

    public void setFolloweds(Set<Follow> follows) {
        this.followeds = follows;
    }

    public Set<Follow> getFollowings() {
        return followings;
    }

    public Appuser followings(Set<Follow> follows) {
        this.followings = follows;
        return this;
    }

    public Appuser addFollowing(Follow follow) {
        this.followings.add(follow);
        follow.setFollowing(this);
        return this;
    }

    public Appuser removeFollowing(Follow follow) {
        this.followings.remove(follow);
        follow.setFollowing(null);
        return this;
    }

    public void setFollowings(Set<Follow> follows) {
        this.followings = follows;
    }

    public Set<Blockuser> getBlockedusers() {
        return blockedusers;
    }

    public Appuser blockedusers(Set<Blockuser> blockusers) {
        this.blockedusers = blockusers;
        return this;
    }

    public Appuser addBlockeduser(Blockuser blockuser) {
        this.blockedusers.add(blockuser);
        blockuser.setBlockeduser(this);
        return this;
    }

    public Appuser removeBlockeduser(Blockuser blockuser) {
        this.blockedusers.remove(blockuser);
        blockuser.setBlockeduser(null);
        return this;
    }

    public void setBlockedusers(Set<Blockuser> blockusers) {
        this.blockedusers = blockusers;
    }

    public Set<Blockuser> getBlockingusers() {
        return blockingusers;
    }

    public Appuser blockingusers(Set<Blockuser> blockusers) {
        this.blockingusers = blockusers;
        return this;
    }

    public Appuser addBlockinguser(Blockuser blockuser) {
        this.blockingusers.add(blockuser);
        blockuser.setBlockinguser(this);
        return this;
    }

    public Appuser removeBlockinguser(Blockuser blockuser) {
        this.blockingusers.remove(blockuser);
        blockuser.setBlockinguser(null);
        return this;
    }

    public void setBlockingusers(Set<Blockuser> blockusers) {
        this.blockingusers = blockusers;
    }

    public Set<Vtopic> getVtopics() {
        return vtopics;
    }

    public Appuser vtopics(Set<Vtopic> vtopics) {
        this.vtopics = vtopics;
        return this;
    }

    public Appuser addVtopic(Vtopic vtopic) {
        this.vtopics.add(vtopic);
        vtopic.setAppuser(this);
        return this;
    }

    public Appuser removeVtopic(Vtopic vtopic) {
        this.vtopics.remove(vtopic);
        vtopic.setAppuser(null);
        return this;
    }

    public void setVtopics(Set<Vtopic> vtopics) {
        this.vtopics = vtopics;
    }

    public Set<Vquestion> getVquestions() {
        return vquestions;
    }

    public Appuser vquestions(Set<Vquestion> vquestions) {
        this.vquestions = vquestions;
        return this;
    }

    public Appuser addVquestion(Vquestion vquestion) {
        this.vquestions.add(vquestion);
        vquestion.setAppuser(this);
        return this;
    }

    public Appuser removeVquestion(Vquestion vquestion) {
        this.vquestions.remove(vquestion);
        vquestion.setAppuser(null);
        return this;
    }

    public void setVquestions(Set<Vquestion> vquestions) {
        this.vquestions = vquestions;
    }

    public Set<Vanswer> getVanswers() {
        return vanswers;
    }

    public Appuser vanswers(Set<Vanswer> vanswers) {
        this.vanswers = vanswers;
        return this;
    }

    public Appuser addVanswer(Vanswer vanswer) {
        this.vanswers.add(vanswer);
        vanswer.setAppuser(this);
        return this;
    }

    public Appuser removeVanswer(Vanswer vanswer) {
        this.vanswers.remove(vanswer);
        vanswer.setAppuser(null);
        return this;
    }

    public void setVanswers(Set<Vanswer> vanswers) {
        this.vanswers = vanswers;
    }

    public Set<Vthumb> getVthumbs() {
        return vthumbs;
    }

    public Appuser vthumbs(Set<Vthumb> vthumbs) {
        this.vthumbs = vthumbs;
        return this;
    }

    public Appuser addVthumb(Vthumb vthumb) {
        this.vthumbs.add(vthumb);
        vthumb.setAppuser(this);
        return this;
    }

    public Appuser removeVthumb(Vthumb vthumb) {
        this.vthumbs.remove(vthumb);
        vthumb.setAppuser(null);
        return this;
    }

    public void setVthumbs(Set<Vthumb> vthumbs) {
        this.vthumbs = vthumbs;
    }

    public Set<Proposal> getProposals() {
        return proposals;
    }

    public Appuser proposals(Set<Proposal> proposals) {
        this.proposals = proposals;
        return this;
    }

    public Appuser addProposal(Proposal proposal) {
        this.proposals.add(proposal);
        proposal.setAppuser(this);
        return this;
    }

    public Appuser removeProposal(Proposal proposal) {
        this.proposals.remove(proposal);
        proposal.setAppuser(null);
        return this;
    }

    public void setProposals(Set<Proposal> proposals) {
        this.proposals = proposals;
    }

    public Set<ProposalVote> getProposalVotes() {
        return proposalVotes;
    }

    public Appuser proposalVotes(Set<ProposalVote> proposalVotes) {
        this.proposalVotes = proposalVotes;
        return this;
    }

    public Appuser addProposalVote(ProposalVote proposalVote) {
        this.proposalVotes.add(proposalVote);
        proposalVote.setAppuser(this);
        return this;
    }

    public Appuser removeProposalVote(ProposalVote proposalVote) {
        this.proposalVotes.remove(proposalVote);
        proposalVote.setAppuser(null);
        return this;
    }

    public void setProposalVotes(Set<ProposalVote> proposalVotes) {
        this.proposalVotes = proposalVotes;
    }

    public Set<Interest> getInterests() {
        return interests;
    }

    public Appuser interests(Set<Interest> interests) {
        this.interests = interests;
        return this;
    }

    public Appuser addInterest(Interest interest) {
        this.interests.add(interest);
        interest.getAppusers().add(this);
        return this;
    }

    public Appuser removeInterest(Interest interest) {
        this.interests.remove(interest);
        interest.getAppusers().remove(this);
        return this;
    }

    public void setInterests(Set<Interest> interests) {
        this.interests = interests;
    }

    public Set<Activity> getActivities() {
        return activities;
    }

    public Appuser activities(Set<Activity> activities) {
        this.activities = activities;
        return this;
    }

    public Appuser addActivity(Activity activity) {
        this.activities.add(activity);
        activity.getAppusers().add(this);
        return this;
    }

    public Appuser removeActivity(Activity activity) {
        this.activities.remove(activity);
        activity.getAppusers().remove(this);
        return this;
    }

    public void setActivities(Set<Activity> activities) {
        this.activities = activities;
    }

    public Set<Celeb> getCelebs() {
        return celebs;
    }

    public Appuser celebs(Set<Celeb> celebs) {
        this.celebs = celebs;
        return this;
    }

    public Appuser addCeleb(Celeb celeb) {
        this.celebs.add(celeb);
        celeb.getAppusers().add(this);
        return this;
    }

    public Appuser removeCeleb(Celeb celeb) {
        this.celebs.remove(celeb);
        celeb.getAppusers().remove(this);
        return this;
    }

    public void setCelebs(Set<Celeb> celebs) {
        this.celebs = celebs;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Appuser)) {
            return false;
        }
        return id != null && id.equals(((Appuser) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Appuser{" +
            "id=" + getId() +
            ", creationDate='" + getCreationDate() + "'" +
            ", assignedVotesPoints=" + getAssignedVotesPoints() +
            "}";
    }
}
