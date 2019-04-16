package me.cyx;

import com.alibaba.fastjson.parser.ParserConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author jie
 * @date 2018/11/15 9:20:19
 */
@SpringBootApplication
public class AppRun {

    public static void main(String[] args) {
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        SpringApplication.run(AppRun.class, args);
    }

}
