package com.cloume.pleader.bridge;
import java.util.HashMap;
import java.util.Map;

public class APIResponse {
	private int code;
	private String message;
	
	private Object result;
	public APIResponse(){}
	
	public APIResponse(int code, Object result, String appPrivateKey){
		this.code = code;
		this.result = result;
	}
	
	public APIResponse(int code, Object result){
		this.code = code;
		this.result = result;
	}
	
	public APIResponse(int code){
		this.code = code;
		this.result = null;
	}

	public Object getResult(){
		return this.result;
	}
	
	public void setResult(Object result){
		this.result = result;
	}
	
	public int getCode() {
		return code;
	}

    public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	/** 操作成功 */
    public static final int RC_SUCCESS = 0;
    public static final int RC_OK = RC_SUCCESS;
    
    /** 用户不存在 */
    public static final int RC_USER_NOT_EXSIST = 10002;

    public static final int RC_ORDER_EXPIRED = 10003;
  
    /** 程序内部错误 */
    public static final int RC_INTERNAL_ERROR = 10099;
    /**参数不为数字    */
    public static final int RC_NOT_NUMBER = 10012; 

    public static final int RC_REQUEST_FAIL = 10022;

    /**
     * 请求参数不合法或者有问题
     */
    public static final int RC_BAD_REQUEST = 10100;

    ///messages
    private static Map<Integer, String> messages = null;
    
    public static String getMessage(int code){
    	if(messages == null){
    		messages = new HashMap<Integer, String>();
    		
    	    messages.put(RC_USER_NOT_EXSIST, "用户不存在");
    	    messages.put(RC_ORDER_EXPIRED, "订单已过期");
    	    messages.put(RC_INTERNAL_ERROR, "程序错误");
    	    messages.put(RC_NOT_NUMBER, "参数值不能转为数字");
    	    messages.put(RC_BAD_REQUEST , "请求不合法");
    	    messages.put(RC_SUCCESS, "成功");
    	    messages.put(RC_REQUEST_FAIL, "很抱歉，您的访问出错了");
    	}
    	
    	String message = messages.get(code);
    	
    	return message == null ? "指定的错误码不存在" : message;
    }
}
