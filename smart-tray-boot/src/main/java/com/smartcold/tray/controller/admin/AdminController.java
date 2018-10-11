package com.smartcold.tray.controller.admin;

import com.smartcold.tray.entity.admin.ACLAdminNode;
import com.smartcold.tray.entity.comm.UserEntity;
import com.smartcold.tray.mapper.UserMapper;
import com.smartcold.tray.util.EncodeUtil;
import com.smartcold.tray.util.ResponseData;
import com.smartcold.tray.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 *@date:2016-6-22 上午11:48:35
 *@Description: Admin login and logout & find admin & add admin
 */
@Controller
@RequestMapping(value = "i/admin")
public class AdminController {

	@Resource
	private UserMapper userMapper;


	private static List<ACLAdminNode> ml=new ArrayList<ACLAdminNode>();


    /**
     *
     * @param username
     * @param password
     * @param sik
     * @param lip
     * @param uip
     * @return
     */
	@RequestMapping(value = "/userlogin",method= RequestMethod.POST)
	@ResponseBody
	public Object userlogin(HttpSession  session, @RequestParam(value="username",required=true) String username, @RequestParam(value="password",required=true) String password, @RequestParam(value="sik",required=true)  Integer sik, String lip, String uip) {
		try {
            password=EncodeUtil.encodeByMD5(password);
			UserEntity admin = userMapper.userLogin(username,password );
			if (admin != null) {
                String cookie = StringUtil.getToken();
                admin.setToken(cookie);
                admin.setSystoken(StringUtil.MD5pwd(password, cookie));
				session.setAttribute("user",admin);
				return	ResponseData.newSuccess(admin);
			}
			return ResponseData.newFailure("用户名或者密码不正确！");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseData.newFailure("数据连接异常！请稍后重试！");
		}
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	@ResponseBody
	public Object logout(HttpServletRequest request) {
		request.getSession().removeAttribute("user");
		Cookie[] cookies = request.getCookies();
		if(cookies==null||cookies.length==0){return true;}
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("systoke")) {

			}
		}
		return true;
	}

	@RequestMapping(value = "/getUser")
	@ResponseBody
	public Object getUser(HttpServletRequest request) {
		return ResponseData.newSuccess((UserEntity)request.getSession().getAttribute("user"));
	}



	@ResponseBody
	@RequestMapping(value = "/getUserMenu")
	public Object getUserMenu(HttpSession session) {
		UserEntity	admin = (UserEntity)session.getAttribute("user");
		if(admin!=null&&admin.getId()!=0){
			return getml("0-01");
		}
		return "false";
	}

	public List<ACLAdminNode> getml(String alc) {
		List<ACLAdminNode> ml = getMenu();
		List<ACLAdminNode> nml=new ArrayList<ACLAdminNode>();
		String[] split = alc.split("-");
		String pacl=split[0];
		String[] cacl=split[1].split("@");
		int index=0,sindex=0,mlsize=ml.size();
//		if(pacl.length()!=cacl.length){System.err.println("无效权限！");return null;}
		for (char fix : pacl.toCharArray()) {
			index=Integer.parseInt(fix+"");
			if(index<mlsize){
				ACLAdminNode aclAdminNode = ml.get(index);
				ACLAdminNode cloneNode = new ACLAdminNode(aclAdminNode.getMenuid(),aclAdminNode.getIcon(),aclAdminNode.getMenuname());
				List<ACLAdminNode> child = aclAdminNode.getChild();
				List<ACLAdminNode> nchild = new ArrayList<ACLAdminNode>();
				if(StringUtil.isnotNull(cacl[index])){
					for (char sfix : cacl[index].toCharArray()) {
						try {
							sindex=Integer.parseInt(sfix+"");
						}catch (Exception e){
							sindex=sfix-87;
						}
						if(sindex<child.size()){
							nchild.add(child.get(sindex));
						}
					}
					cloneNode.setChild(nchild);
					nml.add(cloneNode);
				}

			}
		}
		return nml;
	}

	private synchronized List<ACLAdminNode> getMenu() {
		if(ml.size()>0){return ml;}
		ACLAdminNode pml = new ACLAdminNode("0","main_platform",      "托盘管理");
		List<ACLAdminNode> mlList0=new ArrayList<ACLAdminNode>();
		mlList0.add(new ACLAdminNode("0_1","main_store360",      "载具管理",      "pallet_manage.html"        ));		pml.setChild(mlList0);ml.add(pml);
		mlList0.add(new ACLAdminNode("0_2","main_push",      "载具地图",      "pallet_map.html"        ));		pml.setChild(mlList0);ml.add(pml);
		return ml;
	}

}
