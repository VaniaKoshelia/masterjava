package ru.javaops.masterjava.webapp;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.context.WebContext;
import ru.javaops.masterjava.persist.DBIProvider;
import ru.javaops.masterjava.persist.dao.UserDao;
import ru.javaops.masterjava.service.mail.Addressee;
import ru.javaops.masterjava.service.mail.GroupResult;
import ru.javaops.masterjava.service.mail.MailWSClient;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ru.javaops.masterjava.common.web.ThymeleafListener.engine;

@WebServlet("/send")
@Slf4j
public class SendServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String result = null;
        try {
            req.setCharacterEncoding("UTF-8");
            String users = req.getParameter( "users");
            String subject = req.getParameter("subject");
            String body = req.getParameter("body");
            ImmutableSet<Addressee> addresseeList = ImmutableSet.copyOf(Stream.of(users.split(",")).map(a -> a.split(" ")).map(a -> new Addressee(a[1], a[0])).collect(Collectors.toList()));
            GroupResult groupResult = MailWSClient.sendBulk(addresseeList, subject, body);
            result = groupResult.toString();
        } catch(Exception e) {
            log.error("Processing failed", e);
            result = e.toString();
        }
        resp.getWriter().write(result);
    }
}
