CREATE TABLE `test`.`user`
(
    `id`       int(10)                                                NOT NULL AUTO_INCREMENT,
    `username` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `password` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `time`     datetime(0)                                            NULL DEFAULT NULL,
    `time1`    timestamp(0)                                           NULL DEFAULT NULL,
    `user_id`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `password` (`password`) USING BTREE,
    INDEX `password2` (`password`, `username`) USING BTREE,
    INDEX `user_id` (`user_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;


CREATE TABLE `test`.`user_info`
(
    `ID`        int(10)                                                NOT NULL AUTO_INCREMENT,
    `userName`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `password`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `time`      datetime(0)                                            NULL DEFAULT NULL,
    `time1`     timestamp(0)                                           NULL DEFAULT NULL,
    `userId`    varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `BDContent` text CHARACTER SET utf8 COLLATE utf8_general_ci        NULL,
    PRIMARY KEY (`ID`) USING BTREE,
    INDEX `password` (`password`) USING BTREE,
    INDEX `password2` (`password`, `userName`) USING BTREE,
    INDEX `user_id` (`userId`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;