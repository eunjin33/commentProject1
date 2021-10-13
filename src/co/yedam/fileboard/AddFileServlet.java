package co.yedam.fileboard;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

@WebServlet("/AddFileServlet")
public class AddFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AddFileServlet() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");

		// 1.repuest, saveDir(저장할 폴더위치), maxFileSize, encode, renamePolicy(같은 이름의 파일이
		// 올라가면 이름 변경)
		ServletContext context = getServletContext();
		String saveDir = context.getRealPath("upload");
		int maxSize = 1024 * 1024 * 30;
		String encoding = "UTF-8";

		MultipartRequest multi = new MultipartRequest(request, saveDir, maxSize, encoding,
				new DefaultFileRenamePolicy());
		
		String author = multi.getParameter("author");
		String title = multi.getParameter("title");
		String file = multi.getFilesystemName("file");
		
		FileDAO dao = new FileDAO();
		FileVO vo = dao.uploadFile(author, title, file);
		
		Gson gson = new GsonBuilder().create();
		response.getWriter().println(gson.toJson(vo));
		
		System.out.println(saveDir);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
