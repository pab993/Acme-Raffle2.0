
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import repositories.CodeRepository;
import domain.Code;
import domain.Manager;
import domain.Prize;
import domain.TabooWord;
import domain.User;

@Service
@Transactional
public class CodeService {

	//Managed Repository =============================================================================

	@Autowired
	private CodeRepository		codeRepository;

	//Services
	// ===============================================================================================

	@Autowired
	private ManagerService		managerService;

	@Autowired
	private UserService			userService;

	@Autowired
	private PrizeService		prizeService;

	@Autowired
	private TabooWordService	tabooWordService;


	//SCRUDs Methods

	//===============================================================================================

	public Code create(final Prize prize) {
		Manager principal;
		principal = this.managerService.findByPrincipal();
		Assert.isInstanceOf(Manager.class, principal);

		final Code result;
		final Code code = new Code();
		code.setName(this.codeGenerator(prize));
		code.setWinner(this.isWinnerGenerator());
		code.setPrize(prize);
		prize.getCodes().add(code);
		//		code.setManager(principal);

		this.prizeService.save2(prize);

		result = this.save(code);

		return result;
	}

	public Code createWinner(final Prize prize) {
		Manager principal;
		principal = this.managerService.findByPrincipal();
		Assert.isInstanceOf(Manager.class, principal);

		final Code result;
		final Code code = new Code();
		code.setName(this.codeGenerator(prize));
		code.setWinner(true);
		code.setPrize(prize);
		prize.getCodes().add(code);
		//		code.setManager(principal);

		this.prizeService.save2(prize);

		result = this.save(code);

		return result;
	}

	public Code createNotWinner(final Prize prize) {
		Manager principal;
		principal = this.managerService.findByPrincipal();
		Assert.isInstanceOf(Manager.class, principal);

		final Code result;
		final Code code = new Code();
		code.setName(this.codeGenerator(prize));
		code.setWinner(false);
		code.setPrize(prize);
		prize.getCodes().add(code);
		//		code.setManager(principal);

		this.prizeService.save2(prize);

		result = this.save(code);

		return result;
	}

	public Code save(final Code code) {
		Assert.notNull(code);
		Code result;
		final Manager principal = this.managerService.findByPrincipal();

		Assert.isInstanceOf(Manager.class, principal);
		Assert.notNull(principal);
		Assert.isInstanceOf(Manager.class, principal);

		//		principal.getCodes().add(code);

		result = this.codeRepository.save(code);

		return result;
	}

	public void delete(final Code code) {
		Assert.notNull(code);

		final Manager principal = this.managerService.findByPrincipal();
		Assert.notNull(principal);

		this.codeRepository.delete(code);

	}

	public String codeGenerator(Prize prize) {
		String result = "";
		final String pattern = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		final String pattern2 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		final Random rnd = new Random();
		//		final int nRnd = LoweService.generaNumeroAleatorio(3, 4);
		//		final int nRnd2 = LoweService.generaNumeroAleatorio(2, 5);

		for (int i = 0; i < 4; i++)
			result += pattern.charAt(rnd.nextInt(pattern.length()));
		result += "-";
		for (int i = 0; i < 4; i++)
			result += pattern2.charAt(rnd.nextInt(pattern2.length()));

		final Code code = this.codeRepository.findByCode(result, prize.getId());
		Boolean b = checkTaboo2(result);

		if (code != null) {
			result = "";
			result = this.codeGenerator(prize);
		} else {
			if (b) {
				result = "";
				result = this.codeGenerator(prize);
			}

		}

		return result;
	}

	public Boolean isWinnerGenerator() {

		final Random randomGenerator = new Random();
		final Boolean randomBool = randomGenerator.nextBoolean();

		return randomBool;
	}

	public Code findOne(final int codeId) {
		Code result;

		result = this.codeRepository.findOne(codeId);

		return result;
	}

	public Collection<Code> findAll() {
		Collection<Code> result;

		result = this.codeRepository.findAll();

		return result;
	}

