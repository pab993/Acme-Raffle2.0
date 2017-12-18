
package services;

import java.util.Calendar;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import repositories.CreditCardRepository;
import domain.Actor;
import domain.BrandNameCreditCard;
import domain.CreditCard;

@Transactional
@Service
public class CreditCardService {

	//Repository
	//======================================================================

	@Autowired
	private CreditCardRepository	creditCardRepository;

	//Services
	// ======================================================================

	@Autowired
	private ActorService			actorService;


	//CRUD methods
	//=======================================================================

	public CreditCard findOne(final int id) {
		CreditCard creditCard;

		creditCard = this.creditCardRepository.findOne(id);
		Assert.notNull(creditCard);

		return creditCard;
	}

	public CreditCard create() {

		final CreditCard cc = new CreditCard();
		return cc;

	}

	public CreditCard save(final CreditCard c) {
		Assert.notNull(c);
		final Actor actor = this.actorService.findByPrincipal();
		Assert.isTrue(c.getActor().getId() == actor.getId());
		//Assert.isTrue(checkValidity(c));
		//		delete(c);
		final CreditCard creditCardRes = this.creditCardRepository.save(c);
		return creditCardRes;
	}

	public void delete(final CreditCard c) {
		Assert.notNull(c);
		final Actor actor = this.actorService.findByPrincipal();
		Assert.isTrue(c.getActor().getId() == actor.getId());
		Assert.isTrue(c.getId() != 0);
		Assert.isTrue(this.creditCardRepository.exists(c.getId()));
		this.creditCardRepository.delete(c);
	}

	//Other bussiness methods
	//=======================================================================

	public int[] stringToArray(final String number) {
		final char[] a = number.toCharArray();
		final int[] n = new int[a.length];

		for (int i = 0; i < a.length; i++) {
			final int x = Character.getNumericValue(a[i]);
			n[i] = x;
		}
		return n;
	}
	public boolean verificacionLuhn(final int[] digits, final String number, final BindingResult binding) {
		FieldError error;
		String[] codigos;
		int sum = 0;
		final int length = digits.length;
		for (int i = 0; i < length; i++) {
			// sacar los digitos en orden inverso
			int digit = digits[length - i - 1];

			// cada segundo número se multiplica por 2
			if (i % 2 == 1)
				digit = digit * 2;
			if (digit > 9)
				digit = digit - 9;
			sum = sum + digit;
		}

		if (sum % 10 != 0) {
			codigos = new String[1];
			codigos[0] = "cc.luhn.error";
			error = new FieldError("creditCard", "number", number, false, codigos, null, "");
			binding.addError(error);
		}
		return sum % 10 == 0;
	}

	public boolean verificacionLuhn(final int[] digits) {
		int sum = 0;
		final int length = digits.length;
		for (int i = 0; i < length; i++) {
			// sacar los digitos en orden inverso
			int digit = digits[length - i - 1];

			// cada segundo número se multiplica por 2
			if (i % 2 == 1)
				digit = digit * 2;
			if (digit > 9)
				digit = digit - 9;
			sum = sum + digit;
		}

		return sum % 10 == 0;
	}

	public CreditCard findByManagerId(final int managerId) {
		CreditCard creditCard;
		Assert.notNull(managerId);

		creditCard = this.creditCardRepository.findByManagerId(managerId);

		return creditCard;

	}

	public boolean checkValidity(final CreditCard creditCard, final BindingResult binding) {

		boolean res = false;
		final int[] n = this.stringToArray(creditCard.getNumber());

		if (this.checkBrandName(creditCard, binding) && this.verificacionLuhn(n, creditCard.getNumber(), binding) && this.checkExpirationDate(creditCard, binding))
			res = true;
		return res;
	}

	public boolean checkBrandName(final CreditCard creditCard, final BindingResult binding) {
		FieldError error;
		String[] codigos;
		boolean res = false;

		for (final BrandNameCreditCard bn : BrandNameCreditCard.values())
			if (bn.name().equals(creditCard.getBrandName()))
				res = true;

		if (res == false) {
			codigos = new String[1];
			codigos[0] = "cc.brandname.error";
			error = new FieldError("creditCard", "brandName", creditCard.getBrandName(), false, codigos, null, "");
			binding.addError(error);
		}

		return res;
	}

