package com.zg.controller.forward;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 控制页面跳转的控制器
 * @author Mr Li
 *
 */
@Controller
public class ForwardController {
	@RequestMapping("toview/{url}")
	public String toView(@PathVariable("url") String view,Model model){
		return view ;
	}

}
