package frontend;

import java.util.Map;
import java.util.TreeMap;

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
		
		//prevent illegal access
		if(releaseResults){
			return "votingClosed";
		}
		
		return "register";
	}
	
	@RequestMapping(value = "/approve", method = RequestMethod.POST)
	public String approve(HttpServletRequest req){

		//prevent illegal access
		if(releaseResults){
			return "votingClosed";
		}
		
		//verify name-ssn pair
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

		//prevent illegal access
		if(releaseResults){
			return "votingClosed";
		}
		
		return "registerSuccess";
	}
	
	@RequestMapping("/registerError")
	public String registerError(){

		//prevent illegal access
		if(releaseResults){
			return "votingClosed";
		}
		
		return "registerError";
	}
	/*registration methods end*/
	
	/*validation number request methods*/
	@RequestMapping("/request")
	public String request(){

		//prevent illegal access
		if(releaseResults){
			return "votingClosed";
		}
		
		return "request";
	}
	
	@RequestMapping(value = "/validation", method = RequestMethod.POST)
	public String validation(HttpServletRequest req, Model model){

		//prevent illegal access
		if(releaseResults){
			return "votingClosed";
		}
		
		String name = req.getParameter("nametxt");
		int validationNum = VotingBackend.getValidationNum(name); //get validation number of the voter
		
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

		//prevent illegal access
		if(releaseResults){
			return "votingClosed";
		}
		
		return "validationError";
	}
	
	@RequestMapping(value = "/validationConfirmed", method = RequestMethod.POST)
	public String validationConfirmed(HttpServletRequest req){

		//prevent illegal access
		if(releaseResults){
			return "votingClosed";
		}
		
		VotingBackend.sendVNumToCTF(req.getParameter("nametxt"));
		
		return "redirect:/";
	}
	/*validation number request methods end*/
	
	/*voting methods*/
	@RequestMapping("/vote")
	public String vote(){

		//prevent illegal access
		if(releaseResults){
			return "votingClosed";
		}
		
		return "vote";
	}
	
	@RequestMapping(value = "/acceptVote", method = RequestMethod.POST)
	public String acceptVote(HttpServletRequest req, Model model){

		//prevent illegal access
		if(releaseResults){
			return "votingClosed";
		}
		
		//verify name-ssn pair and record their vote and return an id associated for the person
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

		//prevent illegal access
		if(releaseResults){
			return "votingClosed";
		}
		
		return "voteError";
	}
	
	@RequestMapping(value = "/voteConfirmed", method = RequestMethod.POST)
	public String voteConfirmed(HttpServletRequest req){

		//prevent illegal access
		if(releaseResults){
			return "votingClosed";
		}
		
		//cannot change vote
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
			//populate the various lists to display on the results map
			Map<Integer, String> idVoteMap = VotingBackend.getIDVoteMap();
			TreeMap<Integer, String> idVoteSorted = new TreeMap<Integer, String>(idVoteMap);
			model.addAttribute("idvotes", idVoteSorted);
			int[] voteTally = VotingBackend.getVoteTally();
			model.addAttribute("tally", voteTally);
			Map<String, String> whoVoted = VotingBackend.getWhoVotedMap();
			TreeMap<String, String> whoVotedSorted = new TreeMap<String, String>(whoVoted);
			model.addAttribute("votemap", whoVotedSorted);
			return "results";
		}
	}
	
	static void releaseResults(){
		
		releaseResults = true;
	}
	
	@RequestMapping("/votingClosed")
	public String votingClosed(){
		
		if(releaseResults){
			return "redirect:/results";
		}
		
		return "resultsNotReady";
	}
	/*results methods end*/
}
