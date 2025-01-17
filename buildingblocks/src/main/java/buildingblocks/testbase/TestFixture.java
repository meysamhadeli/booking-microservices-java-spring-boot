package buildingblocks.testbase;

import buildingblocks.mediator.abstractions.IMediator;

public class TestFixture {
    private final IMediator mediator;

    public TestFixture(IMediator mediator) {
        this.mediator = mediator;
    }

    public IMediator getMediator() {
        return mediator;
    }
}