
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;

import repositories.CommentRepository;
import security.Authority;
import domain.Actor;
import domain.Comentable;
import domain.Comment;
import domain.TabooWord;
import forms.CommentForm;

@Service
@Transactional
public class CommentService {

	// Repositories
	// ====================================================================

	@Autowired
	private CommentRepository	commentRepository;

	// Supporting services
	// ====================================================================

	@Autowired
	private ComentableService	comentableService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private TabooWordService	tabooWordService;


	// Simple CRUD methods
	// ====================================================================

	public Comment findOne(final int commentId) {
		Assert.isTrue(commentId != 0);
		Comment result;

		result = this.commentRepository.findOne(commentId);

		return result;
	}

	public Collection<Comment> findAll() {
		Collection<Comment> result;

		result = this.commentRepository.findAll();

		return result;
	}

	public Comment create(final Comentable comentable) {
		Comment result;
		Actor principal;
		Date createMoment;

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		createMoment = new Date(System.currentTimeMillis() - 100);
		result = new Comment();
		result.setCreateMoment(createMoment);
		result.setActor(principal);
		comentable.getComments().add(result);
		result.setComentable(comentable);
		//		result.setBan(false);

		return result;
	}

	public Comment save(final Comment comment) {
		Assert.notNull(comment);
		Comment result;

		result = this.commentRepository.save(comment);

		return result;
	}

	public void delete(final Comment comment) {
		Assert.isTrue(this.actorService.isAdmin());
		Assert.notNull(comment);

		this.commentRepository.delete(comment);

	}

	// Other business methods
	// ===================================================================

	public Collection<Comment> getCommentInappropiate() {
		Assert.isTrue(this.actorService.isAdmin());
		final Collection<Comment> res = new ArrayList<Comment>();
		final Collection<Comment> comments = new ArrayList<Comment>();
		final Collection<TabooWord> tabooWords = this.tabooWordService.findAll();
		final Collection<Comentable> comentables = this.comentableService.findAll();
		for (final Comentable cm : comentables)
			comments.addAll(cm.getComments());
		for (final Comment co : comments)
			for (final TabooWord tw : tabooWords)
				if (!res.contains(co) && co.getText().toLowerCase().contains(tw.getName().toLowerCase()))
					res.add(co);
		return res;
	}

	public Double avgPerActorOfferRequest() {
		Double result;

		result = this.commentRepository.avgPerActorOfferRequest();
		Assert.notNull(result);

		return result;
	}

	public Double avgPerAdminsAndCustomers() {
		Double result;

		result = this.commentRepository.avgPerAdminsAndCustomers();
		Assert.notNull(result);

		return result;
	}

	public Collection<Actor> actorsBetweenAvgTenPerCent() {
		Collection<Actor> result;

		result = this.commentRepository.actorsBetweenAvgTenPerCent();
		Assert.notNull(result);

		return result;
	}

	public Comment reconstruct(final CommentForm commentForm, final BindingResult binding) {
		//Comentable comentable2 = comentableService.findOne(commentForm.getIdComentable());
		final Comentable comentable = this.comentableService.findOneAux(commentForm.getIdComentable());
		final Comment res = this.create(comentable);
		res.setScore(commentForm.getScore());
		res.setText(commentForm.getText());

		return res;

	}

	public Collection<Comment> findAllByComentable(final Comentable comentable) {
		Assert.notNull(comentable);

		Collection<Comment> res;

		res = this.commentRepository.findAllByComentable(comentable.getId());

		return res;
	}

	public Collection<Comment> filterComments(final Comentable comentable) {
		final Actor principal = this.actorService.findByPrincipal();
		Collection<Comment> res = new HashSet<Comment>();
		final Authority i = new Authority();
		i.setAuthority("ADMIN");

		if (principal.getUserAccount().getAuthorities().contains(i))
			res = comentable.getComments();
		else
			res = this.commentRepository.findNotBaned(comentable.getId(), principal.getId());

		return res;
	}

	public Object[] avgStddevOfStarsPerComment() {

		Object[] result;

		result = this.commentRepository.avgStddevOfStarsPerComment();

		return result;
	}

}
