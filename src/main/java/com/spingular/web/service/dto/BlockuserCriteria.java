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
 * Criteria class for the {@link com.spingular.web.domain.Blockuser} entity. This class is used
 * in {@link com.spingular.web.web.rest.BlockuserResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /blockusers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class BlockuserCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter creationDate;

    private LongFilter blockeduserId;

    private LongFilter blockinguserId;

    private LongFilter cblockeduserId;

    private LongFilter cblockinguserId;

    public BlockuserCriteria(){
    }

    public BlockuserCriteria(BlockuserCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.creationDate = other.creationDate == null ? null : other.creationDate.copy();
        this.blockeduserId = other.blockeduserId == null ? null : other.blockeduserId.copy();
        this.blockinguserId = other.blockinguserId == null ? null : other.blockinguserId.copy();
        this.cblockeduserId = other.cblockeduserId == null ? null : other.cblockeduserId.copy();
        this.cblockinguserId = other.cblockinguserId == null ? null : other.cblockinguserId.copy();
    }

    @Override
    public BlockuserCriteria copy() {
        return new BlockuserCriteria(this);
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

    public LongFilter getCblockeduserId() {
        return cblockeduserId;
    }

    public void setCblockeduserId(LongFilter cblockeduserId) {
        this.cblockeduserId = cblockeduserId;
    }

    public LongFilter getCblockinguserId() {
        return cblockinguserId;
    }

    public void setCblockinguserId(LongFilter cblockinguserId) {
        this.cblockinguserId = cblockinguserId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final BlockuserCriteria that = (BlockuserCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(creationDate, that.creationDate) &&
            Objects.equals(blockeduserId, that.blockeduserId) &&
            Objects.equals(blockinguserId, that.blockinguserId) &&
            Objects.equals(cblockeduserId, that.cblockeduserId) &&
            Objects.equals(cblockinguserId, that.cblockinguserId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        creationDate,
        blockeduserId,
        blockinguserId,
        cblockeduserId,
        cblockinguserId
        );
    }

    @Override
    public String toString() {
        return "BlockuserCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (creationDate != null ? "creationDate=" + creationDate + ", " : "") +
                (blockeduserId != null ? "blockeduserId=" + blockeduserId + ", " : "") +
                (blockinguserId != null ? "blockinguserId=" + blockinguserId + ", " : "") +
                (cblockeduserId != null ? "cblockeduserId=" + cblockeduserId + ", " : "") +
                (cblockinguserId != null ? "cblockinguserId=" + cblockinguserId + ", " : "") +
            "}";
    }

}
