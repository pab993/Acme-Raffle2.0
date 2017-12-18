
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Comment extends DomainEntity {

	// Constructors
	// ====================================================================================

	// Attributes
	// ====================================================================================

	private Date	createMoment;
	private String	text;
	private int		score;


	@NotNull
	@Past
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreateMoment() {
		return this.createMoment;
	}

	public void setCreateMoment(final Date createMoment) {
		this.createMoment = createMoment;
	}

	@NotBlank
	public String getText() {
		return this.text;
	}

	public void setText(final String text) {
		this.text = text;
	}

	@Range(min = 0, max = 3)
	@NotNull
	public int getScore() {
		return this.score;
	}

	public void setScore(final int score) {
		this.score = score;
	}


	// Relationships
	// ====================================================================================

	private Actor		actor;
	private Comentable	comentable;


	@Valid
	@ManyToOne(optional = false)
	public Actor getActor() {
		return this.actor;
	}

	public void setActor(final Actor actor) {
		this.actor = actor;
	}

	@Valid
	@ManyToOne(optional = false)
	public Comentable getComentable() {
		return this.comentable;
	}

	public void setComentable(final Comentable comentable) {
		this.comentable = comentable;
	}

}
