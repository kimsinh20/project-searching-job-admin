package jsoft.ads.user;

import java.util.ArrayList;

import org.javatuples.Pair;
import org.javatuples.Triplet;

import jsoft.library.ORDER;
import jsoft.library.Utilities;

import jsoft.objects.UserObject;

public class UserLibrary {
	public static ArrayList<String> viewUser(ArrayList<UserObject> items, UserObject user, int page) {
		StringBuffer tmp = new StringBuffer();

//		tmp.append("<h5 class=\"card-title\">Danh sách người sử dụng</h5>\n");

		tmp.append("<table class=\"table table-striped table-sm datatable\">");
		tmp.append("<thead>");
		tmp.append("<tr>");
		tmp.append("<th data-sortable=\"true\" scope=\"col\">STT</th>");
		tmp.append("<th scope=\"col\"><a href=\"#\" class=\"datatable-sorter\">Ngày tạo</a></th>");
		tmp.append("<th scope=\"col\"><a href=\"#\" class=\"datatable-sorter\">Tài khoản</a></th>");
		tmp.append("<th scope=\"col\"><a href=\"#\" class=\"datatable-sorter\">Họ tên</a></th>");
		if (user.getUser_deleted()) {
			tmp.append("<th scope=\"col\" class=\"\"><a href=\"#\" class=\"datatable-sorter\">Ngày xóa</a></th>");
			tmp.append("<th scope=\"col\" class=\"text-center\" colspan=\"2\">Thực hiện</a></th>");
		} else {
			tmp.append("<th scope=\"col\" class=\"\"><a href=\"#\" class=\"datatable-sorter\">số điện thoại</a></th>");
			tmp.append("<th scope=\"col\" class=\"\"><a href=\"#\" class=\"datatable-sorter\">Hộp thư</a></th>");
			tmp.append("<th scope=\"col\" class=\"\"><a href=\"#\" class=\"datatable-sorter\">Lần đăng nhập</a></th>");
			tmp.append("<th scope=\"col\" class=\"text-center\" colspan=\"3\">Thực hiện</th>");
		}
		tmp.append("<th scope=\"col\">#</th>");
		tmp.append("</tr>");
		tmp.append("</thead>");

		tmp.append("<tbody>");

		
		if(items.size()>0) {
		items.forEach(item -> {
			tmp.append("<tr class=\"align-items-center\">");
			tmp.append("<th class=\"align-middle\" scope=\"row\">" + (items.indexOf(item) + 1) + "</th>");
			tmp.append("<td class=\"align-middle\">" + item.getUser_created_date() + "</td>");
			tmp.append("<td class=\"align-middle\">" + item.getUser_name() + "</td>");
			tmp.append("<td class=\"align-middle\">" + item.getUser_fullname() + "</td>");
			if (user.getUser_deleted()) {
				tmp.append("<td class=\"align-middle\">" + item.getUser_last_modified() + "</td>");
				tmp.append("<td class=\"text-center\"><a href=\"/adv/user/dr?id=" + item.getUser_id() + "&t&r&pid="
						+ item.getUser_parent_id() + "&page=" + page
						+ "\" class=\"btn btn-primary btn-sm\"><i class=\"fas fa-trash-restore-alt\"></i></a></td>");
				tmp.append("<td class=\"text-center\"><a href=\"#\" class=\"btn btn-warning btn-sm\" data-bs-toggle=\"modal\" data-bs-target=\"#delUser"
								+ item.getUser_id() + "\"><i class=\"bi bi-archive\"></i></a></td>");
				tmp.append(UserLibrary.ViewDellUser(item, page));
			} else {
				tmp.append("<td class=\"align-middle\">" + item.getUser_homephone() + "</td>");
				tmp.append("<td class=\"align-middle\">" + item.getUser_email() + "</td>");
				tmp.append("<td class=\"align-middle text-center\">" + item.getUser_logined() + "</td>");
				tmp.append("<td><a href=\"/adv/user/profiles?id="+item.getUser_id()+"&view&page="+page+"\" class=\"btn btn-primary btn-sm\"><i class=\"bi bi-eye-fill\"></i></a></td>");
				if (user.getUser_id() == item.getUser_id()) {
					
					tmp.append("<td><a href=\"/adv/user/profiles?id=" + item.getUser_id()
							+ "&page="+page+"\" class=\"btn btn-secondary btn-sm\"><i class=\"bi bi-pencil-square\"></i></a></td>");
					tmp.append(
							"<td><a href=\"#\" class=\"btn btn-warning btn-sm disabled\" ><i class=\"bi bi-archive\"></i></a></td>");

				} else {
					if (user.getUser_permission() >= 4) {
						tmp.append("<td><a href=\"/adv/user/profiles?id=" + item.getUser_id()
								+ "&page="+page+"\" class=\"btn btn-secondary btn-sm\"><i class=\"bi bi-pencil-square\"></i></a></td>");
						tmp.append(
								"<td><a href=\"#\" class=\"btn btn-warning btn-sm\" data-bs-toggle=\"modal\" data-bs-target=\"#delUser"
										+ item.getUser_id() + "\"><i class=\"bi bi-archive\"></i></a></td>");
						tmp.append(UserLibrary.ViewDellUser(item, page));
					} else {
						if (item.getUser_parent_id() == user.getUser_id()) {
							tmp.append("<td><a href=\"/adv/user/profiles?id=" + item.getUser_id()
									+ "&page="+page+"\" class=\"btn btn-secondary btn-sm\"><i class=\"bi bi-pencil-square\"></i></a></td>");
						} else {
							tmp.append(
									"<td><a href=\"#\" class=\"btn btn-secondary btn-sm disabled\" ><i class=\"bi bi-pencil-square\"></i></a></td>");
						}
						tmp.append(
								"<td><a href=\"#\" class=\"btn btn-warning btn-sm disabled\" ><i class=\"bi bi-archive\"></i></a></td>");
					}
				}
			}

			tmp.append("<th scope=\"row\">" + item.getUser_id() + "</th>");
			tmp.append("</tr>");
		});
		} else {
			 tmp.append("<tr><th class=\"text-center\" colspan=\"10\">Danh sách rỗng</th></tr>");
		}

		tmp.append("</tbody>");
		tmp.append("</table>");

		tmp.append("</div>");
		tmp.append("</div>");

		ArrayList<String> view = new ArrayList<>();
		// cau truc bieu do
		String chart = UserLibrary.createdChart(items).toString();
		view.add(tmp.toString());
		view.add(chart.toString());

		return view;
	}

