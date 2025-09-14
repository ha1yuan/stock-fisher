package io.github.ha1yuan.infra;

import com.longport.Config;
import com.longport.ConfigBuilder;
import com.longport.trade.AccountBalance;
import com.longport.trade.TradeContext;
import io.github.ha1yuan.common.Logs;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class LongBridge {

    @Value("${longport.app.key}")
    private String appKey;
    @Value("${longport.app.secret}")
    private String appSecret;
    @Value("${longport.access.token}")
    private String appAccessToken;

    private Config config;

    @SneakyThrows
    @PostConstruct
    public void init() {

        config = new ConfigBuilder(appKey, appSecret, appAccessToken).build();
        Logs.bizLog.info("LongBridge init success");

    }

    public void getContext() {

        try {

            TradeContext ctx = TradeContext.create(config).get();
            Logs.bizLog.info("LongBridge getContext success");

            for (AccountBalance accountBalance : ctx.getAccountBalance().get()) {
                Logs.bizLog.info(accountBalance.toString());
            }

        } catch (Exception e) {

            Logs.bizLog.error("LongBridge getContext error", e);

        }
    }
}
