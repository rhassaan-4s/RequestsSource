package com._4s_.requestsApproval.web.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com._4s_.requestsApproval.service.RequestsApprovalManager;
import com._4s_.restServices.json.RequestApproval;
import com._4s_.restServices.json.RestStatus;
import com._4s_.restServices.service.RequestsService;

@Controller
@RequestMapping("/ajax")
public class AjaxController {
	protected final Log log = LogFactory.getLog(getClass());

	@Autowired
	RequestsApprovalManager requestsApprovalManager;
	
	@Autowired
	RequestsService requestsService;

	@RequestMapping(value="/requestStatus", method=RequestMethod.POST,
			produces=MediaType.APPLICATION_JSON, consumes=MediaType.APPLICATION_FORM_URLENCODED)
	@ResponseBody
	public Map requestStatus(Long reqId) {
		RestStatus status = new RestStatus();
		Map response = new HashMap();
		List reqStatus = requestsApprovalManager.getRequestStatus(reqId);

		status.setCode("200");
		status.setMessage("Request Inserted Successfully");
		status.setStatus("true");
		response.put("Status", status);
		response.put("Response", reqStatus);
		log.debug("request status returned finished");
		return response;
	}
	
	@RequestMapping(value="/vacInfo", method=RequestMethod.POST,
			produces=MediaType.APPLICATION_JSON, consumes=MediaType.APPLICATION_FORM_URLENCODED)
	@ResponseBody
	public Map vacInfo (RequestApproval requestApproval) {
		log.debug("vac info " + requestApproval.getVac());
		return requestsService.getVacInfo(requestApproval);
	}

}
