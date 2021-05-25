CREATE TABLE `users` (
                         `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
                         `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                         `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                         `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                         `points` int(11) DEFAULT NULL,
                         `user_role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                         `user_status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                         `day` int(11) DEFAULT NULL,
                         `month` int(11) DEFAULT NULL,
                         `year` int(11) DEFAULT NULL,
                         `phone_num1` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                         `phone_num2` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                         `phone_num3` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                         `created_date` datetime(6) DEFAULT NULL,
                         `last_modified_date` datetime(6) DEFAULT NULL,
                         PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8

CREATE TABLE `address` (
                           `address_id` bigint(20) NOT NULL AUTO_INCREMENT,
                           `detail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                           `main` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                           `zip_code` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                           `user_id` bigint(20) DEFAULT NULL,
                           `is_default` bit(1) NOT NULL,
                           `created_date` datetime(6) DEFAULT NULL,
                           `last_modified_date` datetime(6) DEFAULT NULL,
                           PRIMARY KEY (`address_id`),
                           KEY `FK6i66ijb8twgcqtetl8eeeed6v` (`user_id`),
                           CONSTRAINT `FK6i66ijb8twgcqtetl8eeeed6v` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8

CREATE TABLE `email_verify` (
                                `email_verify_id` bigint(20) NOT NULL AUTO_INCREMENT,
                                `created_date` datetime(6) DEFAULT NULL,
                                `last_modified_date` datetime(6) DEFAULT NULL,
                                `accepted` bit(1) NOT NULL,
                                `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                                `expired_time` datetime(6) DEFAULT NULL,
                                `verify_num` int(11) DEFAULT NULL,
                                PRIMARY KEY (`email_verify_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8

CREATE TABLE `item` (
                        `item_id` bigint(20) NOT NULL AUTO_INCREMENT,
                        `capacity` int(11) NOT NULL,
                        `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                        `discount_price` int(11) NOT NULL,
                        `likes_count` int(11) NOT NULL,
                        `name_eng` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                        `name_kor` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                        `price` int(11) NOT NULL,
                        `stock_quantity` int(11) NOT NULL,
                        `save_points` int(11) NOT NULL,
                        `created_date` datetime(6) DEFAULT NULL,
                        `last_modified_date` datetime(6) DEFAULT NULL,
                        `main_img_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                        `sell_total` int(11) NOT NULL,
                        PRIMARY KEY (`item_id`)
) ENGINE=InnoDB AUTO_INCREMENT=116 DEFAULT CHARSET=utf8

CREATE TABLE `item_image` (
                              `item_image_id` bigint(20) NOT NULL AUTO_INCREMENT,
                              `img_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                              `s3key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                              `item_id` bigint(20) DEFAULT NULL,
                              `created_date` datetime(6) DEFAULT NULL,
                              `last_modified_date` datetime(6) DEFAULT NULL,
                              PRIMARY KEY (`item_image_id`),
                              KEY `FKgh2dw7kw81k3rar7ow2vnvp37` (`item_id`),
                              CONSTRAINT `FKgh2dw7kw81k3rar7ow2vnvp37` FOREIGN KEY (`item_id`) REFERENCES `item` (`item_id`)
) ENGINE=InnoDB AUTO_INCREMENT=121 DEFAULT CHARSET=utf8

CREATE TABLE `item_like` (
                             `item_like_id` bigint(20) NOT NULL AUTO_INCREMENT,
                             `created_date` datetime(6) DEFAULT NULL,
                             `last_modified_date` datetime(6) DEFAULT NULL,
                             `item_id` bigint(20) DEFAULT NULL,
                             `user_id` bigint(20) DEFAULT NULL,
                             PRIMARY KEY (`item_like_id`),
                             KEY `FK943xouml6m8ikrxn862iq9qhj` (`item_id`),
                             KEY `FKlc81jr021caudusp3hcfjrb3j` (`user_id`),
                             CONSTRAINT `FK943xouml6m8ikrxn862iq9qhj` FOREIGN KEY (`item_id`) REFERENCES `item` (`item_id`),
                             CONSTRAINT `FKlc81jr021caudusp3hcfjrb3j` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8

CREATE TABLE `category` (
                            `category_id` bigint(20) NOT NULL AUTO_INCREMENT,
                            `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                            `created_date` datetime(6) DEFAULT NULL,
                            `last_modified_date` datetime(6) DEFAULT NULL,
                            PRIMARY KEY (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8

CREATE TABLE `category_item` (
                                 `category_item_id` bigint(20) NOT NULL AUTO_INCREMENT,
                                 `category_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                                 `category_id` bigint(20) DEFAULT NULL,
                                 `item_id` bigint(20) DEFAULT NULL,
                                 `is_main` bit(1) NOT NULL,
                                 `created_date` datetime(6) DEFAULT NULL,
                                 `last_modified_date` datetime(6) DEFAULT NULL,
                                 PRIMARY KEY (`category_item_id`),
                                 KEY `FKcq2n0opf5shyh84ex1fhukcbh` (`category_id`),
                                 KEY `FKu8b4lwqutcdq3363gf6mlujq` (`item_id`),
                                 CONSTRAINT `FKcq2n0opf5shyh84ex1fhukcbh` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`),
                                 CONSTRAINT `FKu8b4lwqutcdq3363gf6mlujq` FOREIGN KEY (`item_id`) REFERENCES `item` (`item_id`)
) ENGINE=InnoDB AUTO_INCREMENT=119 DEFAULT CHARSET=utf8

CREATE TABLE `delivery` (
                            `delivery_id` bigint(20) NOT NULL AUTO_INCREMENT,
                            `created_date` datetime(6) DEFAULT NULL,
                            `last_modified_date` datetime(6) DEFAULT NULL,
                            `delivery_price` int(11) NOT NULL,
                            `delivery_status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                            `memo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                            `detail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                            `main` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                            `zip_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                            `phone_num1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                            `phone_num2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                            `phone_num3` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                            `receiver` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                            PRIMARY KEY (`delivery_id`)
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8

CREATE TABLE `orders` (
                          `order_id` bigint(20) NOT NULL AUTO_INCREMENT,
                          `user_id` bigint(20) DEFAULT NULL,
                          `final_discount_price` int(11) NOT NULL,
                          `final_price` int(11) NOT NULL,
                          `saved_points` int(11) NOT NULL,
                          `used_points` int(11) NOT NULL,
                          `delivery_id` bigint(20) DEFAULT NULL,
                          `payment_method` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                          `created_date` datetime(6) DEFAULT NULL,
                          `last_modified_date` datetime(6) DEFAULT NULL,
                          `order_status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                          PRIMARY KEY (`order_id`),
                          KEY `FK32ql8ubntj5uh44ph9659tiih` (`user_id`),
                          KEY `FKtkrur7wg4d8ax0pwgo0vmy20c` (`delivery_id`),
                          CONSTRAINT `FK32ql8ubntj5uh44ph9659tiih` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
                          CONSTRAINT `FKtkrur7wg4d8ax0pwgo0vmy20c` FOREIGN KEY (`delivery_id`) REFERENCES `delivery` (`delivery_id`)
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8

CREATE TABLE `order_item` (
                              `order_item_id` bigint(20) NOT NULL AUTO_INCREMENT,
                              `item_id` bigint(20) DEFAULT NULL,
                              `orders_id` bigint(20) DEFAULT NULL,
                              `item_discount_price` int(11) NOT NULL,
                              `item_price` int(11) NOT NULL,
                              `item_quantity` int(11) NOT NULL,
                              `created_date` datetime(6) DEFAULT NULL,
                              `last_modified_date` datetime(6) DEFAULT NULL,
                              `leave_review` bit(1) NOT NULL,
                              PRIMARY KEY (`order_item_id`),
                              KEY `FKija6hjjiit8dprnmvtvgdp6ru` (`item_id`),
                              KEY `FKea29bb770t1s99pp2ngvhgwnt` (`orders_id`),
                              CONSTRAINT `FKea29bb770t1s99pp2ngvhgwnt` FOREIGN KEY (`orders_id`) REFERENCES `orders` (`order_id`),
                              CONSTRAINT `FKija6hjjiit8dprnmvtvgdp6ru` FOREIGN KEY (`item_id`) REFERENCES `item` (`item_id`)
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8

CREATE TABLE `review` (
                          `review_id` bigint(20) NOT NULL AUTO_INCREMENT,
                          `created_date` datetime(6) DEFAULT NULL,
                          `last_modified_date` datetime(6) DEFAULT NULL,
                          `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
                          `likes_count` int(11) NOT NULL,
                          `rating` int(11) NOT NULL,
                          `item_id` bigint(20) DEFAULT NULL,
                          `user_id` bigint(20) DEFAULT NULL,
                          PRIMARY KEY (`review_id`),
                          KEY `FK6hb6qqehnsm7mvfgv37m66hd7` (`item_id`),
                          KEY `FK6cpw2nlklblpvc7hyt7ko6v3e` (`user_id`),
                          CONSTRAINT `FK6cpw2nlklblpvc7hyt7ko6v3e` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
                          CONSTRAINT `FK6hb6qqehnsm7mvfgv37m66hd7` FOREIGN KEY (`item_id`) REFERENCES `item` (`item_id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8

CREATE TABLE `review_image` (
                                `review_src_id` bigint(20) NOT NULL AUTO_INCREMENT,
                                `created_date` datetime(6) DEFAULT NULL,
                                `last_modified_date` datetime(6) DEFAULT NULL,
                                `s3key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                                `review_id` bigint(20) DEFAULT NULL,
                                PRIMARY KEY (`review_src_id`),
                                KEY `FK16wp089tx9nm0obc217gvdd6l` (`review_id`),
                                CONSTRAINT `FK16wp089tx9nm0obc217gvdd6l` FOREIGN KEY (`review_id`) REFERENCES `review` (`review_id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8

CREATE TABLE `review_like` (
                               `review_like_id` bigint(20) NOT NULL AUTO_INCREMENT,
                               `review_id` bigint(20) DEFAULT NULL,
                               `user_id` bigint(20) DEFAULT NULL,
                               PRIMARY KEY (`review_like_id`),
                               KEY `FK68am9vk1s1e8n1v873meqkk0k` (`review_id`),
                               KEY `FKkvtrymuejm49631rif0aasg5e` (`user_id`),
                               CONSTRAINT `FK68am9vk1s1e8n1v873meqkk0k` FOREIGN KEY (`review_id`) REFERENCES `review` (`review_id`),
                               CONSTRAINT `FKkvtrymuejm49631rif0aasg5e` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8

CREATE TABLE `qna` (
                       `qna_id` bigint(20) NOT NULL AUTO_INCREMENT,
                       `created_date` datetime(6) DEFAULT NULL,
                       `last_modified_date` datetime(6) DEFAULT NULL,
                       `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                       `is_secret` bit(1) NOT NULL,
                       `qna_status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                       `user_id` bigint(20) DEFAULT NULL,
                       `item_id` bigint(20) DEFAULT NULL,
                       PRIMARY KEY (`qna_id`),
                       KEY `FK90r3rkluxrf6bknnw579991k9` (`user_id`),
                       KEY `FK6oitmelo66kaxer7alamay1ob` (`item_id`),
                       CONSTRAINT `FK6oitmelo66kaxer7alamay1ob` FOREIGN KEY (`item_id`) REFERENCES `item` (`item_id`),
                       CONSTRAINT `FK90r3rkluxrf6bknnw579991k9` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8