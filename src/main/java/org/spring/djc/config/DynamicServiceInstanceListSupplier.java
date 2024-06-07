package org.spring.djc.config;

import org.spring.djc.enums.RequestHeaderEnum;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.core.env.Environment;
import reactor.core.publisher.Flux;
import reactor.util.context.ContextView;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author djc
 * @Description
 * @create 2024-06-07 17:38
 */
public class DynamicServiceInstanceListSupplier implements ServiceInstanceListSupplier {

    private final DiscoveryClient discoveryClient;
    private final Environment environment;

    public DynamicServiceInstanceListSupplier(DiscoveryClient discoveryClient, Environment environment) {
        this.discoveryClient = discoveryClient;
        this.environment = environment;
    }

    @Override
    public String getServiceId() {
        return this.environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
    }

    @Override
    public Flux<List<ServiceInstance>> get() {
        return Flux.deferContextual(this::getServiceInstances);
    }

    private Flux<List<ServiceInstance>> getServiceInstances(ContextView context) {
        return Flux.deferContextual(ctx -> {
            String serviceGroup = ctx.getOrDefault(RequestHeaderEnum.X_SERVICE_GROUP.header(), null);
            List<ServiceInstance> instances = discoveryClient.getInstances(this.getServiceId());
            if (serviceGroup != null) {
                instances = instances.stream()
                        .filter(instance -> serviceGroup.equals(instance.getMetadata().get(RequestHeaderEnum.SERVICE_GROUP.header())))
                        .collect(Collectors.toList());
                if (instances.isEmpty()) {
                    instances = discoveryClient.getInstances(this.getServiceId());
                }
            }
            return Flux.just(instances);
        });
    }
}
