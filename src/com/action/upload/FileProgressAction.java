package com.action.upload;

import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpSession;

import com.action.State;
import com.opensymphony.xwork2.ActionSupport;

public class FileProgressAction extends ActionSupport{
	private State state;
		
    public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}


	@Override
    public String execute() throws Exception {
		HttpSession session=ServletActionContext.getRequest().getSession();
		this.state=(State)session.getAttribute("state");		
		if(state==null){
			System.out.println("action is null");
			state=new State();
			state.setCurrentItem(0);
		}else{
			
			Double a=Double.parseDouble(state.getReadedBytes()+"");
			Double b=Double.parseDouble(state.getTotalBytes()+"");			
			double result=a/b*100;
			state.setRate((int)result);
		}
		return "success";
    }
}
