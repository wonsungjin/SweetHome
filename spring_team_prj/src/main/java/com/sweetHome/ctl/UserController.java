package com.sweetHome.ctl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sweetHome.svc.UserService;
import com.sweetHome.vo.UserVO;

@RestController
@RequestMapping(value = "/user")
public class UserController {
	@Autowired
	private UserService UserService;

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
	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String ctlUserJoin(@ModelAttribute UserVO UserVO) {
		//UserService.svcUserJoin(UserVO);
		System.out.println(UserVO.toString());
		return "텍스트 리턴";
	}


	@RequestMapping(value = "/User_detail", method = RequestMethod.POST)
	public UserVO ctlUserDetail(@RequestParam int Usereq) {
		return UserService.svcUserDetail(Usereq);
	}

	@RequestMapping(value = "/User_update", method = RequestMethod.POST)
	public String ctlUserUpdate(@RequestBody UserVO user) {
		UserService.svcUserUpdate(user);
		return "User updated successfully";
	}

	@RequestMapping(value = "/User_board_list", method = RequestMethod.POST)
	public List<UserVO> ctlUserBoardList(@RequestParam int Usereq) {
		return UserService.svcUserBoardList(Usereq);
	}

	@RequestMapping(value = "/User_replies", method = RequestMethod.POST)
	public List<UserVO> ctlUserReplies(@RequestParam int Usereq) {
		return UserService.svcUserReplies(Usereq);
	}



	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String ctlUserLogin(@RequestBody UserVO UserVO) {
		boolean isAuthenticated = UserService.svcUserLogin(UserVO);
		return isAuthenticated ? "Login successful" : "Login failed";
	}

}