-- 奈雪的茶小程序数据库初始化脚本
-- MySQL 8.0

CREATE DATABASE IF NOT EXISTS naixue DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE naixue;

-- 会员表
CREATE TABLE `member` (
  `customer_id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `openid` VARCHAR(100) COMMENT '微信OpenID',
  `unionid` VARCHAR(100) COMMENT '微信UnionID',
  `nickname` VARCHAR(50) COMMENT '昵称',
  `avatar` VARCHAR(255) COMMENT '头像',
  `mobile_phone` VARCHAR(20) COMMENT '手机号',
  `gender` TINYINT DEFAULT 0 COMMENT '性别: 0-女 1-男',
  `card_no` VARCHAR(50) COMMENT '会员卡号',
  `member_level` INT DEFAULT 1 COMMENT '会员等级: 1-6',
  `point_num` INT DEFAULT 0 COMMENT '积分',
  `balance` DECIMAL(10,2) DEFAULT 0 COMMENT '余额',
  `gift_balance` DECIMAL(10,2) DEFAULT 0 COMMENT '礼品卡余额',
  `current_value` INT DEFAULT 0 COMMENT '当前成长值',
  `member_origin` VARCHAR(20) DEFAULT 'wechat' COMMENT '来源',
  `last_login_time` DATETIME COMMENT '最后登录时间',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted` TINYINT DEFAULT 0,
  UNIQUE KEY `uk_openid` (`openid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员表';

-- 门店表
CREATE TABLE `store` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL COMMENT '门店名称',
  `address` VARCHAR(255) NOT NULL COMMENT '地址',
  `mobile` VARCHAR(20) COMMENT '电话',
  `tel` VARCHAR(20) COMMENT '备用电话',
  `longitude` DECIMAL(10,6) COMMENT '经度',
  `latitude` DECIMAL(10,6) COMMENT '纬度',
  `area_name` VARCHAR(50) COMMENT '区域',
  `is_open` TINYINT(1) DEFAULT 1 COMMENT '是否营业',
  `is_takeout` TINYINT(1) DEFAULT 1 COMMENT '是否支持外卖',
  `takeout_server_status` TINYINT(1) DEFAULT 1 COMMENT '外卖服务状态',
  `forhere_is_open` TINYINT(1) DEFAULT 1 COMMENT '是否支持堂食',
  `min_price` DECIMAL(10,2) DEFAULT 0 COMMENT '起送价',
  `packing_fee` DECIMAL(10,2) DEFAULT 0 COMMENT '包装费',
  `delivery_cost` DECIMAL(10,2) DEFAULT 0 COMMENT '配送费',
  `avg_delivery_cost_time` INT DEFAULT 30 COMMENT '平均配送时间(分钟)',
  `is_show` TINYINT(1) DEFAULT 1 COMMENT '是否展示',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='门店表';

-- 商品分类表
CREATE TABLE `goods_category` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL COMMENT '分类名称',
  `sort` INT DEFAULT 0 COMMENT '排序',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品分类表';

-- 商品表
CREATE TABLE `goods` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `category_id` BIGINT NOT NULL COMMENT '分类ID',
  `name` VARCHAR(100) NOT NULL COMMENT '商品名称',
  `price` DECIMAL(10,2) NOT NULL COMMENT '价格',
  `content` TEXT COMMENT '描述',
  `images` VARCHAR(500) COMMENT '图片',
  `use_property` TINYINT(1) DEFAULT 0 COMMENT '是否有规格',
  `stock` DECIMAL(10,2) DEFAULT 0 COMMENT '库存',
  `sales` INT DEFAULT 0 COMMENT '销量',
  `status` TINYINT(1) DEFAULT 1 COMMENT '状态: 0-下架 1-上架',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  KEY `idx_category_id` (`category_id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品表';

-- 订单表
CREATE TABLE `orders` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `order_no` VARCHAR(32) NOT NULL UNIQUE COMMENT '订单号',
  `member_id` BIGINT NOT NULL COMMENT '会员ID',
  `store_id` BIGINT NOT NULL COMMENT '门店ID',
  `type_cate` TINYINT NOT NULL COMMENT '1-自取 2-外卖',
  `status` TINYINT NOT NULL DEFAULT 0 COMMENT '0-待支付 1-已支付 2-制作中 3-已完成 4-已取消 5-退款中 6-已退款',
  `total_amount` DECIMAL(10,2) NOT NULL COMMENT '总金额',
  `amount` DECIMAL(10,2) NOT NULL COMMENT '实付金额',
  `goods_num` INT NOT NULL COMMENT '商品数量',
  `pay_mode` VARCHAR(20) COMMENT '支付方式',
  `payed_at` DATETIME COMMENT '支付时间',
  `sort_num` VARCHAR(10) COMMENT '取餐号',
  `postscript` VARCHAR(255) COMMENT '备注',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted` TINYINT DEFAULT 0,
  KEY `idx_member_id` (`member_id`),
  KEY `idx_store_id` (`store_id`),
  KEY `idx_status` (`status`),
  KEY `idx_created_at` (`created_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

-- 订单商品表
CREATE TABLE `order_goods` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `order_id` BIGINT NOT NULL COMMENT '订单ID',
  `goods_id` BIGINT NOT NULL COMMENT '商品ID',
  `goods_name` VARCHAR(100) NOT NULL COMMENT '商品名称',
  `price` DECIMAL(10,2) NOT NULL COMMENT '单价',
  `number` INT NOT NULL COMMENT '数量',
  `amount` DECIMAL(10,2) NOT NULL COMMENT '小计',
  `property` VARCHAR(255) COMMENT '规格',
  KEY `idx_order_id` (`order_id`),
  KEY `idx_goods_id` (`goods_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单商品表';

-- 收货地址表
CREATE TABLE `address` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `member_id` BIGINT NOT NULL COMMENT '会员ID',
  `accept_name` VARCHAR(50) NOT NULL COMMENT '收货人',
  `mobile` VARCHAR(20) NOT NULL COMMENT '手机号',
  `sex` TINYINT DEFAULT 0 COMMENT '性别: 0-女 1-男',
  `province` INT COMMENT '省份编码',
  `city` INT COMMENT '城市编码',
  `area` INT COMMENT '区域编码',
  `province_name` VARCHAR(50) COMMENT '省份名称',
  `city_name` VARCHAR(50) COMMENT '城市名称',
  `area_name` VARCHAR(50) COMMENT '区域名称',
  `street` VARCHAR(255) COMMENT '详细地址',
  `door_number` VARCHAR(50) COMMENT '门牌号',
  `is_default` TINYINT(1) DEFAULT 0 COMMENT '是否默认',
  `poiname` VARCHAR(100) COMMENT 'POI名称',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `deleted` TINYINT DEFAULT 0,
  KEY `idx_member_id` (`member_id`),
  KEY `idx_is_default` (`is_default`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='收货地址表';

-- 优惠券表
CREATE TABLE `coupon` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `title` VARCHAR(100) NOT NULL COMMENT '名称',
  `coupon_explain` TEXT COMMENT '使用说明',
  `image_url` VARCHAR(500) COMMENT '图片',
  `discount_amount` DECIMAL(10,2) COMMENT '优惠金额',
  `discount_unit` TINYINT DEFAULT 1 COMMENT '优惠单位: 1-金额 2-折扣',
  `min_price` DECIMAL(10,2) DEFAULT 0 COMMENT '使用门槛',
  `coupon_type` TINYINT DEFAULT 1 COMMENT '类型: 1-茶饮券 2-酒屋券',
  `begin_at` DATETIME COMMENT '开始时间',
  `end_at` DATETIME COMMENT '结束时间',
  `status` TINYINT(1) DEFAULT 1 COMMENT '状态: 0-禁用 1-启用',
  `seller_name` VARCHAR(50) DEFAULT '奈雪の茶' COMMENT '商家名称',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='优惠券表';

-- 会员优惠券表
CREATE TABLE `member_coupon` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `member_id` BIGINT NOT NULL COMMENT '会员ID',
  `coupon_id` BIGINT NOT NULL COMMENT '优惠券ID',
  `status` TINYINT(1) DEFAULT 0 COMMENT '状态: 0-未使用 1-已使用 2-已过期',
  `end_at` DATETIME NOT NULL COMMENT '过期时间',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  KEY `idx_member_id` (`member_id`),
  KEY `idx_coupon_id` (`coupon_id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员优惠券表';

-- 积分流水表
CREATE TABLE `points_flow` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `member_id` BIGINT NOT NULL COMMENT '会员ID',
  `change_type` TINYINT NOT NULL COMMENT '变动类型: 1-增加 2-减少',
  `change_num` INT NOT NULL COMMENT '变动数量',
  `reason` VARCHAR(100) COMMENT '原因',
  `source_type` TINYINT COMMENT '来源类型: 1-订单 2-退款 3-兑换 4-活动 5-签到 6-储值',
  `seller_name` VARCHAR(50) COMMENT '商家名称',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  KEY `idx_member_id` (`member_id`),
  KEY `idx_created_at` (`created_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='积分流水表';

-- 签到记录表
CREATE TABLE `attendance` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `member_id` BIGINT NOT NULL COMMENT '会员ID',
  `date` DATE NOT NULL COMMENT '签到日期',
  `attendance_point` INT DEFAULT 0 COMMENT '获得积分',
  `reward_days` INT DEFAULT 1 COMMENT '连续天数',
  `store_id` BIGINT COMMENT '门店ID',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  UNIQUE KEY `uk_member_date` (`member_id`, `date`),
  KEY `idx_member_id` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='签到记录表';

-- 储值卡表
CREATE TABLE `recharge_card` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL COMMENT '名称',
  `value` DECIMAL(10,2) NOT NULL COMMENT '面值',
  `sell_price` DECIMAL(10,2) NOT NULL COMMENT '售价',
  `description` TEXT COMMENT '说明',
  `image` VARCHAR(500) COMMENT '图片',
  `sales` INT DEFAULT 0 COMMENT '销量',
  `status` TINYINT(1) DEFAULT 1 COMMENT '状态: 0-禁用 1-启用',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='储值卡表';

-- 轮播图表
CREATE TABLE `banner` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `title` VARCHAR(100) COMMENT '标题',
  `image` VARCHAR(255) NOT NULL COMMENT '图片',
  `link` VARCHAR(255) COMMENT '链接',
  `sort` INT DEFAULT 0 COMMENT '排序',
  `status` TINYINT(1) DEFAULT 1 COMMENT '状态: 0-禁用 1-启用',
  `position` VARCHAR(20) DEFAULT 'home' COMMENT '位置',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='轮播图表';

-- 会员等级权益表
CREATE TABLE `member_level_benefits` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `level` INT NOT NULL COMMENT '等级',
  `card_name` VARCHAR(20) NOT NULL COMMENT '等级名称',
  `picture` VARCHAR(255) COMMENT '等级图片',
  `benefits_name` VARCHAR(50) COMMENT '权益名称',
  `benefits_type` TINYINT DEFAULT 0 COMMENT '权益类型',
  `benefits_item_type` TINYINT COMMENT '权益项类型',
  `num` INT DEFAULT 0 COMMENT '数量',
  `unit_type` TINYINT DEFAULT 0 COMMENT '单位类型',
  KEY `idx_level` (`level`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员等级权益表';

-- 积分商品表
CREATE TABLE `points_goods` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `goods_name` VARCHAR(100) NOT NULL COMMENT '商品名称',
  `points_price` INT NOT NULL COMMENT '积分价格',
  `amount` DECIMAL(10,2) DEFAULT 0 COMMENT '金额',
  `exchange_type` TINYINT DEFAULT 1 COMMENT '兑换类型',
  `goods_stock` INT DEFAULT 0 COMMENT '库存',
  `exchanged_num` INT DEFAULT 0 COMMENT '已兑换数量',
  `img` VARCHAR(500) COMMENT '图片',
  `exchange_desc` TEXT COMMENT '兑换说明',
  `goods_type` TINYINT DEFAULT 1 COMMENT '商品类型',
  `category` VARCHAR(50) COMMENT '分类',
  `status` TINYINT(1) DEFAULT 1 COMMENT '状态',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='积分商品表';

-- 礼品卡活动表
CREATE TABLE `gift_card_activity` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL COMMENT '活动名称',
  `activity_code` VARCHAR(50) COMMENT '活动代码',
  `image_url` VARCHAR(500) COMMENT '图片',
  `status` TINYINT(1) DEFAULT 1 COMMENT '状态',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='礼品卡活动表';

-- 初始化数据

-- 门店数据
INSERT INTO `store` (`name`, `address`, `mobile`, `longitude`, `latitude`, `is_open`, `is_takeout`, `takeout_server_status`, `forhere_is_open`, `min_price`, `packing_fee`, `delivery_cost`, `avg_delivery_cost_time`) VALUES
('卓悦中心ONE AVENUE店', '深圳市福田区海田路与福华一路交汇', '0755-82722513', '114.065927', '22.537361', 1, 1, 1, 1, 30.00, 2.00, 2.00, 40),
('福田coco park店', '深圳市福田区福华三路星河Coco Park', '0755-82722514', '114.065927', '22.537361', 1, 1, 1, 1, 30.00, 2.00, 2.00, 35),
('南山万象城店', '深圳市南山区华润万象城', '0755-82722515', '114.065927', '22.537361', 1, 1, 1, 1, 30.00, 2.00, 2.00, 45);

-- 商品分类数据
INSERT INTO `goods_category` (`name`, `sort`) VALUES
('奈雪早餐', 1),
('霸气鲜果茶', 2),
('宝藏鲜奶茶', 3),
('芝士纯茶', 4),
('纯茶', 5),
('气泡水', 6),
('奈雪甜点', 7),
('节日限定', 8);

-- 商品数据
INSERT INTO `goods` (`category_id`, `name`, `price`, `content`, `images`, `use_property`, `stock`, `sales`, `status`) VALUES
(1, '奈雪早餐套餐', 18.50, '购买三明治,享早餐指定饮品半价', 'https://example.com/breakfast.jpg', 0, 9999956.00, 487, 1),
(2, '霸气葡萄', 28.00, '当季新鲜葡萄，茉莉茶底', 'https://example.com/grape.jpg', 0, 9999956.00, 1256, 1),
(2, '霸气橙子', 25.00, '新奇士橙子，茉莉茶底', 'https://example.com/orange.jpg', 0, 9999956.00, 986, 1),
(2, '霸气草莓', 28.00, '新鲜草莓，茉莉茶底', 'https://example.com/strawberry.jpg', 0, 9999956.00, 1567, 1),
(3, '芋泥宝藏奶茶', 22.00, '芋泥+奶茶，温暖甜蜜', 'https://example.com/taro.jpg', 0, 9999956.00, 2341, 1),
(3, '杨枝甘露宝藏茶', 24.00, '芒果+椰汁+宝藏茶', 'https://example.com/mango.jpg', 0, 9999956.00, 1876, 1),
(4, '茉莉初雪', 18.00, '茉莉绿茶，清新自然', 'https://example.com/jasmine.jpg', 0, 9999956.00, 654, 1),
(5, '茉莉花茶', 15.00, '经典茉莉花茶', 'https://example.com/greentea.jpg', 0, 9999956.00, 432, 1),
(6, '气泡水', 16.00, '清爽气泡水', 'https://example.com/sparkling.jpg', 0, 9999956.00, 321, 1),
(7, '芋泥雪域小贝', 25.00, '芋泥奶油蛋糕', 'https://example.com/cake.jpg', 0, 9999956.00, 876, 1);

-- 储值卡数据
INSERT INTO `recharge_card` (`name`, `value`, `sell_price`, `description`, `status`) VALUES
('30元储值卡', 30.00, 30.00, '1. 储值成功后，不可退款...\n2. 可在奈雪任意门店使用', 1),
('50元储值卡', 50.00, 50.00, '1. 储值成功后，不可退款...\n2. 可在奈雪任意门店使用', 1),
('100元储值卡', 100.00, 100.00, '1. 储值成功后，不可退款...\n2. 可在奈雪任意门店使用\n3. 赠品：限量周边', 1),
('200元储值卡', 200.00, 200.00, '1. 储值成功后，不可退款...\n2. 可在奈雪任意门店使用\n3. 赠品：限定礼盒', 1);

-- 优惠券数据
INSERT INTO `coupon` (`title`, `coupon_explain`, `discount_amount`, `discount_unit`, `min_price`, `coupon_type`, `begin_at`, `end_at`, `status`) VALUES
('新人专享券', '<p>1. 本券仅限新用户使用</p><p>2. 满30元减5元</p>', 5.00, 1, 30.00, 1, NOW(), DATE_ADD(NOW(), INTERVAL 30 DAY), 1),
('生日免费券', '<p>1. 生日当月可用</p><p>2. 指定饮品免费</p>', 0.00, 1, 0.00, 1, NOW(), DATE_ADD(NOW(), INTERVAL 30 DAY), 1),
('茶饮满二赠一券', '<p>1. 购买两杯茶饮，赠一杯</p><p>2. 限本人使用</p>', 0.00, 1, 0.00, 1, NOW(), DATE_ADD(NOW(), INTERVAL 7 DAY), 1),
('会员专享8折券', '<p>1. 指定商品8折</p><p>2. 限会员使用</p>', 0.20, 2, 0.00, 1, NOW(), DATE_ADD(NOW(), INTERVAL 15 DAY), 1);

-- 轮播图数据
INSERT INTO `banner` (`title`, `image`, `link`, `sort`, `status`, `position`) VALUES
('新品上市', 'https://example.com/banner1.jpg', '', 1, 1, 'home'),
('会员日活动', 'https://example.com/banner2.jpg', '', 2, 1, 'home'),
('限时优惠', 'https://example.com/banner3.jpg', '', 3, 1, 'home');

-- 会员等级权益数据
INSERT INTO `member_level_benefits` (`level`, `card_name`, `picture`, `benefits_name`, `benefits_type`, `benefits_item_type`, `num`, `unit_type`) VALUES
(1, 'V1', 'https://example.com/v1.png', '开卡特权', 0, 0, 2, 0),
(2, 'V2', 'https://example.com/v2.png', '开卡特权', 0, 0, 2, 0),
(2, 'V2', 'https://example.com/v2.png', '生日礼包', 1, 0, 1, 0),
(3, 'V3', 'https://example.com/v3.png', '开卡特权', 0, 0, 3, 0),
(3, 'V3', 'https://example.com/v3.png', '生日礼包', 1, 0, 2, 0),
(4, 'V4', 'https://example.com/v4.png', '开卡特权', 0, 0, 3, 0),
(4, 'V4', 'https://example.com/v4.png', '生日礼包', 1, 0, 2, 0),
(4, 'V4', 'https://example.com/v4.png', '专属客服', 2, 0, 1, 0);

-- 积分商品数据
INSERT INTO `points_goods` (`goods_name`, `points_price`, `amount`, `exchange_type`, `goods_stock`, `exchanged_num`, `img`, `exchange_desc`, `goods_type`, `category`, `status`) VALUES
('软欧包免费券', 800, 0.00, 1, 99665, 1241, 'https://example.com/voucher.jpg', '<p>兑换规则：</p><p>1. 积分兑换，不可退款</p>', 2, '奈雪好券', 1),
('指定饮品5折券', 500, 0.00, 1, 50000, 856, 'https://example.com/discount.jpg', '<p>兑换规则：</p><p>1. 指定饮品5折</p>', 2, '奈雪好券', 1),
('限定周边套装', 2000, 0.00, 1, 1000, 45, 'https://example.com/gift.jpg', '<p>兑换规则：</p><p>1. 限定周边套装</p>', 3, '奈雪好物', 1);

-- 礼品卡活动数据
INSERT INTO `gift_card_activity` (`name`, `activity_code`, `image_url`, `status`) VALUES
('梦回童年六一玩趣', 'KID2024', 'https://example.com/kids.jpg', 1),
('拥抱同一份热爱', 'LOVE2024', 'https://example.com/love.jpg', 1);
