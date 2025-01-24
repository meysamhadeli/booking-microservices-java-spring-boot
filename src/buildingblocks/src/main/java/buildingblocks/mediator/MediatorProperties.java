package buildingblocks.mediator;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "mediator")
@ConditionalOnBean({MediatorConfiguration.class})
public class MediatorProperties {

    private boolean enabled = true;
    private boolean enabledLogPipeline = true;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabledLogPipeline() {
        return enabledLogPipeline;
    }

    public void setEnabledLogPipeline(boolean enabledLogPipeline) {
        this.enabledLogPipeline = enabledLogPipeline;
    }
}
