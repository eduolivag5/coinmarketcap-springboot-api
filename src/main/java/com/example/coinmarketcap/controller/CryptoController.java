package com.example.coinmarketcap.controller;

import com.example.coinmarketcap.service.CryptoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class CryptoController {

    private final CryptoService cryptoService;

    public CryptoController(CryptoService cryptoService) {
        this.cryptoService = cryptoService;
    }

    // --- CATEGORÍA: CRIPTOMONEDAS ---

    @Tag(name = "Criptomonedas", description = "Consultas sobre precios, listados y metadatos de tokens")
    @Operation(summary = "Listado general", description = "Obtiene el ranking de criptomonedas por capitalización de mercado.")
    @GetMapping("/listings")
    public ResponseEntity<Object> getListings(
            @RequestParam(defaultValue = "1") String start,
            @RequestParam(defaultValue = "100") String limit,
            @RequestParam(defaultValue = "USD") String convert) {
        return cryptoService.callCmc("/v1/cryptocurrency/listings/latest",
                Map.of("start", start, "limit", limit, "convert", convert));
    }

    @Tag(name = "Criptomonedas")
    @Operation(summary = "Info por ID", description = "Metadatos detallados usando el ID interno.")
    @GetMapping("/crypto/info/id")
    public ResponseEntity<Object> getInfoById(@RequestParam String id) {
        return cryptoService.callCmc("/v2/cryptocurrency/info", Map.of("id", id));
    }

    @Tag(name = "Criptomonedas")
    @Operation(summary = "Cotizaciones por ID")
    @GetMapping("/crypto/quotes/id")
    public ResponseEntity<Object> getQuotesById(@RequestParam String id) {
        return cryptoService.callCmc("/v2/cryptocurrency/quotes/latest", Map.of("id", id));
    }

    @Tag(name = "Criptomonedas")
    @Operation(summary = "Mapa de símbolos", description = "Mapea Tickers (BTC) a IDs de CoinMarketCap.")
    @GetMapping("/crypto/map")
    public ResponseEntity<Object> getCryptoMap(@RequestParam String symbol) {
        return cryptoService.callCmc("/v1/cryptocurrency/map",
                Map.of("listing_status", "active", "symbol", symbol));
    }

    // --- CATEGORÍA: CATEGORÍAS (SECTORES) ---

    @Tag(name = "Sectores y Categorías", description = "Agrupaciones por ecosistemas (AI, DeFi, Memes...)")
    @Operation(summary = "Listar todas las categorías")
    @GetMapping("/categories")
    public ResponseEntity<Object> getCategories() {
        return cryptoService.callCmc("/v1/cryptocurrency/categories", null);
    }

    @Tag(name = "Sectores y Categorías")
    @Operation(summary = "Detalles de una categoría")
    @GetMapping("/categories/details")
    public ResponseEntity<Object> getCategoryDetails(@RequestParam String id) {
        return cryptoService.callCmc("/v1/cryptocurrency/category", Map.of("id", id));
    }

    // --- CATEGORÍA: EXCHANGES ---

    @Tag(name = "Exchanges", description = "Información sobre casas de intercambio")
    @Operation(summary = "Info de Exchange por Slug")
    @GetMapping("/exchange/info")
    public ResponseEntity<Object> getExchangeInfo(@RequestParam String slug) {
        return cryptoService.callCmc("/v1/exchange/info", Map.of("slug", slug));
    }

    @Tag(name = "Exchanges")
    @Operation(summary = "Activos en Exchange")
    @GetMapping("/exchange/assets")
    public ResponseEntity<Object> getExchangeAssets(@RequestParam String id) {
        return cryptoService.callCmc("/v1/exchange/assets", Map.of("id", id));
    }

    // --- CATEGORÍA: MÉTRICAS GLOBALES ---

    @Tag(name = "Métricas Globales", description = "Datos generales del mercado y sentimiento")
    @Operation(summary = "Índice de Miedo y Codicia")
    @GetMapping("/fear-and-greed")
    public ResponseEntity<Object> getFearAndGreed() {
        return cryptoService.callCmc("/v3/fear-and-greed/latest", null);
    }

    @Tag(name = "Métricas Globales")
    @Operation(summary = "Métricas globales del mercado")
    @GetMapping("/global-metrics")
    public ResponseEntity<Object> getGlobalMetrics() {
        return cryptoService.callCmc("/v1/global-metrics/quotes/latest", null);
    }
}