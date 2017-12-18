
package forms;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import domain.DomainEntity;

public class BillForm extends DomainEntity {

	public BillForm() {
		super();
	}


	private Date	computeDate;
	private Date	payMomentCS;


	@NotNull
	@DateTimeFormat(pattern = "MM/yyyy")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getComputeDate() {
		return this.computeDate;
	}

	public void setComputeDate(final Date computeDate) {
		this.computeDate = computeDate;
	}

	@DateTimeFormat(pattern = "MM/yyyy")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getPayMomentCS() {
		return this.payMomentCS;
	}

	public void setPayMomentCS(final Date payMomentCS) {
		this.payMomentCS = payMomentCS;
	}

}
