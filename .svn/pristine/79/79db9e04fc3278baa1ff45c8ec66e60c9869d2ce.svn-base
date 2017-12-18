
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Entity
@Access(AccessType.PROPERTY)
public class Prize extends Comentable {

	private String	name;
	private String	description;
	private int		codesGenerated;
	private int		winnerCodes;


	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@Range(min = 1, max = 1000)
	public int getCodesGenerated() {
		return codesGenerated;
	}

	public void setCodesGenerated(int codesGenerated) {
		this.codesGenerated = codesGenerated;
	}

	@Range(min = 1, max = 1000)
	public int getWinnerCodes() {
		return winnerCodes;
	}

	public void setWinnerCodes(int winnerCodes) {
		this.winnerCodes = winnerCodes;
	}


	//Relationships
	// =======================================================

	private Raffle					raffle;
	private Collection<Code>		codes;
	private Collection<Property>	properties;


	@Valid
	@ManyToOne(optional = false)
	public Raffle getRaffle() {
		return this.raffle;
	}

	public void setRaffle(final Raffle raffle) {
		this.raffle = raffle;
	}

	@Valid
	@OneToMany(mappedBy = "prize")
	public Collection<Code> getCodes() {
		return this.codes;
	}

	public void setCodes(final Collection<Code> codes) {
		this.codes = codes;
	}

	@Valid
	@ManyToMany(mappedBy = "prizes")
	public Collection<Property> getProperties() {
		return this.properties;
	}

	public void setProperties(final Collection<Property> properties) {
		this.properties = properties;
	}

}
