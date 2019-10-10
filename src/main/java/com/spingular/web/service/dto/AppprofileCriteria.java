package com.spingular.web.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.spingular.web.domain.enumeration.Gender;
import com.spingular.web.domain.enumeration.CivilStatus;
import com.spingular.web.domain.enumeration.Gender;
import com.spingular.web.domain.enumeration.Purpose;
import com.spingular.web.domain.enumeration.Physical;
import com.spingular.web.domain.enumeration.Religion;
import com.spingular.web.domain.enumeration.EthnicGroup;
import com.spingular.web.domain.enumeration.Studies;
import com.spingular.web.domain.enumeration.Eyes;
import com.spingular.web.domain.enumeration.Smoker;
import com.spingular.web.domain.enumeration.Children;
import com.spingular.web.domain.enumeration.FutureChildren;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.spingular.web.domain.Appprofile} entity. This class is used
 * in {@link com.spingular.web.web.rest.AppprofileResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /appprofiles?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AppprofileCriteria implements Serializable, Criteria {
    /**
     * Class for filtering Gender
     */
    public static class GenderFilter extends Filter<Gender> {

        public GenderFilter() {
        }

        public GenderFilter(GenderFilter filter) {
            super(filter);
        }

        @Override
        public GenderFilter copy() {
            return new GenderFilter(this);
        }

    }
    /**
     * Class for filtering CivilStatus
     */
    public static class CivilStatusFilter extends Filter<CivilStatus> {

        public CivilStatusFilter() {
        }

        public CivilStatusFilter(CivilStatusFilter filter) {
            super(filter);
        }

        @Override
        public CivilStatusFilter copy() {
            return new CivilStatusFilter(this);
        }

    }
    /**
     * Class for filtering Purpose
     */
    public static class PurposeFilter extends Filter<Purpose> {

        public PurposeFilter() {
        }

        public PurposeFilter(PurposeFilter filter) {
            super(filter);
        }

        @Override
        public PurposeFilter copy() {
            return new PurposeFilter(this);
        }

    }
    /**
     * Class for filtering Physical
     */
    public static class PhysicalFilter extends Filter<Physical> {

        public PhysicalFilter() {
        }

        public PhysicalFilter(PhysicalFilter filter) {
            super(filter);
        }

        @Override
        public PhysicalFilter copy() {
            return new PhysicalFilter(this);
        }

    }
    /**
     * Class for filtering Religion
     */
    public static class ReligionFilter extends Filter<Religion> {

        public ReligionFilter() {
        }

        public ReligionFilter(ReligionFilter filter) {
            super(filter);
        }

        @Override
        public ReligionFilter copy() {
            return new ReligionFilter(this);
        }

    }
    /**
     * Class for filtering EthnicGroup
     */
    public static class EthnicGroupFilter extends Filter<EthnicGroup> {

        public EthnicGroupFilter() {
        }

        public EthnicGroupFilter(EthnicGroupFilter filter) {
            super(filter);
        }

        @Override
        public EthnicGroupFilter copy() {
            return new EthnicGroupFilter(this);
        }

    }
    /**
     * Class for filtering Studies
     */
    public static class StudiesFilter extends Filter<Studies> {

        public StudiesFilter() {
        }

        public StudiesFilter(StudiesFilter filter) {
            super(filter);
        }

        @Override
        public StudiesFilter copy() {
            return new StudiesFilter(this);
        }

    }
    /**
     * Class for filtering Eyes
     */
    public static class EyesFilter extends Filter<Eyes> {

        public EyesFilter() {
        }

        public EyesFilter(EyesFilter filter) {
            super(filter);
        }

        @Override
        public EyesFilter copy() {
            return new EyesFilter(this);
        }

    }
    /**
     * Class for filtering Smoker
     */
    public static class SmokerFilter extends Filter<Smoker> {

        public SmokerFilter() {
        }

        public SmokerFilter(SmokerFilter filter) {
            super(filter);
        }

        @Override
        public SmokerFilter copy() {
            return new SmokerFilter(this);
        }

    }
    /**
     * Class for filtering Children
     */
    public static class ChildrenFilter extends Filter<Children> {

        public ChildrenFilter() {
        }

        public ChildrenFilter(ChildrenFilter filter) {
            super(filter);
        }

        @Override
        public ChildrenFilter copy() {
            return new ChildrenFilter(this);
        }

    }
    /**
     * Class for filtering FutureChildren
     */
    public static class FutureChildrenFilter extends Filter<FutureChildren> {

        public FutureChildrenFilter() {
        }

        public FutureChildrenFilter(FutureChildrenFilter filter) {
            super(filter);
        }

        @Override
        public FutureChildrenFilter copy() {
            return new FutureChildrenFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter creationDate;

    private GenderFilter gender;

    private StringFilter phone;

    private StringFilter bio;

    private StringFilter facebook;

    private StringFilter twitter;

    private StringFilter linkedin;

    private StringFilter instagram;

    private StringFilter googlePlus;

    private InstantFilter birthdate;

    private CivilStatusFilter civilStatus;

    private GenderFilter lookingFor;

    private PurposeFilter purpose;

    private PhysicalFilter physical;

    private ReligionFilter religion;

    private EthnicGroupFilter ethnicGroup;

    private StudiesFilter studies;

    private IntegerFilter sibblings;

    private EyesFilter eyes;

    private SmokerFilter smoker;

    private ChildrenFilter children;

    private FutureChildrenFilter futureChildren;

    private BooleanFilter pet;

    private LongFilter appuserId;

    public AppprofileCriteria(){
    }

    public AppprofileCriteria(AppprofileCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.creationDate = other.creationDate == null ? null : other.creationDate.copy();
        this.gender = other.gender == null ? null : other.gender.copy();
        this.phone = other.phone == null ? null : other.phone.copy();
        this.bio = other.bio == null ? null : other.bio.copy();
        this.facebook = other.facebook == null ? null : other.facebook.copy();
        this.twitter = other.twitter == null ? null : other.twitter.copy();
        this.linkedin = other.linkedin == null ? null : other.linkedin.copy();
        this.instagram = other.instagram == null ? null : other.instagram.copy();
        this.googlePlus = other.googlePlus == null ? null : other.googlePlus.copy();
        this.birthdate = other.birthdate == null ? null : other.birthdate.copy();
        this.civilStatus = other.civilStatus == null ? null : other.civilStatus.copy();
        this.lookingFor = other.lookingFor == null ? null : other.lookingFor.copy();
        this.purpose = other.purpose == null ? null : other.purpose.copy();
        this.physical = other.physical == null ? null : other.physical.copy();
        this.religion = other.religion == null ? null : other.religion.copy();
        this.ethnicGroup = other.ethnicGroup == null ? null : other.ethnicGroup.copy();
        this.studies = other.studies == null ? null : other.studies.copy();
        this.sibblings = other.sibblings == null ? null : other.sibblings.copy();
        this.eyes = other.eyes == null ? null : other.eyes.copy();
        this.smoker = other.smoker == null ? null : other.smoker.copy();
        this.children = other.children == null ? null : other.children.copy();
        this.futureChildren = other.futureChildren == null ? null : other.futureChildren.copy();
        this.pet = other.pet == null ? null : other.pet.copy();
        this.appuserId = other.appuserId == null ? null : other.appuserId.copy();
    }

    @Override
    public AppprofileCriteria copy() {
        return new AppprofileCriteria(this);
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

    public GenderFilter getGender() {
        return gender;
    }

    public void setGender(GenderFilter gender) {
        this.gender = gender;
    }

    public StringFilter getPhone() {
        return phone;
    }

    public void setPhone(StringFilter phone) {
        this.phone = phone;
    }

    public StringFilter getBio() {
        return bio;
    }

    public void setBio(StringFilter bio) {
        this.bio = bio;
    }

    public StringFilter getFacebook() {
        return facebook;
    }

    public void setFacebook(StringFilter facebook) {
        this.facebook = facebook;
    }

    public StringFilter getTwitter() {
        return twitter;
    }

    public void setTwitter(StringFilter twitter) {
        this.twitter = twitter;
    }

    public StringFilter getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(StringFilter linkedin) {
        this.linkedin = linkedin;
    }

    public StringFilter getInstagram() {
        return instagram;
    }

    public void setInstagram(StringFilter instagram) {
        this.instagram = instagram;
    }

    public StringFilter getGooglePlus() {
        return googlePlus;
    }

    public void setGooglePlus(StringFilter googlePlus) {
        this.googlePlus = googlePlus;
    }

    public InstantFilter getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(InstantFilter birthdate) {
        this.birthdate = birthdate;
    }

    public CivilStatusFilter getCivilStatus() {
        return civilStatus;
    }

    public void setCivilStatus(CivilStatusFilter civilStatus) {
        this.civilStatus = civilStatus;
    }

    public GenderFilter getLookingFor() {
        return lookingFor;
    }

    public void setLookingFor(GenderFilter lookingFor) {
        this.lookingFor = lookingFor;
    }

    public PurposeFilter getPurpose() {
        return purpose;
    }

    public void setPurpose(PurposeFilter purpose) {
        this.purpose = purpose;
    }

    public PhysicalFilter getPhysical() {
        return physical;
    }

    public void setPhysical(PhysicalFilter physical) {
        this.physical = physical;
    }

    public ReligionFilter getReligion() {
        return religion;
    }

    public void setReligion(ReligionFilter religion) {
        this.religion = religion;
    }

    public EthnicGroupFilter getEthnicGroup() {
        return ethnicGroup;
    }

    public void setEthnicGroup(EthnicGroupFilter ethnicGroup) {
        this.ethnicGroup = ethnicGroup;
    }

    public StudiesFilter getStudies() {
        return studies;
    }

    public void setStudies(StudiesFilter studies) {
        this.studies = studies;
    }

    public IntegerFilter getSibblings() {
        return sibblings;
    }

    public void setSibblings(IntegerFilter sibblings) {
        this.sibblings = sibblings;
    }

    public EyesFilter getEyes() {
        return eyes;
    }

    public void setEyes(EyesFilter eyes) {
        this.eyes = eyes;
    }

    public SmokerFilter getSmoker() {
        return smoker;
    }

    public void setSmoker(SmokerFilter smoker) {
        this.smoker = smoker;
    }

    public ChildrenFilter getChildren() {
        return children;
    }

    public void setChildren(ChildrenFilter children) {
        this.children = children;
    }

    public FutureChildrenFilter getFutureChildren() {
        return futureChildren;
    }

    public void setFutureChildren(FutureChildrenFilter futureChildren) {
        this.futureChildren = futureChildren;
    }

    public BooleanFilter getPet() {
        return pet;
    }

    public void setPet(BooleanFilter pet) {
        this.pet = pet;
    }

    public LongFilter getAppuserId() {
        return appuserId;
    }

    public void setAppuserId(LongFilter appuserId) {
        this.appuserId = appuserId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AppprofileCriteria that = (AppprofileCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(creationDate, that.creationDate) &&
            Objects.equals(gender, that.gender) &&
            Objects.equals(phone, that.phone) &&
            Objects.equals(bio, that.bio) &&
            Objects.equals(facebook, that.facebook) &&
            Objects.equals(twitter, that.twitter) &&
            Objects.equals(linkedin, that.linkedin) &&
            Objects.equals(instagram, that.instagram) &&
            Objects.equals(googlePlus, that.googlePlus) &&
            Objects.equals(birthdate, that.birthdate) &&
            Objects.equals(civilStatus, that.civilStatus) &&
            Objects.equals(lookingFor, that.lookingFor) &&
            Objects.equals(purpose, that.purpose) &&
            Objects.equals(physical, that.physical) &&
            Objects.equals(religion, that.religion) &&
            Objects.equals(ethnicGroup, that.ethnicGroup) &&
            Objects.equals(studies, that.studies) &&
            Objects.equals(sibblings, that.sibblings) &&
            Objects.equals(eyes, that.eyes) &&
            Objects.equals(smoker, that.smoker) &&
            Objects.equals(children, that.children) &&
            Objects.equals(futureChildren, that.futureChildren) &&
            Objects.equals(pet, that.pet) &&
            Objects.equals(appuserId, that.appuserId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        creationDate,
        gender,
        phone,
        bio,
        facebook,
        twitter,
        linkedin,
        instagram,
        googlePlus,
        birthdate,
        civilStatus,
        lookingFor,
        purpose,
        physical,
        religion,
        ethnicGroup,
        studies,
        sibblings,
        eyes,
        smoker,
        children,
        futureChildren,
        pet,
        appuserId
        );
    }

    @Override
    public String toString() {
        return "AppprofileCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (creationDate != null ? "creationDate=" + creationDate + ", " : "") +
                (gender != null ? "gender=" + gender + ", " : "") +
                (phone != null ? "phone=" + phone + ", " : "") +
                (bio != null ? "bio=" + bio + ", " : "") +
                (facebook != null ? "facebook=" + facebook + ", " : "") +
                (twitter != null ? "twitter=" + twitter + ", " : "") +
                (linkedin != null ? "linkedin=" + linkedin + ", " : "") +
                (instagram != null ? "instagram=" + instagram + ", " : "") +
                (googlePlus != null ? "googlePlus=" + googlePlus + ", " : "") +
                (birthdate != null ? "birthdate=" + birthdate + ", " : "") +
                (civilStatus != null ? "civilStatus=" + civilStatus + ", " : "") +
                (lookingFor != null ? "lookingFor=" + lookingFor + ", " : "") +
                (purpose != null ? "purpose=" + purpose + ", " : "") +
                (physical != null ? "physical=" + physical + ", " : "") +
                (religion != null ? "religion=" + religion + ", " : "") +
                (ethnicGroup != null ? "ethnicGroup=" + ethnicGroup + ", " : "") +
                (studies != null ? "studies=" + studies + ", " : "") +
                (sibblings != null ? "sibblings=" + sibblings + ", " : "") +
                (eyes != null ? "eyes=" + eyes + ", " : "") +
                (smoker != null ? "smoker=" + smoker + ", " : "") +
                (children != null ? "children=" + children + ", " : "") +
                (futureChildren != null ? "futureChildren=" + futureChildren + ", " : "") +
                (pet != null ? "pet=" + pet + ", " : "") +
                (appuserId != null ? "appuserId=" + appuserId + ", " : "") +
            "}";
    }

}
