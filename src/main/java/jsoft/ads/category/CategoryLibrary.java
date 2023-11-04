package jsoft.ads.category;

import java.util.ArrayList;
import java.util.HashMap;

import jsoft.ads.section.SectionLibrary;
import jsoft.library.Utilities;
import jsoft.objects.CategoryObject;
import jsoft.objects.SectionObject;
import jsoft.objects.UserObject;

public class CategoryLibrary {
	public static String viewCategory(ArrayList<CategoryObject> items,Short total,HashMap<Integer, String> manager_name,int page,UserObject user) {
		StringBuffer tmp = new StringBuffer();
		
		tmp.append("<div class=\"card\">");
		tmp.append("<div class=\"card-body\">");

		tmp.append("<table class=\"table table-striped\">");
		tmp.append("<thead>");
		tmp.append("<tr>");
		tmp.append("<th scope=\"col\">STT</th>");
		tmp.append("<th scope=\"col\">Ngày tọa</th>");
		tmp.append("<th scope=\"col\">Tên thể loại</th>");
		tmp.append("<th scope=\"col\">Chuyên mục</th>");
		tmp.append("<th scope=\"col\">Quản lí</th>");
		tmp.append("<th scope=\"col\">Ghi chú</th>");
		tmp.append("<th scope=\"col\" colspan=\"3\">Thao tác</th>");
		tmp.append("<th scope=\"col\">#</th>");
		tmp.append("</tr>");
		tmp.append("</thead>");
		
		tmp.append("<tbody>");
		if(items.size()>0) {
		items.forEach(item -> {
			tmp.append("<tr>");
			tmp.append("<th scope=\"row\">"+(items.indexOf(item) + 1)+"</th>");
			tmp.append("<td>"+item.getCategory_created_date()+"</td>");
			tmp.append("<td>"+item.getCategory_name()+"</td>");
			tmp.append("<td>"+item.getCategory_section_name()+"</td>");

			tmp.append("<td>"+manager_name.get(item.getCategory_manager_id())+"</td>");
			tmp.append("<td>"+(jsoft.library.Utilities_text.shortenText(item.getCategory_notes(), 10))+"</td>");
			if(item.isCategory_delete()) {
			tmp.append("<td class=\"text-center\"><a href=\"/adv/category/dr?id=" + item.getCategory_id() + "&t&r&page=" + page
					+ "\" class=\"btn btn-primary btn-sm\"><i class=\"fas fa-trash-restore-alt\"></i></a></td>");
			}
			tmp.append("<td><a href=\"/adv/category/edit?id="+item.getCategory_id()+"&view\" class=\"btn btn-primary btn-sm\"><i class=\"bi bi-eye-fill\"></i></a></td>");
			if(item.getCategory_manager_id()==user.getUser_id() || user.getUser_permission()> item.getCategory_permis()) {
				tmp.append("<td><a href=\"/adv/category/edit?id=" + item.getCategory_id()
				+ "\" class=\"btn btn-secondary btn-sm\"><i class=\"bi bi-pencil-square\"></i></a></td>");
			} else {
				tmp.append("<td><a href=\"/adv/category/edit?id=" + item.getCategory_id()
				+ "\" class=\"btn btn-secondary btn-sm disabled\"><i class=\"bi bi-pencil-square\"></i></a></td>");
			}
			if(item.getCategory_permis()<=user.getUser_permission()) {
				tmp.append("<td class=\"text-center\"><a href=\"#\" class=\"btn btn-warning btn-sm\" data-bs-toggle=\"modal\" data-bs-target=\"#delCategory"
						+ item.getCategory_id() + "\"><i class=\"bi bi-archive\"></i></a></td>");
			} else {
				tmp.append("<td class=\"text-center\"><a href=\"#\" class=\"btn btn-warning btn-sm disabled\" data-bs-toggle=\"modal\" data-bs-target=\"#delCategory"
						+ item.getCategory_id() + "\"><i class=\"bi bi-archive\"></i></a></td>");
			}
			tmp.append(CategoryLibrary.ViewDellCategory(item, page));
			tmp.append("<th scope=\"row\">"+item.getCategory_id()+"</th>");
			tmp.append("</tr>");			
		});
		} else {
			 tmp.append("<tr><th class=\"text-center\" colspan=\"10\">Danh sÃ¡ch rá»—ng</th></tr>");
		}

		
		tmp.append("</tbody>");
		tmp.append("</table>");
		
		tmp.append("</div>");
		tmp.append("</div>");

		
		return tmp.toString();
	}
	public static String viewManagerOptions(ArrayList<UserObject> users,int selected_id) {
		StringBuilder tmp = new StringBuilder();
		users.forEach(item->{
			if(item.getUser_id()==selected_id) {
				tmp.append("<option value=\""+item.getUser_id()+"\" selected>");
			} else {
				tmp.append("<option value=\""+item.getUser_id()+"\">");	
			}
			
			tmp.append(item.getUser_fullname()).append(" (").append(item.getUser_name()).append(")");
			tmp.append("</option>");
		});
		return tmp.toString();
	}
	public static String viewSectionOptions(ArrayList<SectionObject> sections,int selected_id) {
		StringBuilder tmp = new StringBuilder();
		sections.forEach(item->{
			if(item.getSection_manager_id()==selected_id) {
				tmp.append("<option value=\""+item.getSection_manager_id()+"\" selected>");
			} else {
				tmp.append("<option value=\""+item.getSection_manager_id()+"\">");	
			}
			
			tmp.append(item.getSection_name());
			tmp.append("</option>");
		});
		return tmp.toString();
	}
	public static StringBuilder pagination(int total, Byte pageSize,int page,String saveKey,boolean trash) {
//		System.out.println(total);
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
		//phan trang
		if(total>0) {
		out.append("<nav aria-label=\"Page navigation example\">");
		out.append("<ul class=\"pagination justify-content-center\">");
		
		String isPrevious = (page<=1)?"disabled":"";
		String isNext = (page==totalPage)?"disabled":"";
		
		out.append("<li class=\"page-item "+isPrevious+"\">");
		out.append("<a class=\"page-link\" href=\"/adv/category/list?page="+(page > 1 ? page - 1 : 1)+urlkey+"\" tabindex=\"-1\" aria-disabled=\"true\"><span aria-hidden=\"true\">&laquo;</span></a></a>");
		out.append("</li>");
		if(page>=4) {
		out.append("<li class=\"page-item\">");
		out.append("<a class=\"page-link\" href=\"/adv/category/list?page=1"+urlkey+"\" tabindex=\"-1\" aria-disabled=\"true\"><span aria-hidden=\"true\">1</span></a></a>");
		out.append("</li>");
		}
		//left current
		String leftCurrent = "";
		int count=0;
		for(int i=page-1;i>0;i--) {
			leftCurrent = "<li class=\"page-item\"><a class=\"page-link\" href=\"/adv/category/list?page="+i+urlkey+"\">"+i+"</a></li>"+leftCurrent;

			if(++count>=2) {
				break;
			}
		}
		if(page>=4) {
			leftCurrent = "<li class=\"page-item disabled\"><a class=\"page-link\" href=\"#\">....</a></li>"+leftCurrent;
		}
		out.append(leftCurrent);
		
		out.append(" <li class=\"page-item active\" aria-current=\"page\">");
		out.append("<a class=\"page-link\" href=\"/adv/category/list?page="+page+urlkey+"\">"+page+"</a>");
		out.append("</li>");
		
		String rightCurrent ="";
		 count = 0;
		for(int i=page+1;i<=totalPage;i++) {
			rightCurrent+="<li class=\"page-item\"><a class=\"page-link\" href=\"/adv/category/list?page="+i+urlkey+"\">"+i+"</a></li>";
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
		out.append("<a class=\"page-link\"  href=\"/adv/category/list?page="+totalPage+urlkey+"\"><span aria-hidden=\"true\">"+totalPage+"</span></a></a>");
		out.append("</li>");
		}
		out.append("<li class=\"page-item "+isNext+"\" >");
		out.append("<a class=\"page-link\"  href=\"/adv/category/list?page="+(page < totalPage ? page + 1 : totalPage)+urlkey+"\"><span aria-hidden=\"true\">&raquo;</span></a></a>");
		out.append("</li>");
		out.append("</ul>");
		out.append("</nav><!-- End Disabled and active states -->");
		}
		return out;
	}
	private static StringBuilder ViewDellCategory(CategoryObject item, int page) {
		StringBuilder tmp = new StringBuilder();

		String title;
		if (item.isCategory_delete()) {
			title = "XÃ³a vÄ©nh viá»…n";
		} else {
			title = "XÃ³a tÃ i khoáº£n";
		}

		tmp.append("<div class=\"modal modal-fullscreen-sm-down fade\" id=\"delCategory" + item.getCategory_id()
				+ "\" tabindex=\"-1\" aria-labelledby=\"exampleModalLabel\" aria-hidden=\"true\">");
		tmp.append("<div class=\"modal-dialog\">");
		tmp.append("<div class=\"modal-content\">");
		tmp.append("<div class=\"modal-header\">");
		tmp.append("<h1 class=\"modal-title fs-5\" id=\"exampleModalLabel\">" + title + "</h1>");
		tmp.append(
				"<button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"modal\" aria-label=\"Close\"></button>");
		tmp.append("</div>");
		tmp.append("<div class=\"modal-body\">");
		if (item.isCategory_delete()) {
			tmp.append("Báº¡n sáº½ vÄ©nh viá»…n xÃ³a thá»ƒ loáº¡i <b>").append(" (" + item.getCategory_name() + ")</b>");
			tmp.append("<p>TÃ i khoáº£n khÃ´ng thá»ƒ phá»¥c há»“i Ä‘Æ°á»£c ná»¯a!!!</p>");
		} else {
			tmp.append("Báº¡n cÃ³ cháº¯c cháº¯n xÃ³a thá»ƒ loáº¡i <b>").append(" (" + item.getCategory_name() + ")</b>");
			tmp.append("Há»‡ thá»‘ng táº¡m thá»i lÆ°u vÃ o thÃ¹ng rÃ¡c cÃ³ thá»ƒ phuc há»“i trong vÃ²ng 30 ngÃ y");
		}

		tmp.append("</div>");
		tmp.append("<div class=\"modal-footer\">");
		if (item.isCategory_delete()) {
			tmp.append("<a href=\"/adv/category/dr?id=" + item.getCategory_id() + "&page="
					+ page + "\" class=\"btn btn-danger px-5 py-2\">XÃ³a vÄ©nh viá»…n</a>");
		} else {
			tmp.append("<a href=\"/adv/category/dr?id=" + item.getCategory_id() + "&t"
					+ "&page=" + page + "\" class=\"btn btn-warning px-5 py-2\">XÃ³a</a>");
		}
		tmp.append(
				"<button type=\"button\" class=\"btn btn-secondary px-5 py-2\" data-bs-dismiss=\"modal\">Há»§y</button>");
		tmp.append("</div>");
		tmp.append("</div>");
		tmp.append("</div>");
		tmp.append("</div>");
		return tmp;
	}
	
	public static StringBuilder createdChart(HashMap<Integer, String> datas) {
		StringBuilder data = new StringBuilder();
		StringBuilder sections = new StringBuilder();
		 for (int i : datas.keySet()) {
			    data.append(i);
			    sections.append("'" + Utilities.decode(datas.get(i))).append("'");
				if (i < datas.size()) {
					data.append(",");
					sections.append(",");
				}
		    }
		StringBuilder tmp = new StringBuilder();
		tmp.append("<div class=\"card\">");
		tmp.append("<div class=\"card-body\">");
		tmp.append("<h5 class=\"card-title\">Biá»ƒu Ä‘á»“ thá»‘ng kÃª thá»ƒ loáº¡i theo chuyÃªn má»¥c</h5>");
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
		if(datas.size()<=15) {
			tmp.append("height: 350,");
		} else if(datas.size()<=30) {
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
		tmp.append("categories: [" + sections.toString() + "],");
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
