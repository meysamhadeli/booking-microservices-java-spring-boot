package buildingblocks.testbase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

public abstract class EndToEndTestBase extends IntegrationTestBase{
    @Autowired
    protected MockMvc mockMvc;
}
