import java.io.IOException;
import java.io.PrintWriter;

/*
    http response에 Alert javascript 보내기
 */
import javax.servlet.http.HttpServletResponse;

public class AlertScriptUtils {
    public static void init(HttpServletResponse response) {
        response.setContentType("text/html; charset=utf-8");
        response.setCharacterEncoding("utf-8");
    }

    public static void alert(HttpServletResponse response, String alertText)
            throws IOException {
        init(response);
        PrintWriter out = response.getWriter();
        out.println("<script>alert('" + alertText + "');</script> ");
        out.flush();
    }

    public static void alertAndMovePage(HttpServletResponse response,
                                        String alertText, String nextPage) throws IOException {
        init(response);
        PrintWriter out = response.getWriter();
        out.println("<script>alert('" + alertText + "'); location.href='"
                + nextPage + "';</script> ");
        out.flush();
    }

    public static void alertAndBackPage(HttpServletResponse response,
                                        String alertText) throws IOException {
        init(response);
        PrintWriter out = response.getWriter();
        out.println(
                "<script>alert('" + alertText + "'); history.go(-1);</script>");
        out.flush();
    }
}
