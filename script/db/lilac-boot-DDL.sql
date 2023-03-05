-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema db_lilac_boot
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema db_lilac_boot
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `db_lilac_boot` DEFAULT CHARACTER SET utf8mb4 ;
USE `db_lilac_boot` ;

-- -----------------------------------------------------
-- Table `db_lilac_boot`.`tbl_youtube_channel`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_lilac_boot`.`tbl_youtube_channel` (
                                                                     `id` BIGINT NOT NULL AUTO_INCREMENT,
                                                                     `channel_id` VARCHAR(50) NOT NULL,
    `title` VARCHAR(100) NULL,
    `description` VARCHAR(1000) NULL,
    `publish_date` DATETIME NULL,
    `view_count` BIGINT UNSIGNED NULL,
    `subscriber_count` BIGINT UNSIGNED NULL COMMENT '구독자수',
    `subscriber_count_hidden` TINYINT(1) NULL DEFAULT 0 COMMENT '구독자수 비공개 여부',
    `video_count` BIGINT UNSIGNED NULL,
    `branding_keywords` VARCHAR(255) NULL COMMENT '채널에 등록한 채널 관련 키워드 \n예) 정보처리기사 정보처리산업기사 컴활1급 컴활2급 컴퓨터활용능력1급 컴퓨터활용능력2급 \\\"정보처리기사 필기\\\" \\\"정보처리기사 실기\\\" \\\"정보처리산업기사 필기\\\" \\\"정보처리산업기사 실기\\\" 사무자동화산업기사',
    `thumbnail_medium` VARCHAR(2048) NULL,
    `thumbnail_high` VARCHAR(2048) NULL,
    `reg_date` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `c_id_UNIQUE` (`channel_id` ASC) VISIBLE)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_lilac_boot`.`tbl_youtube_playlist`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_lilac_boot`.`tbl_youtube_playlist` (
                                                                      `id` BIGINT NOT NULL AUTO_INCREMENT,
                                                                      `playlist_id` VARCHAR(50) NOT NULL,
    `channel_id` BIGINT NOT NULL,
    `title` VARCHAR(100) NULL,
    `publish_date` DATETIME NULL,
    `thumbnail_medium` VARCHAR(2048) NULL,
    `thumbnail_high` VARCHAR(2048) NULL,
    `item_count` INT NULL,
    `reg_date` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `playlist_id_UNIQUE` (`playlist_id` ASC) INVISIBLE,
    INDEX `fk_tbl_youtube_playlist_tbl_youtube_channel1_idx` (`channel_id` ASC) VISIBLE,
    FULLTEXT INDEX `full_txt_idx_title` (`title`) VISIBLE,
    CONSTRAINT `fk_tbl_youtube_playlist_tbl_youtube_channel`
    FOREIGN KEY (`channel_id`)
    REFERENCES `db_lilac_boot`.`tbl_youtube_channel` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_lilac_boot`.`tbl_youtube`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_lilac_boot`.`tbl_youtube` (
                                                             `id` BIGINT NOT NULL AUTO_INCREMENT,
                                                             `video_id` VARCHAR(50) NOT NULL,
    `channel_id` BIGINT NOT NULL,
    `youtube_playlist_id` BIGINT NULL,
    `title` VARCHAR(100) NOT NULL,
    `description` VARCHAR(5000) NULL,
    `publish_date` DATETIME NULL,
    `reg_date` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
    `thumbnail_default` VARCHAR(2048) NULL,
    `thumbnail_medium` VARCHAR(2048) NULL,
    `thumbnail_high` VARCHAR(2048) NULL,
    `playlist_id` VARCHAR(50) NULL,
    `view_count` BIGINT NULL DEFAULT 0,
    `search_count` INT NULL DEFAULT 0 COMMENT '웹페이지에서 조회한 횟수',
    `like_count` BIGINT NULL,
    `favorite_count` BIGINT NULL,
    `comment_count` BIGINT NULL,
    `comment_disabled` TINYINT(1) NULL DEFAULT 0,
    `duration` VARCHAR(40) NULL,
    `score` FLOAT NULL,
    `magnitude` FLOAT NULL,
    FULLTEXT INDEX `title_idx` (`title`) INVISIBLE,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `video_id_idx` (`video_id` ASC) VISIBLE,
    INDEX `fk_tbl_youtube_tbl_youtube_channel1_idx` (`channel_id` ASC) VISIBLE,
    INDEX `fk_tbl_youtube_tbl_playlist1_idx` (`youtube_playlist_id` ASC) VISIBLE,
    FULLTEXT INDEX `full_txt_idx_title` (`title`) VISIBLE,
    CONSTRAINT `fk_tbl_youtube_tbl_youtube_channel`
    FOREIGN KEY (`channel_id`)
    REFERENCES `db_lilac_boot`.`tbl_youtube_channel` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    CONSTRAINT `fk_tbl_youtube_tbl_playlist`
    FOREIGN KEY (`youtube_playlist_id`)
    REFERENCES `db_lilac_boot`.`tbl_youtube_playlist` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_lilac_boot`.`tbl_recommended_video`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_lilac_boot`.`tbl_recommended_video` (
                                                                       `id` BIGINT NOT NULL AUTO_INCREMENT,
                                                                       `youtube_id` BIGINT NOT NULL,
                                                                       `reg_date` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
                                                                       PRIMARY KEY (`id`, `youtube_id`),
    INDEX `fk_tbl_recommended_video_tbl_youtube1_idx` (`youtube_id` ASC) VISIBLE,
    CONSTRAINT `fk_tbl_recommended_video_tbl_youtube`
    FOREIGN KEY (`youtube_id`)
    REFERENCES `db_lilac_boot`.`tbl_youtube` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_lilac_boot`.`tbl_youtube_comment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_lilac_boot`.`tbl_youtube_comment` (
                                                                     `id` BIGINT NOT NULL AUTO_INCREMENT,
                                                                     `youtube_id` BIGINT NOT NULL,
                                                                     `comment_id` VARCHAR(50) NOT NULL,
    `total_reply_count` BIGINT NULL,
    `author_display_name` VARCHAR(100) NULL,
    `text_original` TEXT NULL,
    `text_display` TEXT NULL,
    `publish_date` DATETIME NULL,
    `update_date` DATETIME NULL,
    `parent_id` VARCHAR(50) NULL,
    `channel_id` VARCHAR(50) NULL,
    `reg_date` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    INDEX `fk_tnl_youtube_comment_tbl_youtube1_idx` (`youtube_id` ASC) VISIBLE,
    UNIQUE INDEX `comment_id_UNIQUE` (`comment_id` ASC) VISIBLE,
    CONSTRAINT `fk_tnl_youtube_comment_tbl_youtube`
    FOREIGN KEY (`youtube_id`)
    REFERENCES `db_lilac_boot`.`tbl_youtube` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_lilac_boot`.`tbl_subject`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_lilac_boot`.`tbl_subject` (
                                                             `id` INT NOT NULL AUTO_INCREMENT,
                                                             `code` INT NOT NULL,
                                                             `name` VARCHAR(50) NOT NULL,
    `key_word` VARCHAR(255) NOT NULL,
    `key_word_book` VARCHAR(255) NOT NULL,
    `reg_date` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
    `is_active` TINYINT(1) NULL DEFAULT 1,
    `is_scheduled` TINYINT(1) NULL DEFAULT 0,
    `page_token` VARCHAR(50) NULL,
    `update_time` DATETIME NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE,
    UNIQUE INDEX `code_UNIQUE` (`code` ASC) VISIBLE)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_lilac_boot`.`tbl_lib_region`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_lilac_boot`.`tbl_lib_region` (
                                                                `code` SMALLINT NOT NULL,
                                                                `name` CHAR(2) NOT NULL,
    PRIMARY KEY (`code`))
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_lilac_boot`.`tbl_lib_dtl_region`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_lilac_boot`.`tbl_lib_dtl_region` (
                                                                    `code` INT NOT NULL,
                                                                    `region_code` SMALLINT NOT NULL,
                                                                    `name` VARCHAR(10) NOT NULL,
    `detail_name` VARCHAR(10) NOT NULL,
    PRIMARY KEY (`code`, `region_code`),
    INDEX `fk_tbl_lib_dtl_region_tbl_lib_region1_idx` (`region_code` ASC) VISIBLE,
    CONSTRAINT `fk_tbl_lib_dtl_region_tbl_lib_region`
    FOREIGN KEY (`region_code`)
    REFERENCES `db_lilac_boot`.`tbl_lib_region` (`code`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_lilac_boot`.`tbl_member`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_lilac_boot`.`tbl_member` (
                                                            `id` BIGINT NOT NULL AUTO_INCREMENT,
                                                            `nickname` VARCHAR(20) NULL,
    `email` VARCHAR(100) NULL,
    `password` VARCHAR(20) NULL,
    `description` VARCHAR(1000) NULL,
    `profile_original` VARCHAR(255) NULL,
    `profile_save` VARCHAR(2048) NULL,
    `grade` INT NOT NULL,
    `reg_date` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
    `region` SMALLINT NULL,
    `dtl_region` INT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `nickname_UNIQUE` (`nickname` ASC) VISIBLE,
    UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
    INDEX `fk_tbl_member_tbl_lib_region1_idx` (`region` ASC) VISIBLE,
    INDEX `fk_tbl_member_tbl_lib_dtl_region1_idx` (`dtl_region` ASC) VISIBLE,
    CONSTRAINT `fk_tbl_member_tbl_lib_region`
    FOREIGN KEY (`region`)
    REFERENCES `db_lilac_boot`.`tbl_lib_region` (`code`)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
    CONSTRAINT `fk_tbl_member_tbl_lib_dtl_region`
    FOREIGN KEY (`dtl_region`)
    REFERENCES `db_lilac_boot`.`tbl_lib_dtl_region` (`code`)
    ON DELETE SET NULL
    ON UPDATE CASCADE)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_lilac_boot`.`tbl_license`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_lilac_boot`.`tbl_license` (
                                                             `id` INT NOT NULL AUTO_INCREMENT,
                                                             `code` INT NOT NULL,
                                                             `name` VARCHAR(50) NOT NULL,
    `key_word` VARCHAR(50) NOT NULL,
    `reg_date` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
    `schedule_json` JSON NULL,
    `is_active` TINYINT(1) NULL DEFAULT 1,
    `is_scheduled` TINYINT(1) NULL DEFAULT 0,
    `page_token` VARCHAR(50) NULL,
    `update_time` DATETIME NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE,
    UNIQUE INDEX `key_word_UNIQUE` (`key_word` ASC) VISIBLE)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_lilac_boot`.`tbl_lecture`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_lilac_boot`.`tbl_lecture` (
                                                             `id` BIGINT NOT NULL AUTO_INCREMENT,
                                                             `member_id` BIGINT NOT NULL,
                                                             `license_id` INT NULL,
                                                             `subject_id` INT NULL,
                                                             `title` VARCHAR(100) NULL,
    `description` VARCHAR(500) NULL,
    `reg_date` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
    `progress` INT NULL,
    PRIMARY KEY (`id`, `member_id`),
    INDEX `fk_tbl_lecture_tbl_member1_idx` (`member_id` ASC) VISIBLE,
    INDEX `fk_tbl_lecture_tbl_subject1_idx` (`subject_id` ASC) VISIBLE,
    INDEX `fk_tbl_lecture_tbl_license1_idx` (`license_id` ASC) VISIBLE,
    CONSTRAINT `fk_tbl_lecture_tbl_member`
    FOREIGN KEY (`member_id`)
    REFERENCES `db_lilac_boot`.`tbl_member` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    CONSTRAINT `fk_tbl_lecture_tbl_subject`
    FOREIGN KEY (`subject_id`)
    REFERENCES `db_lilac_boot`.`tbl_subject` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
    CONSTRAINT `fk_tbl_lecture_tbl_license`
    FOREIGN KEY (`license_id`)
    REFERENCES `db_lilac_boot`.`tbl_license` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_lilac_boot`.`ref_tbl_lecture_youtube`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_lilac_boot`.`ref_tbl_lecture_youtube` (
                                                                         `id` BIGINT NOT NULL AUTO_INCREMENT,
                                                                         `lecture_id` BIGINT NOT NULL,
                                                                         `lecture_member_id` BIGINT NOT NULL,
                                                                         `youtube_id` BIGINT NOT NULL,
                                                                         `youtube_playlist_id` BIGINT NULL,
                                                                         `reg_date` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
                                                                         PRIMARY KEY (`id`),
    INDEX `fk_ref_tbl_lecture_youtube_tbl_lecture1_idx` (`lecture_id` ASC, `lecture_member_id` ASC) VISIBLE,
    INDEX `fk_ref_tbl_lecture_youtube_tbl_youtube1_idx` (`youtube_id` ASC) INVISIBLE,
    CONSTRAINT `fk_ref_tbl_lecture_youtube_tbl_lecture`
    FOREIGN KEY (`lecture_id` , `lecture_member_id`)
    REFERENCES `db_lilac_boot`.`tbl_lecture` (`id` , `member_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    CONSTRAINT `fk_ref_tbl_lecture_youtube_tbl_youtube`
    FOREIGN KEY (`youtube_id`)
    REFERENCES `db_lilac_boot`.`tbl_youtube` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_lilac_boot`.`tbl_book`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_lilac_boot`.`tbl_book` (
                                                          `id` BIGINT NOT NULL AUTO_INCREMENT,
                                                          `isbn13` BIGINT NOT NULL,
                                                          `title` VARCHAR(1024) NULL,
    `contents` VARCHAR(2048) NULL,
    `url` VARCHAR(2048) NULL,
    `publish_date` DATETIME NULL,
    `authors` VARCHAR(255) NULL,
    `publisher` VARCHAR(255) NULL,
    `translators` VARCHAR(255) NULL,
    `price` INT NULL DEFAULT 0,
    `sale_price` INT NULL DEFAULT 0,
    `thumbnail` VARCHAR(2048) NULL,
    `status` VARCHAR(10) NULL,
    `reg_date` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
    `update_date` DATETIME NULL,
    FULLTEXT INDEX `full_text_index_book_title` (`title`) VISIBLE,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `isbn13_UNIQUE` (`isbn13` ASC) VISIBLE)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_lilac_boot`.`tbl_library`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_lilac_boot`.`tbl_library` (
                                                             `id` BIGINT NOT NULL AUTO_INCREMENT,
                                                             `lib_code` INT NOT NULL,
                                                             `name` VARCHAR(100) NULL,
    `address` VARCHAR(100) NULL,
    `tel` VARCHAR(20) NULL,
    `fax` VARCHAR(20) NULL,
    `latitude` CHAR(10) NULL,
    `longitude` CHAR(10) NULL,
    `homepage` VARCHAR(255) NULL,
    `closed` VARCHAR(255) NULL,
    `operating_time` VARCHAR(50) NULL,
    `reg_date` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
    `update_date` DATETIME NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `lib_code_UNIQUE` (`lib_code` ASC) VISIBLE)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_lilac_boot`.`ref_tbl_lecture_book`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_lilac_boot`.`ref_tbl_lecture_book` (
                                                                      `id` BIGINT NOT NULL AUTO_INCREMENT,
                                                                      `lecture_id` BIGINT NOT NULL,
                                                                      `lecture_member_id` BIGINT NOT NULL,
                                                                      `book_id` BIGINT NOT NULL,
                                                                      `reg_date` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
                                                                      PRIMARY KEY (`id`),
    INDEX `fk_ref_tbl_lecture_book_tbl_lecture1_idx` (`lecture_id` ASC, `lecture_member_id` ASC) VISIBLE,
    INDEX `fk_ref_tbl_lecture_book_tbl_book1_idx` (`book_id` ASC) VISIBLE,
    CONSTRAINT `fk_ref_tbl_lecture_book_tbl_lecture`
    FOREIGN KEY (`lecture_id` , `lecture_member_id`)
    REFERENCES `db_lilac_boot`.`tbl_lecture` (`id` , `member_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    CONSTRAINT `fk_ref_tbl_lecture_book_tbl_book1`
    FOREIGN KEY (`book_id`)
    REFERENCES `db_lilac_boot`.`tbl_book` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_lilac_boot`.`tbl_freeboard`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_lilac_boot`.`tbl_freeboard` (
                                                               `id` BIGINT NOT NULL AUTO_INCREMENT,
                                                               `member_id` BIGINT NOT NULL,
                                                               `title` VARCHAR(100) NULL,
    `contents` VARCHAR(2000) NULL,
    `reg_date` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
    `hits` INT NULL,
    `attached_file` TINYINT NULL,
    `like_count` INT NULL,
    PRIMARY KEY (`id`, `member_id`),
    INDEX `fk_tbl_freeboard_tbl_member1_idx` (`member_id` ASC) VISIBLE,
    CONSTRAINT `fk_tbl_freeboard_tbl_member`
    FOREIGN KEY (`member_id`)
    REFERENCES `db_lilac_boot`.`tbl_member` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_lilac_boot`.`tbl_recommened_book`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_lilac_boot`.`tbl_recommened_book` (
                                                                     `id` BIGINT NOT NULL AUTO_INCREMENT,
                                                                     `book_id` BIGINT NOT NULL,
                                                                     `reg_date` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
                                                                     PRIMARY KEY (`id`, `book_id`),
    INDEX `fk_tbl_recommened_book_tbl_book1_idx` (`book_id` ASC) VISIBLE,
    CONSTRAINT `fk_tbl_recommened_book_tbl_book1`
    FOREIGN KEY (`book_id`)
    REFERENCES `db_lilac_boot`.`tbl_book` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_lilac_boot`.`tbl_attached_file`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_lilac_boot`.`tbl_attached_file` (
                                                                   `id` BIGINT NOT NULL AUTO_INCREMENT,
                                                                   `freeboard_id` BIGINT NOT NULL,
                                                                   `original_file` VARCHAR(255) NULL,
    `stored_file` VARCHAR(2048) NULL,
    PRIMARY KEY (`id`, `freeboard_id`),
    INDEX `fk_tbl_attached_file_tbl_freeboard1_idx` (`freeboard_id` ASC) VISIBLE,
    CONSTRAINT `fk_tbl_attached_file_tbl_freeboard`
    FOREIGN KEY (`freeboard_id`)
    REFERENCES `db_lilac_boot`.`tbl_freeboard` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_lilac_boot`.`tbl_freeboard_comment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_lilac_boot`.`tbl_freeboard_comment` (
                                                                       `id` BIGINT NOT NULL AUTO_INCREMENT,
                                                                       `freeboard_id` BIGINT NOT NULL,
                                                                       `member_id` BIGINT NOT NULL,
                                                                       `contents` VARCHAR(1000) NULL,
    `reg_date` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`, `freeboard_id`, `member_id`),
    INDEX `fk_tbl_freeboard_comment_tbl_freeboard1_idx` (`member_id` ASC) VISIBLE,
    CONSTRAINT `fk_tbl_freeboard_comment_tbl_freeboard`
    FOREIGN KEY (`member_id`)
    REFERENCES `db_lilac_boot`.`tbl_freeboard` (`member_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_lilac_boot`.`tbl_youtube_like`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_lilac_boot`.`tbl_youtube_like` (
                                                                  `id` BIGINT NOT NULL AUTO_INCREMENT,
                                                                  `youtube_id` BIGINT NOT NULL,
                                                                  `member_id` BIGINT NOT NULL,
                                                                  `reg_date` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
                                                                  `like_idx` INT NULL,
                                                                  PRIMARY KEY (`id`, `youtube_id`, `member_id`),
    INDEX `fk_tbl_youtube_like_tbl_youtube1_idx` (`youtube_id` ASC) VISIBLE,
    INDEX `fk_tbl_youtube_like_tbl_member1_idx` (`member_id` ASC) VISIBLE,
    CONSTRAINT `fk_tbl_youtube_like_tbl_youtube`
    FOREIGN KEY (`youtube_id`)
    REFERENCES `db_lilac_boot`.`tbl_youtube` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    CONSTRAINT `fk_tbl_youtube_like_tbl_member`
    FOREIGN KEY (`member_id`)
    REFERENCES `db_lilac_boot`.`tbl_member` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_lilac_boot`.`tbl_freeboard_like`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_lilac_boot`.`tbl_freeboard_like` (
                                                                    `id` BIGINT NOT NULL AUTO_INCREMENT,
                                                                    `member_id` BIGINT NOT NULL,
                                                                    `freeboard_id` BIGINT NOT NULL,
                                                                    `reg_date` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
                                                                    `like_idx` INT NULL,
                                                                    PRIMARY KEY (`id`, `member_id`, `freeboard_id`),
    INDEX `fk_tbl_freeboard_like_tbl_freeboard1_idx` (`freeboard_id` ASC, `member_id` ASC) VISIBLE,
    CONSTRAINT `fk_tbl_freeboard_like_tbl_freeboard`
    FOREIGN KEY (`freeboard_id` , `member_id`)
    REFERENCES `db_lilac_boot`.`tbl_freeboard` (`id` , `member_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_lilac_boot`.`tbl_recommended_playlist`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_lilac_boot`.`tbl_recommended_playlist` (
                                                                          `id` BIGINT NOT NULL AUTO_INCREMENT,
                                                                          `youtube_playlist_id` BIGINT NOT NULL,
                                                                          `reg_date` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
                                                                          PRIMARY KEY (`id`, `youtube_playlist_id`),
    INDEX `fk_tbl_recommended_playlist_tbl_youtube_playlist1_idx` (`youtube_playlist_id` ASC) VISIBLE,
    CONSTRAINT `fk_tbl_recommended_playlist_tbl_youtube_playlist1`
    FOREIGN KEY (`youtube_playlist_id`)
    REFERENCES `db_lilac_boot`.`tbl_youtube_playlist` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_lilac_boot`.`tbl_book_exist`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_lilac_boot`.`tbl_book_exist` (
                                                                `id` BIGINT NOT NULL,
                                                                `tbl_book_id` BIGINT NOT NULL,
                                                                `tbl_library_id` BIGINT NOT NULL,
                                                                `hasBook` TINYINT(1) NOT NULL,
    `loanAvailable` TINYINT(1) NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_tbl_book_has_tbl_library_tbl_library1_idx` (`tbl_library_id` ASC) VISIBLE,
    INDEX `fk_tbl_book_has_tbl_library_tbl_book1_idx` (`tbl_book_id` ASC) VISIBLE,
    CONSTRAINT `fk_tbl_book_has_tbl_library_tbl_book1`
    FOREIGN KEY (`tbl_book_id`)
    REFERENCES `db_lilac_boot`.`tbl_book` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `fk_tbl_book_has_tbl_library_tbl_library1`
    FOREIGN KEY (`tbl_library_id`)
    REFERENCES `db_lilac_boot`.`tbl_library` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_lilac_boot`.`tbl_comment_relation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_lilac_boot`.`tbl_comment_relation` (
                                                                      `pa_comment` BIGINT NOT NULL,
                                                                      `ch_comment` BIGINT NOT NULL,
                                                                      `member_id` BIGINT NOT NULL,
                                                                      `dapth` SMALLINT NULL,
                                                                      PRIMARY KEY (`pa_comment`, `ch_comment`, `member_id`),
    INDEX `fk_tbl_comment_relation_tbl_freeboard_comment1_idx` (`pa_comment` ASC, `ch_comment` ASC, `member_id` ASC) VISIBLE,
    CONSTRAINT `fk_tbl_comment_relation_tbl_freeboard_comment1`
    FOREIGN KEY (`pa_comment` , `ch_comment` , `member_id`)
    REFERENCES `db_lilac_boot`.`tbl_freeboard_comment` (`id` , `freeboard_id` , `member_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;