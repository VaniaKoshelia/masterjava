package ru.javaops.masterjava.service.mail;

import com.google.common.collect.ImmutableSet;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.MalformedURLException;
import java.net.URL;

public class MailServiceClient {

    public static void main(String[] args) throws MalformedURLException {
        Service service = Service.create(
                new URL("http://localhost:8080/mail/mailService?wsdl"),
                new QName("http://mail.javaops.ru/", "MailServiceImplService"));

        MailService mailService = service.getPort(MailService.class);
        mailService.sendToGroup(ImmutableSet.of(
                new Addressee("vanya.koshelya.99@ukr.net", null)), null, "Subject", "Body");

        GroupResult groupResult = mailService.sendBulk(ImmutableSet.of(new Addressee("Master Java <masterjava@javaops.ru>"), new Addressee("Іван Кошеля, <vanya.koshelya.99@ukr.net>")), "Subject", "Body");
        System.out.println("\nBulk mail groupResult:\n" + groupResult);
    }
}
