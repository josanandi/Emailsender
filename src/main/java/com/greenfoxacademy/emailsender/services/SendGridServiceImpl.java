package com.greenfoxacademy.emailsender.services;

import com.greenfoxacademy.emailsender.model.EmailPojo;
import com.sendgrid.*;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SendGridServiceImpl implements SendGridService {
    final private String sendGridApi = "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";

    /**
     * PersonalizeEmail - details setting for each email. For the complete example:
     * https://github.com/sendgrid/sendgrid-java/blob/master/examples/helpers/mail/Example.java
     *
     * @param emailPojo
     * @return Mail
     */
    private Mail PersonalizeEmail(EmailPojo emailPojo) {

        Mail mail = new Mail();

        /* From information setting */
        Email fromEmail = new Email();
        fromEmail.setName(emailPojo.getFromName());
        fromEmail.setEmail(emailPojo.getFromEmail());

        mail.setFrom(fromEmail);
        mail.setSubject(emailPojo.getEmailSubject());

        /*
         * Personalization setting, we only add recipient info for this particular
         * example
         */
        Personalization personalization = new Personalization();
        Email to = new Email();
        to.setName(emailPojo.getToName());
        to.setEmail(emailPojo.getToEmail());
        personalization.addTo(to);

        personalization.addHeader("X-Test", "test");
        personalization.addHeader("X-Mock", "true");

        /* Substitution value settings */
        personalization.addSubstitution("%name%", emailPojo.getToName());
        personalization.addSubstitution("%from%", emailPojo.getFromName());

        mail.addPersonalization(personalization);

        /* Set template id */
        mail.setTemplateId("d-3d771abb48344611aefe7b001d0a1def");

        /* Reply to setting */
        Email replyTo = new Email();
        replyTo.setName(emailPojo.getFromName());
        replyTo.setEmail(emailPojo.getFromEmail());
        mail.setReplyTo(replyTo);

        /* Adding Content of the email */
        Content content = new Content();

        /* Adding email message/body */
        content.setType("text/plain");
        content.setValue(emailPojo.getMessage());
        mail.addContent(content);

        return mail;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.monirthougth.sendgrid.service.SendGridService#sendMail(com.monirthought.
     * sendgrid.pojo.EmailPojo)
     */
    @Override
    public String sendMail(EmailPojo emailPojo) {

        SendGrid sg = new SendGrid(sendGridApi);
        sg.addRequestHeader("X-Mock", "true");

        Request request = new Request();
        Mail mail = PersonalizeEmail(emailPojo);
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            ex.printStackTrace();
            return "Failed to send mail! " + ex.getMessage();
        }
        return "Email is sent Successfully!!";
    }
}
