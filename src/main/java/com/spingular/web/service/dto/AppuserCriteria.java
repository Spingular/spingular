package com.spingular.web.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.spingular.web.domain.Appuser} entity. This class is used
 * in {@link com.spingular.web.web.rest.AppuserResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /appusers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AppuserCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter creationDate;

    private LongFilter assignedVotesPoints;

    private LongFilter userId;

    private LongFilter appprofileId;

    private LongFilter appphotoId;

    private LongFilter communityId;

    private LongFilter blogId;

    private LongFilter notificationId;

    private LongFilter albumId;

    private LongFilter commentId;

    private LongFilter postId;

    private LongFilter senderId;

    private LongFilter receiverId;

    private LongFilter followedId;

    private LongFilter followingId;

    private LongFilter blockeduserId;

    private LongFilter blockinguserId;

    private LongFilter vtopicId;

    private LongFilter vquestionId;

    private LongFilter vanswerId;

    private LongFilter vthumbId;

    private LongFilter proposalId;

    private LongFilter proposalVoteId;

    private LongFilter interestId;

    private LongFilter activityId;

    private LongFilter celebId;

    public AppuserCriteria(){
    }

    public AppuserCriteria(AppuserCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.creationDate = other.creationDate == null ? null : other.creationDate.copy();
        this.assignedVotesPoints = other.assignedVotesPoints == null ? null : other.assignedVotesPoints.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.appprofileId = other.appprofileId == null ? null : other.appprofileId.copy();
        this.appphotoId = other.appphotoId == null ? null : other.appphotoId.copy();
        this.communityId = other.communityId == null ? null : other.communityId.copy();
        this.blogId = other.blogId == null ? null : other.blogId.copy();
        this.notificationId = other.notificationId == null ? null : other.notificationId.copy();
        this.albumId = other.albumId == null ? null : other.albumId.copy();
        this.commentId = other.commentId == null ? null : other.commentId.copy();
        this.postId = other.postId == null ? null : other.postId.copy();
        this.senderId = other.senderId == null ? null : other.senderId.copy();
        this.receiverId = other.receiverId == null ? null : other.receiverId.copy();
        this.followedId = other.followedId == null ? null : other.followedId.copy();
        this.followingId = other.followingId == null ? null : other.followingId.copy();
        this.blockeduserId = other.blockeduserId == null ? null : other.blockeduserId.copy();
        this.blockinguserId = other.blockinguserId == null ? null : other.blockinguserId.copy();
        this.vtopicId = other.vtopicId == null ? null : other.vtopicId.copy();
        this.vquestionId = other.vquestionId == null ? null : other.vquestionId.copy();
        this.vanswerId = other.vanswerId == null ? null : other.vanswerId.copy();
        this.vthumbId = other.vthumbId == null ? null : other.vthumbId.copy();
        this.proposalId = other.proposalId == null ? null : other.proposalId.copy();
        this.proposalVoteId = other.proposalVoteId == null ? null : other.proposalVoteId.copy();
        this.interestId = other.interestId == null ? null : other.interestId.copy();
        this.activityId = other.activityId == null ? null : other.activityId.copy();
        this.celebId = other.celebId == null ? null : other.celebId.copy();
    }

    @Override
    public AppuserCriteria copy() {
        return new AppuserCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public InstantFilter getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(InstantFilter creationDate) {
        this.creationDate = creationDate;
    }

    public LongFilter getAssignedVotesPoints() {
        return assignedVotesPoints;
    }

    public void setAssignedVotesPoints(LongFilter assignedVotesPoints) {
        this.assignedVotesPoints = assignedVotesPoints;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    public LongFilter getAppprofileId() {
        return appprofileId;
    }

    public void setAppprofileId(LongFilter appprofileId) {
        this.appprofileId = appprofileId;
    }

    public LongFilter getAppphotoId() {
        return appphotoId;
    }

    public void setAppphotoId(LongFilter appphotoId) {
        this.appphotoId = appphotoId;
    }

    public LongFilter getCommunityId() {
        return communityId;
    }

    public void setCommunityId(LongFilter communityId) {
        this.communityId = communityId;
    }

    public LongFilter getBlogId() {
        return blogId;
    }

    public void setBlogId(LongFilter blogId) {
        this.blogId = blogId;
    }

    public LongFilter getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(LongFilter notificationId) {
        this.notificationId = notificationId;
    }

    public LongFilter getAlbumId() {
        return albumId;
    }

    public void setAlbumId(LongFilter albumId) {
        this.albumId = albumId;
    }

    public LongFilter getCommentId() {
        return commentId;
    }

    public void setCommentId(LongFilter commentId) {
        this.commentId = commentId;
    }

    public LongFilter getPostId() {
        return postId;
    }

    public void setPostId(LongFilter postId) {
        this.postId = postId;
    }

    public LongFilter getSenderId() {
        return senderId;
    }

    public void setSenderId(LongFilter senderId) {
        this.senderId = senderId;
    }

    public LongFilter getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(LongFilter receiverId) {
        this.receiverId = receiverId;
    }

    public LongFilter getFollowedId() {
        return followedId;
    }

    public void setFollowedId(LongFilter followedId) {
        this.followedId = followedId;
    }

    public LongFilter getFollowingId() {
        return followingId;
    }

    public void setFollowingId(LongFilter followingId) {
        this.followingId = followingId;
    }

    public LongFilter getBlockeduserId() {
        return blockeduserId;
    }

    public void setBlockeduserId(LongFilter blockeduserId) {
        this.blockeduserId = blockeduserId;
    }

    public LongFilter getBlockinguserId() {
        return blockinguserId;
    }

    public void setBlockinguserId(LongFilter blockinguserId) {
        this.blockinguserId = blockinguserId;
    }

    public LongFilter getVtopicId() {
        return vtopicId;
    }

    public void setVtopicId(LongFilter vtopicId) {
        this.vtopicId = vtopicId;
    }

    public LongFilter getVquestionId() {
        return vquestionId;
    }

    public void setVquestionId(LongFilter vquestionId) {
        this.vquestionId = vquestionId;
    }

    public LongFilter getVanswerId() {
        return vanswerId;
    }

    public void setVanswerId(LongFilter vanswerId) {
        this.vanswerId = vanswerId;
    }

    public LongFilter getVthumbId() {
        return vthumbId;
    }

    public void setVthumbId(LongFilter vthumbId) {
        this.vthumbId = vthumbId;
    }

    public LongFilter getProposalId() {
        return proposalId;
    }

    public void setProposalId(LongFilter proposalId) {
        this.proposalId = proposalId;
    }

    public LongFilter getProposalVoteId() {
        return proposalVoteId;
    }

    public void setProposalVoteId(LongFilter proposalVoteId) {
        this.proposalVoteId = proposalVoteId;
    }

    public LongFilter getInterestId() {
        return interestId;
    }

    public void setInterestId(LongFilter interestId) {
        this.interestId = interestId;
    }

    public LongFilter getActivityId() {
        return activityId;
    }

    public void setActivityId(LongFilter activityId) {
        this.activityId = activityId;
    }

    public LongFilter getCelebId() {
        return celebId;
    }

    public void setCelebId(LongFilter celebId) {
        this.celebId = celebId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AppuserCriteria that = (AppuserCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(creationDate, that.creationDate) &&
            Objects.equals(assignedVotesPoints, that.assignedVotesPoints) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(appprofileId, that.appprofileId) &&
            Objects.equals(appphotoId, that.appphotoId) &&
            Objects.equals(communityId, that.communityId) &&
            Objects.equals(blogId, that.blogId) &&
            Objects.equals(notificationId, that.notificationId) &&
            Objects.equals(albumId, that.albumId) &&
            Objects.equals(commentId, that.commentId) &&
            Objects.equals(postId, that.postId) &&
            Objects.equals(senderId, that.senderId) &&
            Objects.equals(receiverId, that.receiverId) &&
            Objects.equals(followedId, that.followedId) &&
            Objects.equals(followingId, that.followingId) &&
            Objects.equals(blockeduserId, that.blockeduserId) &&
            Objects.equals(blockinguserId, that.blockinguserId) &&
            Objects.equals(vtopicId, that.vtopicId) &&
            Objects.equals(vquestionId, that.vquestionId) &&
            Objects.equals(vanswerId, that.vanswerId) &&
            Objects.equals(vthumbId, that.vthumbId) &&
            Objects.equals(proposalId, that.proposalId) &&
            Objects.equals(proposalVoteId, that.proposalVoteId) &&
            Objects.equals(interestId, that.interestId) &&
            Objects.equals(activityId, that.activityId) &&
            Objects.equals(celebId, that.celebId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        creationDate,
        assignedVotesPoints,
        userId,
        appprofileId,
        appphotoId,
        communityId,
        blogId,
        notificationId,
        albumId,
        commentId,
        postId,
        senderId,
        receiverId,
        followedId,
        followingId,
        blockeduserId,
        blockinguserId,
        vtopicId,
        vquestionId,
        vanswerId,
        vthumbId,
        proposalId,
        proposalVoteId,
        interestId,
        activityId,
        celebId
        );
    }

    @Override
    public String toString() {
        return "AppuserCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (creationDate != null ? "creationDate=" + creationDate + ", " : "") +
                (assignedVotesPoints != null ? "assignedVotesPoints=" + assignedVotesPoints + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (appprofileId != null ? "appprofileId=" + appprofileId + ", " : "") +
                (appphotoId != null ? "appphotoId=" + appphotoId + ", " : "") +
                (communityId != null ? "communityId=" + communityId + ", " : "") +
                (blogId != null ? "blogId=" + blogId + ", " : "") +
                (notificationId != null ? "notificationId=" + notificationId + ", " : "") +
                (albumId != null ? "albumId=" + albumId + ", " : "") +
                (commentId != null ? "commentId=" + commentId + ", " : "") +
                (postId != null ? "postId=" + postId + ", " : "") +
                (senderId != null ? "senderId=" + senderId + ", " : "") +
                (receiverId != null ? "receiverId=" + receiverId + ", " : "") +
                (followedId != null ? "followedId=" + followedId + ", " : "") +
                (followingId != null ? "followingId=" + followingId + ", " : "") +
                (blockeduserId != null ? "blockeduserId=" + blockeduserId + ", " : "") +
                (blockinguserId != null ? "blockinguserId=" + blockinguserId + ", " : "") +
                (vtopicId != null ? "vtopicId=" + vtopicId + ", " : "") +
                (vquestionId != null ? "vquestionId=" + vquestionId + ", " : "") +
                (vanswerId != null ? "vanswerId=" + vanswerId + ", " : "") +
                (vthumbId != null ? "vthumbId=" + vthumbId + ", " : "") +
                (proposalId != null ? "proposalId=" + proposalId + ", " : "") +
                (proposalVoteId != null ? "proposalVoteId=" + proposalVoteId + ", " : "") +
                (interestId != null ? "interestId=" + interestId + ", " : "") +
                (activityId != null ? "activityId=" + activityId + ", " : "") +
                (celebId != null ? "celebId=" + celebId + ", " : "") +
            "}";
    }

}
