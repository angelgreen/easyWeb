package org.easyweb.controller;


public class ErrorController extends AbstractController{

	public ErrorController() {
		super();
	}

    public void do404Error() {
        model.put("code",404);
        model.put("msg","can't find org.easyweb.controller");
        errorRender();
    }
}
