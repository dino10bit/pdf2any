package com.xunfei.pdf2any;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

@WebServlet("/UploadFile")
@SuppressWarnings("rawtypes")
public class UploadFile extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession(true);
		String sessionId=session.getId();

		String realPath=this.getServletContext().getRealPath("")+"/uploads/"+sessionId;
		new File(realPath).mkdirs();
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// maximum size that will be stored in memory
		factory.setSizeThreshold(4096);
		// the location for saving data that is larger than getSizeThreshold()
		factory.setRepository(new File(realPath));

		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("utf-8");
		// maximum size before a FileUploadException will be thrown
		upload.setSizeMax(1000000000);
		String inFileName=null;
		List fileItems;
		try {
			fileItems = upload.parseRequest(request);
			Iterator i = fileItems.iterator();
			i.next();
			FileItem fi = (FileItem) i.next();
			String fileName = fi.getName();
			inFileName=realPath+"/"+fileName;
			fi.write(new File(inFileName));
			session.setAttribute("fileName", inFileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
