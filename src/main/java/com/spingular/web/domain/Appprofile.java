package com.spingular.web.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

import com.spingular.web.domain.enumeration.Gender;

import com.spingular.web.domain.enumeration.CivilStatus;

import com.spingular.web.domain.enumeration.Purpose;

import com.spingular.web.domain.enumeration.Physical;

import com.spingular.web.domain.enumeration.Religion;

import com.spingular.web.domain.enumeration.EthnicGroup;

import com.spingular.web.domain.enumeration.Studies;

import com.spingular.web.domain.enumeration.Eyes;

import com.spingular.web.domain.enumeration.Smoker;

import com.spingular.web.domain.enumeration.Children;

import com.spingular.web.domain.enumeration.FutureChildren;

/**
 * A Appprofile.
 */
@Entity
@Table(name = "appprofile")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Appprofile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "creation_date", nullable = false)
    private Instant creationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Size(max = 20)
    @Column(name = "phone", length = 20)
    private String phone;

    @Size(max = 7500)
    @Column(name = "bio", length = 7500)
    private String bio;

    @Size(max = 50)
    @Column(name = "facebook", length = 50)
    private String facebook;

    @Size(max = 50)
    @Column(name = "twitter", length = 50)
    private String twitter;

    @Size(max = 50)
    @Column(name = "linkedin", length = 50)
    private String linkedin;

    @Size(max = 50)
    @Column(name = "instagram", length = 50)
    private String instagram;

    @Size(max = 50)
    @Column(name = "google_plus", length = 50)
    private String googlePlus;

    @Column(name = "birthdate")
    private Instant birthdate;

    @Enumerated(EnumType.STRING)
    @Column(name = "civil_status")
    private CivilStatus civilStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "looking_for")
    private Gender lookingFor;

    @Enumerated(EnumType.STRING)
    @Column(name = "purpose")
    private Purpose purpose;

    @Enumerated(EnumType.STRING)
    @Column(name = "physical")
    private Physical physical;

    @Enumerated(EnumType.STRING)
    @Column(name = "religion")
    private Religion religion;

    @Enumerated(EnumType.STRING)
    @Column(name = "ethnic_group")
    private EthnicGroup ethnicGroup;

    @Enumerated(EnumType.STRING)
    @Column(name = "studies")
    private Studies studies;

    @Min(value = -1)
    @Max(value = 20)
    @Column(name = "sibblings")
    private Integer sibblings;

    @Enumerated(EnumType.STRING)
    @Column(name = "eyes")
    private Eyes eyes;

    @Enumerated(EnumType.STRING)
    @Column(name = "smoker")
    private Smoker smoker;

    @Enumerated(EnumType.STRING)
    @Column(name = "children")
    private Children children;

    @Enumerated(EnumType.STRING)
    @Column(name = "future_children")
    private FutureChildren futureChildren;

    @Column(name = "pet")
    private Boolean pet;

    @OneToOne(optional = false)    @NotNull

    @JoinColumn(unique = true)
    private Appuser appuser;

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

    public Appprofile creationDate(Instant creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public Gender getGender() {
        return gender;
    }

    public Appprofile gender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public Appprofile phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBio() {
        return bio;
    }

    public Appprofile bio(String bio) {
        this.bio = bio;
        return this;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getFacebook() {
        return facebook;
    }

    public Appprofile facebook(String facebook) {
        this.facebook = facebook;
        return this;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getTwitter() {
        return twitter;
    }

    public Appprofile twitter(String twitter) {
        this.twitter = twitter;
        return this;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public Appprofile linkedin(String linkedin) {
        this.linkedin = linkedin;
        return this;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }

    public String getInstagram() {
        return instagram;
    }

    public Appprofile instagram(String instagram) {
        this.instagram = instagram;
        return this;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getGooglePlus() {
        return googlePlus;
    }

    public Appprofile googlePlus(String googlePlus) {
        this.googlePlus = googlePlus;
        return this;
    }

    public void setGooglePlus(String googlePlus) {
        this.googlePlus = googlePlus;
    }

    public Instant getBirthdate() {
        return birthdate;
    }

    public Appprofile birthdate(Instant birthdate) {
        this.birthdate = birthdate;
        return this;
    }

    public void setBirthdate(Instant birthdate) {
        this.birthdate = birthdate;
    }

    public CivilStatus getCivilStatus() {
        return civilStatus;
    }

    public Appprofile civilStatus(CivilStatus civilStatus) {
        this.civilStatus = civilStatus;
        return this;
    }

    public void setCivilStatus(CivilStatus civilStatus) {
        this.civilStatus = civilStatus;
    }

    public Gender getLookingFor() {
        return lookingFor;
    }

    public Appprofile lookingFor(Gender lookingFor) {
        this.lookingFor = lookingFor;
        return this;
    }

    public void setLookingFor(Gender lookingFor) {
        this.lookingFor = lookingFor;
    }

    public Purpose getPurpose() {
        return purpose;
    }

    public Appprofile purpose(Purpose purpose) {
        this.purpose = purpose;
        return this;
    }

    public void setPurpose(Purpose purpose) {
        this.purpose = purpose;
    }

    public Physical getPhysical() {
        return physical;
    }

    public Appprofile physical(Physical physical) {
        this.physical = physical;
        return this;
    }

    public void setPhysical(Physical physical) {
        this.physical = physical;
    }

    public Religion getReligion() {
        return religion;
    }

    public Appprofile religion(Religion religion) {
        this.religion = religion;
        return this;
    }

    public void setReligion(Religion religion) {
        this.religion = religion;
    }

    public EthnicGroup getEthnicGroup() {
        return ethnicGroup;
    }

    public Appprofile ethnicGroup(EthnicGroup ethnicGroup) {
        this.ethnicGroup = ethnicGroup;
        return this;
    }

    public void setEthnicGroup(EthnicGroup ethnicGroup) {
        this.ethnicGroup = ethnicGroup;
    }

    public Studies getStudies() {
        return studies;
    }

    public Appprofile studies(Studies studies) {
        this.studies = studies;
        return this;
    }

    public void setStudies(Studies studies) {
        this.studies = studies;
    }

    public Integer getSibblings() {
        return sibblings;
    }

    public Appprofile sibblings(Integer sibblings) {
        this.sibblings = sibblings;
        return this;
    }

    public void setSibblings(Integer sibblings) {
        this.sibblings = sibblings;
    }

    public Eyes getEyes() {
        return eyes;
    }

    public Appprofile eyes(Eyes eyes) {
        this.eyes = eyes;
        return this;
    }

    public void setEyes(Eyes eyes) {
        this.eyes = eyes;
    }

    public Smoker getSmoker() {
        return smoker;
    }

    public Appprofile smoker(Smoker smoker) {
        this.smoker = smoker;
        return this;
    }

    public void setSmoker(Smoker smoker) {
        this.smoker = smoker;
    }

    public Children getChildren() {
        return children;
    }

    public Appprofile children(Children children) {
        this.children = children;
        return this;
    }

    public void setChildren(Children children) {
        this.children = children;
    }

    public FutureChildren getFutureChildren() {
        return futureChildren;
    }

    public Appprofile futureChildren(FutureChildren futureChildren) {
        this.futureChildren = futureChildren;
        return this;
    }

    public void setFutureChildren(FutureChildren futureChildren) {
        this.futureChildren = futureChildren;
    }

    public Boolean isPet() {
        return pet;
    }

    public Appprofile pet(Boolean pet) {
        this.pet = pet;
        return this;
    }

    public void setPet(Boolean pet) {
        this.pet = pet;
    }

    public Appuser getAppuser() {
        return appuser;
    }

    public Appprofile appuser(Appuser appuser) {
        this.appuser = appuser;
        return this;
    }

    public void setAppuser(Appuser appuser) {
        this.appuser = appuser;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Appprofile)) {
            return false;
        }
        return id != null && id.equals(((Appprofile) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Appprofile{" +
            "id=" + getId() +
            ", creationDate='" + getCreationDate() + "'" +
            ", gender='" + getGender() + "'" +
            ", phone='" + getPhone() + "'" +
            ", bio='" + getBio() + "'" +
            ", facebook='" + getFacebook() + "'" +
            ", twitter='" + getTwitter() + "'" +
            ", linkedin='" + getLinkedin() + "'" +
            ", instagram='" + getInstagram() + "'" +
            ", googlePlus='" + getGooglePlus() + "'" +
            ", birthdate='" + getBirthdate() + "'" +
            ", civilStatus='" + getCivilStatus() + "'" +
            ", lookingFor='" + getLookingFor() + "'" +
            ", purpose='" + getPurpose() + "'" +
            ", physical='" + getPhysical() + "'" +
            ", religion='" + getReligion() + "'" +
            ", ethnicGroup='" + getEthnicGroup() + "'" +
            ", studies='" + getStudies() + "'" +
            ", sibblings=" + getSibblings() +
            ", eyes='" + getEyes() + "'" +
            ", smoker='" + getSmoker() + "'" +
            ", children='" + getChildren() + "'" +
            ", futureChildren='" + getFutureChildren() + "'" +
            ", pet='" + isPet() + "'" +
            "}";
    }
}
