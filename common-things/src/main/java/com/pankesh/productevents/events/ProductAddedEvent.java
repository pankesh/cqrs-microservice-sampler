package com.pankesh.productevents.events;


public class ProductAddedEvent extends AbstractEvent {


    private static final long serialVersionUID = 8465037689330943899L;

    private String name;

    public ProductAddedEvent() {
    }

    public ProductAddedEvent(String id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}