package entelect.training.incubator.spring.booking.loyality;

import entelect.training.incubator.spring.booking.client.gen.CaptureRewardsRequest;
import entelect.training.incubator.spring.booking.client.gen.CaptureRewardsResponse;
import entelect.training.incubator.spring.booking.client.gen.RewardsBalanceRequest;
import entelect.training.incubator.spring.booking.client.gen.RewardsBalanceResponse;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import java.math.BigDecimal;

@Component
public class LoyaltyClient extends WebServiceGatewaySupport {

    public RewardsBalanceResponse getRewardsBalance(String passport) {
        RewardsBalanceRequest rewardsBalanceRequest = new RewardsBalanceRequest();
        rewardsBalanceRequest.setPassportNumber(passport);

        return (RewardsBalanceResponse) getWebServiceTemplate().marshalSendAndReceive(rewardsBalanceRequest);
    }

    public CaptureRewardsResponse captureRewards(String passport, BigDecimal amount) {
        CaptureRewardsRequest captureRewardsRequest = new CaptureRewardsRequest();
        captureRewardsRequest.setPassportNumber(passport);
        captureRewardsRequest.setAmount(amount);

        return (CaptureRewardsResponse) getWebServiceTemplate().marshalSendAndReceive(captureRewardsRequest);
    }
}
