package cn.valinaa.auction;

import cn.valinaa.auction.utils.GenerateKeyPair;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AuctionApplicationTests {
    
    @Test
    void contextLoads() {
        GenerateKeyPair.createKeyPair();
    }
    
}