	private static StringBuilder ViewDellUser(UserObject item, int page) {
		StringBuilder tmp = new StringBuilder();

		String title;
		if (item.getUser_deleted()) {
			title = "Xóa vĩnh viễn";
		} else {
			title = "Xóa tài khoản";
		}

		tmp.append("<div class=\"modal modal-fullscreen-sm-down fade\" id=\"delUser" + item.getUser_id()
				+ "\" tabindex=\"-1\" aria-labelledby=\"exampleModalLabel\" aria-hidden=\"true\">");
		tmp.append("<div class=\"modal-dialog\">");
		tmp.append("<div class=\"modal-content\">");
		tmp.append("<div class=\"modal-header\">");
		tmp.append("<h1 class=\"modal-title fs-5\" id=\"exampleModalLabel\">" + title + "</h1>");
		tmp.append(
				"<button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"modal\" aria-label=\"Close\"></button>");
		tmp.append("</div>");
		tmp.append("<div class=\"modal-body\">");
		if (item.getUser_deleted()) {
			tmp.append("Bạn sẽ vĩnh viễn xóa tài khoản <b>").append(" (" + item.getUser_name() + ")</b>");
			tmp.append("<p>Tài khoản không thể phục hồi được nữa!!!</p>");
		} else {
			tmp.append("Bạn có chắc chắn xóa tài khoản <b>").append(" (" + item.getUser_name() + ")</b>");
			tmp.append("Hệ thống tạm thời lưu vào thùng rác có thể phuc hồi trong vòng 30 ngày");
		}

		tmp.append("</div>");
		tmp.append("<div class=\"modal-footer\">");
		if (item.getUser_deleted()) {
			tmp.append("<a href=\"/adv/user/dr?id=" + item.getUser_id() + "&pid=" + item.getUser_parent_id() + "&page="
					+ page + "\" class=\"btn btn-danger px-5 py-2\">Xóa vĩnh viễn</a>");
		} else {
			tmp.append("<a href=\"/adv/user/dr?id=" + item.getUser_id() + "&t&pid=" + item.getUser_parent_id()
					+ "&page=" + page + "\" class=\"btn btn-warning px-5 py-2\">Xóa</a>");
		}
		tmp.append(
				"<button type=\"button\" class=\"btn btn-secondary px-5 py-2\" data-bs-dismiss=\"modal\">Hủy</button>");
		tmp.append("</div>");
		tmp.append("</div>");
		tmp.append("</div>");
		tmp.append("</div>");
		return tmp;
	}

