
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class ConfigurationSystem extends DomainEntity {

	//Construct
	//====================================================

	//Attributes
	//====================================================

	private String	banner;
	private Double	taxes;
	private Date	payMoment;


	@SafeHtml(whitelistType = WhiteListType.NONE)
	@URL
	public String getBanner() {
		return this.banner;
	}
	public void setBanner(final String banner) {
		this.banner = banner;
	}

	@NotNull
	@Min(0)
	@Digits(integer = 3, fraction = 2)
	public Double getTaxes() {
		return this.taxes;
	}
	public void setTaxes(final Double taxes) {
		this.taxes = taxes;
	}

	@DateTimeFormat(pattern = "MM/yyyy")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getPayMoment() {
		return this.payMoment;
	}
	public void setPayMoment(final Date payMoment) {
		this.payMoment = payMoment;
	}

	//Relationships
	//====================================================
}
