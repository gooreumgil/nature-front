package com.rainyheaven.nature.core.domain.review;

import com.rainyheaven.nature.core.domain.item.Item;
import com.rainyheaven.nature.core.domain.item.ItemService;
import com.rainyheaven.nature.core.domain.orderitem.OrderItem;
import com.rainyheaven.nature.core.domain.orderitem.OrderItemService;
import com.rainyheaven.nature.core.domain.review.dto.app.ReviewSaveRequestDto;
import com.rainyheaven.nature.core.domain.reviewimage.ReviewImage;
import com.rainyheaven.nature.core.domain.reviewimage.ReviewImageService;
import com.rainyheaven.nature.core.domain.reviewlike.ReviewLike;
import com.rainyheaven.nature.core.domain.reviewlike.ReviewLikeService;
import com.rainyheaven.nature.core.domain.user.User;
import com.rainyheaven.nature.core.domain.user.UserService;
import com.rainyheaven.nature.core.valuebinder.CustomValueBinder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewImageService reviewImageService;
    private final ReviewLikeService reviewLikeService;
    private final UserService userService;
    private final ItemService itemService;
    private final OrderItemService orderItemService;

    private final CustomValueBinder valueBinder;

    public Review findById(Long id) {
        return reviewRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @Transactional
    public void save(ReviewSaveRequestDto reviewSaveRequestDto, Long userId, Long itemId, Long orderItemId) {
        User user = userService.findById(userId);
        Item item = itemService.findById(itemId);
        Review review = Review.create(reviewSaveRequestDto, item, user);

        if (!reviewSaveRequestDto.getFiles().isEmpty()) {
            List<ReviewImage> reviewImages = imageUpload(reviewSaveRequestDto.getFiles());
            review.addAllReviewImages(reviewImages);
        }

        OrderItem orderItem = orderItemService.findById(orderItemId);
        if (orderItem.isLeaveReview()) {
            throw new RuntimeException("이미 리뷰를 작성하셨습니다.");
        }
        boolean containsFile = false;
        if (!reviewSaveRequestDto.getFiles().isEmpty()) containsFile = true;

        int bonusPoint = getBonusPoint(orderItem, containsFile);
        user.plusPoints(bonusPoint);

        orderItem.setLeaveReview(true);
        reviewRepository.save(review);

    }

    private int getBonusPoint(OrderItem orderItem, boolean containsFile) {
        int resultPrice = orderItem.getResultPrice();
        double rate = 0.01;
        if (containsFile) rate = 0.012;

        return Math.toIntExact(Math.round(resultPrice * rate));
    }

    public Page<Review> getPageByUser(Long userId, Pageable pageable) {
        return reviewRepository.findAllByUserIdWithItem(userId, pageable);

    }

    public Page<Review> getPageByItem(Long itemId, Pageable pageable) {
        return reviewRepository.findAllByItemIdWithUser(itemId, pageable);
    }

    public int getTotalByUser(Long userId) {

        return reviewRepository.countAllByUserId(userId);
    }

    public int getTotalByItem(Long itemId) {
        return reviewRepository.countAllByItemId(itemId);
    }

    @Transactional
    public void addLike(Long id, Long userId) {
        Review review = findById(id);
        User user = userService.findById(userId);
        boolean exist = reviewLikeService.existByReviewAndUser(review.getId(), user.getId());

        // 해당 글에 해당 유저가 좋아요를 했다면 에러
        if (exist) {
            throw new RuntimeException("이미 좋아요한 글입니다.");
        }
        ReviewLike.create(review, user);

    }

    @Transactional
    public void deleteLike(Long id, Long userId) {
        Review review = findById(id);
        reviewLikeService.deleteByReviewAndUser(id, userId);
        review.minusLikesCount();
    }

    private List<ReviewImage> imageUpload(List<MultipartFile> letterImages) {

        List<ReviewImage> reviewImages = new ArrayList<>();

        for (MultipartFile file : letterImages) {
            try {
                BufferedImage read = ImageIO.read(file.getInputStream());
                String originalFilename = file.getOriginalFilename();

                String imageType = null;

                int pos = originalFilename.lastIndexOf(".");
                String ext = originalFilename.substring(pos + 1).toLowerCase();

                if (ext.contains("png")) {
                    imageType = "png";
                } else if (ext.contains("jpg") || ext.contains("jpeg")) {
                    imageType = "jpg";
                }

                ImageIO.write(read, imageType, new File(valueBinder.getUploadPath() + file.getOriginalFilename()));
                File writeFile = new File(valueBinder.getUploadPath() + file.getOriginalFilename());

                UUID uuid = UUID.randomUUID();
                ReviewImage reviewImage = reviewImageService.uploadImage(writeFile, uuid);
                reviewImages.add(reviewImage);

                writeFile.delete();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return reviewImages;
    }

}
