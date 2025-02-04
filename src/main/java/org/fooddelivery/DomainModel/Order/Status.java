package org.fooddelivery.DomainModel.Order;

public enum Status {
    Placed,
    In_Preparation,
    Out_for_Delivery,
    Delivered,
    Cancelled,
    Completed,
    Failed,
    Pending,
    Refunded;
}
