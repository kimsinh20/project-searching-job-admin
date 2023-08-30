package jsoft.ads.user;

import java.util.ArrayList;

import jsoft.objects.UserObject;

public class UserLibrary {
	public static String viewUser(ArrayList<UserObject> items, UserObject user,int page) {
		StringBuffer tmp = new StringBuffer();

		

//		tmp.append("<h5 class=\"card-title\">Danh sách người sử dụng</h5>\n");

		tmp.append("<table class=\"table table-striped table-sm\">");
		tmp.append("<thead>");
		tmp.append("<tr>");
		tmp.append("<th scope=\"col\">STT</th>");
		tmp.append("<th scope=\"col\">Ngày tạo</th>");
		tmp.append("<th scope=\"col\">Tài khoản</th>");
		tmp.append("<th scope=\"col\">Họ tên</th>");
		tmp.append("<th scope=\"col\" class=\"\">số điện thoại</th>");
		tmp.append("<th scope=\"col\" class=\"\">Hộp thư</th>");
		tmp.append("<th scope=\"col\" class=\"\">Lần đăng nhập</th>");
		tmp.append("<th scope=\"col\" class=\"text-center\" colspan=\"3\">Thực hiện</th>");
		tmp.append("<th scope=\"col\">#</th>");
		tmp.append("</tr>");
		tmp.append("</thead>");

		tmp.append("<tbody>");

		items.forEach(item -> {
			tmp.append("<tr class=\"align-items-center\">");
			tmp.append("<th class=\"align-middle\" scope=\"row\">" + (items.indexOf(item) + 1) + "</th>");
			tmp.append("<td class=\"align-middle\">" + item.getUser_created_date() + "</td>");
			tmp.append("<td class=\"align-middle\">" + item.getUser_name() + "</td>");
			tmp.append("<td class=\"align-middle\">" + item.getUser_fullname() + "</td>");
			tmp.append("<td class=\"align-middle\">" + item.getUser_homephone() + "</td>");
			tmp.append("<td class=\"align-middle\">" + item.getUser_email() + "</td>");
			tmp.append("<td class=\"align-middle text-center\">" + item.getUser_logined() + "</td>");
			tmp.append("<td><a hred=\"#\" class=\"btn btn-primary btn-sm\"><i class=\"bi bi-eye-fill\"></i></a></td>");
			if (user.getUser_id() == item.getUser_id()) {
				tmp.append("<td><a href=\"/adv/user/profiles?id="+item.getUser_id()+"\" class=\"btn btn-secondary btn-sm\"><i class=\"bi bi-pencil-square\"></i></a></td>");
				tmp.append("<td><a href=\"#\" class=\"btn btn-warning btn-sm disabled\" ><i class=\"bi bi-archive\"></i></a></td>");
			} else {
				if (user.getUser_permission() >= 4) {
					tmp.append("<td><a href=\"/adv/user/profiles?id="+item.getUser_id()+"\" class=\"btn btn-secondary btn-sm\"><i class=\"bi bi-pencil-square\"></i></a></td>");
					tmp.append("<td><a href=\"#\" class=\"btn btn-warning btn-sm\" data-bs-toggle=\"modal\" data-bs-target=\"#delUser"+item.getUser_id()+"\"><i class=\"bi bi-archive\"></i></a></td>");
					tmp.append(UserLibrary.ViewDellUser(item,page));
				} else {
					if (item.getUser_parent_id() == user.getUser_id()) {
						tmp.append(
								"<td><a href=\"/adv/user/profiles?id="+item.getUser_id()+"\" class=\"btn btn-secondary btn-sm\"><i class=\"bi bi-pencil-square\"></i></a></td>");
					} else {
						tmp.append(
								"<td><a href=\"#\" class=\"btn btn-secondary btn-sm disabled\" ><i class=\"bi bi-pencil-square\"></i></a></td>");
					}
					tmp.append("<td><a href=\"#\" class=\"btn btn-warning btn-sm disabled\" ><i class=\"bi bi-archive\"></i></a></td>");
				}
			}
			tmp.append("<th scope=\"row\">" + item.getUser_id() + "</th>");
			tmp.append("</tr>");
		});

		tmp.append("</tbody>");
		tmp.append("</table>");

		tmp.append("</div>");
		tmp.append("</div>");

		return tmp.toString();
	}
	private static StringBuilder ViewDellUser(UserObject item,int page) {
		StringBuilder tmp = new StringBuilder();
		tmp.append("<div class=\"modal modal-fullscreen-sm-down fade\" id=\"delUser"+item.getUser_id()+"\" tabindex=\"-1\" aria-labelledby=\"exampleModalLabel\" aria-hidden=\"true\">");
		tmp.append("<div class=\"modal-dialog\">");
		tmp.append("<div class=\"modal-content\">");
		tmp.append("<div class=\"modal-header\">");
		tmp.append("<h1 class=\"modal-title fs-5\" id=\"exampleModalLabel\">Xóa tài khoản</h1>");
		tmp.append("<button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"modal\" aria-label=\"Close\"></button>");
		tmp.append("</div>");
		tmp.append("<div class=\"modal-body\">");
		tmp.append("Bạn có chắc chắn xóa tài khoản <b>").append(" ("+item.getUser_name()+")</b>");
		tmp.append("</div>");
		tmp.append("<div class=\"modal-footer\">");
		tmp.append("<a href=\"/adv/user/dr?id="+item.getUser_id()+"&pid="+item.getUser_parent_id()+"&page="+page+"\" class=\"btn btn-danger px-5 py-2\">Xóa</a>");
		tmp.append("<button type=\"button\" class=\"btn btn-secondary px-5 py-2\" data-bs-dismiss=\"modal\">Hủy</button>");
		tmp.append("</div>");
		tmp.append("</div>");
		tmp.append("</div>");
		tmp.append("</div>");
		return tmp;
	}
}
