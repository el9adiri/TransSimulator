package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import model.Algorithme;
import model.Graphe;
import model.Station;

/**
 * Servlet implementation class SimulatorController
 */
@WebServlet("/SimulatorController")
public class SimulatorController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Graphe graphe = HomeController.graphe;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SimulatorController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String json = new Gson().toJson(graphe.lignes);

	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(json);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String source = request.getParameter("s");
		String destination = request.getParameter("d");
		Algorithme app = new Algorithme(source, destination, graphe);
		String json = new Gson().toJson(app.execute());
	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(json);
	}

}
