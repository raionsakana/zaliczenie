package edu.iis.mto.testreactor.coffee;

import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

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

    @Test(expected = Exception.class)
    public void testIfGrinderIsUsed() {
        CoffeOrder coffeOrder = mock(CoffeOrder.class);

        when(coffeOrder.getSize()).thenReturn(CoffeeSize.STANDARD);
        when(this.grinder.canGrindFor(CoffeeSize.STANDARD)).thenReturn(true);
        when(this.grinder.grind(CoffeeSize.STANDARD)).thenReturn(2.3);

        this.coffeeMachine.make(coffeOrder);

        verify(this.grinder, times(1)).canGrindFor(CoffeeSize.STANDARD);
        verify(this.grinder, times(1)).grind(CoffeeSize.STANDARD);
    }

}
