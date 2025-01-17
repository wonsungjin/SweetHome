package com.sweetHome.ctl;


import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sweetHome.common.PagingUtil;
import com.sweetHome.svc.UserService;
import com.sweetHome.vo.BoardVO;
import com.sweetHome.vo.ReplyVO;
import com.sweetHome.vo.UserVO;
import com.sweetHome.vo.UsersOauthVO;

@Controller
@RequestMapping(value = "/user")
public class UserController {
	@Autowired
	private UserService userService;

	//json - json
	//dataType : json
	//	@RequestMapping(value = "/join", method = RequestMethod.POST)
	//    public UserVO ctlUserJoin(@RequestBody UserVO UserVO) {
	//    	//UserService.svcUserJoin(UserVO);
	//		System.out.println(UserVO.toString());
	//        return UserVO;
	//    }

	//json -- str
	//dataType : "텍스트...., data는 json인 경우
	//	@RequestMapping(value = "/join", method = RequestMethod.POST)
	//    public String ctlUserJoin(@RequestBody UserVO UserVO) {
	//    	//UserService.svcUserJoin(UserVO);
	//		System.out.println(UserVO.toString());
	//        return "텍스트 리턴";
	//    }

	//str --- str
	//dataType : "텍스트...., data는 쿼리스트링(?key=v&ke=vv)
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseBody
	public String ctlUserJoin(@ModelAttribute UserVO userVO,@ModelAttribute UsersOauthVO userOauthVO) {
	
		userVO.setUsersOauthVO(userOauthVO);
		userService.svcUserJoin(userVO);
		return "텍스트 리턴";
	}


	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public String ctlUserDetail(Model model,HttpSession session) {
		model.addAttribute("KEY_USERVO", userService.svcUserDetail((int)session.getAttribute("userSeq")));
		model.addAttribute("KEY_USEROAUTHVO", userService.svcUserOauth((int)session.getAttribute("userSeq")));
		return "jsp/mypage";
	}
	@RequestMapping(value = "/detail_reply", method = RequestMethod.GET)
	public String ctlUserDetailReply(Model model,HttpSession session) {
		model.addAttribute("KEY_USERVO", userService.svcUserDetail((int)session.getAttribute("userSeq")));
		model.addAttribute("KEY_USEROAUTHVO", userService.svcUserOauth((int)session.getAttribute("userSeq")));
		return "jsp/mypage_reply";
	}
	@RequestMapping(value = "/detail_update", method = RequestMethod.GET)
	public String ctlUserDetailUpdate(Model model,HttpSession session) {
		model.addAttribute("KEY_USERVO", userService.svcUserDetail((int)session.getAttribute("userSeq")));
		model.addAttribute("KEY_USEROAUTHVO", userService.svcUserOauth((int)session.getAttribute("userSeq")));
		return "jsp/mypage_update";
	}
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public String ctlUserUpdate(@ModelAttribute UserVO userVO) {

		userService.svcUserUpdate(userVO);
		return "성공";
	}

	@RequestMapping(value = "/board_list")
	public String ctlBoardList(Model model,HttpSession session
			, @RequestParam(value="currentPage", required=false, defaultValue="1") int currentPage 
			) {
		model.addAttribute("KEY_USERVO", userService.svcUserDetail((int)session.getAttribute("userSeq")));
		model.addAttribute("KEY_USEROAUTHVO", userService.svcUserOauth((int)session.getAttribute("userSeq")));
		//페이징
		int listCount = userService.svcBoardCount((int)session.getAttribute("userSeq"));
		PagingUtil page = new PagingUtil("/user/board_list?userSeq="+(int)session.getAttribute("userSeq"), currentPage, listCount, 4, 5);
		String pageHtmlStr = page.getPagingHtml().toString();
		
		BoardVO boardVO = new BoardVO();
		boardVO.setUserSeq((int)session.getAttribute("userSeq"));
		boardVO.setStartSeq(page.getStartSeq());
		boardVO.setEndSeq(page.getEndSeq());
		
		List<BoardVO> list = userService.svcUserBoardList(boardVO);
		model.addAttribute("KEY_BOARDLIST", list);
		model.addAttribute("KEY_PAGEING_HTML", pageHtmlStr);
		return "jsp/mypage_board";     				//   /  lec05_board/board_list  .jsp  
	}

	@RequestMapping(value = "/reply_list", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<List<ReplyVO>>  ctlUserReplies(@RequestParam("userSeq") int userSeq) {
		List<ReplyVO> rlist = userService.svcUserReplies(userSeq);
		return new ResponseEntity<List<ReplyVO>> (rlist, HttpStatus.OK);
	}



}
