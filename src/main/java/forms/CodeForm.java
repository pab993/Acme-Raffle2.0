
package forms;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

import domain.DomainEntity;

public class CodeForm extends DomainEntity {

	public CodeForm() {
		super();
	}


	private String	name;
	private int		idPrize;


	@SafeHtml(whitelistType = WhiteListType.NONE)
	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotNull
	public int getIdPrize() {
		return this.idPrize;
	}

	public void setIdPrize(final int idPrize) {
		this.idPrize = idPrize;
	}

}
