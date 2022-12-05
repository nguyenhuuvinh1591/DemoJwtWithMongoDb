package com.example.demo.Rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entity.SendEmail;
import com.example.demo.Service.SendEmailService;
import com.example.demo.base.AbstractRest;
import com.example.demo.common.DtsApiResponse;
import com.example.demo.constant.CommonConstant;

@RestController
@RequestMapping("/send")
public class SimpleEmailExampleRest extends AbstractRest{

    @Autowired
    private SendEmailService sendEmailService;

    @RequestMapping(value = "/email", method = RequestMethod.POST)
    public DtsApiResponse sendSimpleEmail(@RequestBody SendEmail sendEmail) {
        long start = System.currentTimeMillis();
		try {
			sendEmailService.sendEmail(sendEmail.getReceiver(), sendEmail.getContent(), sendEmail.getSubject());
			return successHandler.handlerSuccess("Send email success", start);
		} catch (Exception e) {
			return this.errorHandler.handlerException(e, start);
		}
    }
    
    @RequestMapping(value = "/emailAll", method = RequestMethod.POST)
    public DtsApiResponse sendSimpleEmailAll(@RequestBody SendEmail sendEmail) {
        long start = System.currentTimeMillis();
		try {
			sendEmailService.sendAllEmailInContact(Integer.valueOf(CommonConstant.SPAM), sendEmail.getContent(), sendEmail.getSubject());
			return successHandler.handlerSuccess("Send email success", start);
		} catch (Exception e) {
			return this.errorHandler.handlerException(e, start);
		}
    }

}