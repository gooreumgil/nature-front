package com.rainyheaven.nature.core.domain.categoryitem;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryItemService {

    private final CategoryItemRepository categoryItemRepository;

}
