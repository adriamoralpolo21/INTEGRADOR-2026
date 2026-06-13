package com.glovo.app.dto.cart;

import java.math.BigDecimal;
import java.util.List;

public class CartResponseDto {
    private List<CartItemDto> items;
    private int count;
    private BigDecimal total;

    public List<CartItemDto> getItems() {
        return items;
    }

    public void setItems(List<CartItemDto> items) {
        this.items = items;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