	public boolean checkBrandName(final CreditCard creditCard) {
		boolean res = false;

		for (final BrandNameCreditCard bn : BrandNameCreditCard.values())
			if (bn.name().equals(creditCard.getBrandName()))
				res = true;
		return res;
	}

	public boolean checkExpirationDate(final CreditCard creditCard, final BindingResult binding) {
		FieldError error;
		String[] codigos;
		boolean res = true;

		final long l = 10;
		final Calendar actualCalendar = Calendar.getInstance();
		final Date actual = new Date(System.currentTimeMillis() - l);
		actualCalendar.setTime(actual);

		System.out.print(creditCard.getExpirationYear());
		System.out.print("//");
		System.out.print(creditCard.getExpirationMonth());
		System.out.print("-------");
		System.out.print(actualCalendar.get(Calendar.YEAR));
		System.out.print("//");
		System.out.print(actualCalendar.get(Calendar.MONTH) + 1);
		System.out.print("-------");

		if (creditCard.getExpirationYear() < actualCalendar.get(Calendar.YEAR)) {
			res = false;
			codigos = new String[1];
			codigos[0] = "cc.expirationYear.error";
			error = new FieldError("creditCard", "expirationYear", creditCard.getExpirationYear(), false, codigos, null, "");
			binding.addError(error);

		} else if (creditCard.getExpirationYear() == actualCalendar.get(Calendar.YEAR) && creditCard.getExpirationMonth() <= actualCalendar.get(Calendar.MONTH) + 1) {
			res = false;
			codigos = new String[1];
			codigos[0] = "cc.expirationMonth.error";
			error = new FieldError("creditCard", "expirationMonth", creditCard.getExpirationMonth(), false, codigos, null, "");
			binding.addError(error);
		}

		return res;
	}

	public boolean checkExpirationDate(final CreditCard creditCard) {
		boolean res = true;

		final long l = 10;
		final Date actual = new Date(System.currentTimeMillis() - l);

		final Calendar actualCalendar = Calendar.getInstance();
		actualCalendar.setTime(actual);

		//		Calendar calcc = new GregorianCalendar();
		//
		//		calcc.set(Calendar.YEAR, creditCard.getExpirationYear());
		//		calcc.set(Calendar.MONTH, creditCard.getExpirationMonth() - 1);
		//		calcc.set(Calendar.DAY_OF_MONTH, 1);
		//
		//		System.out.print(calcc.get(Calendar.YEAR));
		//		System.out.print("//");
		//		System.out.print(calcc.get(Calendar.MONTH));
		//		System.out.print("-------");
		//
		//		long diferenciaEn_ms = calcc.getTimeInMillis() - actualCalendar.getTimeInMillis(); //1/12/2017  - 25/12/2017 = 6
		//		long dias = diferenciaEn_ms / (1000 * 60 * 60 * 24 * 7);

		if (creditCard.getExpirationYear() < actualCalendar.get(Calendar.YEAR))
			res = false;
		else if (creditCard.getExpirationYear() == actualCalendar.get(Calendar.YEAR) && creditCard.getExpirationMonth() <= actualCalendar.get(Calendar.MONTH) + 1)
			res = false;
		//		else if (dias <= 1)
		//			res = false;

		return res;
	}

	public CreditCard reconstruct(final CreditCard creditCard, final BindingResult binding) {

		final CreditCard result = new CreditCard();

		result.setActor(creditCard.getActor());
		result.setBrandName(creditCard.getBrandName());
		result.setCVV(creditCard.getCVV());
		result.setExpirationMonth(creditCard.getExpirationMonth());
		result.setExpirationYear(creditCard.getExpirationYear());
		result.setHolderName(creditCard.getHolderName());
		result.setNumber(creditCard.getNumber());
		result.setId(creditCard.getId());
		result.setVersion(creditCard.getVersion());

		this.checkValidity(result, binding);

		return result;
	}

}
