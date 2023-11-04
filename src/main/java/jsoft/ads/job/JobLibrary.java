package jsoft.ads.job;

import java.util.ArrayList;
import java.util.HashMap;

import jsoft.library.Utilities;
import jsoft.objects.CompanyObject;
import jsoft.objects.JobObject;
import jsoft.objects.SkillObject;
import jsoft.objects.UserObject;

public class JobLibrary {
	public static String viewJob(ArrayList<JobObject> items,Short total,HashMap<Integer, String> author,int page,UserObject user) {
		StringBuffer tmp = new StringBuffer();
		
		tmp.append("<div class=\"card\">");
		tmp.append("<div class=\"card-body\">");

		tmp.append("<table class=\"table table-striped\">");
		tmp.append("<thead>");
		tmp.append("<tr>");
		tmp.append("<th scope=\"col\">STT</th>");
		tmp.append("<th scope=\"col\">Tiêu đề</th>");
		tmp.append("<th scope=\"col\">Công ty</th>");
	//	tmp.append("<th scope=\"col\">Ngành nghề</th>");
	//	tmp.append("<th scope=\"col\">Số lượng</th>");
		tmp.append("<th scope=\"col\">Thời gian</th>");
		tmp.append("<th scope=\"col\">Trạng thái</th>");
		tmp.append("<th scope=\"col\" class='text-center' colspan=\"3\">Thực hiện</th>");
		tmp.append("<th scope=\"col\">#</th>");
		tmp.append("</tr>");
		tmp.append("</thead>");
		
		tmp.append("<tbody>");
		if(items.size()>0) {
		items.forEach(item -> {
			tmp.append("<tr class='fs-7 text-lowercase'>");
			tmp.append("<th scope=\"row\">"+(items.indexOf(item) + 1)+"</th>");
			tmp.append("<td>"+jsoft.library.Utilities_text.shortenText(item.getJob_title(),4)+"</td>");
			tmp.append("<td>"+jsoft.library.Utilities_text.shortenText(item.getCompany().getCompany_name(),3)+"</td>");
		//	tmp.append("<td>"+item.getJob_career().getCareer_name()+"</td>");
		//	tmp.append("<td>"+item.getJob_quantity()+"</td>");
			tmp.append("<td class=\"\">"
			+ item.getJob_created_date()+"<i class=\"bi bi-arrow-right-square-fill\"></i>"
			+item.getJob_expiration_date()+"</td>");
			tmp.append("<td>");
			tmp.append("<select onchange=\"fetchUpdateStatusJob("+item.getJob_id()+",this.value);\" id=\"slcStatus\" class=\"form-control\">");
			tmp.append("<option value=\"0\" "+(item.getJob_status()==0?"selected":"")+">Ðang chờ phê duyệt</option>");
			tmp.append("<option value=\"1\" "+(item.getJob_status()==1?"selected":"")+">Ðang tuyển dụng</option>");
			tmp.append("<option value=\"2\" "+(item.getJob_status()==2?"selected":"")+">Ðã hết hạn</option>");
			tmp.append("<option value=\"3\" "+(item.getJob_status()==3?"selected":"")+">Ðã tuyển dụng</option>");
			tmp.append("<option value=\"4\" "+(item.getJob_status()==4?"selected":"")+">Tạm ngưng tuyển dụng</option>");
			tmp.append("<option value=\"5\" "+(item.getJob_status()==5?"selected":"")+">Ðã hủy</option>");
			tmp.append("</select >");
			tmp.append("</td>");
			
			if(item.isJob_delete()) {
			tmp.append("<td class=\"text-center\"><a href=\"/adv/job/dr?id=" + item.getJob_id() + "&t&r&page=" + page
					+ "\" class=\"btn btn-primary btn-sm\"><i class=\"fas fa-trash-restore-alt\"></i></a></td>");
			}
			// view
			tmp.append("<td><a href=\"/adv/job/view?id="+item.getJob_id()+"\" class=\"btn btn-primary btn-sm\"><i class=\"bi bi-eye-fill\"></i></a></td>");
			// edit
			tmp.append("<td><a href=\"#\" class=\"btn btn-secondary btn-sm\" data-bs-toggle=\"modal\" onclick=\"fetchJobView("+item.getJob_id()+")\" data-bs-target=\"#addEditJob\"><i class=\"bi bi-pencil-square \"></i></a></td>");
		//	tmp.append("<td><a href=\"/adv/job/edit?id=" + item.getJob_id()
			//	+ "&page="+page+"\" class=\"btn btn-secondary btn-sm \"><i class=\"bi bi-pencil-square\"></i></a></td>");
			//del
				tmp.append("<td class=\"text-center\"><a href=\"#\" class=\"btn btn-warning btn-sm\" data-bs-toggle=\"modal\" data-bs-target=\"#delJob"
						+ item.getJob_id() + "\"><i class=\"bi bi-archive\"></i></a></td>");
			tmp.append(JobLibrary.ViewDellJob(item, page));
			tmp.append("<th scope=\"row\">"+item.getJob_id()+"</th>");
			tmp.append("</tr>");			
		});
		} else {
			 tmp.append("<tr><th class=\"text-center\" colspan=\"10\">Danh sách rỗng</th></tr>");
		}

		
		tmp.append("</tbody>");
		tmp.append("</table>");
		
		tmp.append("</div>");
		tmp.append("</div>");

		
		return tmp.toString();
	}
	