	public static StringBuilder pagination(UserObject user, Short total, Byte pageSize,int page,String saveKey,boolean trash) {
		 page =(page<=1)? 1 : page;
		 String urlkey = "";
		 if(saveKey!=null && !saveKey.equalsIgnoreCase("")) {
			 // savekey ton tai
			 urlkey="&key="+saveKey;
		 } 
		 if(trash) {
			 urlkey+="&trash";
		 }
		int totalPage = total < pageSize ? 1 : (int) Math.ceil((double) total / pageSize);

		if (total % pageSize == 0) {
			totalPage = totalPage - 1;
		}

		StringBuilder out = new StringBuilder();
		if(total>0) {
		//phan trang
		out.append("<nav aria-label=\"Page navigation example\">");
		out.append("<ul class=\"pagination justify-content-center\">");
		
		String isPrevious = (page<=1)?"disabled":"";
		String isNext = (page==totalPage)?"disabled":"";
		
		out.append("<li class=\"page-item "+isPrevious+"\">");
		out.append("<a class=\"page-link\" href=\"/adv/user/list?page="+(page > 1 ? page - 1 : 1)+urlkey+"\" tabindex=\"-1\" aria-disabled=\"true\"><span aria-hidden=\"true\">&laquo;</span></a></a>");
		out.append("</li>");
		if(page>=4) {
		out.append("<li class=\"page-item\">");
		out.append("<a class=\"page-link\" href=\"/adv/user/list?page=1"+urlkey+"\" tabindex=\"-1\" aria-disabled=\"true\"><span aria-hidden=\"true\">1</span></a></a>");
		out.append("</li>");
		}
		//left current
		String leftCurrent = "";
		int count=0;
		for(int i=page-1;i>0;i--) {
			leftCurrent = "<li class=\"page-item\"><a class=\"page-link\" href=\"/adv/user/list?page="+i+urlkey+"\">"+i+"</a></li>"+leftCurrent;

			if(++count>=2) {
				break;
			}
		}
		if(page>=4) {
			leftCurrent = "<li class=\"page-item disabled\"><a class=\"page-link\" href=\"#\">....</a></li>"+leftCurrent;
		}
		out.append(leftCurrent);
		
		out.append(" <li class=\"page-item active\" aria-current=\"page\">");
		out.append("<a class=\"page-link\" href=\"/adv/user/list?page="+page+urlkey+"\">"+page+"</a>");
		out.append("</li>");
		
		String rightCurrent ="";
		 count = 0;
		for(int i=page+1;i<=totalPage;i++) {
			rightCurrent+="<li class=\"page-item\"><a class=\"page-link\" href=\"/adv/user/list?page="+i+urlkey+"\">"+i+"</a></li>";
			if(++count>=2) {
				break;
			}
		}
	
		if(totalPage>page+2) {
			rightCurrent += "<li class=\"page-item disabled\"><a class=\"page-link\" href=\"#\">....</a></li>";
		}
		out.append(rightCurrent);
//		out.append(" <li class=\"page-item\"><a class=\"page-link\" href=\"/adv/user/list?page="+totalPage+"\">"+totalPage+"</a></li>");
		if(page<totalPage-2) {
		out.append("<li class=\"page-item\" >");
		out.append("<a class=\"page-link\"  href=\"/adv/user/list?page="+totalPage+urlkey+"\"><span aria-hidden=\"true\">"+totalPage+"</span></a></a>");
		out.append("</li>");
		}
		out.append("<li class=\"page-item "+isNext+"\" >");
		out.append("<a class=\"page-link\"  href=\"/adv/user/list?page="+(page < totalPage ? page + 1 : totalPage)+urlkey+"\"><span aria-hidden=\"true\">&raquo;</span></a></a>");
		out.append("</li>");
		out.append("</ul>");
		out.append("</nav><!-- End Disabled and active states -->");
		}
		return out;
	}

