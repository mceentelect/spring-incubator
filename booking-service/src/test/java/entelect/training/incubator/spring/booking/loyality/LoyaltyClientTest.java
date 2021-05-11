package entelect.training.incubator.spring.booking.loyality;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.math.BigDecimal;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = LoyaltyClientConfig.class, loader = AnnotationConfigContextLoader.class)
public class LoyaltyClientTest {
    @Autowired
    private LoyaltyClient loyaltyClient;

    @Test
    void getRewardsBalance() {
        try {
            var rewardsBalance = loyaltyClient.getRewardsBalance("Test123");
            System.out.println("rewardsBalance:\t" + rewardsBalance);
        } catch (Exception e) {
            System.err.println("Oh no..." + e.getMessage());
        }
    }

    @Test
    void captureRewards() {
        try {
            var captureRewards = loyaltyClient.captureRewards("Test321", new BigDecimal("345"));
            System.out.println("captureRewards:\t" + captureRewards);
        } catch (Exception e) {
            System.err.println("Oh no..." + e.getMessage());
        }
    }
}