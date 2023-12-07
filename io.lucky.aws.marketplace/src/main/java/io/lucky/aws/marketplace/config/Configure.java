package io.lucky.aws.marketplace.config;

public class Configure {

    private static Configure instance;

    public static synchronized Configure getInstance(){
        if (instance == null) {
            instance = new Configure();
        }
        return instance;
    }

    private Configure(){}


    public boolean enable_aws_marketplace = true;
    public boolean debug_aws_marketplace = true;
}
