package cn.valinaa.auction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class AuctionApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(AuctionApplication.class, args);
    }
    
}
