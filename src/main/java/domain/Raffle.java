
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Raffle extends Comentable {

	private String	logo;
	private String	title;
	private String	description;
	private Date	publicationTime;
	private Date	deadline;


	@NotBlank
	@URL
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getLogo() {
		return this.logo;
	}

	public void setLogo(final String logo) {
		this.logo = logo;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getPublicationTime() {
		return this.publicationTime;
	}

	public void setPublicationTime(final Date publicationTime) {
		this.publicationTime = publicationTime;
	}

	@NotNull
	@Future
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getDeadline() {
		return this.deadline;
	}

	public void setDeadline(final Date deadline) {
		this.deadline = deadline;
	}


	//Relationships
	// =======================================================

	private Collection<Audit>	audits;
	private Manager				manager;
	private Bill				bill;
	private Collection<Prize>	prizes;


	@Valid
	@OneToMany(mappedBy = "raffle")
	public Collection<Audit> getAudits() {
		return this.audits;
	}

	public void setAudits(final Collection<Audit> audits) {
		this.audits = audits;
	}

	@Valid
	@ManyToOne(optional = false)
	public Manager getManager() {
		return this.manager;
	}

	public void setManager(final Manager manager) {
		this.manager = manager;
	}

	@Valid
	@OneToMany(mappedBy = "raffle")
	public Collection<Prize> getPrizes() {
		return this.prizes;
	}

	public void setPrizes(final Collection<Prize> prizes) {
		this.prizes = prizes;
	}

	@Valid
	@OneToOne(mappedBy = "raffle", optional = true)
	public Bill getBill() {
		return this.bill;
	}

	public void setBill(final Bill bill) {
		this.bill = bill;
	}

}
