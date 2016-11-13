package controller;

import com.google.gson.Gson;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by rafa93br on 08/11/16.
 */
public class Controller extends HttpServlet {
    Gson gson = new Gson();

    /**
     * Preparar response para ser do tipo JSON
     * @param response
     */
    protected void prepareResponse(HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }


    protected String reqBody(HttpServletRequest request) throws IOException {
        StringBuffer sb = new StringBuffer();
        BufferedReader bufferedReader = null;
        String content = "";

        try {
            bufferedReader =  request.getReader() ; //new BufferedReader(new InputStreamReader(inputStream));
            char[] charBuffer = new char[128];
            int bytesRead;
            while ( (bytesRead = bufferedReader.read(charBuffer)) != -1 ) {
                sb.append(charBuffer, 0, bytesRead);
            }
        } catch (IOException ex) {
            throw ex;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    throw ex;
                }
            }
        }

        return sb.toString();
    }
}
