package com.pankesh.productcommand.aggregates;

import java.util.ArrayList;
import java.util.List;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

import com.pankesh.productcommand.commands.AddProductCommand;
import com.pankesh.productcommand.commands.MarkProductAsSaleableCommand;
import com.pankesh.productcommand.commands.MarkProductAsUnsaleableCommand;
import com.pankesh.productevents.events.AbstractEvent;
import com.pankesh.productevents.events.ProductAddedEvent;
import com.pankesh.productevents.events.ProductSaleableEvent;
import com.pankesh.productevents.events.ProductUnsaleableEvent;


public class ProductAggregateTest {

    private FixtureConfiguration<ProductAggregate> fixture;

    @Before
    public void setUp() throws Exception {
//        fixture = Fixtures.newGivenWhenThenFixture(ProductAggregate.class);
        fixture = new AggregateTestFixture<>(ProductAggregate.class);
    }

    @Test
    public void testAddProduct() throws Exception {
        fixture.given()
                .when(new AddProductCommand("product-1", "product name"))
                .expectEvents(new ProductAddedEvent("product-1", "product name"));
    }

    @Test
    public void testMarkProductItemAsSaleable() throws Exception {
        fixture.given(new ProductAddedEvent("product-2", "product name"))
                .when(new MarkProductAsSaleableCommand("product-2"))
                .expectEvents(new ProductSaleableEvent("product-2"));
    }

    @Test
    public void testMarkProductItemAsUnsaleableIsAllowed() throws Exception {
        List<AbstractEvent> events = new ArrayList<AbstractEvent>();
        events.add(new ProductAddedEvent("product-3", "product name"));
        events.add(new ProductSaleableEvent("product-3"));

        fixture.given(events)
                .when(new MarkProductAsUnsaleableCommand("product-3"))
                .expectEvents(new ProductUnsaleableEvent("product-3"));
    }

    @Test
    public void testMarkProductItemAsUnsaleableIsPrevented() throws Exception {
        List<AbstractEvent> events = new ArrayList<AbstractEvent>();
        events.add(new ProductAddedEvent("product-3", "product name"));

        fixture.given(events)
                .when(new MarkProductAsUnsaleableCommand("product-3"))
                .expectException(IllegalStateException.class);
    }
}
