package com.spingular.web.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

import com.spingular.web.domain.enumeration.NotificationReason;

/**
 * A Notification.
 */
@Entity
@Table(name = "notification")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Notification implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "creation_date", nullable = false)
    private Instant creationDate;

    @Column(name = "notification_date")
    private Instant notificationDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "notification_reason", nullable = false)
    private NotificationReason notificationReason;

    @Size(min = 2, max = 100)
    @Column(name = "notification_text", length = 100)
    private String notificationText;

    @Column(name = "is_delivered")
    private Boolean isDelivered;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("notifications")
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

    public Notification creationDate(Instant creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public Instant getNotificationDate() {
        return notificationDate;
    }

    public Notification notificationDate(Instant notificationDate) {
        this.notificationDate = notificationDate;
        return this;
    }

    public void setNotificationDate(Instant notificationDate) {
        this.notificationDate = notificationDate;
    }

    public NotificationReason getNotificationReason() {
        return notificationReason;
    }

    public Notification notificationReason(NotificationReason notificationReason) {
        this.notificationReason = notificationReason;
        return this;
    }

    public void setNotificationReason(NotificationReason notificationReason) {
        this.notificationReason = notificationReason;
    }

    public String getNotificationText() {
        return notificationText;
    }

    public Notification notificationText(String notificationText) {
        this.notificationText = notificationText;
        return this;
    }

    public void setNotificationText(String notificationText) {
        this.notificationText = notificationText;
    }

    public Boolean isIsDelivered() {
        return isDelivered;
    }

    public Notification isDelivered(Boolean isDelivered) {
        this.isDelivered = isDelivered;
        return this;
    }

    public void setIsDelivered(Boolean isDelivered) {
        this.isDelivered = isDelivered;
    }

    public Appuser getAppuser() {
        return appuser;
    }

    public Notification appuser(Appuser appuser) {
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
        if (!(o instanceof Notification)) {
            return false;
        }
        return id != null && id.equals(((Notification) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Notification{" +
            "id=" + getId() +
            ", creationDate='" + getCreationDate() + "'" +
            ", notificationDate='" + getNotificationDate() + "'" +
            ", notificationReason='" + getNotificationReason() + "'" +
            ", notificationText='" + getNotificationText() + "'" +
            ", isDelivered='" + isIsDelivered() + "'" +
            "}";
    }
}
