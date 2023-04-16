package com.bootlabs.springreactivei18n.controller;


import com.bootlabs.springreactivei18n.config.MessageTranslator;
import com.bootlabs.springreactivei18n.dto.BookDTO;
import com.bootlabs.springreactivei18n.dto.SuccessResponseDTO;
import com.bootlabs.springreactivei18n.exception.DataNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Validated
@Slf4j
@RestController
@RequestMapping("/api/book")
public class BookController {


    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<SuccessResponseDTO> createBook(@RequestBody @Valid Mono<BookDTO> bookDTO, ServerWebExchange exchange) {
        return bookDTO
                .map(bookDTO1 -> new SuccessResponseDTO(bookDTO1, MessageTranslator.getMessage("success.save.entity", exchange)));
    }

    @GetMapping(value = "/{id}")
    public Mono<SuccessResponseDTO> getOneBook(@PathVariable("id") Long id, ServerWebExchange exchange) {
        log.debug("REST request to get BookDTO : {}", id);
        if (id > 10){
            throw new DataNotFoundException("error.id.not.found", String.valueOf(id));
        }
        return Mono.just(new SuccessResponseDTO(new BookDTO(id,"Spring WebFlux", "1425IO4K8G", "Spring WebFlux Rest API internationalization i18n", 100, 200), MessageTranslator.getMessage("success.fetch.one", exchange)));
    }

}