	//Other Business Methods =========================================================================

	public Collection<Code> findAllByUserAndPrize(final int actorId, final int prizeId) {

		Collection<Code> result;

		result = this.codeRepository.findAllByUserAndPrize(actorId, prizeId);

		return result;
	}

	public Collection<Code> findCodesByPrize(final int prizeId) {

		Collection<Code> result;

		result = this.codeRepository.findCodesByPrize(prizeId);

		return result;
	}

	public Code findCodesByPrizeAndName(final int prizeId, final String name) {

		Code result;

		result = this.codeRepository.findCodesByPrizeAndName(prizeId, name);

		return result;
	}

	public String codeMessage(final Code code) {

		final User principal = this.userService.findByPrincipal();
		Assert.notNull(principal);

		String result = "";

		if (code == null)
			result = "code.notFound";
		else if (code.getWinner() == true && code.getUser() == null) {
			code.setUser(principal);
			code.setMoment(new Date(System.currentTimeMillis() - 100));
			this.saveUser(code);
			result = "code.winner";
		} else if (code.getWinner() == true && code.getUser() != null) {
			if (code.getUser().getId() == principal.getId())
				result = "code.winnerYet";
			else
				result = "code.otherWinner";
		} else if (code.getWinner() == false && code.getUser() == null) {
			code.setUser(principal);
			code.setMoment(new Date(System.currentTimeMillis() - 100));
			this.saveUser(code);
			result = "code.notWinner";
		} else if (code.getWinner() == false && code.getUser() != null)
			if (code.getUser().getId() == principal.getId())
				result = "code.notWinnerYet";
			else
				result = "code.otherNotWinner";

		return result;
	}

	public Code saveUser(final Code code) {
		Assert.notNull(code);
		Code result;

		final User principal = this.userService.findByPrincipal();
		Assert.notNull(principal);

		result = this.codeRepository.save(code);

		return result;
	}

	public String transformCode(final String code) {
		String result = code;
		final String char4 = code.substring(4, 5);
		if (code.length() < 8) {

		} else if (char4.equals("/") || char4.equals("-") || char4.equals(" "))
			result = code.substring(0, 4) + "-" + code.substring(5);
		else
			result = code.substring(0, 4) + "-" + code.substring(4);

		return result;
	}

	public void recontruct(Code code, BindingResult binding) {
		// TODO Auto-generated method stub
		checkUnique(code, binding);
		checkTaboo(code, binding);
	}

	private void checkTaboo(Code code, BindingResult binding) {
		// TODO Auto-generated method stub
		FieldError error;
		String[] codigos;
		Boolean res = false;
		Collection<TabooWord> tws = new ArrayList<TabooWord>();
		tws = tabooWordService.findAll();
		for (TabooWord tw : tws) {
			if (code.getName().contains(tw.getName().toUpperCase())) {
				res = true;
				break;
			}
		}

		if (res) {
			codigos = new String[1];
			codigos[0] = "code.name.taboo.error";
			error = new FieldError("code", "name", code.getName(), false, codigos, null, "");
			binding.addError(error);
		}

	}

	private void checkUnique(Code code, BindingResult binding) {
		// TODO Auto-generated method stub
		FieldError error;
		String[] codigos;
		Boolean res = false;
		Code exists = codeRepository.findByCode(code.getName(), code.getPrize().getId());
		if (exists != null) {
			res = true;
		}
		if (res) {
			codigos = new String[1];
			codigos[0] = "code.name.exist.error";
			error = new FieldError("code", "name", code.getName(), false, codigos, null, "");
			binding.addError(error);
		}

	}

	private Boolean checkTaboo2(String code) {
		Boolean res = false;
		Collection<TabooWord> tws = new ArrayList<TabooWord>();
		tws = tabooWordService.findAll();
		for (TabooWord tw : tws) {
			if (code.contains(tw.getName().toUpperCase())) {
				res = true;
				break;
			}
		}

		return res;
	}
}
