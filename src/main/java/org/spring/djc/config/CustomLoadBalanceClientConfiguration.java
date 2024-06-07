package org.spring.djc.config;


import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @author djc
 * @Description
 * @create 2024-06-07 15:47
 */
@SuppressWarnings("all")
@Configuration(proxyBeanMethods = false)
@LoadBalancerClients(defaultConfiguration = CustomLoadBalanceClientConfiguration.class)
public class CustomLoadBalanceClientConfiguration {

    @Bean
    @ConditionalOnBean(LoadBalancerClientFactory.class)
    public DynamicServiceInstanceListSupplier customLoadBalancer(DiscoveryClient discoveryClient,
                                                                 Environment environment) {
        return new DynamicServiceInstanceListSupplier(discoveryClient, environment);
    }

}
