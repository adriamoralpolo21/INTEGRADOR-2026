package com.glovo.app.config;

import com.glovo.app.entity.CategoriaProducto;
import com.glovo.app.entity.Producto;
import com.glovo.app.repository.ProductoRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class ProductSeeder {

    private final ProductoRepository productoRepository;

    @PostConstruct
    public void seedProductos() {

        long count = productoRepository.count();
        System.out.println("🟡 ProductSeeder: productos actuales en BD = " + count);

        if (count > 0) {
            System.out.println("🟡 ProductSeeder: ya hay productos, no inserto nada.");
            return;
        }

        /* ===============================
         *  🟢 FARMACIA
         * =============================== */

        Producto f1 = Producto.builder()
                .nombre("Panadol Antigripal 24 tabletas")
                .descripcion("Alivio de síntomas de resfrío y gripe.")
                .precio(new BigDecimal("19.90"))
                .categoria(CategoriaProducto.FARMACIA)
                .imagenUrl("https://i-cf65.ch-static.com/content/dam/cf-consumer-healthcare/panadol-reskin/es_PE/Reskin/products/Packshot-Nuevo-Panadol-Antigripal.png?auto=format")
                .disponible(true)
                .destacado(true)
                .build();

        Producto f2 = Producto.builder()
                .nombre("La Roche-Posay Hyalu B5 Sérum")
                .descripcion("Sérum facial con ácido hialurónico y vitamina B5 para hidratación intensa.")
                .precio(new BigDecimal("149.90"))
                .categoria(CategoriaProducto.FARMACIA)
                .imagenUrl("https://www.farmaciassanchezantoniolli.com.ar/4269-thickbox_default/serum-hyalu-b5-de-la-roche-posay-con-acido-hialuronico.jpg")
                .disponible(true)
                .destacado(true)
                .build();

        Producto f3 = Producto.builder()
                .nombre("Sulfato Ferroso 300mg Tableta")
                .descripcion("Suplemento de hierro para fortalecer el organismo.")
                .precio(new BigDecimal("23.50"))
                .categoria(CategoriaProducto.FARMACIA)
                .imagenUrl("https://realplaza.vtexassets.com/arquivos/ids/21829457/imageUrl_1.jpg?v=637834656146730000")
                .disponible(true)
                .destacado(false)
                .build();

        Producto f4 = Producto.builder()
                .nombre("Isdin Fusion Water Ultraligero SPF 50+")
                .descripcion("Protector solar ultraligero para uso diario, piel normal o seca.")
                .precio(new BigDecimal("9.90"))
                .categoria(CategoriaProducto.FARMACIA)
                .imagenUrl("https://www.isdin.com/sites/default/files/productos/imagenes/es_thumbnails_22_sept_fusion_fluid_0_a.jpg?v=1663672799")
                .disponible(true)
                .destacado(true)
                .build();

        Producto f5 = Producto.builder()
                .nombre("Hydro Boost Neutrogena")
                .descripcion("Gel hidratante facial con ácido hialurónico para piel fresca y suave.")
                .precio(new BigDecimal("74.90"))
                .categoria(CategoriaProducto.FARMACIA)
                .imagenUrl("https://a-static.mlcdn.com.br/1500x1500/hidratante-facial-neutrogena-hydro-boost-water-gel/epocacosmeticos-integra/21120/7b92bb6fe484d54882e9c1b7abf2efa6.jpg")
                .disponible(true)
                .destacado(true)
                .build();

        Producto f6 = Producto.builder()
                .nombre("Pack Elvive Pure")
                .descripcion("Pack shampoo + acondicionador con ácido hialurónico, nutrición total.")
                .precio(new BigDecimal("34.90"))
                .categoria(CategoriaProducto.FARMACIA)
                .imagenUrl("https://i5.walmartimages.cl/asr/ce321421-d93b-4a6f-8d34-f1084fef1a56.554ef6bf0aa97aaa329dd820d22ff419.jpeg?odnHeight=2000&odnWidth=2000&odnBg=ffffff")
                .disponible(true)
                .destacado(false)
                .build();

        Producto f7 = Producto.builder()
                .nombre("Ensure Advance Vainilla 850g")
                .descripcion("Suplemento nutricional con proteínas, calcio y vitaminas esenciales.")
                .precio(new BigDecimal("79.90"))
                .categoria(CategoriaProducto.FARMACIA)
                .imagenUrl("https://nutrikal.cl/wp-content/uploads/2023/03/Ensure-Advance-sabor-Vainilla-700x700.png")
                .disponible(true)
                .destacado(true)
                .build();

        Producto f8 = Producto.builder()
                .nombre("H&S Limpieza Renovadora 375ml")
                .descripcion("Shampoo Head & Shoulders para control de caspa y limpieza renovadora.")
                .precio(new BigDecimal("19.90"))
                .categoria(CategoriaProducto.FARMACIA)
                .imagenUrl("https://walmartcr.vtexassets.com/arquivos/ids/582272-800-600?v=638478746459670000&width=800&height=600&aspect=true")
                .disponible(true)
                .destacado(false)
                .build();

        Producto f9 = Producto.builder()
                .nombre("Sedal Ceramidas 340ml")
                .descripcion("Acondicionador que fortalece y suaviza el cabello. Uso diario.")
                .precio(new BigDecimal("14.50"))
                .categoria(CategoriaProducto.FARMACIA)
                .imagenUrl("https://th.bing.com/th/id/R.c3272121271fcde4f7ee4b4580430f03?rik=6uy7c6Q%2bxgARZg&pid=ImgRaw&r=0")
                .disponible(true)
                .destacado(false)
                .build();

        Producto f10 = Producto.builder()
                .nombre("Toallas Húmedas Ninet x120")
                .descripcion("Toallas húmedas suaves y resistentes, paquete de 120 unidades.")
                .precio(new BigDecimal("8.50"))
                .categoria(CategoriaProducto.FARMACIA)
                .imagenUrl("https://tse2.mm.bing.net/th/id/OIP.cTVp7z0iDxHG1LZIrVPEgwHaHa?rs=1&pid=ImgDetMain")
                .disponible(true)
                .destacado(false)
                .build();

        /* ===============================
         *  🟠 SUPERMERCADO
         * =============================== */

        Producto s1 = Producto.builder()
                .nombre("Pack Gaseosa Coca + Inca 1.5L")
                .descripcion("Pack de gaseosas Coca-Cola + Inca Kola sin azúcar, botellas 1.5L.")
                .precio(new BigDecimal("9.90"))
                .categoria(CategoriaProducto.SUPERMERCADO)
                .imagenUrl("https://metroio.vtexassets.com/arquivos/ids/547076-800-auto?v=638633469995970000&width=800&height=auto&aspect=true")
                .disponible(true)
                .destacado(true)
                .build();

        Producto s2 = Producto.builder()
                .nombre("Plátanos de Seda (kg)")
                .descripcion("Plátanos frescos de seda, calidad extra, precio por kilogramo.")
                .precio(new BigDecimal("3.90"))
                .categoria(CategoriaProducto.SUPERMERCADO)
                .imagenUrl("https://th.bing.com/th/id/R.711f577426343b3ab91853e65f2e14ae?rik=67zu0bBm3a5LdA&riu=http%3a%2f%2fwww.malatintamagazine.com%2fwp-content%2fuploads%2f2016%2f04%2fBanana6.png&ehk=dFifsvXLbQaUj6oYygHA2eo306OCHprxhwVw71Sbg20%3d&risl=&pid=ImgRaw&r=0")
                .disponible(true)
                .destacado(false)
                .build();

        Producto s3 = Producto.builder()
                .nombre("Papel Higiénico Suave pack x12")
                .descripcion("Papel higiénico doble hoja, pack de 12 rollos.")
                .precio(new BigDecimal("16.50"))
                .categoria(CategoriaProducto.SUPERMERCADO)
                .imagenUrl("https://plazavea.vteximg.com.br/arquivos/ids/192244-1000-1000/927501.jpg?v=636444689135700000")
                .disponible(true)
                .destacado(false)
                .build();

        Producto s4 = Producto.builder()
                .nombre("Detergente Marsella 1L")
                .descripcion("Detergente líquido Marsella, fragancia clásica, botella 1L.")
                .precio(new BigDecimal("10.90"))
                .categoria(CategoriaProducto.SUPERMERCADO)
                .imagenUrl("https://images.contentstack.io/v3/assets/blt5e6c562c7d14bc51/bltf21f03322d0a5846/66501561e2c16b15c59bf4b9/MB-MARSELLA.jpg")
                .disponible(true)
                .destacado(false)
                .build();

        Producto s5 = Producto.builder()
                .nombre("Detergente Ayudín 1L")
                .descripcion("Detergente líquido Ayudín, limpieza efectiva, botella 1L.")
                .precio(new BigDecimal("8.20"))
                .categoria(CategoriaProducto.SUPERMERCADO)
                .imagenUrl("https://tse1.mm.bing.net/th/id/OIP.Sb6Rr01MRuRRleisQU9cRQHaHa?cb=12ucfimg=1&rs=1&pid=ImgDetMain&o=7&rm=3")
                .disponible(true)
                .destacado(false)
                .build();

        Producto s6 = Producto.builder()
                .nombre("Pack Leche Gloria x6 (400g)")
                .descripcion("Pack de 6 latas de leche evaporada Gloria de 400g.")
                .precio(new BigDecimal("19.90"))
                .categoria(CategoriaProducto.SUPERMERCADO)
                .imagenUrl("https://http2.mlstatic.com/D_NQ_NP_895590-MPE71264967842_082023-O.webp")
                .disponible(true)
                .destacado(true)
                .build();

        Producto s7 = Producto.builder()
                .nombre("Leche Chocolatada Laive Sixpack 170mL")
                .descripcion("Pack de 6 unidades de leche chocolatada Laive de 170 mL.")
                .precio(new BigDecimal("10.20"))
                .categoria(CategoriaProducto.SUPERMERCADO)
                .imagenUrl("https://imagedelivery.net/4fYuQyy-r8_rpBpcY7lH_A/tottusPE/43314215_1/public")
                .disponible(true)
                .destacado(false)
                .build();

        Producto s8 = Producto.builder()
                .nombre("Torta de Chocolate Clásica 1kg")
                .descripcion("Torta húmeda de chocolate con cobertura, 1kg.")
                .precio(new BigDecimal("36.90"))
                .categoria(CategoriaProducto.SUPERMERCADO)
                .imagenUrl("https://media.tottus.com.pe/tottusPE/42096619_1/width=240,height=240,quality=70,format=webp,fit=pad")
                .disponible(true)
                .destacado(true)
                .build();

        Producto s9 = Producto.builder()
                .nombre("Torta Helada Fresa & Vainilla 1kg")
                .descripcion("Torta helada de fresa y vainilla con capas de gelatina.")
                .precio(new BigDecimal("34.50"))
                .categoria(CategoriaProducto.SUPERMERCADO)
                .imagenUrl("https://images.rappi.pe/products/de6eebdd-7c6c-4950-8e51-c9abdada22d8.jpg")
                .disponible(true)
                .destacado(false)
                .build();

        Producto s10 = Producto.builder()
                .nombre("Tripack Filete de Atún Primor (3x140g)")
                .descripcion("Pack de 3 latas de filete de atún Primor de 140g cada una.")
                .precio(new BigDecimal("14.50"))
                .categoria(CategoriaProducto.SUPERMERCADO)
                .imagenUrl("https://metroio.vtexassets.com/arquivos/ids/576974/X3-FILETE-ATUN-PRIMOR-140GR-48LTA-1-291535.jpg?v=638778409687730000")
                .disponible(true)
                .destacado(false)
                .build();

        Producto s11 = Producto.builder()
                .nombre("Arroz Blanco Faraón 5kg")
                .descripcion("Arroz blanco superior Faraón, bolsa de 5kg, granos seleccionados.")
                .precio(new BigDecimal("25.50"))
                .categoria(CategoriaProducto.SUPERMERCADO)
                .imagenUrl("https://vegaperu.vtexassets.com/arquivos/ids/155610/7754294000071.jpg?v=637612900469830000")
                .disponible(true)
                .destacado(true)
                .build();

        Producto s12 = Producto.builder()
                .nombre("Helado de Crema Choco Chips 1L")
                .descripcion("Helado sabor vainilla con trozos de chocolate, envase 1L.")
                .precio(new BigDecimal("14.50"))
                .categoria(CategoriaProducto.SUPERMERCADO)
                .imagenUrl("https://metroio.vtexassets.com/arquivos/ids/522731/299433003-01-18575.jpg?v=638495878469800000")
                .disponible(true)
                .destacado(false)
                .build();

        Producto s13 = Producto.builder()
                .nombre("Mayonesa Alacena Doypack 475g")
                .descripcion("Mayonesa Alacena en presentación doypack de 475 g.")
                .precio(new BigDecimal("7.90"))
                .categoria(CategoriaProducto.SUPERMERCADO)
                .imagenUrl("https://jumbo.vteximg.com.br/arquivos/ids/778130/Mayonesa-500-g.jpg?v=638411328177500000")
                .disponible(true)
                .destacado(false)
                .build();

        Producto s14 = Producto.builder()
                .nombre("Pollo Entero (por kg)")
                .descripcion("Pollo entero fresco con menudencia, ideal para horno o parrilla.")
                .precio(new BigDecimal("25.50"))
                .categoria(CategoriaProducto.SUPERMERCADO)
                .imagenUrl("https://laconstanciacarniceria.com/wp-content/uploads/2021/02/1111.jpg")
                .disponible(true)
                .destacado(false)
                .build();

        /* ===============================
         *  🍔 COMIDA (Restaurantes)
         * =============================== */

        // BEMBOS
        Producto c1 = Producto.builder()
                .nombre("Bembos - Hamburguesa Clásica")
                .descripcion("Carne 100% res, lechuga, tomate y salsa de la casa.")
                .precio(new BigDecimal("18.90"))
                .categoria(CategoriaProducto.COMIDA)
                .imagenUrl("https://images.unsplash.com/photo-1550547660-d9450f859349?q=80&w=800&auto=format&fit=crop")
                .disponible(true)
                .destacado(true)
                .build();

        Producto c2 = Producto.builder()
                .nombre("Bembos - Cheeseburger Doble")
                .descripcion("Doble queso, doble carne y pan brioche.")
                .precio(new BigDecimal("24.50"))
                .categoria(CategoriaProducto.COMIDA)
                .imagenUrl("https://images.unsplash.com/photo-1568901346375-23c9450c58cd?q=80&w=800&auto=format&fit=crop")
                .disponible(true)
                .destacado(false)
                .build();

        Producto c3 = Producto.builder()
                .nombre("Bembos - Papas Clásicas")
                .descripcion("Papas crocantes y doradas.")
                .precio(new BigDecimal("9.90"))
                .categoria(CategoriaProducto.COMIDA)
                .imagenUrl("https://images.unsplash.com/photo-1541592106381-b31e9677c0e5?q=80&w=800&auto=format&fit=crop")
                .disponible(true)
                .destacado(false)
                .build();

        // KFC
        Producto c4 = Producto.builder()
                .nombre("KFC - Bucket 8 presas")
                .descripcion("Pollo frito crujiente receta original.")
                .precio(new BigDecimal("48.90"))
                .categoria(CategoriaProducto.COMIDA)
                .imagenUrl("https://tse2.mm.bing.net/th/id/OIP.k1T7jBI18hfop_61WyfzBQAAAA?rs=1&pid=ImgDetMain")
                .disponible(true)
                .destacado(true)
                .build();

        Producto c5 = Producto.builder()
                .nombre("KFC - Sandwich Crispy")
                .descripcion("Filete crujiente con mayonesa y pepinillos.")
                .precio(new BigDecimal("16.90"))
                .categoria(CategoriaProducto.COMIDA)
                .imagenUrl("https://tse1.mm.bing.net/th/id/OIP.O5B0a1DrwmcDswnTed88IwHaHa?rs=1&pid=ImgDetMain")
                .disponible(true)
                .destacado(false)
                .build();

        Producto c6 = Producto.builder()
                .nombre("KFC - Papas con gravy")
                .descripcion("Clásicas papas con salsa gravy.")
                .precio(new BigDecimal("7.90"))
                .categoria(CategoriaProducto.COMIDA)
                .imagenUrl("https://static.vecteezy.com/system/resources/previews/025/065/142/non_2x/french-fries-with-ai-generated-free-png.png")
                .disponible(true)
                .destacado(false)
                .build();

        // PIZZA HUT
        Producto c7 = Producto.builder()
                .nombre("Pizza Hut - Pizza Pepperoni")
                .descripcion("Pepperoni, queso mozzarella y salsa de tomate.")
                .precio(new BigDecimal("35.00"))
                .categoria(CategoriaProducto.COMIDA)
                .imagenUrl("https://tse2.mm.bing.net/th/id/OIP.hQO4pTb_XhWo1rWAfsSowgHaE8?rs=1&pid=ImgDetMain")
                .disponible(true)
                .destacado(true)
                .build();

        Producto c8 = Producto.builder()
                .nombre("Pizza Hut - Pizza Hawaiana")
                .descripcion("Jamón, piña y queso mozzarella.")
                .precio(new BigDecimal("32.50"))
                .categoria(CategoriaProducto.COMIDA)
                .imagenUrl("https://nuestrasrecetas.es/wp-content/uploads/2015/05/hawaiana-1024x683.jpg")
                .disponible(true)
                .destacado(false)
                .build();

        Producto c9 = Producto.builder()
                .nombre("Pizza Hut - Alitas BBQ")
                .descripcion("Alitas bañadas en salsa BBQ.")
                .precio(new BigDecimal("18.00"))
                .categoria(CategoriaProducto.COMIDA)
                .imagenUrl("https://assets.unileversolutions.com/recipes-v2/237633.jpg?imwidth=1200")
                .disponible(true)
                .destacado(false)
                .build();

        // CHIFA WONG
        Producto c10 = Producto.builder()
                .nombre("Chifa Wong - Arroz Chaufa Especial")
                .descripcion("Arroz frito con pollo, cerdo, huevo y verduras.")
                .precio(new BigDecimal("22.00"))
                .categoria(CategoriaProducto.COMIDA)
                .imagenUrl("https://tse2.mm.bing.net/th/id/OIP.nyQkQsVuXG-nuuxYRWLhLgHaE8?rs=1&pid=ImgDetMain")
                .disponible(true)
                .destacado(true)
                .build();

        Producto c11 = Producto.builder()
                .nombre("Chifa Wong - Tallarines Saltados")
                .descripcion("Tallarines salteados con carne y verduras.")
                .precio(new BigDecimal("20.00"))
                .categoria(CategoriaProducto.COMIDA)
                .imagenUrl("https://th.bing.com/th/id/R.35d1ccaba3f3d966d0759f9722b79acd?rik=bPKIk2eX4CcMQw&pid=ImgRaw&r=0")
                .disponible(true)
                .destacado(false)
                .build();

        // ROKY'S
        Producto c12 = Producto.builder()
                .nombre("Rokys - Pollo a la Brasa Entero")
                .descripcion("Pollo sazonado y asado a la perfección.")
                .precio(new BigDecimal("45.00"))
                .categoria(CategoriaProducto.COMIDA)
                .imagenUrl("https://blog.redbus.pe/wp-content/uploads/2017/12/pollo-a-la-brasa.jpg")
                .disponible(true)
                .destacado(true)
                .build();

        Producto c13 = Producto.builder()
                .nombre("Rokys - Media Pollo a la Brasa")
                .descripcion("Media porción de nuestro famoso pollo.")
                .precio(new BigDecimal("25.00"))
                .categoria(CategoriaProducto.COMIDA)
                .imagenUrl("https://tse4.mm.bing.net/th/id/OIP.JvXSl8qvJV_WmoyyNwjDAAHaHa?rs=1&pid=ImgDetMain")
                .disponible(true)
                .destacado(false)
                .build();

        // STARBUCKS
        Producto c14 = Producto.builder()
                .nombre("Starbucks - Café Latte")
                .descripcion("Espresso con leche vaporizada.")
                .precio(new BigDecimal("12.00"))
                .categoria(CategoriaProducto.COMIDA)
                .imagenUrl("https://th.bing.com/th/id/R.e8b6f6c7304cdc1ea66851cf0f0870e4?rik=UsC%2fvXXOt9bYcQ&pid=ImgRaw&r=0")
                .disponible(true)
                .destacado(false)
                .build();

        Producto c15 = Producto.builder()
                .nombre("Starbucks - Frappuccino de Mocha")
                .descripcion("Bebida fría con café, chocolate y crema batida.")
                .precio(new BigDecimal("15.00"))
                .categoria(CategoriaProducto.COMIDA)
                .imagenUrl("https://www.cleaneatingkitchen.com/wp-content/uploads/2023/06/Iced-Mocha-Vertical-Hero.jpg")
                .disponible(true)
                .destacado(true)
                .build();

        Producto c16 = Producto.builder()
                .nombre("Starbucks - Muffin de Arándanos")
                .descripcion("Muffin esponjoso con arándanos frescos.")
                .precio(new BigDecimal("8.00"))
                .categoria(CategoriaProducto.COMIDA)
                .imagenUrl("https://www.splenda.com/wp-content/themes/bistrotheme/assets/recipe-images/blueberry-muffins.jpg")
                .disponible(true)
                .destacado(false)
                .build();

        /* ===============================
         *  🛍️ TIENDAS (Retail)
         * =============================== */

        // Fashion Style
        Producto t1 = Producto.builder()
                .nombre("Fashion Style - Polera oversize unisex")
                .descripcion("Algodón peruano, fit relajado, ideal para uso diario.")
                .precio(new BigDecimal("89.90"))
                .categoria(CategoriaProducto.TIENDA)
                .imagenUrl("https://i.pinimg.com/1200x/b5/ae/38/b5ae388d1adb936db136b95691b905d2.jpg")
                .disponible(true)
                .destacado(true)
                .build();

        Producto t2 = Producto.builder()
                .nombre("Fashion Style - Jean baggy negro")
                .descripcion("Tela stretch, tiro medio, cómodo y resistente.")
                .precio(new BigDecimal("119.90"))
                .categoria(CategoriaProducto.TIENDA)
                .imagenUrl("https://i.pinimg.com/1200x/3d/ad/63/3dad63847e19e93c3e9d13a763d0db3c.jpg")
                .disponible(true)
                .destacado(false)
                .build();

        Producto t3 = Producto.builder()
                .nombre("Fashion Style - Casaca denim clásica")
                .descripcion("Casaca jean para combinar con cualquier outfit.")
                .precio(new BigDecimal("159.90"))
                .categoria(CategoriaProducto.TIENDA)
                .imagenUrl("https://i.pinimg.com/1200x/92/04/c5/9204c5e8992053391d836c7aeae4bd70.jpg")
                .disponible(true)
                .destacado(false)
                .build();

        // Zapatería El Kraken
        Producto t4 = Producto.builder()
                .nombre("Zapatería El Kraken - Zapatillas urbanas")
                .descripcion("Suela antideslizante y plantilla acolchada.")
                .precio(new BigDecimal("189.90"))
                .categoria(CategoriaProducto.TIENDA)
                .imagenUrl("https://i.pinimg.com/1200x/ab/b6/21/abb6215854e2614c82a8a316b7b871be.jpg")
                .disponible(true)
                .destacado(true)
                .build();

        Producto t5 = Producto.builder()
                .nombre("Zapatería El Kraken - Zapatos de vestir")
                .descripcion("Cuero sintético, perfectos para eventos formales.")
                .precio(new BigDecimal("209.90"))
                .categoria(CategoriaProducto.TIENDA)
                .imagenUrl("https://i.pinimg.com/1200x/64/e8/ac/64e8ac0a628fff6549a8ef8249a676af.jpg")
                .disponible(true)
                .destacado(false)
                .build();

        Producto t6 = Producto.builder()
                .nombre("Zapatería El Kraken - Sandalias casuales")
                .descripcion("Ideales para el verano, cómodas y ligeras.")
                .precio(new BigDecimal("79.90"))
                .categoria(CategoriaProducto.TIENDA)
                .imagenUrl("https://i.pinimg.com/1200x/49/73/1b/49731bd87a5456f28970e0b62b27d149.jpg")
                .disponible(true)
                .destacado(false)
                .build();

        // Coolbox
        Producto t7 = Producto.builder()
                .nombre("Coolbox - Smartphone gama media")
                .descripcion("128GB de almacenamiento y cámara dual.")
                .precio(new BigDecimal("899.00"))
                .categoria(CategoriaProducto.TIENDA)
                .imagenUrl("https://i.pinimg.com/736x/0d/76/9a/0d769a1f4450001415b60c814bb754a0.jpg")
                .disponible(true)
                .destacado(true)
                .build();

        Producto t8 = Producto.builder()
                .nombre("Coolbox - Audífonos inalámbricos")
                .descripcion("Con cancelación de ruido y estuche de carga.")
                .precio(new BigDecimal("149.00"))
                .categoria(CategoriaProducto.TIENDA)
                .imagenUrl("https://i.pinimg.com/736x/12/2d/9c/122d9cdd1ff678510621b9a0c10c82ff.jpg")
                .disponible(true)
                .destacado(false)
                .build();

        Producto t9 = Producto.builder()
                .nombre("Coolbox - Laptop para estudiantes")
                .descripcion("Pantalla 14\", 8GB RAM, SSD de 256GB.")
                .precio(new BigDecimal("1999.00"))
                .categoria(CategoriaProducto.TIENDA)
                .imagenUrl("https://i.pinimg.com/736x/69/96/8a/69968a4397724231650c21084e06096f.jpg")
                .disponible(true)
                .destacado(false)
                .build();

        // Mundo de Papel
        Producto t10 = Producto.builder()
                .nombre("Mundo de Papel - Cuaderno universitario")
                .descripcion("100 hojas cuadriculadas, tapa dura.")
                .precio(new BigDecimal("12.90"))
                .categoria(CategoriaProducto.TIENDA)
                .imagenUrl("https://i.pinimg.com/1200x/5a/7e/e5/5a7ee5ab6904d63bfa95edc134f55eec.jpg")
                .disponible(true)
                .destacado(false)
                .build();

        Producto t11 = Producto.builder()
                .nombre("Mundo de Papel - Pack de lapiceros")
                .descripcion("Tintas azul, negro y rojo, escritura suave.")
                .precio(new BigDecimal("9.90"))
                .categoria(CategoriaProducto.TIENDA)
                .imagenUrl("https://i.pinimg.com/736x/24/a9/01/24a9017bfa25319de61bfe681b7d90a4.jpg")
                .disponible(true)
                .destacado(false)
                .build();

        Producto t12 = Producto.builder()
                .nombre("Mundo de Papel - Agenda 2026")
                .descripcion("Agenda semanal con separadores y stickers.")
                .precio(new BigDecimal("29.90"))
                .categoria(CategoriaProducto.TIENDA)
                .imagenUrl("https://i.pinimg.com/736x/17/c7/c2/17c7c2c55576d323ec83811744b4407b.jpg")
                .disponible(true)
                .destacado(true)
                .build();

        // Joyería Brillos
        Producto t13 = Producto.builder()
                .nombre("Joyería Brillos - Cadena de plata 925")
                .descripcion("Cadena fina, ideal para uso diario.")
                .precio(new BigDecimal("149.90"))
                .categoria(CategoriaProducto.TIENDA)
                .imagenUrl("https://i.pinimg.com/736x/a1/c3/e2/a1c3e2a09c0f067dad744f9cacd72bd2.jpg")
                .disponible(true)
                .destacado(true)
                .build();

        Producto t14 = Producto.builder()
                .nombre("Joyería Brillos - Anillo de compromiso")
                .descripcion("Baño en plata con zirconia brillante.")
                .precio(new BigDecimal("399.90"))
                .categoria(CategoriaProducto.TIENDA)
                .imagenUrl("https://i.pinimg.com/736x/ed/ea/b5/edeab551f52670268d6398d1db8ca71a.jpg")
                .disponible(true)
                .destacado(false)
                .build();

        Producto t15 = Producto.builder()
                .nombre("Joyería Brillos - Pulsera minimalista")
                .descripcion("Diseño delicado y elegante.")
                .precio(new BigDecimal("89.90"))
                .categoria(CategoriaProducto.TIENDA)
                .imagenUrl("https://i.pinimg.com/1200x/f4/2d/f5/f42df51349509390c96101aece07c7f7.jpg")
                .disponible(true)
                .destacado(false)
                .build();

        // SportLife
        Producto t16 = Producto.builder()
                .nombre("SportLife - Polo deportivo dry-fit")
                .descripcion("Tela respirable para entrenamientos intensos.")
                .precio(new BigDecimal("69.90"))
                .categoria(CategoriaProducto.TIENDA)
                .imagenUrl("https://i.pinimg.com/736x/40/18/ae/4018aea74d95c67b8dac05ff0a9bd4a1.jpg")
                .disponible(true)
                .destacado(false)
                .build();

        Producto t17 = Producto.builder()
                .nombre("SportLife - Short de running")
                .descripcion("Ligero, con bolsillo oculto para llaves.")
                .precio(new BigDecimal("59.90"))
                .categoria(CategoriaProducto.TIENDA)
                .imagenUrl("https://i.pinimg.com/1200x/bd/fc/c4/bdfcc4d98a9ea880b6ffdcfa8890ab69.jpg")
                .disponible(true)
                .destacado(false)
                .build();

        Producto t18 = Producto.builder()
                .nombre("SportLife - Tomatodo deportivo")
                .descripcion("700ml, libre de BPA, ideal para gimnasio.")
                .precio(new BigDecimal("39.90"))
                .categoria(CategoriaProducto.TIENDA)
                .imagenUrl("https://i.pinimg.com/1200x/f6/74/99/f67499c04e4ac0c53eab4111e7293089.jpg")
                .disponible(true)
                .destacado(true)
                .build();

        // ====== GUARDAR TODOS ======

        productoRepository.save(f1);  productoRepository.save(f2);
        productoRepository.save(f3);  productoRepository.save(f4);
        productoRepository.save(f5);  productoRepository.save(f6);
        productoRepository.save(f7);  productoRepository.save(f8);
        productoRepository.save(f9);  productoRepository.save(f10);

        productoRepository.save(s1);  productoRepository.save(s2);
        productoRepository.save(s3);  productoRepository.save(s4);
        productoRepository.save(s5);  productoRepository.save(s6);
        productoRepository.save(s7);  productoRepository.save(s8);
        productoRepository.save(s9);  productoRepository.save(s10);
        productoRepository.save(s11); productoRepository.save(s12);
        productoRepository.save(s13); productoRepository.save(s14);

        productoRepository.save(c1);  productoRepository.save(c2);
        productoRepository.save(c3);  productoRepository.save(c4);
        productoRepository.save(c5);  productoRepository.save(c6);
        productoRepository.save(c7);  productoRepository.save(c8);
        productoRepository.save(c9);  productoRepository.save(c10);
        productoRepository.save(c11); productoRepository.save(c12);
        productoRepository.save(c13); productoRepository.save(c14);
        productoRepository.save(c15); productoRepository.save(c16);

        productoRepository.save(t1);  productoRepository.save(t2);
        productoRepository.save(t3);  productoRepository.save(t4);
        productoRepository.save(t5);  productoRepository.save(t6);
        productoRepository.save(t7);  productoRepository.save(t8);
        productoRepository.save(t9);  productoRepository.save(t10);
        productoRepository.save(t11); productoRepository.save(t12);
        productoRepository.save(t13); productoRepository.save(t14);
        productoRepository.save(t15); productoRepository.save(t16);
        productoRepository.save(t17); productoRepository.save(t18);

        System.out.println("✔ Productos de FARMACIA, SUPERMERCADO, COMIDA y TIENDAS creados en la BD.");
    }
}