package frontend;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import classes.VotingBackend;

@Controller
public class VotingController {
	
	private static boolean releaseResults = false;
	
	@RequestMapping("/")
	public String home(){
		
		return "home";
	}
	
	/*registration methods*/
	@RequestMapping("/register")
	public String register(){
		
		return "register";
	}
	
	@RequestMapping("/approve")
	public String approve(HttpServletRequest req){
		
		boolean approved = VotingBackend.approveVoter(req.getParameter("nametxt"), Integer.parseInt(req.getParameter("ssntxt")));
		
		if(approved){
			return "redirect:/registerSuccess";
		}
		else{
			return "redirect:/registerError";
		}
	}
	
	@RequestMapping("/registerSuccess")
	public String registerSuccess(){
		
		return "registerSuccess";
	}
	
	@RequestMapping("/registerError")
	public String registerError(){
		
		return "registerError";
	}
	/*registration methods end*/
	
	/*validation number request methods*/
	@RequestMapping("/request")
	public String request(){
		
		return "request";
	}
	
	@RequestMapping(value = "/validation", method = RequestMethod.POST)
	public String validation(HttpServletRequest req, Model model){
		
		String name = req.getParameter("nametxt");
		int validationNum = VotingBackend.getValidationNum(name);
		
		String nonce = req.getParameter("noncetxt");
		if(validationNum == -1){
			return "redirect:/validationError";
		}
		else{
			model.addAttribute("validationNum", validationNum);
			model.addAttribute("nonce", nonce);
			model.addAttribute("name", name);
			return "validationConfirm";
		}
	}
	
	@RequestMapping("/validationError")
	public String validationError(){
		
		return "validationError";
	}
	
	@RequestMapping("/validationConfirmed")
	public String validationConfirmed(HttpServletRequest req){
		
		VotingBackend.sendVNumToCTF(req.getParameter("nametxt"));
		
		return "redirect:/";
	}
	/*validation number request methods end*/
	
	/*voting methods*/
	@RequestMapping("/vote")
	public String vote(){
		
		return "vote";
	}
	
	@RequestMapping(value = "/acceptVote", method = RequestMethod.POST)
	public String acceptVote(HttpServletRequest req, Model model){
		
		int id = VotingBackend.getID(Integer.parseInt(req.getParameter("validationNumtxt")), Integer.parseInt(req.getParameter("ssntxt")), 
				req.getParameter("votetxt"));
		
		if(id == -1){
			return "redirect:/voteError";
		}
		else{
			model.addAttribute("vote", req.getParameter("votetxt"));
			model.addAttribute("nonce", req.getParameter("noncetxt"));
			model.addAttribute("id", id);
			return "voteConfirm";
		}
	}
	
	@RequestMapping("/voteError")
	public String voteError(){
		
		return "voteError";
	}
	
	@RequestMapping("/voteConfirmed")
	public String voteConfirmed(HttpServletRequest req){
		
		VotingBackend.lockVote(Integer.parseInt(req.getParameter("idtxt")));
		
		return "redirect:/";
	}
	/*voting methods end*/
	
	/*results methods*/
	@RequestMapping("/results")
	public String results(Model model){
		
		if(!releaseResults){
			return "resultsNotReady";
		}
		else{
			Map<Integer, String> idVoteMap = VotingBackend.getIDVoteMap();
			model.addAttribute("results", idVoteMap);
			return "results";
		}
	}
	
	static void releaseResults(){
		
		releaseResults = true;
	}
	/*results methods end*/
}
