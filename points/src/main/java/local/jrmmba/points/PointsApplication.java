package local.jrmmba.points;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class PointsApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(PointsApplication.class, args);
    }

}
