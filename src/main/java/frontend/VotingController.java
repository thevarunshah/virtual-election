package frontend;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import classes.VotingBackend;

@Controller
public class VotingController {
	
	@RequestMapping("/")
	public String home(){
		
		return "home";
	}

	@RequestMapping("/register")
	public String register(){
		
		return "register";
	}
	
	@RequestMapping("/approve")
	public String approve(HttpServletRequest req){
		
		VotingBackend.approveVoter(req.getParameter("nametxt"), Integer.parseInt(req.getParameter("ssntxt")));
		//make above return boolean
		//go to error page
		
		return "redirect:/";
	}
	
	@RequestMapping("/request")
	public String request(){
		
		return "request";
	}
	
	@RequestMapping("/validation")
	public String validation(HttpServletRequest req, Model model){
		
		int validationNum = VotingBackend.getValidationNum(req.getParameter("nametxt"));
		if(validationNum == -1){
			model.addAttribute("validationNum", validationNum);
			model.addAttribute("nonce", req.getParameter("noncetxt"));
			model.addAttribute("name", req.getParameter("nametxt"));
			//redirect to error page
		}
		else{
			model.addAttribute("validationNum", validationNum);
			model.addAttribute("nonce", req.getParameter("noncetxt"));
			model.addAttribute("name", req.getParameter("nametxt"));
		}
		
		return "validationConfirm";
	}
	
	@RequestMapping("/validationConfirmed")
	public String validationConfirm(HttpServletRequest req){
		
		//don't let them request another number
		//send valnum to ctf
		VotingBackend.sendVNumToCTF(req.getParameter("nametxt"));
		
		return "redirect:/";
	}
	
	@RequestMapping("/vote")
	public String vote(){
		
		return "vote";
	}
	
	@RequestMapping("/acceptVote")
	public String acceptVote(HttpServletRequest req, Model model){
		
		int id = VotingBackend.getID(Integer.parseInt(req.getParameter("validationNumtxt")), Integer.parseInt(req.getParameter("ssntxt")), 
				req.getParameter("votetxt"));
		
		if(id == -1){
			model.addAttribute("vote", req.getParameter("votetxt"));
			model.addAttribute("nonce", req.getParameter("noncetxt"));
			model.addAttribute("id", id);
			//redirect to error page
		}
		else{
			model.addAttribute("vote", req.getParameter("votetxt"));
			model.addAttribute("nonce", req.getParameter("noncetxt"));
			model.addAttribute("id", id);
		}
		
		return "voteConfirm";
	}
	
	@RequestMapping("voteConfirmed")
	public String voteConfirmed(){
		
		//lock the vote
		
		return "redirect:/";
	}
}
