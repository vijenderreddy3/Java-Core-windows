package login;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginCheck
 */
@WebServlet("/LoginCheck")
public class LoginCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public LoginCheck() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		boolean connection=jdbcCommunications.Jdbc.jdbcConn();
		if(connection){
			System.out.println("Conection established");
		}else{
			System.out.println("failed to establish connection");
		}
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		Map<String, String[]> entered = request.getParameterMap();
		ArrayList<String> enteredList = new ArrayList<String>();
		for (String key : entered.keySet()) {
			enteredList.add(entered.get(key)[0]);
		}
		String enteredUsername = enteredList.get(0);
		String enteredPassword = enteredList.get(1);
		boolean loginVerify = jdbcCommunications.Jdbc.checkLogin(enteredUsername, enteredPassword);
		System.out.println("loginVerify:"+loginVerify);
		if (loginVerify) {
			RequestDispatcher rd=request.getRequestDispatcher("userLoginSuccess.html");
			//response.sendRedirect("userLoginSuccess.html");
			rd.forward(request, response);
		} else {
			response.setContentType("text/html");
			RequestDispatcher rd1=request.getRequestDispatcher("home.html");
			rd1.forward(request, response);
			
		}
	}

}
