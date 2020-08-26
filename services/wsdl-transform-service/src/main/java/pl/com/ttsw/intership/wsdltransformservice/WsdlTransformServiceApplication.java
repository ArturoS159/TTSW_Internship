package pl.com.ttsw.intership.wsdltransformservice;

import org.bson.types.Decimal128;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.convert.CustomConversions;

import java.math.BigDecimal;
import java.util.Arrays;

@SpringBootApplication
@EnableEurekaClient
public class WsdlTransformServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(WsdlTransformServiceApplication.class, args);
    }

    @Bean
    CustomConversions customConverions() {
        Converter<Decimal128, BigDecimal> decimal128ToBigDecimal = new Converter<Decimal128, BigDecimal>() {
            @Override
            public BigDecimal convert(Decimal128 s) {
                return s==null ? null : s.bigDecimalValue();
            }
        };

        Converter<BigDecimal, Decimal128> bigDecimalToDecimal128 = new Converter<BigDecimal, Decimal128>() {
            @Override
            public Decimal128 convert(BigDecimal s) {
                return s==null ? null : new Decimal128(s);
            }
        };

        return new CustomConversions(Arrays.asList(decimal128ToBigDecimal, bigDecimalToDecimal128));
    }
}
