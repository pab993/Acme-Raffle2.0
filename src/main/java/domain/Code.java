
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Code extends DomainEntity {

	private String	name;
	private Boolean	winner;
	private Date	moment;


	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	//	@Pattern(regexp = "^\\b([A-Z0-9])\\w{3}\\b[-, ,/]\\b([A-Z0-9])\\w{3}$")
	//	@Pattern(regexp = "^\\b[A-Z0-9]{4}\\b[-, ,/]\\b[A-Z0-9]{4}$")
	//Pattern5 ASDRS32D    \\b([A-Z0-9])\\w{7}\\b
	//	@Pattern(regexp = "^\\b[A-Z0-9]{4}\\b[-]\\b[A-Z0-9]{4}$")
	@Pattern(regexp = "^[A-Z0-9]{4}-[A-Z0-9]{4}$")
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public Boolean getWinner() {
		return this.winner;
	}

	public void setWinner(final Boolean winner) {
		this.winner = winner;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Past
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}


	//Relationships
	// =======================================================

	private Prize	prize;
	private User	user;


	@Valid
	@ManyToOne(optional = true)
	public Prize getPrize() {
		return this.prize;
	}

	public void setPrize(final Prize prize) {
		this.prize = prize;
	}

	@Valid
	@ManyToOne(optional = true)
	public User getUser() {
		return this.user;
	}

	public void setUser(final User user) {
		this.user = user;
	}

}
