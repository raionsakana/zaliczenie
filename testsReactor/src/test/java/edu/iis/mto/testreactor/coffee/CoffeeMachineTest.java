package edu.iis.mto.testreactor.coffee;

import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

import edu.iis.mto.testreactor.coffee.milkprovider.MilkProvider;
import edu.iis.mto.testreactor.coffee.milkprovider.MilkProviderException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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

    @Test
    public void testIfGetReceipesInvokedThreeTimes() {
        Map<CoffeeSize, Integer> waterMap = new HashMap<>();
        waterMap.put(CoffeeSize.STANDARD, 10);

        CoffeeReceipe coffeeReceipe = CoffeeReceipe
            .builder()
            .withMilkAmount(3)
            .withWaterAmounts(waterMap)
            .build();

        Optional<CoffeeReceipe> coffeeReceipeOptional = Optional.of(coffeeReceipe);
        CoffeOrder coffeOrder = mock(CoffeOrder.class);

        when(coffeOrder.getSize()).thenReturn(CoffeeSize.STANDARD);
        when(coffeOrder.getType()).thenReturn(CoffeType.LATTE);

        when(this.grinder.canGrindFor(CoffeeSize.STANDARD)).thenReturn(true);
        when(this.grinder.grind(CoffeeSize.STANDARD)).thenReturn(2.3);
        when(this.coffeeReceipes.getReceipe(CoffeType.LATTE)).thenReturn(coffeeReceipeOptional);

        this.coffeeMachine.make(coffeOrder);

        verify(this.coffeeReceipes, times(3)).getReceipe(CoffeType.LATTE);
    }

    @Test
    public void testIfMilkProviderMethodHeatInvoked() throws MilkProviderException {
        Map<CoffeeSize, Integer> waterMap = new HashMap<>();
        waterMap.put(CoffeeSize.STANDARD, 10);

        CoffeeReceipe coffeeReceipe = CoffeeReceipe
                .builder()
                .withMilkAmount(3)
                .withWaterAmounts(waterMap)
                .build();

        Optional<CoffeeReceipe> coffeeReceipeOptional = Optional.of(coffeeReceipe);
        CoffeOrder coffeOrder = mock(CoffeOrder.class);

        when(coffeOrder.getSize()).thenReturn(CoffeeSize.STANDARD);
        when(coffeOrder.getType()).thenReturn(CoffeType.LATTE);

        when(this.grinder.canGrindFor(CoffeeSize.STANDARD)).thenReturn(true);
        when(this.grinder.grind(CoffeeSize.STANDARD)).thenReturn(2.3);
        when(this.coffeeReceipes.getReceipe(CoffeType.LATTE)).thenReturn(coffeeReceipeOptional);

        this.coffeeMachine.make(coffeOrder);

        verify(this.milkProvider, times(1)).heat();
    }

    @Test
    public void testIfMilkProviderMethodPourInvoked() throws MilkProviderException {
        Map<CoffeeSize, Integer> waterMap = new HashMap<>();
        waterMap.put(CoffeeSize.STANDARD, 10);

        CoffeeReceipe coffeeReceipe = CoffeeReceipe
                .builder()
                .withMilkAmount(3)
                .withWaterAmounts(waterMap)
                .build();

        Optional<CoffeeReceipe> coffeeReceipeOptional = Optional.of(coffeeReceipe);
        CoffeOrder coffeOrder = mock(CoffeOrder.class);

        when(coffeOrder.getSize()).thenReturn(CoffeeSize.STANDARD);
        when(coffeOrder.getType()).thenReturn(CoffeType.LATTE);

        when(this.grinder.canGrindFor(CoffeeSize.STANDARD)).thenReturn(true);
        when(this.grinder.grind(CoffeeSize.STANDARD)).thenReturn(2.3);
        when(this.coffeeReceipes.getReceipe(CoffeType.LATTE)).thenReturn(coffeeReceipeOptional);

        this.coffeeMachine.make(coffeOrder);

        verify(this.milkProvider, times(1)).pour(3);
    }

}
