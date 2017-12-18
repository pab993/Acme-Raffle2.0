
package forms;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

import domain.DomainEntity;

public class CommentForm extends DomainEntity {

	public CommentForm() {
		super();
	}


	//	private String	title;
	private String	text;
	private int		score;
	private int		idComentable;


	//	public String getTitle() {
	//		return title;
	//	}
	//	public void setTitle(String title) {
	//		this.title = title;
	//	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
	@NotBlank
	public String getText() {
		return this.text;
	}

	public void setText(final String text) {
		this.text = text;
	}

	@NotNull
	@Range(min = 0, max = 3)
	public int getScore() {
		return this.score;
	}

	public void setScore(final int score) {
		this.score = score;
	}

	public int getIdComentable() {
		return this.idComentable;
	}

	public void setIdComentable(final int idComentable) {
		this.idComentable = idComentable;
	}

}
