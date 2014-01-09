package com.coe.framework.action;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;


import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class BaseAction extends ActionSupport implements Preparable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3629413919634325161L;
	
	/**
	 * 日志
	 */
	

	public Logger logger = Logger.getLogger(BaseAction.class);

	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 取得session
	 * 
	 * @return
	 */
	public Map<String, Object> getSession() {
		return ServletActionContext.getContext().getSession();
	}

	/**
	 * 取得Request
	 * 
	 * @return
	 */
	public HttpServletRequest getRequest() {
		return (HttpServletRequest) ServletActionContext.getContext().get(
				ServletActionContext.HTTP_REQUEST);
	}

	/**
	 * 取得Response
	 * 
	 * @return
	 */
	public HttpServletResponse getResponse() {
		return (HttpServletResponse) ServletActionContext.getContext().get(
				ServletActionContext.HTTP_RESPONSE);
	}

	/**
	 * 取得application
	 * 
	 * @return
	 */
	public Map<String, Object> getApplication() {
		return ServletActionContext.getContext().getApplication();
	}

	/**
	 * 取得Parameters
	 * 
	 * @return
	 */
	public Map<String, Object> getParameters() {
		return ServletActionContext.getContext().getParameters();
	}
	

	/**
	 * 打印json数据到客户端
	 * 
	 */
	public void printWriteJSONData(String jsonData) throws IOException{
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.println(jsonData);		
		out.flush();
		out.close();
	}
}