	public static StringBuilder createdChart(ArrayList<UserObject> items) {
		StringBuilder data = new StringBuilder();
		StringBuilder accounts = new StringBuilder();
		items.forEach(item -> {
			data.append(item.getUser_logined());
			accounts.append("'" + Utilities.decode(item.getUser_fullname())).append("(" + item.getUser_name() + ")'");
			if (items.indexOf(item) < items.size()) {
				data.append(",");
				accounts.append(",");
			}
		});
		StringBuilder tmp = new StringBuilder();
		tmp.append("<div class=\"card\">");
		tmp.append("<div class=\"card-body\">");
		tmp.append("<h5 class=\"card-title\">Biểu đồ đăng nhập hệ thống</h5>");
		tmp.append("");
		tmp.append("<!-- Bar Chart -->");
		tmp.append("<div id=\"barChart\"></div>");
		tmp.append("");
		tmp.append("<script>");
		tmp.append("document.addEventListener(\"DOMContentLoaded\", () => {");
		tmp.append("new ApexCharts(document.querySelector(\"#barChart\"), {");
		tmp.append("series: [{");
		tmp.append("data: [" + data.toString() + "]");
		tmp.append("}],");
		tmp.append("chart: {");
		tmp.append("type: 'bar',");
		if(items.size()<=15) {
			tmp.append("height: 350,");
		} else if(items.size()<=30) {
			tmp.append("height: 600,");
		} else {
			tmp.append("height: 750,");
		}
		
		tmp.append("},");
		tmp.append("plotOptions: {");
		tmp.append("bar: {");
		tmp.append("borderRadius: 4,");
		tmp.append("horizontal: true,");
		tmp.append("}");
		tmp.append("},");
		tmp.append("dataLabels: {");
		tmp.append("enabled: false");
		tmp.append("},");
		tmp.append("xaxis: {");
		tmp.append("categories: [" + accounts.toString() + "],");
		tmp.append("}, ");
		tmp.append("yaxis: {");
		tmp.append("show: true,");
		tmp.append("labels: {");
		tmp.append("show: true,");
		tmp.append("align: 'right',");
		tmp.append("minWidth: 0,");
		tmp.append("maxWidth: 300,");
		tmp.append(
				"style: {color:[], fontSize: '15px', fontFamily: 'Arial, san-serif', fontWeight: 400, cssClass:'apexcharts-yaxis-label'},");
		tmp.append("},");
		tmp.append("}");
		tmp.append("}).render();");
		tmp.append("});");
		tmp.append("</script>");
		tmp.append("<!-- End Bar Chart -->");
		tmp.append("");
		tmp.append("</div>");
		tmp.append("</div>");
		return tmp;
	}
}
