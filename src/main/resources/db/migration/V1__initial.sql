SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

CREATE SCHEMA IF NOT EXISTS `consep` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `consep`;

-- ---------------------------------------------------------------

CREATE TABLE IF NOT EXISTS `organization` (
  `id`                 BIGINT          NOT NULL AUTO_INCREMENT,
  `name`               VARCHAR(120) NOT NULL UNIQUE,
  `abbreviation`       VARCHAR(20)  NOT NULL UNIQUE,

  PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci;

-- ---------------------------------------------------------------

CREATE TABLE IF NOT EXISTS `users` (
  `id`                 BIGINT          NOT NULL AUTO_INCREMENT,
  `name`               VARCHAR(60)  NULL     DEFAULT NULL,
  `organization_id`    BIGINT,
  `password`           VARCHAR(255) NOT NULL,
  `login`              VARCHAR(50)  NOT NULL UNIQUE,
  `role`               ENUM('USER', 'ADMIN') NOT NULL DEFAULT 'USER',

  PRIMARY KEY (`id`),

  INDEX idx_users_organization_id (organization_id),

  CONSTRAINT `fk_users_organization`
  FOREIGN KEY (`organization_id`)
    REFERENCES `organization` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci;

-- ---------------------------------------------------------------

CREATE TABLE IF NOT EXISTS `project` (
  `id`                 BIGINT           NOT NULL,
  `name`               VARCHAR(120)     NOT NULL,
  `organization_id`    BIGINT           NOT NULL,
  `public_notice_id`   BIGINT           NOT NULL,
  `status`             ENUM('COMPLETED', 'FINISHED', 'ONGOING', 'CANCELLED') NOT NULL DEFAULT 'ONGOING',
  `value`              DECIMAL(10,2) NOT NULL CHECK (`value` >= 0), 

  PRIMARY KEY (`id`), 

  INDEX idx_project_organization (organization_id),
  INDEX idx_project_public_notice (public_notice_id),

  CONSTRAINT `fk_project_entity_model`
  FOREIGN KEY (`id`)
    REFERENCES `entity_model` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_project_organization`
  FOREIGN KEY (`organization_id`)
    REFERENCES `organization` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_project_public_notice`
  FOREIGN KEY (`public_notice_id`)
    REFERENCES `public_notice` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci;

-- ---------------------------------------------------------------

-- para pdf

CREATE TABLE IF NOT EXISTS `entity_model` (
  `id`                 BIGINT           NOT NULL AUTO_INCREMENT,

  PRIMARY KEY (`id`)

) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci;

-- ---------------------------------------------------------------

CREATE TABLE IF NOT EXISTS `public_notice` (
  `id`                 BIGINT           NOT NULL,
  `organization_id`    BIGINT           NOT NULL,
  `number`             VARCHAR(20)   NOT NULL,
  `created_at`         DATE          NOT NULL DEFAULT (CURRENT_DATE),
  `end_at`             DATE,

  PRIMARY KEY (`id`),

  INDEX idx_public_notice_organization (organization_id),

  CONSTRAINT `fk_public_notice_organization`
  FOREIGN KEY (`organization_id`)
    REFERENCES `organization` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_public_notice_entity_model`
  FOREIGN KEY (`id`)
    REFERENCES `entity_model` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci;

-- ---------------------------------------------------------------

CREATE TABLE IF NOT EXISTS `payment` (
  `id`                 BIGINT           NOT NULL,
  `to_pay_id`          BIGINT           NOT NULL,
  `type`               ENUM('PAYMENT_SLIP', 'PAYMENT_PROOF')   NOT NULL,
  `created_at`         DATE          NOT NULL DEFAULT (CURRENT_DATE),
  `value`              DECIMAL(10,2) NOT NULL CHECK (`value` >= 0),

  PRIMARY KEY (`id`),

  INDEX idx_payment_to_pay (to_pay_id),

  CONSTRAINT `fk_payment_entity_model`
  FOREIGN KEY (`id`)
    REFERENCES `entity_model` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_payment_to_pay`
  FOREIGN KEY (`to_pay_id`)
    REFERENCES `to_pay` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci;
  
-- ---------------------------------------------------------------

CREATE TABLE IF NOT EXISTS `to_pay` (
  `id`                 BIGINT           NOT NULL,
  `project_id`         BIGINT           NOT NULL,
  `value`              DECIMAL(10,2)    NOT NULL CHECK (`value` >= 0),
  `type`               ENUM('INVOICE', 'PIX') NOT NULL,
  `pix_code`           VARCHAR(255),
  `created_at`         DATE          NOT NULL DEFAULT (CURRENT_DATE),
  `paid_at`            DATE,
  `due_date`           DATE             NOT NULL,
  `scheduled_at`       DATE,
  `status`             ENUM('PENDING', 'SCHEDULED', 'PAID', 'OVERDUE')   NOT NULL DEFAULT 'PENDING',

  PRIMARY KEY (`id`),

  INDEX idx_to_pay_project (project_id),

  CONSTRAINT `fk_to_pay_project`
  FOREIGN KEY (`project_id`)
    REFERENCES `project` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_to_pay_entity_model`
  FOREIGN KEY (`id`)
    REFERENCES `entity_model` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci;

-- ---------------------------------------------------------------

CREATE TABLE IF NOT EXISTS `document` (
  `id`                 BIGINT        NOT NULL AUTO_INCREMENT,
  `entity_model_id`    BIGINT        NOT NULL,
  `original_name`      VARCHAR(100),
  `generated_name`     CHAR(36),
  `sha256`             VARCHAR(64),

  PRIMARY KEY (`id`),

  UNIQUE KEY uk_document_sha256 (sha256),

  INDEX idx_document_entity_model (entity_model_id),

  CONSTRAINT `fk_entity_model_document`
  FOREIGN KEY (`entity_model_id`)
    REFERENCES `entity_model` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci;


-- ---------------------------------------------------------------

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;