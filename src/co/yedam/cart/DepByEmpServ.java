package co.yedam.cart;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

@WebServlet("/DepByEmpServ")
public class DepByEmpServ extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DepByEmpServ() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		EmpDAO dao = new EmpDAO();
		//<String, Integer> 타입의 map 데이터를 가지고 옴 
		Map<String, Integer> map =  dao.getDeptByEmp();
		
		Set<Entry<String,Integer>>set = map.entrySet();
		
		//Set 컬렉션을 루핀 돌면서 
		JsonArray outArray = new JsonArray();
		for(Entry<String, Integer> entry : set) {
			JsonArray innerAry = new JsonArray();
			//정보를 담아와서 outArray에 추가하는 것 
			innerAry.add(entry.getKey()); 
			innerAry.add(entry.getValue());
			outArray.add(innerAry);
		}
		
	
		Gson gson = new GsonBuilder().create();
		out.println(gson.toJson(outArray));
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
