
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public class Manager extends Actor {

	//Relationships
	// =======================================================

	public Collection<Raffle>	raffles;


	@Valid
	@OneToMany(mappedBy = "manager")
	public Collection<Raffle> getRaffles() {
		return raffles;
	}

	public void setRaffles(Collection<Raffle> raffles) {
		this.raffles = raffles;
	}

}
