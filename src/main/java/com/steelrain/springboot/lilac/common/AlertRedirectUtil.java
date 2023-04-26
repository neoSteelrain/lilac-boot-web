package com.steelrain.springboot.lilac.common;

import com.steelrain.springboot.lilac.datamodel.AlertMessageDTO;
import org.springframework.ui.Model;

public class AlertRedirectUtil {

    /**
     * 리다이렉트 하면서 사용자에게 alert 창에 메시지를 보여줄 수 있도록한다
     * - 동작방식 및 순서
     * 1. 메시지 정보, 리다렉트할 URL를 받아 AlertMessageDTO 객체를 생성하고 Model 객체에 담는다
     * 2. common/alertMessage.html 페이지로 forward
     * 3. common/alertMessage.html 에서는 Model에 있는 메시지를 alert창으로 보여준다
     * 4. URL 위치로 redirect 한다
     * 
     * common/alertMessage.html 페이지
     * @param msg 사용자에게 보여줄 메시지
     * @param url 리다이렉트할 URL, url은 location.href 에 들어가기 때문에 첫화면으로 가려면 컨텍스트패스로 넣어줘야 한다
     * @param model 메시지정보와 리다이렉트 URL을 담을 Model객체
     * @return forward 하려는 comm/alertMessage.html 페이지
     */
    public static String alertRedirect(String msg, String url, Model model){
        AlertMessageDTO alertDTO = AlertMessageDTO.builder()
                .message(msg)
                .redirectURL(url)
                .build();
        model.addAttribute("alertDTO", alertDTO);
        return "common/alertMessage";
    }
}
