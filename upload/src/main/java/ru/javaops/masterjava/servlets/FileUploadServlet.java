package ru.javaops.masterjava.servlets;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import ru.javaops.masterjava.model.User;
import ru.javaops.masterjava.util.ThymeleafAppUtil;
import ru.javaops.masterjava.util.UserExport;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.util.List;


@WebServlet("/")
@MultipartConfig
public class FileUploadServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebContext context = new WebContext(req, resp, req.getServletContext());
        TemplateEngine engine = ThymeleafAppUtil.getTemplateEngine(getServletContext());
        engine.process("export", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request,
                                  HttpServletResponse response)
            throws ServletException, IOException {
        WebContext context = new WebContext(request, response, request.getServletContext());
        TemplateEngine engine = ThymeleafAppUtil.getTemplateEngine(getServletContext());
        // Create path components to save the file
        final Part filePart = request.getPart("file");
        UserExport export = new UserExport();
        try (InputStream filecontent = filePart.getInputStream()) {
            List<User> users = export.getUsers(filecontent);
            context.setVariable("users", users);
            engine.process("result", context, response.getWriter());
        } catch (Exception e) {
            context.setVariable("exception", e);
            engine.process("exception", context, response.getWriter());
        }



        /*final String fileName = getFileName(filePart);
        final String path = request.getParameter("destination");
        OutputStream out = null;
        final PrintWriter writer = response.getWriter();

        try {
            out = new FileOutputStream(new File(path + File.separator
                    + fileName));
            filecontent = filePart.getInputStream();

            int read = 0;
            final byte[] bytes = new byte[1024];

            while ((read = filecontent.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            writer.println("New file " + fileName + " created at " + path);
            LOGGER.log(Level.INFO, "File{0}being uploaded to {1}",
                    new Object[]{fileName, path});
        } catch (FileNotFoundException fne) {
            writer.println("You either did not specify a file to upload or are "
                    + "trying to upload a file to a protected or nonexistent "
                    + "location.");
            writer.println("<br/> ERROR: " + fne.getMessage());

            LOGGER.log(Level.SEVERE, "Problems during file upload. Error: {0}",
                    new Object[]{fne.getMessage()});
        } finally {
            if (out != null) {
                out.close();
            }
            if (filecontent != null) {
                filecontent.close();
            }
            if (writer != null) {
                writer.close();
            }
        }*/
    }

    private String getFileName(final Part part) {
        final String partHeader = part.getHeader("content-disposition");

        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(
                        content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}