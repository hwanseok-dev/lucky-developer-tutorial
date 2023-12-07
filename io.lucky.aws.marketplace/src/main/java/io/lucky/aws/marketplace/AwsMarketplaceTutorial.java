package io.lucky.aws.marketplace;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@Slf4j
public class AwsMarketplaceTutorial {
    @Value(value = "${aws.marketplace.api.region}")
    private String awsMarketplaceApiRegion;
    @Value(value = "${aws.marketplace.api.secretKey}")
    private String awsMarketplaceApiSecretKey;
    @Value(value = "${aws.marketplace.api.accessKey}")
    private String awsMarketplaceApiSecretAccessKey;

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(AwsMarketplaceTutorial.class, args);
        AwsMarketplaceTutorial main = context.getBean(AwsMarketplaceTutorial.class);
        main.addSysPropsForAwsMarketplace();
    }

    private void addSysPropsForAwsMarketplace(){
        log.info("[System Property] aws.region : {}", awsMarketplaceApiRegion);
        log.info("[System Property] aws.secretKey : {}", awsMarketplaceApiSecretKey);
        log.info("[System Property] aws.accessKeyId : {}", awsMarketplaceApiSecretAccessKey);

        System.setProperty("aws.region", awsMarketplaceApiRegion);
        System.setProperty("aws.secretKey", awsMarketplaceApiSecretKey);
        System.setProperty("aws.accessKeyId", awsMarketplaceApiSecretAccessKey);
    }
}