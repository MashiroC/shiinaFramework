package org.redrock.frameworkDemo.framework.been;

import lombok.Data;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@Data
public class View {
    private String jspName;
    private Map<String, Object> data;

    public View() {}

    public View(String jspName, Map<String, Object> data) {
        this.jspName = jspName;
        this.data = data;
    }

    public void view(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher view = request.getRequestDispatcher(jspName);
        for (Map.Entry<String,Object> entry : data.entrySet()) {
            request.setAttribute(entry.getKey(), entry.getValue());
        }
        view.forward(request,response);
    }
}
