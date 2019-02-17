package ru.javaops.masterjava.util;

import ru.javaops.masterjava.model.User;
import ru.javaops.masterjava.model.UserFlag;
import ru.javaops.masterjava.xml.util.StaxStreamProcessor;

import javax.xml.stream.events.XMLEvent;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class UserExport {

    public List<User> getUsers(InputStream is) throws Exception {
        List<User> users = new ArrayList<>();
        try (StaxStreamProcessor processor = new StaxStreamProcessor(is)) {
            User user = null;
            while (processor.doUntil(XMLEvent.START_ELEMENT, "User")) {
                user = new User();
                user.setEmail(processor.getAttribute("email"));
                user.setCity(processor.getAttribute("city"));
                user.setUserFlag(UserFlag.valueOf(processor.getAttribute("flag")));
                users.add(user);
            }
        }
        return users;
     }
}
