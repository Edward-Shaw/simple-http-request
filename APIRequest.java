package com.edward.http;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;

public class APIRequest {

	public enum ApiRequestMethod {
		GET, POST, PUT, DELETE
	}
	
	protected HttpClient getHttpClient(){
		HttpClient client = new HttpClient();
		client.getParams().setContentCharset("utf-8");
		return client;
	}
	
	protected String call;
	protected ApiRequestMethod method;
	protected String paramsStr;
	
	protected String getBaseURL(){
		return "http://pleader.cloume.com/";
	};
	
	private List<NameValuePair> headers = new ArrayList<NameValuePair>();
	
	public void addHeaders(String key, String value){
		NameValuePair header = new NameValuePair(key, value);
		headers.add(header);
	}
	
	protected void beforeExecute(HttpMethod m){
		addHeaders("Accept", "application/json");
		for(NameValuePair nv : headers){
			m.setRequestHeader(nv.getName(), nv.getValue());
		}
	};
	
	/***
	 * Construct a OAPI request
	 * @param call: api path
	 * @param method: request method
	 */
	public APIRequest(String call, ApiRequestMethod method){
		this.setCall(call);
		this.setMethod(method);
	}
	
	public APIRequest(String call, ApiRequestMethod method, String requestParameters){
		this.setCall(call);
		this.setMethod(method);
		this.setParamsStr(requestParameters);
	}

	public JSON execute(){
		
		HttpMethod m = null;
		
		String fullCall = this.getBaseURL() + this.call;
		
		if(this.method == ApiRequestMethod.GET){
			m = new GetMethod(fullCall);
			((GetMethod) m).setQueryString(paramsStr);
			//((GetMethod) m).setQueryString(array);
			
		}else if(this.method == ApiRequestMethod.POST){
			m = new PostMethod(fullCall);
			RequestEntity entity = new StringRequestEntity(paramsStr);
			((PostMethod) m).setRequestEntity(entity);
			//((PostMethod) m).setRequestBody(array);
		}
		
		JSON obj = JSONObject.fromObject(null);
		
		this.beforeExecute(m);
		
		try {
			int state = this.getHttpClient().executeMethod(m);
			if(state == HttpStatus.SC_OK){
				InputStream bs = m.getResponseBodyAsStream();
				BufferedReader br = new BufferedReader( new InputStreamReader(bs, "utf8") );
				String temp = null;
				StringBuffer buffer = new StringBuffer();
				while((temp = br.readLine()) != null){
					buffer.append(temp);
				}
				String body = buffer.toString();
				if(body.length() > 0){
					
					obj = JSONSerializer.toJSON(body);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(m != null){
			m.releaseConnection();
		}
		
		return obj;
	}
	
	public void setCall(String call){
		this.call = call;
	}
	
	public void setMethod(ApiRequestMethod method) {
		this.method = method;
	}
	
	public void setParamsStr(String paramsStr){
		this.paramsStr = paramsStr;
	}
}
