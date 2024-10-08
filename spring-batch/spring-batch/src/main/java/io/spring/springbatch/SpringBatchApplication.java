package io.spring.springbatch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableBatchProcessing // 4개의 설정 클래스를 실행시키고, 스프링 배치의 초기화 및 실행 구성이 이루어지게 됩니다.
@SpringBootApplication
public class SpringBatchApplication {

    public static void main(String []args){
        SpringApplication.run(SpringBatchApplication.class, args);
    }
}