	public static String viewCompanyOptions(ArrayList<CompanyObject> companies) {
		StringBuilder tmp = new StringBuilder();
		companies.forEach(item->{
			tmp.append("<option value=\""+item.getCompany_id()+"\">");	
			tmp.append(item.getCompany_name());
			tmp.append("</option>");
		});
		return tmp.toString();
	}
	public static String viewSkillOptions(ArrayList<SkillObject> companies) {
		StringBuilder tmp = new StringBuilder();
		companies.forEach(item->{
			tmp.append("<option value=\""+item.getSkill_id()+"\">");	
			tmp.append(item.getSkill_name());
			tmp.append("</option>");
		});
		return tmp.toString();
	}
	public static StringBuilder pagination(int total, Byte pageSize,int page,String saveKey,boolean trash) {
//		System.out.println(total);
		 page =(page<=1)? 1 : page;
		 
		 String urlkey = "",url = "/adv/job/list?page=";
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
		out.append("<a class=\"page-link\" href=\""+url+(page > 1 ? page - 1 : 1)+urlkey+"\" tabindex=\"-1\" aria-disabled=\"true\"><span aria-hidden=\"true\">&laquo;</span></a></a>");
		out.append("</li>");
		if(page>=4) {
		out.append("<li class=\"page-item\">");
		out.append("<a class=\"page-link\" href=\""+url+"1"+urlkey+"\" tabindex=\"-1\" aria-disabled=\"true\"><span aria-hidden=\"true\">1</span></a></a>");
		out.append("</li>");
		}
		//left current
		String leftCurrent = "";
		int count=0;
		for(int i=page-1;i>0;i--) {
			leftCurrent = "<li class=\"page-item\"><a class=\"page-link\" href=\"/adv/job/list?page="+i+urlkey+"\">"+i+"</a></li>"+leftCurrent;

			if(++count>=2) {
				break;
			}
		}
		if(page>=4) {
			leftCurrent = "<li class=\"page-item disabled\"><a class=\"page-link\" href=\"#\">....</a></li>"+leftCurrent;
		}
		out.append(leftCurrent);
		
		out.append(" <li class=\"page-item active\" aria-current=\"page\">");
		out.append("<a class=\"page-link\" href=\""+url+page+urlkey+"\">"+page+"</a>");
		out.append("</li>");
		
		String rightCurrent ="";
		 count = 0;
		for(int i=page+1;i<=totalPage;i++) {
			rightCurrent+="<li class=\"page-item\"><a class=\"page-link\" href=\""+url+i+urlkey+"\">"+i+"</a></li>";
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
		out.append("<a class=\"page-link\"  href=\""+url+totalPage+urlkey+"\"><span aria-hidden=\"true\">"+totalPage+"</span></a></a>");
		out.append("</li>");
		}
		out.append("<li class=\"page-item "+isNext+"\" >");
		out.append("<a class=\"page-link\"  href=\""+url+(page < totalPage ? page + 1 : totalPage)+urlkey+"\"><span aria-hidden=\"true\">&raquo;</span></a></a>");
		out.append("</li>");
		out.append("</ul>");
		out.append("</nav><!-- End Disabled and active states -->");
		}
		return out;
	}
	private static StringBuilder ViewDellJob(JobObject item, int page) {
		StringBuilder tmp = new StringBuilder();

		String title;
		if (item.isJob_delete()) {
			title = "Xóa vĩnh viễn";
		} else {
			title = "Xóa tài khoản";
		}

		tmp.append("<div class=\"modal modal-fullscreen-sm-down fade\" id=\"delJob" + item.getJob_id()
				+ "\" tabindex=\"-1\" aria-labelledby=\"exampleModalLabel\" aria-hidden=\"true\">");
		tmp.append("<div class=\"modal-dialog\">");
		tmp.append("<div class=\"modal-content\">");
		tmp.append("<div class=\"modal-header\">");
		tmp.append("<h1 class=\"modal-title fs-5\" id=\"exampleModalLabel\">" + title + "</h1>");
		tmp.append(
				"<button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"modal\" aria-label=\"Close\"></button>");
		tmp.append("</div>");
		tmp.append("<div class=\"modal-body\">");
		if (item.isJob_delete()) {
			tmp.append("Bạn sẽ vĩnh viễn xóa công ty <b>").append(" (" + item.getJob_title() + ")</b>");
			tmp.append("<p>Tài khoản không thể phục hồi được nữa!!!</p>");
		} else {
			tmp.append("Bạn có chắc chắn xóa công ty <b>").append(" (" + item.getJob_title() + ")</b>");
			tmp.append("Hệ thống tạm thời lưu vào thùng rác có thể phuc hồi trong vòng 30 ngày");
		}

		tmp.append("</div>");
		tmp.append("<div class=\"modal-footer\">");
		if (item.isJob_delete()) {
			tmp.append("<a href=\"/adv/job/dr?id=" + item.getJob_id() + "&page="
					+ page + "\" class=\"btn btn-danger px-5 py-2\">Xóa vĩnh viễn</a>");
		} else {
			tmp.append("<a href=\"/adv/job/dr?id=" + item.getJob_id() + "&t"
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
		tmp.append("<h5 class=\"card-title\">Biểu đồ thống kê công ty theo chuyên mục</h5>");
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
