package com.glovo.app.controllers;

import com.glovo.app.dto.cart.CartResponseDto;
import com.glovo.app.services.CartService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/items")
    public CartResponseDto items(Authentication auth) {
        return cartService.getCart(auth);
    }

    @PostMapping("/add")
    public CartResponseDto add(
            Authentication auth,
            @RequestParam String id,
            @RequestParam String nombre,
            // importante: lo recibimos como String para evitar errores de formato
            @RequestParam String precio,
            @RequestParam(name = "img", required = false) String img,
            @RequestParam(name = "servicio", required = false) String servicio,
            @RequestParam(name = "cantidad", defaultValue = "1") int cantidad
    ) {
        return cartService.addItem(auth, id, nombre, precio, img, servicio, cantidad);
    }

    @PostMapping("/remove")
    public CartResponseDto remove(Authentication auth, @RequestParam String id) {
        return cartService.removeOne(auth, id);
    }

    @PostMapping("/clear")
    public CartResponseDto clear(Authentication auth) {
        return cartService.clear(auth);
    }

    @PostMapping("/qty")
    public CartResponseDto changeQty(
            Authentication auth,
            @RequestParam String id,
            @RequestParam int cantidad
    ) {
        return cartService.setCantidad(auth, id, cantidad);
    }
}
