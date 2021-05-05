package com.rainyheaven.nature.app.domain.category;

import com.rainyheaven.nature.core.domain.category.CategoryService;
import com.rainyheaven.nature.core.domain.category.dto.app.CategoryResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> findAll() {
        return ResponseEntity.ok(
                categoryService.findAll()
                        .stream()
                        .map(category -> new CategoryResponseDto(category.getId(), category.getName()))
                        .collect(Collectors.toList())
        );
    }

}
