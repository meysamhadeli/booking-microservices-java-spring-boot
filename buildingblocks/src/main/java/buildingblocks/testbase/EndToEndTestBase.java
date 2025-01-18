package buildingblocks.testbase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@WithMockUser(authorities = "ADMIN")
public abstract class EndToEndTestBase extends IntegrationTestBase {
    @Autowired
    protected MockMvc mockMvc;
}
