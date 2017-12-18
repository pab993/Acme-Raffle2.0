
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.BillService;
import services.ComentableService;
import services.CommentService;
import services.ManagerService;
import services.PrizeService;
import services.RaffleService;
import services.UserService;

@Controller
@RequestMapping("/dashboard")
public class DashboardController extends AbstractController {

	//Services--------------------------------------------------------

	@Autowired
	ActorService		actorService;

	@Autowired
	UserService			userService;

	@Autowired
	ManagerService		managerService;

	@Autowired
	RaffleService		raffleService;

	@Autowired
	BillService			billService;

	@Autowired
	PrizeService		prizeService;

	@Autowired
	CommentService		commentService;

	@Autowired
	ComentableService	comentableService;


	//Constructor------------------------------------------------------
	public DashboardController() {
		super();
	}

	//Listing----------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView dashboard() {
		ModelAndView result;

		result = new ModelAndView("dashboard/list");

		result.addObject("findUsersOrderByValidCodes", this.userService.findUsersOrderByValidCodes());
		result.addObject("findManagersOrderByUnpaidBills", this.managerService.findManagersOrderByUnpaidBills());

		result.addObject("ratioBanUnBanUsers", this.userService.ratioBanUnBanUsers());
		result.addObject("ratioDebtorManagers", this.managerService.ratioDebtorManagers());

		result.addObject("minMaxAvgStddevOfPrizesPerRaffle", this.raffleService.minMaxAvgStddevOfPrizesPerRaffle());
		result.addObject("minMaxAvgStddevOfCodesPerPrize", this.prizeService.minMaxAvgStddevOfCodesPerPrize());
		result.addObject("minMaxAvgStddevOfValidCodesPerUser", this.userService.minMaxAvgStddevOfValidCodesPerUser());

		result.addObject("minMaxAvgOfBillsPerManager", this.billService.minMaxAvgOfBillsPerManager());
		result.addObject("minMaxAvgOfUnpaidBillsPerManager", this.billService.minMaxAvgOfUnpaidBillsPerManager());
		result.addObject("minMaxAvgOfPaidBillsPerManager", this.billService.minMaxAvgOfPaidBillsPerManager());

		result.addObject("avgStddevOfCommentsPerComentable", this.comentableService.avgStddevOfCommentsPerComentable());
		result.addObject("avgStddevOfStarsPerComment", this.commentService.avgStddevOfStarsPerComment());
		result.addObject("avgStarsPerActorGroupByCountry", this.actorService.avgStarsPerActorGroupByCountry());
		result.addObject("avgStarsPerActorGroupByCity", this.actorService.avgStarsPerActorGroupByCity());

		return result;
	}
}
