package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
@Access(AccessType.PROPERTY)
public class Comentable extends DomainEntity {

	// Constructor
	// ====================================================================================

	public Comentable() {
		super();
	}


	// Getters & setters
	// ====================================================================================

	// Relationships
	// ====================================================================================

	private Collection<Comment>	comments;


	@OneToMany(mappedBy = "comentable")
	public Collection<Comment> getComments() {
		return comments;
	}

	public void setComments(Collection<Comment> comments) {
		this.comments = comments;
	}
}

