package local.jrmmba.points.services;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Spring Boot needs to know what username to use for the auditing fields CreatedBy and ModifiedBy
 * Since this application does not have specific users, a generic username of SYSTEM will be used.
 */
@Component
public class UserAuditing
        implements AuditorAware<String>
{
    /**
     * The current user
     *
     * @return Optional(String) of current user
     */
    @Override
    public Optional<String> getCurrentAuditor()
    {
        String uname = "SYSTEM";
        return Optional.of(uname);
    }
}
