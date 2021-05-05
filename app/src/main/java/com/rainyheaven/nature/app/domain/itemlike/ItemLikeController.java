package com.rainyheaven.nature.app.domain.itemlike;

import com.rainyheaven.nature.core.domain.itemlike.ItemLikeService;
import com.rainyheaven.nature.core.domain.user.TokenUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/item-like")
@RequiredArgsConstructor
public class ItemLikeController {

    private final ItemLikeService itemLikeService;

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam List<Long> ids, @AuthenticationPrincipal TokenUser tokenUser) {
        itemLikeService.deleteByIds(ids, tokenUser.getId());
        return ResponseEntity.ok().build();
    }

}
