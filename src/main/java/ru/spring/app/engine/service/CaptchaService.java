//package ru.spring.app.engine.service;
//
//import com.github.cage.Cage;
//import com.github.cage.GCage;
//import org.apache.commons.io.FileUtils;
//import org.springframework.stereotype.Service;
//
//import javax.servlet.http.HttpSession;
//import java.io.File;
//import java.io.IOException;
//import java.util.Base64;
//
//@Service
//public class CaptchaService {
//
//    private static final long serialVersionUID = 1490947492185481844L;
//    private static final Cage cage = new GCage();
//
//    public static void generateToken(HttpSession session) {
//        String token = cage.getTokenGenerator().next();
//
//        session.setAttribute("captchaToken", token);
//        markTokenUsed(session, false);
//    }
//
//    public static String getToken(HttpSession session) {
//        Object val = session.getAttribute("captchaToken");
//
//        return val != null ? val.toString() : null;
//    }
//
//    public boolean captchaEnter() throws IOException {
//        Cage cage = new GCage();
//        cage.draw(cage.getTokenGenerator().next());
//
//
////        try (OutputStream os = new FileOutputStream("captcha.jpg", false)) {
////            cage.draw(cage.getTokenGenerator().next(), os);
////        }
//        byte[] fileContent = FileUtils.readFileToByteArray(new File((cage).getTokenGenerator().next()));
//        String encodedString = Base64.getEncoder().encodeToString(fileContent);
//
//
//        return false;
//    }
//
//}
