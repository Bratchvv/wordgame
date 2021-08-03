package com.wordgame.admin.config;

import java.io.Serializable;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;


/**
 * Request bean для храниеня промежуточной информации.
 *
 * @author Vladimir Bbratchikov
 */
@Component
@SessionScope
@Data
public class Session implements Serializable {

    private String currentPage;
}
