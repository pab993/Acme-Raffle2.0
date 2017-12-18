
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Message extends DomainEntity {

	private Date		moment;
	private String		subject;
	private String		body;
	private Priority	priority;


	@Temporal(TemporalType.TIMESTAMP)
	@Past
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return moment;
	}

	public void setMoment(Date moment) {
		this.moment = moment;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@NotNull
	@Enumerated(EnumType.STRING)
	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}


	//Relationships
	private Actor				send;
	private Collection<Actor>	receives;
	private Folder				folder;


	@Valid
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	public Actor getSend() {
		return send;
	}

	public void setSend(Actor send) {
		this.send = send;
	}

	@Valid
	@ManyToMany(fetch = FetchType.LAZY)
	public Collection<Actor> getReceives() {
		return receives;
	}

	public void setReceives(Collection<Actor> receives) {
		this.receives = receives;
	}

	@Valid
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	public Folder getFolder() {
		return folder;
	}

	public void setFolder(Folder folder) {
		this.folder = folder;
	}

	@Override
	public Message clone() {

		Message msg = new Message();
		msg.setBody(this.body);
		msg.setMoment(this.moment);
		msg.setPriority(priority);
		msg.setReceives(receives);
		msg.setSubject(subject);
		msg.setSend(send);

		return msg;
	}

}
