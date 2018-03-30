package org.redrock.frameworkDemo.framework.core;

import org.redrock.frameworkDemo.framework.been.Data;
import org.redrock.frameworkDemo.framework.been.Handle;
import org.redrock.frameworkDemo.framework.been.View;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "test", urlPatterns = "/*")
public class DispatcherServlet extends HttpServlet {
    private PropsLoader propsLoader;
    private ClassLoader classLoader;
    private BeanFactory beanFactory;

    @Override
    public void init() throws ServletException {
        propsLoader = new PropsLoader(getServletContext());
        classLoader = new ClassLoader(propsLoader);
        beanFactory = new BeanFactory(classLoader);
    }

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String uri = request.getRequestURI();
        String method = request.getMethod();
        String requestMapping = method + ":" + uri;
        Handle handle = beanFactory.getHandle(requestMapping);
        if (handle != null) {
            Map<String, String> parameterMap = beanFactory.getParameterMap(request);
            Object object = handle.invoke(parameterMap);
            if (object != null) {
                if (object instanceof Data) {
                    Data data = (Data) object;
                    data.write(response);
                } else if (object instanceof View) {
                    View view = (View) object;
                    view.view(request, response);
                }
            } else {
                Data data = new Data();
                data.setResponseJson("{\"result\":\"success\"}");
                data.write(response);
            }
        }
    }
}
