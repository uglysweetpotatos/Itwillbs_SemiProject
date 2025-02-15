package com.car.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.car.db.CarInfoDAO;
import com.car.db.CarInfoDTO;

public class CarListUser implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int pageSize = 15;
		String pageNum = request.getParameter("pageNum");
		if(pageNum == null) {
			pageNum = "1";
		}
		
		int currentPage = Integer.parseInt(pageNum);
		int startRow = (currentPage-1) * pageSize + 1;
		int endRow = startRow + pageSize - 1;
		
		CarInfoDAO dao = new CarInfoDAO();
		List<CarInfoDTO> boardList = dao.getCarList(startRow, pageSize);
		
		// ? ??΄μ§?? 10κ°μ ??΄μ§? λ²νΈκ°? λ³΄μ΄κ²? ?€? 
		int count = dao.getCarCount();
		int pageBlock = 10;
		int startPage = (currentPage - 1) / pageBlock * pageBlock + 1;
		int endPage = startPage + pageBlock - 1;
		int pageCount  = count / pageSize + (count % pageSize == 0 ? 0 : 1);
		if(endPage > pageCount) {
			endPage = pageCount;
		}
		
		// κΈ?λͺ©λ‘, ??΄μ§? ? λ³? => request? ???₯
		request.setAttribute("boardList", boardList );
		request.setAttribute("startPage", startPage);
		request.setAttribute("pageBlock", pageBlock);
		request.setAttribute("endPage", endPage);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("pageCount", pageCount);
		
		// ?΄? ? λ³΄λ?? ???₯ => ./center/notice.jsp
		ActionForward forward = new ActionForward();
		forward.setPath("./center/notice.jsp");
		forward.setRedirect(false);
		return forward;
	}

}
