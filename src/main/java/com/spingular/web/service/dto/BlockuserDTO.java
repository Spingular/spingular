package com.spingular.web.service.dto;
import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Lob;

/**
 * A DTO for the {@link com.spingular.web.domain.Blockuser} entity.
 */
public class BlockuserDTO implements Serializable {

    private Long id;

    private Instant creationDate;

    private Long blockeduserId;

    private String blockeduserFirstName;

    private String blockeduserLastName;
    
    private Long blockinguserId;

    private String blockinguserFirstName;

    private String blockinguserLastName;
    
    private Long cblockeduserId;
    
    @Lob
    private byte[] cblockeduserImage;

    private String cblockeduserImageContentType;

    private String cblockeduserCommunityname;

    private Long cblockinguserId;

    @Lob
    private byte[] cblockinguserImage;

    private String cblockinguserImageContentType;

    private String cblockinguserCommunityname;  

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public Long getBlockeduserId() {
        return blockeduserId;
    }

    public void setBlockeduserId(Long appuserId) {
        this.blockeduserId = appuserId;
    }

    public Long getBlockinguserId() {
        return blockinguserId;
    }

    public void setBlockinguserId(Long appuserId) {
        this.blockinguserId = appuserId;
    }

    public Long getCblockeduserId() {
        return cblockeduserId;
    }

    public void setCblockeduserId(Long communityId) {
        this.cblockeduserId = communityId;
    }

    public Long getCblockinguserId() {
        return cblockinguserId;
    }

    public void setCblockinguserId(Long communityId) {
        this.cblockinguserId = communityId;
    }

    public String getBlockeduserFirstName() {
		return blockeduserFirstName;
	}

	public void setBlockeduserFirstName(String blockeduserFirstName) {
		this.blockeduserFirstName = blockeduserFirstName;
	}

	public String getBlockeduserLastName() {
		return blockeduserLastName;
	}

	public void setBlockeduserLastName(String blockeduserLastName) {
		this.blockeduserLastName = blockeduserLastName;
	}

	public String getBlockinguserFirstName() {
		return blockinguserFirstName;
	}

	public void setBlockinguserFirstName(String blockinguserFirstName) {
		this.blockinguserFirstName = blockinguserFirstName;
	}

	public String getBlockinguserLastName() {
		return blockinguserLastName;
	}

	public void setBlockinguserLastName(String blockinguserLastName) {
		this.blockinguserLastName = blockinguserLastName;
	}

	public byte[] getCblockeduserImage() {
		return cblockeduserImage;
	}

	public void setCblockeduserImage(byte[] cblockeduserImage) {
		this.cblockeduserImage = cblockeduserImage;
	}

	public String getCblockeduserImageContentType() {
		return cblockeduserImageContentType;
	}

	public void setCblockeduserImageContentType(String cblockeduserImageContentType) {
		this.cblockeduserImageContentType = cblockeduserImageContentType;
	}

	public String getCblockeduserCommunityname() {
		return cblockeduserCommunityname;
	}

	public void setCblockeduserCommunityname(String cblockeduserCommunityname) {
		this.cblockeduserCommunityname = cblockeduserCommunityname;
	}

	public byte[] getCblockinguserImage() {
		return cblockinguserImage;
	}

	public void setCblockinguserImage(byte[] cblockinguserImage) {
		this.cblockinguserImage = cblockinguserImage;
	}

	public String getCblockinguserImageContentType() {
		return cblockinguserImageContentType;
	}

	public void setCblockinguserImageContentType(String cblockinguserImageContentType) {
		this.cblockinguserImageContentType = cblockinguserImageContentType;
	}

	public String getCblockinguserCommunityname() {
		return cblockinguserCommunityname;
	}

	public void setCblockinguserCommunityname(String cblockinguserCommunityname) {
		this.cblockinguserCommunityname = cblockinguserCommunityname;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BlockuserDTO blockuserDTO = (BlockuserDTO) o;
        if (blockuserDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), blockuserDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BlockuserDTO{" +
            "id=" + getId() +
            ", creationDate='" + getCreationDate() + "'" +
            ", blockeduser=" + getBlockeduserId() +
            ", blockeduserFirstName=" + getBlockeduserFirstName() +
            ", blockeduserLastName=" + getBlockeduserLastName() +
            ", blockinguser=" + getBlockinguserId() +
            ", blockinguserFirstName=" + getBlockinguserFirstName() +
            ", blockinguserLastName=" + getBlockinguserLastName() +
            ", cblockeduser=" + getCblockeduserId() +
            ", cblockeduserImage='" + getCblockeduserImage() + "'" +
            ", cblockeduserCommunityname=" + getCblockeduserCommunityname() +
            ", cblockinguser=" + getCblockinguserId() +
            ", cblockinguserImage='" + getCblockinguserImage() + "'" +
            ", cblockinguserCommunityname=" + getCblockinguserCommunityname() +
            "}";
    }
}
