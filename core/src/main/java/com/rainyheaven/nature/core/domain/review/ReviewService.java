package com.rainyheaven.nature.core.domain.review;

import com.rainyheaven.nature.core.domain.item.Item;
import com.rainyheaven.nature.core.domain.item.ItemService;
import com.rainyheaven.nature.core.domain.review.dto.app.ReviewSaveRequestDto;
import com.rainyheaven.nature.core.domain.reviewimage.ReviewImage;
import com.rainyheaven.nature.core.domain.reviewimage.ReviewImageService;
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
    private final UserService userService;
    private final ItemService itemService;

    private final CustomValueBinder valueBinder;


    @Transactional
    public void save(ReviewSaveRequestDto reviewSaveRequestDto, Long userId, Long itemId) {
        User user = userService.findById(userId);
        Item item = itemService.findById(itemId);
        Review review = Review.create(reviewSaveRequestDto, item, user);

        if (!reviewSaveRequestDto.getFiles().isEmpty()) {
            List<ReviewImage> reviewImages = imageUpload(reviewSaveRequestDto.getFiles());
            review.addAllReviewImages(reviewImages);
        }

        reviewRepository.save(review);

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
