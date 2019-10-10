package com.spingular.web.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Message.
 */
@Entity
@Table(name = "message")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "creation_date", nullable = false)
    private Instant creationDate;

    @NotNull
    @Size(min = 2, max = 8000)
    @Column(name = "message_text", length = 8000, nullable = false)
    private String messageText;

    @Column(name = "is_delivered")
    private Boolean isDelivered;

    @ManyToOne
    @JsonIgnoreProperties("senders")
    private Appuser sender;

    @ManyToOne
    @JsonIgnoreProperties("receivers")
    private Appuser receiver;

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

    public Message creationDate(Instant creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public String getMessageText() {
        return messageText;
    }

    public Message messageText(String messageText) {
        this.messageText = messageText;
        return this;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public Boolean isIsDelivered() {
        return isDelivered;
    }

    public Message isDelivered(Boolean isDelivered) {
        this.isDelivered = isDelivered;
        return this;
    }

    public void setIsDelivered(Boolean isDelivered) {
        this.isDelivered = isDelivered;
    }

    public Appuser getSender() {
        return sender;
    }

    public Message sender(Appuser appuser) {
        this.sender = appuser;
        return this;
    }

    public void setSender(Appuser appuser) {
        this.sender = appuser;
    }

    public Appuser getReceiver() {
        return receiver;
    }

    public Message receiver(Appuser appuser) {
        this.receiver = appuser;
        return this;
    }

    public void setReceiver(Appuser appuser) {
        this.receiver = appuser;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Message)) {
            return false;
        }
        return id != null && id.equals(((Message) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Message{" +
            "id=" + getId() +
            ", creationDate='" + getCreationDate() + "'" +
            ", messageText='" + getMessageText() + "'" +
            ", isDelivered='" + isIsDelivered() + "'" +
            "}";
    }
}
