package edu.iis.mto.testreactor.coffee;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import edu.iis.mto.testreactor.coffee.milkprovider.MilkProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CoffeeMachineTest {

    @Mock
    private Grinder grinder;

    @Mock
    private MilkProvider milkProvider;

    @Mock
    private CoffeeReceipes coffeeReceipes;

    private CoffeeMachine coffeeMachine;

    @Before
    public void setUp() {
        this.coffeeMachine = new CoffeeMachine(this.grinder, this.milkProvider, this.coffeeReceipes);
    }

    @Test
    public void itCompiles() {
        assertThat(true, equalTo(true));
    }

    @Test(expected = NoCoffeeBeansException.class)
    public void testIfMakeThrownNoCoffeeBeansException() {
        this.coffeeMachine.make(mock(CoffeOrder.class));
    }


}
