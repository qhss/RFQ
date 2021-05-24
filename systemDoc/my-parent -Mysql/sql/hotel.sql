/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.27 : Database - hotel
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`hotel` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `hotel`;

/*Table structure for table `bs_hotel` */

DROP TABLE IF EXISTS `bs_hotel`;

CREATE TABLE `bs_hotel` (
  `id` varchar(36) NOT NULL,
  `company_id` varchar(36) DEFAULT NULL COMMENT '组织机构(公司Id)',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(36) DEFAULT NULL COMMENT '创建者ID',
  `deleted` int(2) DEFAULT '0' COMMENT '是否已删除',
  `update_time` bigint(20) DEFAULT NULL COMMENT '更新时间',
  `update_user_id` varchar(36) DEFAULT NULL COMMENT '更新者ID',
  `canceled` int(2) DEFAULT '0' COMMENT '是否已禁用 0-未禁用 1-已禁用',
  `code` varchar(50) DEFAULT '' COMMENT '编码',
  `name` varchar(100) DEFAULT '' COMMENT '名称',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `address` varchar(500) DEFAULT '' COMMENT '地理位置',
  `labels` varchar(500) DEFAULT '' COMMENT '标签,逗号分隔',
  `resume` text COMMENT '酒店介绍',
  `tel` varchar(50) DEFAULT '' COMMENT '联系电话',
  `city` varchar(200) DEFAULT '' COMMENT '所在城市',
  `cover_url` varchar(500) DEFAULT '' COMMENT '封面图片地址',
  `recommend_word` varchar(100) DEFAULT '' COMMENT '酒店介绍词',
  `level` int(3) DEFAULT '0' COMMENT '星级',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `bs_hotel` */

insert  into `bs_hotel`(`id`,`company_id`,`create_time`,`create_user_id`,`deleted`,`update_time`,`update_user_id`,`canceled`,`code`,`name`,`remark`,`address`,`labels`,`resume`,`tel`,`city`,`cover_url`,`recommend_word`,`level`) values ('2c91d18e6e9fb4d2016ea081dad70004',NULL,1574650895062,'4865dad0e30f11e9a426cfca894de597',1,1574652350967,'4865dad0e30f11e9a426cfca894de597',1,'122','1234酒店',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0),('2c91d18e6e9fb4d2016ea09940f4000a',NULL,1574652428531,'4865dad0e30f11e9a426cfca894de597',0,1574749681300,'4865dad0e30f11e9a426cfca894de597',0,'1231','1酒店',NULL,'秀南街','wiiw','<p>1<img alt=\"APP logo-02.png\">11 恢诡谲怪hgjgsh</p><p>1</p>','15919919206','深圳','http://hotel.utour.xin/uploadFiles/mainpic/27da2b7f01114384bffc2c752fb64a8c.png',NULL,2),('2c91d18e6ea0bb7a016ea11d76bc0001',NULL,1574661093049,'4865dad0e30f11e9a426cfca894de597',0,1574663018771,'2c91d18e6ea0bb7a016ea11d76df0002',0,'wns','威尼斯马格拉美居酒店',NULL,'南山区深圳湾科技生态园10栋B座4楼D02','休闲度假','<p>2010年开业，2010年装修，共有99间房</p><p>对于想要捕捉马格拉城市风采的旅客来说，威尼斯马格拉美爵酒店(Hotel Mercure Venezia-Marghera)是一个理想的选择。MESTRE火车站位于距离该酒店大约2km的地方。著名的景点Spiaggia di Ponente、Chiesa dei Santi Francesco e Chiara d＇Assisi和Bar Zenit均可步行很短距离到达。从酒店到Parrocchia Santa Maria Immacolata di Lourdes游览很方便，UCI Cinemas和Villa Pozzi也均在附近。</p><p>客房内的所有设施都是经过精心的考虑和安排，空调在满足您入住需求的同时又能增添家的温馨感。在餐厅服务方面，酒店西餐厅会提供美食。酒吧给旅客提供了一个舒适的环境，可供休憩。旅客想要在自己的房间边听音乐边享受美食，只需呼叫送餐服务。若是觉得酒店的餐饮无法满足您挑剔的味蕾，不妨去附近的Ristorante Da Tura Hotel Bologna Stazione（西餐）或Restaurant da Bepi Venesian（西餐）品尝下一流的推荐美食。</p><p>酒店提供的休闲设施，旨在为旅客营造多姿多彩、奢华完美的住宿体验。酒店配备有会议厅，可供旅客使用。品质保证的礼宾服务，让您真正体验宾至如归的享受。酒店客人可以额外使用停车场。</p>','0755-518000','深圳 龙岗','http://hotel.utour.xin/uploadFiles/mainpic/4902c0ee426144b6b7bbfcedfec7cfd8.jpg','威尼斯马格拉美爵酒店一个理想的选择',0),('402880f56e020f14016e027a99d3000a',NULL,1571999619538,'4865dad0e30f11e9a426cfca894de597',0,1574749994498,'4865dad0e30f11e9a426cfca894de597',0,'2019001','曼谷文华东方酒店',NULL,'南山区深圳湾科技生态园10栋B座4楼D02','曼谷,东方公寓,免费停车,累积晚数','<p><span style=\"color: rgb(85, 85, 85);\">.谷文华东方酒店的口碑服务可追溯到 125 年前，当时酒店以水手旅馆的身份首度开门迎客，就坐落于湄南河畔。作为旅游黄金时代的典型代表，该酒店可以最高的水准满足住客的需求。最近退休的总经理 Kurt Wachtveitl 在该酒店服务 41 年，因致力于使酒店在舒适性、风格和典雅方面卓尔不群，在业内享有传奇性的声誉.</span><br></p><p><span style=\"color: rgb(85, 85, 85);\"><span style=\"color: rgb(85, 85, 85);\">曼谷东方公寓地处树木掩映的街区，靠近美国大使馆、暹罗商务区和是隆商务区，是您体验清幽住宿环境、乐享购物之旅的理想之选。酒店大堂色彩亮丽，以各式鲜花装点；泳池区时尚温馨，甲板酒吧服务热情；小屋浪漫舒适；健身中心小巧精致，配备先进的 TechnoGym 健身器材及重量训练器材</span><br></span></p><p><span style=\"color: rgb(85, 85, 85);\"><span style=\"color: rgb(85, 85, 85);\"><img alt=\"logo - 副本.png\" src=\"http://static.asiawebdirect.com/m/cn/bangkok/portals/bangkok-com/homepage/top10/top10-luxury-hotels/pagePropertiesImage/the-siam-1200.jpg\" width=\"500\" height=\"333.3333333333333\"><br></span></span></p>','13926576007','深圳','http://static.asiawebdirect.com/m/cn/bangkok/portals/bangkok-com/homepage/top10/top10-luxury-hotels/allParagraphs/0/top10Set/0/image/mandarin-oriental.jpg','靠近暹罗天地购物中心的豪华酒店，设有SPA',4),('4028abf16eca6a60016eca9c00a00000',NULL,1575357251740,'4865dad0e30f11e9a426cfca894de597',1,1575357284897,'4865dad0e30f11e9a426cfca894de597',1,'002','实力酒店',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0),('4028abf16eca6a60016ecaba38620003',NULL,1575359232097,'4865dad0e30f11e9a426cfca894de597',1,1575374886636,'4865dad0e30f11e9a426cfca894de597',0,'002','测试版',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0),('ff8080816e98ec80016e98f0cd2d0002',NULL,1574523948332,'4865dad0e30f11e9a426cfca894de597',0,1574569729004,'4865dad0e30f11e9a426cfca894de597',1,'001','东莞酒店',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0);

/*Table structure for table `bs_hotel_consumer` */

DROP TABLE IF EXISTS `bs_hotel_consumer`;

CREATE TABLE `bs_hotel_consumer` (
  `id` varchar(36) NOT NULL,
  `company_id` varchar(36) DEFAULT NULL COMMENT '组织机构(公司Id)',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(36) DEFAULT NULL COMMENT '创建者ID',
  `deleted` int(2) DEFAULT '0' COMMENT '是否已删除',
  `update_time` bigint(20) DEFAULT NULL COMMENT '更新时间',
  `update_user_id` varchar(36) DEFAULT NULL COMMENT '更新者ID',
  `canceled` int(2) DEFAULT '0' COMMENT '是否已禁用 0-未禁用 1-已禁用',
  `consumer_idea` varchar(1000) DEFAULT '' COMMENT '客户建议',
  `consumer_name` varchar(100) DEFAULT '' COMMENT '客户名称',
  `consumer_phone` varchar(100) DEFAULT '' COMMENT '客户电话',
  `consumer_price` varchar(100) DEFAULT '' COMMENT '客户消费',
  `hotel_id` varchar(32) DEFAULT '' COMMENT '酒店id',
  `consumer_idea1111` varchar(1000) DEFAULT '' COMMENT '客户建议',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `bs_hotel_consumer` */

/*Table structure for table `bs_hotel_foods` */

DROP TABLE IF EXISTS `bs_hotel_foods`;

CREATE TABLE `bs_hotel_foods` (
  `id` varchar(36) NOT NULL,
  `company_id` varchar(36) DEFAULT NULL COMMENT '组织机构(公司Id)',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(36) DEFAULT NULL COMMENT '创建者ID',
  `deleted` int(2) DEFAULT '0' COMMENT '是否已删除',
  `update_time` bigint(20) DEFAULT NULL COMMENT '更新时间',
  `update_user_id` varchar(36) DEFAULT NULL COMMENT '更新者ID',
  `canceled` int(2) DEFAULT '0' COMMENT '是否已禁用 0-未禁用 1-已禁用',
  `food_name` varchar(1000) DEFAULT '' COMMENT '����',
  `food_price` varchar(1000) DEFAULT '' COMMENT '��Ǯ',
  `food_type` varchar(32) DEFAULT '' COMMENT '������� 1-С�� 2-�ײ� 3-��ˮ',
  `hotel_id` varchar(32) DEFAULT '' COMMENT '�Ƶ����',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `bs_hotel_foods` */

/*Table structure for table `bs_hotel_photo` */

DROP TABLE IF EXISTS `bs_hotel_photo`;

CREATE TABLE `bs_hotel_photo` (
  `id` varchar(36) NOT NULL,
  `company_id` varchar(36) DEFAULT NULL COMMENT '组织机构(公司Id)',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(36) DEFAULT NULL COMMENT '创建者ID',
  `deleted` int(2) DEFAULT '0' COMMENT '是否已删除',
  `update_time` bigint(20) DEFAULT NULL COMMENT '更新时间',
  `update_user_id` varchar(36) DEFAULT NULL COMMENT '更新者ID',
  `canceled` int(2) DEFAULT '0' COMMENT '是否已禁用 0-未禁用 1-已禁用',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `sort` int(4) DEFAULT '99' COMMENT '排序号',
  `url` varchar(500) DEFAULT '' COMMENT '图片网址',
  `hotel_id` varchar(36) DEFAULT NULL COMMENT '酒店id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `bs_hotel_photo` */

insert  into `bs_hotel_photo`(`id`,`company_id`,`create_time`,`create_user_id`,`deleted`,`update_time`,`update_user_id`,`canceled`,`remark`,`sort`,`url`,`hotel_id`) values ('2c91d18e6ea0b1d8016ea0b348710000',NULL,1574654134382,'2c91d18e6e9fb4d2016ea09940fa000b',0,1574654134382,'2c91d18e6e9fb4d2016ea09940fa000b',0,'',0,'http://hotel.utour.xin/uploadFiles/hotelpics/a755e5ec14c5478da39063f2552077f6.png','2c91d18e6e9fb4d2016ea09940f4000a'),('2c91d18e6ea0bb7a016ea122d23e0004',NULL,1574661444158,'2c91d18e6ea0bb7a016ea11d76df0002',0,1574661444158,'2c91d18e6ea0bb7a016ea11d76df0002',0,'',0,'http://hotel.utour.xin/uploadFiles/hotelpics/7f0f29e4261e42abadd3554999437ffb.jpg','2c91d18e6ea0bb7a016ea11d76bc0001'),('2c91d18e6ea0bb7a016ea122d2400005',NULL,1574661444159,'2c91d18e6ea0bb7a016ea11d76df0002',0,1574661444159,'2c91d18e6ea0bb7a016ea11d76df0002',0,'',1,'http://hotel.utour.xin/uploadFiles/hotelpics/221d2741fdcc4c25a37874fbb03f730b.jpg','2c91d18e6ea0bb7a016ea11d76bc0001'),('2c91d18e6ea0bb7a016ea122d2410006',NULL,1574661444160,'2c91d18e6ea0bb7a016ea11d76df0002',0,1574661444160,'2c91d18e6ea0bb7a016ea11d76df0002',0,'',2,'http://hotel.utour.xin/uploadFiles/hotelpics/7f25896b4503492ab2555454504286de.jpg','2c91d18e6ea0bb7a016ea11d76bc0001'),('2c91d18e6ea0bb7a016ea122d2420007',NULL,1574661444162,'2c91d18e6ea0bb7a016ea11d76df0002',0,1574661444162,'2c91d18e6ea0bb7a016ea11d76df0002',0,'',3,'http://hotel.utour.xin/uploadFiles/hotelpics/0026909f82ff4c22a391bb6fe2b60f9d.jpg','2c91d18e6ea0bb7a016ea11d76bc0001'),('40289f5e6e0922cb016e0aef82470006',NULL,1572141498950,'402880f56e020f14016e02890e90000e',0,1572141498950,'402880f56e020f14016e02890e90000e',0,'',0,'http://static.asiawebdirect.com/m/cn/bangkok/portals/bangkok-com/homepage/top10/top10-luxury-hotels/pagePropertiesImage/the-siam-1200.jpg','402880f56e020f14016e027a99d3000a'),('40289f5e6e0922cb016e0aef824d0007',NULL,1572141498956,'402880f56e020f14016e02890e90000e',0,1572141498956,'402880f56e020f14016e02890e90000e',0,'',1,'https://ac-q.static.booking.cn/images/hotel/max1024x768/167/167821082.jpg','402880f56e020f14016e027a99d3000a'),('40289f5e6e0922cb016e0aef82570008',NULL,1572141498965,'402880f56e020f14016e02890e90000e',0,1572141498965,'402880f56e020f14016e02890e90000e',0,'',2,'http://www.xxyo.com/pictures/chengdu/zfhg2.jpg','402880f56e020f14016e027a99d3000a');

/*Table structure for table `bs_hotel_policy` */

DROP TABLE IF EXISTS `bs_hotel_policy`;

CREATE TABLE `bs_hotel_policy` (
  `id` varchar(36) NOT NULL,
  `company_id` varchar(36) DEFAULT NULL COMMENT '组织机构(公司Id)',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(36) DEFAULT NULL COMMENT '创建者ID',
  `deleted` int(2) DEFAULT '0' COMMENT '是否已删除',
  `update_time` bigint(20) DEFAULT NULL COMMENT '更新时间',
  `update_user_id` varchar(36) DEFAULT NULL COMMENT '更新者ID',
  `canceled` int(2) DEFAULT '0' COMMENT '是否已禁用 0-未禁用 1-已禁用',
  `addbed` varchar(500) DEFAULT '' COMMENT '儿童及加床政策',
  `breakfast` varchar(1000) DEFAULT '' COMMENT '早餐信息',
  `checkin_time` varchar(20) DEFAULT '' COMMENT '入住时间',
  `checkout_time` varchar(20) DEFAULT '' COMMENT '离店时间',
  `hotel_id` varchar(32) DEFAULT '' COMMENT '酒店外键',
  `hotel_notice` varchar(2000) DEFAULT '' COMMENT '酒店提示',
  `paytypes` varchar(500) DEFAULT '' COMMENT '可用支付方式',
  `pet` varchar(500) DEFAULT '' COMMENT '宠物',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKjp6gm1w1oqlrb0o0e8yxwmodo` (`hotel_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `bs_hotel_policy` */

insert  into `bs_hotel_policy`(`id`,`company_id`,`create_time`,`create_user_id`,`deleted`,`update_time`,`update_user_id`,`canceled`,`addbed`,`breakfast`,`checkin_time`,`checkout_time`,`hotel_id`,`hotel_notice`,`paytypes`,`pet`) values ('2c91d18e6e9fb4d2016ea0a4cf2e000e',NULL,1574653185838,'2c91d18e6e9fb4d2016ea09940fa000b',0,1574653185838,'2c91d18e6e9fb4d2016ea09940fa000b',0,'+50','自助餐','14:00','12:00','2c91d18e6e9fb4d2016ea09940f4000a','请提早\n','现金 银行卡 信用卡','不可携带宠物'),('2c91d18e6ea0bb7a016ea12724f70009',NULL,1574661727479,'2c91d18e6ea0bb7a016ea11d76df0002',0,1574661727479,'2c91d18e6ea0bb7a016ea11d76df0002',0,'酒店允许携带儿童入住\n每间客房最多容纳1名12岁及以下儿童，和成人共用现有床铺。','早餐类型：美式\n形式：自助餐\n价格：EUR  10/人\n营业时间：星期一、二、三、四、五 07:00至10:00\n星期六、日 07:00至11:00','12:00','12:00','2c91d18e6ea0bb7a016ea11d76bc0001','无','银联',''),('40289f5e6e06b6bb016e06c572ed0007',NULL,1572071633602,'402880f56e020f14016e02890e90000e',0,1577103709082,'402880f56e020f14016e02890e90000e',0,'无儿童床','自助式早餐需另收费，成人大约 THB 1500，儿童大约 THB 600','15:00','12:00','402880f56e020f14016e027a99d3000a','酒店可提供连通房/相邻房，但需视空房情况而定。住客可通过预订确认上的联系信息向酒店提出要求。\n12 岁以下的住客不允许进入 SPA 场所。\n请注意，文化规范和住客政策可能因国家/地区和住宿而有所不同\n','中国银联和国际信信用卡','不可携带宠物等');

/*Table structure for table `bs_hotel_room` */

DROP TABLE IF EXISTS `bs_hotel_room`;

CREATE TABLE `bs_hotel_room` (
  `id` varchar(36) NOT NULL,
  `company_id` varchar(36) DEFAULT NULL COMMENT '组织机构(公司Id)',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(36) DEFAULT NULL COMMENT '创建者ID',
  `deleted` int(2) DEFAULT '0' COMMENT '是否已删除',
  `update_time` bigint(20) DEFAULT NULL COMMENT '更新时间',
  `update_user_id` varchar(36) DEFAULT NULL COMMENT '更新者ID',
  `canceled` int(2) DEFAULT '0' COMMENT '是否已禁用 0-未禁用 1-已禁用',
  `area` varchar(4) DEFAULT '' COMMENT '面积(平方)',
  `bed_resume` varchar(50) DEFAULT '' COMMENT '床位情况',
  `person_max` varchar(50) DEFAULT '' COMMENT '最大人数',
  `remark` varchar(100) DEFAULT '' COMMENT '备注',
  `type_name` varchar(500) DEFAULT '' COMMENT '房型名称',
  `hotel_id` varchar(36) DEFAULT NULL COMMENT '酒店id',
  `cover_url` varchar(500) DEFAULT '' COMMENT '封面图片地址',
  `bathroom_device` text COMMENT '浴室设施(逗号分隔)',
  `food_device` text COMMENT '食品饮料',
  `room_device` text COMMENT '房间设施(逗号分隔)',
  `member_price` decimal(10,2) DEFAULT '0.00' COMMENT '会员价格',
  `plan_count` int(4) DEFAULT '0' COMMENT '可销售数量',
  `price` decimal(10,2) DEFAULT '0.00' COMMENT '门市价格',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `bs_hotel_room` */

insert  into `bs_hotel_room`(`id`,`company_id`,`create_time`,`create_user_id`,`deleted`,`update_time`,`update_user_id`,`canceled`,`area`,`bed_resume`,`person_max`,`remark`,`type_name`,`hotel_id`,`cover_url`,`bathroom_device`,`food_device`,`room_device`,`member_price`,`plan_count`,`price`) values ('2c91d18e6e3a5203016e48d16550000a',NULL,1573179712848,'402880f56e020f14016e02890e90000e',0,1573179899405,'402880f56e020f14016e02890e90000e',0,'33','两张1.2','2',NULL,'双床房','402880f56e020f14016e027a99d3000a','http://hotel.utour.xin/uploadFiles/roompic/dc19019cf22448448908bdc64d6d95ae.png','','','','400.00',10,'500.00'),('2c91d18e6e9fb4d2016ea0a5502a000f',NULL,1574653218858,'2c91d18e6e9fb4d2016ea09940fa000b',0,1574654790953,'2c91d18e6e9fb4d2016ea09940fa000b',0,'30','两张1.2M','2人',NULL,'双床房','2c91d18e6e9fb4d2016ea09940f4000a','','','','','450.00',10,'500.00'),('2c91d18e6e9fb4d2016ea0a67e780010',NULL,1574653296248,'2c91d18e6e9fb4d2016ea09940fa000b',0,1574654801943,'2c91d18e6e9fb4d2016ea09940fa000b',0,'35','一张2M','2',NULL,'大床房','2c91d18e6e9fb4d2016ea09940f4000a','','','','','500.00',20,'550.00'),('2c91d18e6ea0bb7a016ea12d2668000a',NULL,1574662121063,'2c91d18e6ea0bb7a016ea11d76df0002',0,1574662136579,'2c91d18e6ea0bb7a016ea11d76df0002',0,'23','2张1.5M床','2人',NULL,'标双','2c91d18e6ea0bb7a016ea11d76bc0001','http://hotel.utour.xin/uploadFiles/roompic/e7b7c9e7d5b346f3bd65e911587877b6.jpg','吹风机,牙具,浴盆','啤酒','免费WIFI','100.00',20,'395.00'),('402880ec7187a486017187b41cc90000',NULL,1587119660228,'402880f56e020f14016e02890e90000e',1,1587119757686,'402880f56e020f14016e02890e90000e',0,'20','两张2米','2',NULL,'测试','402880f56e020f14016e027a99d3000a','','独卫','提供早餐','空调',NULL,NULL,NULL),('402880ec7187a486017187b6d5fd0002',NULL,1587119838716,'402880f56e020f14016e02890e90000e',0,1587119838716,'402880f56e020f14016e02890e90000e',0,'20','2张1.5','2',NULL,'测试','402880f56e020f14016e027a99d3000a','','安抚','元','元',NULL,NULL,NULL),('40289f5e6e0cd038016e0cd23b3e0000',NULL,1572173134624,'402880f56e020f14016e02890e90000e',0,1572443011298,'402880f56e020f14016e02890e90000e',0,'25','1张1.8m的大床','2人',NULL,'豪华客房(Premier)','402880f56e020f14016e027a99d3000a','https://thumbnails.trvl-media.com/5RZFkqHuB3UtaP6le73rHLqv1cM=/773x530/smart/filters:quality(60)/images.trvl-media.com/hotels/1000000/20000/14000/13953/ed0c4861_z.jpg','22]','rr','23,sdf,22,3','2.00',5,'125.00'),('40289f5e6e0cd038016e0daec76a000f',NULL,1572187588454,'402880f56e020f14016e02890e90000e',0,1573179824613,'402880f56e020f14016e02890e90000e',0,'25','1张1.5m','2人',NULL,'套房(Selandia)','402880f56e020f14016e027a99d3000a','https://thumbnails.trvl-media.com/1HCFjdaq2FZ2e67qvMM9VR0q6uk=/773x530/smart/filters:quality(60)/images.trvl-media.com/hotels/1000000/20000/14000/13953/40ec2b8f_z.jpg','有','热水壶','什么都没有,234','1900.00',3,'2000.00'),('40289f5e6e0cd038016e0db0729e0012',NULL,1572187697820,'402880f56e020f14016e02890e90000e',1,1572187714017,'402880f56e020f14016e02890e90000e',0,'234','234','234',NULL,'24','402880f56e020f14016e027a99d3000a','https://thumbnails.trvl-media.com/9mIDL-Sq0byACi6KoHdVllH58VI=/773x530/smart/filters:quality(60)/images.trvl-media.com/hotels/1000000/20000/14000/13953/a28ee13a_z.jpg','123','1234','2134','0.00',0,'0.00'),('4028abf16ecbc303016ecbc82a700000',NULL,1575376923241,'402880f56e020f14016e02890e90000e',1,1575377063220,'402880f56e020f14016e02890e90000e',0,'100','2zhang','3',NULL,'测试房','402880f56e020f14016e027a99d3000a','','ffagf,af','afa','afef',NULL,NULL,NULL);

/*Table structure for table `bs_hotel_setting` */

DROP TABLE IF EXISTS `bs_hotel_setting`;

CREATE TABLE `bs_hotel_setting` (
  `id` varchar(36) NOT NULL,
  `company_id` varchar(36) DEFAULT NULL COMMENT '组织机构(公司Id)',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(36) DEFAULT NULL COMMENT '创建者ID',
  `deleted` int(2) DEFAULT '0' COMMENT '是否已删除',
  `update_time` bigint(20) DEFAULT NULL COMMENT '更新时间',
  `update_user_id` varchar(36) DEFAULT NULL COMMENT '更新者ID',
  `canceled` int(2) DEFAULT '0' COMMENT '是否已禁用 0-未禁用 1-已禁用',
  `biz_service` varchar(500) DEFAULT '' COMMENT '商务服务',
  `child_setting` varchar(2000) DEFAULT '' COMMENT '儿童设施',
  `common_service` varchar(500) DEFAULT '' COMMENT '通用设施',
  `desk_service` varchar(500) DEFAULT '' COMMENT '前台服务',
  `hotel_id` varchar(32) DEFAULT '' COMMENT '网络酒店外键',
  `network` varchar(20) DEFAULT '' COMMENT '网络',
  `other_service` varchar(500) DEFAULT '' COMMENT '其他设施',
  `relax_setting` varchar(1000) DEFAULT '' COMMENT '休闲娱乐',
  `repast_service` varchar(500) DEFAULT '' COMMENT '餐饮服务',
  `traffic_service` varchar(20) DEFAULT '' COMMENT '交通服务',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKp9r3wwut6hewh90jdltoa63ll` (`hotel_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `bs_hotel_setting` */

insert  into `bs_hotel_setting`(`id`,`company_id`,`create_time`,`create_user_id`,`deleted`,`update_time`,`update_user_id`,`canceled`,`biz_service`,`child_setting`,`common_service`,`desk_service`,`hotel_id`,`network`,`other_service`,`relax_setting`,`repast_service`,`traffic_service`) values ('2c91d18e6e9fb4d2016ea0a3fc14000d',NULL,1574653131795,'2c91d18e6e9fb4d2016ea09940fa000b',0,1574653131795,'2c91d18e6e9fb4d2016ea09940fa000b',0,'无','无','健身房','中英文','2c91d18e6e9fb4d2016ea09940f4000a','wifi','无','游泳池','自助餐','接驳车'),('2c91d18e6ea0bb7a016ea12548670008',NULL,1574661605479,'2c91d18e6ea0bb7a016ea11d76df0002',0,1574663132682,'2c91d18e6ea0bb7a016ea11d76df0002',0,'行李寄存','行李寄存','酒吧,咖啡厅','收费停车场,24小时前台','2c91d18e6ea0bb7a016ea11d76bc0001','WIFI,房间宽带,用区WiFi','叫醒服务','健身房,游泳池','中餐厅,西餐厅','的士服务'),('40289f5e6e06ed4e016e06f0c5ac0000',NULL,1572074472869,'402880f56e020f14016e02890e90000e',0,1573179419856,'402880f56e020f14016e02890e90000e',0,'商务中心','商务中心','图书馆','24小时前台服务,礼宾服务','402880f56e020f14016e027a99d3000a','免费Wifi,免费宽带','自动柜员机,指定吸烟区','室外游泳池,健身设施,室外网球场,全套 SPA 服务,SPA 护理室,健身课程,普拉提课程,瑜伽课程/指导,免费池畔茅屋','1234,会议空间,会议室,会议中心','接机服务,的士预定,景区接驳');

/*Table structure for table `bs_hotel_worker` */

DROP TABLE IF EXISTS `bs_hotel_worker`;

CREATE TABLE `bs_hotel_worker` (
  `id` varchar(36) NOT NULL,
  `company_id` varchar(36) DEFAULT NULL COMMENT '组织机构(公司Id)',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(36) DEFAULT NULL COMMENT '创建者ID',
  `deleted` int(2) DEFAULT '0' COMMENT '是否已删除',
  `update_time` bigint(20) DEFAULT NULL COMMENT '更新时间',
  `update_user_id` varchar(36) DEFAULT NULL COMMENT '更新者ID',
  `canceled` int(2) DEFAULT '0' COMMENT '是否已禁用 0-未禁用 1-已禁用',
  `hotel_id` varchar(32) DEFAULT '' COMMENT '�Ƶ����',
  `worker_age` varchar(10) DEFAULT '' COMMENT 'Ա������',
  `worker_name` varchar(100) DEFAULT '' COMMENT 'Ա������',
  `worker_phone` varchar(100) DEFAULT '' COMMENT 'Ա���绰 ',
  `worker_price` varchar(100) DEFAULT '' COMMENT 'Ա������',
  `worker_type` varchar(32) DEFAULT '' COMMENT 'ְλ��� 1-�߹� 2-�鳤 3-��ͨԱ��',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `bs_hotel_worker` */

/*Table structure for table `bs_organization` */

DROP TABLE IF EXISTS `bs_organization`;

CREATE TABLE `bs_organization` (
  `id` varchar(36) NOT NULL,
  `company_id` varchar(36) DEFAULT NULL COMMENT '组织机构(公司Id)',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(36) DEFAULT NULL COMMENT '创建者ID',
  `deleted` int(2) DEFAULT '0' COMMENT '是否已删除',
  `update_time` bigint(20) DEFAULT NULL COMMENT '更新时间',
  `update_user_id` varchar(36) DEFAULT NULL COMMENT '更新者ID',
  `canceled` int(2) DEFAULT '0' COMMENT '是否已禁用 0-未禁用 1-已禁用',
  `address` varchar(200) DEFAULT '' COMMENT '负责人地址',
  `code` varchar(10) DEFAULT '' COMMENT '机构编码,erp库存地点编码',
  `memo` varchar(100) DEFAULT '' COMMENT '备注',
  `name` varchar(50) DEFAULT '' COMMENT '名称',
  `pid` varchar(36) DEFAULT '' COMMENT '父级id',
  `sort` int(5) DEFAULT '0' COMMENT '排序',
  `tel` varchar(20) DEFAULT '' COMMENT '负责人电话',
  `type` int(2) DEFAULT '0' COMMENT '组织机构类型 1-部门 2-仓库',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `bs_organization` */

insert  into `bs_organization`(`id`,`company_id`,`create_time`,`create_user_id`,`deleted`,`update_time`,`update_user_id`,`canceled`,`address`,`code`,`memo`,`name`,`pid`,`sort`,`tel`,`type`) values ('cde2e5c4a6ee11e9b80900ff3ae20cce','cde2e5c4a6ee11e9b80900ff3ae20cce',1563160639,'4cb3e6f5e1fe45738895d0fd416e43ac',0,1563160639,'4cb3e6f5e1fe45738895d0fd416e43ac',0,'','1','','测试公司','0',0,'0',1),('dd3fdd20a6ee11e9b80900ff3ae20cce','cde2e5c4a6ee11e9b80900ff3ae20cce',1563160639,'4cb3e6f5e1fe45738895d0fd416e43ac',0,1571972320247,'4865dad0e30f11e9a426cfca894de597',0,'',NULL,'','管理部门','cde2e5c4a6ee11e9b80900ff3ae20cce',10,'0',2);

/*Table structure for table `bs_room_device` */

DROP TABLE IF EXISTS `bs_room_device`;

CREATE TABLE `bs_room_device` (
  `id` varchar(36) NOT NULL,
  `company_id` varchar(36) DEFAULT NULL COMMENT '组织机构(公司Id)',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(36) DEFAULT NULL COMMENT '创建者ID',
  `deleted` int(2) DEFAULT '0' COMMENT '是否已删除',
  `update_time` bigint(20) DEFAULT NULL COMMENT '更新时间',
  `update_user_id` varchar(36) DEFAULT NULL COMMENT '更新者ID',
  `canceled` int(2) DEFAULT '0' COMMENT '是否已禁用 0-未禁用 1-已禁用',
  `bathroom_device` text COMMENT '浴室设施(逗号分隔)',
  `food` text COMMENT '食品饮料',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `room_device` text COMMENT '房间设施(逗号分隔)',
  `hotel_room_id` varchar(36) DEFAULT NULL COMMENT '酒店房型id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `bs_room_device` */

/*Table structure for table `bs_room_photo` */

DROP TABLE IF EXISTS `bs_room_photo`;

CREATE TABLE `bs_room_photo` (
  `id` varchar(36) NOT NULL,
  `company_id` varchar(36) DEFAULT NULL COMMENT '组织机构(公司Id)',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(36) DEFAULT NULL COMMENT '创建者ID',
  `deleted` int(2) DEFAULT '0' COMMENT '是否已删除',
  `update_time` bigint(20) DEFAULT NULL COMMENT '更新时间',
  `update_user_id` varchar(36) DEFAULT NULL COMMENT '更新者ID',
  `canceled` int(2) DEFAULT '0' COMMENT '是否已禁用 0-未禁用 1-已禁用',
  `remark` varchar(100) DEFAULT '' COMMENT '备注',
  `sort` int(4) DEFAULT '99' COMMENT '排序号',
  `url` varchar(500) DEFAULT '' COMMENT '图片网址',
  `hotel_room_id` varchar(36) DEFAULT NULL COMMENT '酒店房型id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `bs_room_photo` */

insert  into `bs_room_photo`(`id`,`company_id`,`create_time`,`create_user_id`,`deleted`,`update_time`,`update_user_id`,`canceled`,`remark`,`sort`,`url`,`hotel_room_id`) values ('2c91d18e6e3a5203016e48d26783000c',NULL,1573179778946,'402880f56e020f14016e02890e90000e',0,1573179778946,'402880f56e020f14016e02890e90000e',0,'',0,'http://hotel.utour.xin/uploadFiles/roompics/d1c07d58a9ff4be49ba55ddc34259b81.jpg','2c91d18e6e3a5203016e48d16550000a'),('2c91d18e6e3a5203016e48d26784000d',NULL,1573179778948,'402880f56e020f14016e02890e90000e',0,1573179778948,'402880f56e020f14016e02890e90000e',0,'',1,'http://hotel.utour.xin/uploadFiles/roompics/ff5022573152482b9d38a39206c49d28.jpg','2c91d18e6e3a5203016e48d16550000a'),('2c91d18e6ea0bb7a016ea12d268f000b',NULL,1574662121102,'2c91d18e6ea0bb7a016ea11d76df0002',0,1574662121102,'2c91d18e6ea0bb7a016ea11d76df0002',0,'',0,'http://hotel.utour.xin/uploadFiles/roompics/bdd67665a7fd4dac8d73cdff328eabb1.jpg','2c91d18e6ea0bb7a016ea12d2668000a'),('2c91d18e6ea0bb7a016ea12d2691000c',NULL,1574662121104,'2c91d18e6ea0bb7a016ea11d76df0002',0,1574662121104,'2c91d18e6ea0bb7a016ea11d76df0002',0,'',1,'http://hotel.utour.xin/uploadFiles/roompics/04e89f89717c4946a7597ecc6a293471.jpg','2c91d18e6ea0bb7a016ea12d2668000a'),('2c91d18e6ea0bb7a016ea12d2692000d',NULL,1574662121106,'2c91d18e6ea0bb7a016ea11d76df0002',0,1574662121106,'2c91d18e6ea0bb7a016ea11d76df0002',0,'',2,'http://hotel.utour.xin/uploadFiles/roompics/951b5a71785443509029b1dc1afe4701.jpg','2c91d18e6ea0bb7a016ea12d2668000a'),('402880f56e1a703a016e1ce7f9110001',NULL,1572442994958,'402880f56e020f14016e02890e90000e',0,1572442994958,'402880f56e020f14016e02890e90000e',0,'',0,'https://thumbnails.trvl-media.com/9mIDL-Sq0byACi6KoHdVllH58VI=/773x530/smart/filters:quality(60)/images.trvl-media.com/hotels/1000000/20000/14000/13953/a28ee13a_z.jpg','40289f5e6e0cd038016e0daec76a000f'),('402880f56e1a703a016e1ce8392c0002',NULL,1572443011370,'402880f56e020f14016e02890e90000e',0,1572443011370,'402880f56e020f14016e02890e90000e',0,'',0,'https://thumbnails.trvl-media.com/pe5_NRkjrzE-m49_f9uORRBGeMM=/773x530/smart/filters:quality(60)/images.trvl-media.com/hotels/1000000/20000/14000/13953/6ef69fb2_z.jpg','40289f5e6e0cd038016e0cd23b3e0000');

/*Table structure for table `sys_log` */

DROP TABLE IF EXISTS `sys_log`;

CREATE TABLE `sys_log` (
  `id` varchar(36) NOT NULL,
  `company_id` varchar(36) DEFAULT NULL COMMENT '组织机构(公司Id)',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(36) DEFAULT NULL COMMENT '创建者ID',
  `deleted` int(2) DEFAULT '0' COMMENT '是否已删除',
  `update_time` bigint(20) DEFAULT NULL COMMENT '更新时间',
  `update_user_id` varchar(36) DEFAULT NULL COMMENT '更新者ID',
  `content` varchar(300) DEFAULT '' COMMENT '内容',
  `details` varchar(500) DEFAULT '' COMMENT '详情',
  `ip` varchar(20) DEFAULT '' COMMENT '操作ip',
  `modular` varchar(100) DEFAULT '' COMMENT '模块',
  `state` int(2) DEFAULT '0' COMMENT '操作状态',
  `type` varchar(50) DEFAULT '' COMMENT '类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `sys_log` */

/*Table structure for table `sys_menu` */

DROP TABLE IF EXISTS `sys_menu`;

CREATE TABLE `sys_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(20) DEFAULT '' COMMENT '编码',
  `name` varchar(100) DEFAULT '' COMMENT '名称',
  `pid` int(7) DEFAULT NULL COMMENT '父级id',
  `remarks` varchar(100) DEFAULT '' COMMENT '备注',
  `sort` int(3) DEFAULT '0' COMMENT '排序',
  `state` int(2) DEFAULT '0' COMMENT '状态',
  `style` varchar(20) DEFAULT '' COMMENT '样式',
  `type` int(3) DEFAULT '0' COMMENT '类型',
  `url` varchar(200) DEFAULT '' COMMENT '访问地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4;

/*Data for the table `sys_menu` */

insert  into `sys_menu`(`id`,`code`,`name`,`pid`,`remarks`,`sort`,`state`,`style`,`type`,`url`) values (3,'','系统管理',0,'系统管理',15,0,'am-icon-cog',0,''),(4,'','用户及授权',3,'用户及授权',0,0,'am-icon-user',0,'user/user'),(5,'ROLE_USER_ROLE','角色管理',3,'角色管理',1,0,'am-icon-users',0,'role/role'),(6,'','组织机构管理',3,'组织机构管理',2,0,'am-icon-role',0,'depart/orgManage'),(7,'','基础档案管理',0,'',0,0,'',0,''),(8,'','档案管理',7,'',0,0,'',0,'hotel'),(9,'','酒店简介',13,'',0,0,'',0,'hotel/info'),(10,'','酒店设施',13,'',10,0,'',0,'hotel/setting'),(11,'','入住须知',13,'',15,0,'',0,'hotel/policy'),(12,'','周边情况',13,'',20,0,'',0,'hotel/around'),(13,'','酒店资料',0,'',20,0,'',0,''),(14,'','酒店图片',13,'',5,0,'',0,'hotel/photo'),(15,'','房型和房态',0,'',25,0,'',0,''),(16,'','房型设置',15,'',0,0,'',0,'room'),(17,'','房型报价',15,'',0,0,'',0,'price'),(18,'','横幅管理',7,'',0,0,'',0,'banner'),(19,'','城市管理',7,'',0,0,'',0,'city'),(20,'','订单管理',0,'',30,0,'',0,''),(21,'','客户订单',20,'',0,0,'',0,'bill'),(23,'','会员管理',0,'',5,0,'',0,''),(24,'','会员列表',23,'',0,0,'',0,'member'),(25,'','游客列表',23,'',5,0,'',0,'guest'),(26,'','餐饮信息模块',0,'',40,0,'',0,''),(27,'','餐饮管理',26,'',0,0,'',0,'food'),(28,'','客房信息模块',0,'',45,0,'',0,''),(29,'','房型设置',28,'',0,0,'',0,'room'),(30,'','房型报价',28,'',0,0,'',0,'price'),(31,'','客人信息模块',0,'',50,0,'',0,''),(32,'','客人信息管理',31,'',0,0,'',0,'customer'),(33,'','员工信息模块',0,'',55,0,'',0,''),(34,'','员工信息',33,'',0,0,'',0,'worker'),(35,'','餐饮分区',0,'',60,0,'',0,''),(36,'','特色美食',35,'',0,0,'',0,'food');

/*Table structure for table `sys_organization` */

DROP TABLE IF EXISTS `sys_organization`;

CREATE TABLE `sys_organization` (
  `id` varchar(36) NOT NULL,
  `company_id` varchar(36) DEFAULT NULL COMMENT '组织机构(公司Id)',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(36) DEFAULT NULL COMMENT '创建者ID',
  `deleted` int(2) DEFAULT '0' COMMENT '是否已删除',
  `update_time` bigint(20) DEFAULT NULL COMMENT '更新时间',
  `update_user_id` varchar(36) DEFAULT NULL COMMENT '更新者ID',
  `canceled` int(2) DEFAULT '0' COMMENT '是否已禁用 0-未禁用 1-已禁用',
  `address` varchar(200) DEFAULT '' COMMENT '负责人地址',
  `code` varchar(10) DEFAULT '' COMMENT '机构编码,erp库存地点编码',
  `memo` varchar(100) DEFAULT '' COMMENT '备注',
  `name` varchar(50) DEFAULT '' COMMENT '名称',
  `pid` varchar(36) DEFAULT '' COMMENT '父级id',
  `sort` int(5) DEFAULT '0' COMMENT '排序',
  `tel` varchar(20) DEFAULT '' COMMENT '负责人电话',
  `type` int(2) DEFAULT '0' COMMENT '组织机构类型 1-部门 2-仓库',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `sys_organization` */

insert  into `sys_organization`(`id`,`company_id`,`create_time`,`create_user_id`,`deleted`,`update_time`,`update_user_id`,`canceled`,`address`,`code`,`memo`,`name`,`pid`,`sort`,`tel`,`type`) values ('2c91d18e6e9fb4d2016ea07909040000','cde2e5c4a6ee11e9b80900ff3ae20cce',1574650317056,'4865dad0e30f11e9a426cfca894de597',0,1574650317056,'4865dad0e30f11e9a426cfca894de597',NULL,NULL,NULL,'','1324酒店','cde2e5c4a6ee11e9b80900ff3ae20cce',NULL,NULL,2),('cde2e5c4a6ee11e9b80900ff3ae20cce','cde2e5c4a6ee11e9b80900ff3ae20cce',1563160639,'4cb3e6f5e1fe45738895d0fd416e43ac',0,1563160639,'4cb3e6f5e1fe45738895d0fd416e43ac',0,'','1','','测试公司','0',0,'0',1),('dd3fdd20a6ee11e9b80900ff3ae20cce','cde2e5c4a6ee11e9b80900ff3ae20cce',1563160639,'4cb3e6f5e1fe45738895d0fd416e43ac',0,1571972320247,'4865dad0e30f11e9a426cfca894de597',0,'',NULL,'','管理部门','cde2e5c4a6ee11e9b80900ff3ae20cce',10,'0',2);

/*Table structure for table `sys_role` */

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `id` varchar(36) NOT NULL,
  `company_id` varchar(36) DEFAULT NULL COMMENT '组织机构(公司Id)',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(36) DEFAULT NULL COMMENT '创建者ID',
  `deleted` int(2) DEFAULT '0' COMMENT '是否已删除',
  `update_time` bigint(20) DEFAULT NULL COMMENT '更新时间',
  `update_user_id` varchar(36) DEFAULT NULL COMMENT '更新者ID',
  `canceled` int(2) DEFAULT '0' COMMENT '是否已禁用 0-未禁用 1-已禁用',
  `descripe` varchar(100) DEFAULT '' COMMENT '描述',
  `is_admin` int(2) DEFAULT '0' COMMENT '是否管理员',
  `name` varchar(50) DEFAULT '' COMMENT '名称',
  `type` int(3) DEFAULT '0' COMMENT '类型',
  `code` varchar(20) DEFAULT '' COMMENT '特殊角色编码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `sys_role` */

insert  into `sys_role`(`id`,`company_id`,`create_time`,`create_user_id`,`deleted`,`update_time`,`update_user_id`,`canceled`,`descripe`,`is_admin`,`name`,`type`,`code`) values ('047df2c3a46540869dff9c9de134b2de','cde2e5c4a6ee11e9b80900ff3ae20cce',1569830572,'4865dad0e30f11e9a426cfca894de597',1,1584092399097,'4865dad0e30f11e9a426cfca894de597',0,'',1,'管理员',0,''),('402880ec70d302b00170d34335f50000','cde2e5c4a6ee11e9b80900ff3ae20cce',1584092362221,'4865dad0e30f11e9a426cfca894de597',0,1584092362221,'4865dad0e30f11e9a426cfca894de597',0,'',1,'管理员测试',0,''),('402880f56e020f14016e02883939000b','cde2e5c4a6ee11e9b80900ff3ae20cce',1572000512311,'4865dad0e30f11e9a426cfca894de597',0,1572000512311,'4865dad0e30f11e9a426cfca894de597',0,'',1,'常规权限',0,'HOTELADMIN');

/*Table structure for table `sys_role_permissions` */

DROP TABLE IF EXISTS `sys_role_permissions`;

CREATE TABLE `sys_role_permissions` (
  `id` varchar(36) NOT NULL,
  `company_id` varchar(36) DEFAULT NULL COMMENT '组织机构(公司Id)',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(36) DEFAULT NULL COMMENT '创建者ID',
  `deleted` int(2) DEFAULT '0' COMMENT '是否已删除',
  `update_time` bigint(20) DEFAULT NULL COMMENT '更新时间',
  `update_user_id` varchar(36) DEFAULT NULL COMMENT '更新者ID',
  `menu_id` varchar(36) DEFAULT '' COMMENT '菜单id',
  `role_id` varchar(36) DEFAULT '' COMMENT '角色id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `sys_role_permissions` */

insert  into `sys_role_permissions`(`id`,`company_id`,`create_time`,`create_user_id`,`deleted`,`update_time`,`update_user_id`,`menu_id`,`role_id`) values ('2c981da26dec4ca5016ded4782420049','cde2e5c4a6ee11e9b80900ff3ae20cce',1571643949634,'402881fe6cfb05e9016cfb314be80006',0,1571643949634,'402881fe6cfb05e9016cfb314be80006','26','047df2c3a46540869dff9c9de134b2de'),('2c981da26dec4ca5016ded478243004a','cde2e5c4a6ee11e9b80900ff3ae20cce',1571643949634,'402881fe6cfb05e9016cfb314be80006',0,1571643949634,'402881fe6cfb05e9016cfb314be80006','27','047df2c3a46540869dff9c9de134b2de'),('2c981da26dec4ca5016ded478244004b','cde2e5c4a6ee11e9b80900ff3ae20cce',1571643949635,'402881fe6cfb05e9016cfb314be80006',0,1571643949635,'402881fe6cfb05e9016cfb314be80006','28','047df2c3a46540869dff9c9de134b2de'),('2c981da26dec4ca5016ded478245004c','cde2e5c4a6ee11e9b80900ff3ae20cce',1571643949636,'402881fe6cfb05e9016cfb314be80006',1,1571643949636,'402881fe6cfb05e9016cfb314be80006','28','047df2c3a46540869dff9c9de134b2de'),('402880f56ea5462e016ea57dab570000','cde2e5c4a6ee11e9b80900ff3ae20cce',1574734506837,'4865dad0e30f11e9a426cfca894de597',0,1574734506837,'4865dad0e30f11e9a426cfca894de597','30','047df2c3a46540869dff9c9de134b2de'),('402880f56ea5462e016ea57daba40006','cde2e5c4a6ee11e9b80900ff3ae20cce',1574734506915,'4865dad0e30f11e9a426cfca894de597',1,1574734506915,'4865dad0e30f11e9a426cfca894de597','25','047df2c3a46540869dff9c9de134b2de'),('402880f56ea5462e016ea57daba40008','cde2e5c4a6ee11e9b80900ff3ae20cce',1574734506915,'4865dad0e30f11e9a426cfca894de597',0,1574734506915,'4865dad0e30f11e9a426cfca894de597','3','047df2c3a46540869dff9c9de134b2de'),('402880f56ea5462e016ea57daba40009','cde2e5c4a6ee11e9b80900ff3ae20cce',1574734506915,'4865dad0e30f11e9a426cfca894de597',0,1574734506915,'4865dad0e30f11e9a426cfca894de597','4','047df2c3a46540869dff9c9de134b2de'),('402880f56ea5462e016ea57daba40011','cde2e5c4a6ee11e9b80900ff3ae20cce',1574734506915,'4865dad0e30f11e9a426cfca894de597',0,1574734506915,'4865dad0e30f11e9a426cfca894de597','5','047df2c3a46540869dff9c9de134b2de'),('40289b5e78588b0e0178589955d20003','cde2e5c4a6ee11e9b80900ff3ae20cce',1616394147281,'4865dad0e30f11e9a426cfca894de597',NULL,1616394147281,'4865dad0e30f11e9a426cfca894de597','3','402880ec70d302b00170d34335f50000'),('40289b5e78588b0e0178589955d40004','cde2e5c4a6ee11e9b80900ff3ae20cce',1616394147283,'4865dad0e30f11e9a426cfca894de597',NULL,1616394147283,'4865dad0e30f11e9a426cfca894de597','4','402880ec70d302b00170d34335f50000'),('40289b5e78588b0e0178589955d60005','cde2e5c4a6ee11e9b80900ff3ae20cce',1616394147285,'4865dad0e30f11e9a426cfca894de597',NULL,1616394147285,'4865dad0e30f11e9a426cfca894de597','5','402880ec70d302b00170d34335f50000'),('40289b5e78588b0e0178589955d80006','cde2e5c4a6ee11e9b80900ff3ae20cce',1616394147287,'4865dad0e30f11e9a426cfca894de597',NULL,1616394147287,'4865dad0e30f11e9a426cfca894de597','6','402880ec70d302b00170d34335f50000'),('40289b5e78588b0e0178589955db0007','cde2e5c4a6ee11e9b80900ff3ae20cce',1616394147290,'4865dad0e30f11e9a426cfca894de597',NULL,1616394147290,'4865dad0e30f11e9a426cfca894de597','28','402880ec70d302b00170d34335f50000'),('40289b5e78588b0e0178589955dc0008','cde2e5c4a6ee11e9b80900ff3ae20cce',1616394147292,'4865dad0e30f11e9a426cfca894de597',NULL,1616394147292,'4865dad0e30f11e9a426cfca894de597','29','402880ec70d302b00170d34335f50000'),('40289b5e78588b0e0178589955dd0009','cde2e5c4a6ee11e9b80900ff3ae20cce',1616394147293,'4865dad0e30f11e9a426cfca894de597',NULL,1616394147293,'4865dad0e30f11e9a426cfca894de597','30','402880ec70d302b00170d34335f50000'),('40289b5e785cbccc01785cbdfa820000','cde2e5c4a6ee11e9b80900ff3ae20cce',1616463657600,'402880ec718ad71a01718ad99c000000',NULL,1616463657600,'402880ec718ad71a01718ad99c000000','26','402880f56e020f14016e02883939000b'),('40289b5e785cbccc01785cbdfa8a0001','cde2e5c4a6ee11e9b80900ff3ae20cce',1616463657610,'402880ec718ad71a01718ad99c000000',NULL,1616463657610,'402880ec718ad71a01718ad99c000000','27','402880f56e020f14016e02883939000b'),('40289b5e785cbccc01785cbdfa8d0002','cde2e5c4a6ee11e9b80900ff3ae20cce',1616463657612,'402880ec718ad71a01718ad99c000000',NULL,1616463657612,'402880ec718ad71a01718ad99c000000','28','402880f56e020f14016e02883939000b'),('40289b5e785cbccc01785cbdfa8f0003','cde2e5c4a6ee11e9b80900ff3ae20cce',1616463657614,'402880ec718ad71a01718ad99c000000',NULL,1616463657614,'402880ec718ad71a01718ad99c000000','30','402880f56e020f14016e02883939000b'),('40289b5e785cbccc01785cbdfa910004','cde2e5c4a6ee11e9b80900ff3ae20cce',1616463657617,'402880ec718ad71a01718ad99c000000',NULL,1616463657617,'402880ec718ad71a01718ad99c000000','31','402880f56e020f14016e02883939000b'),('40289b5e785cbccc01785cbdfa930005','cde2e5c4a6ee11e9b80900ff3ae20cce',1616463657619,'402880ec718ad71a01718ad99c000000',NULL,1616463657619,'402880ec718ad71a01718ad99c000000','32','402880f56e020f14016e02883939000b');

/*Table structure for table `sys_time_task` */

DROP TABLE IF EXISTS `sys_time_task`;

CREATE TABLE `sys_time_task` (
  `id` varchar(36) NOT NULL,
  `company_id` varchar(36) DEFAULT NULL COMMENT '组织机构(公司Id)',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(36) DEFAULT NULL COMMENT '创建者ID',
  `deleted` int(2) DEFAULT '0' COMMENT '是否已删除',
  `update_time` bigint(20) DEFAULT NULL COMMENT '更新时间',
  `update_user_id` varchar(36) DEFAULT NULL COMMENT '更新者ID',
  `canceled` int(2) DEFAULT '0' COMMENT '是否已禁用 0-未禁用 1-已禁用',
  `content` varchar(500) DEFAULT '' COMMENT '内容',
  `runstate` int(2) DEFAULT '0' COMMENT '运行状态',
  `runtime` varchar(50) DEFAULT '' COMMENT '执行时间',
  `title` varchar(100) DEFAULT '' COMMENT '标题',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `sys_time_task` */

/*Table structure for table `sys_time_task_result` */

DROP TABLE IF EXISTS `sys_time_task_result`;

CREATE TABLE `sys_time_task_result` (
  `id` varchar(36) NOT NULL,
  `company_id` varchar(36) DEFAULT NULL COMMENT '组织机构(公司Id)',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(36) DEFAULT NULL COMMENT '创建者ID',
  `deleted` int(2) DEFAULT '0' COMMENT '是否已删除',
  `update_time` bigint(20) DEFAULT NULL COMMENT '更新时间',
  `update_user_id` varchar(36) DEFAULT NULL COMMENT '更新者ID',
  `result` varchar(500) DEFAULT '' COMMENT '执行结果',
  `runtime` int(11) DEFAULT '0' COMMENT '执行完成时间',
  `task_id` varchar(36) DEFAULT '' COMMENT '定时任务id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `sys_time_task_result` */

/*Table structure for table `sys_user` */

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `id` varchar(36) NOT NULL,
  `company_id` varchar(36) DEFAULT NULL COMMENT '组织机构(公司Id)',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(36) DEFAULT NULL COMMENT '创建者ID',
  `deleted` int(2) DEFAULT '0' COMMENT '是否已删除',
  `update_time` bigint(20) DEFAULT NULL COMMENT '更新时间',
  `update_user_id` varchar(36) DEFAULT NULL COMMENT '更新者ID',
  `canceled` int(2) DEFAULT '0' COMMENT '是否已禁用 0-未禁用 1-已禁用',
  `age` int(3) DEFAULT '0' COMMENT '年龄',
  `birth_day` varchar(20) DEFAULT '' COMMENT '出生年月日',
  `hotel_id` varchar(36) DEFAULT '' COMMENT '酒店ID(用于酒店用户)',
  `is_update_password` int(2) DEFAULT '0' COMMENT '状态',
  `login_name` varchar(50) DEFAULT '' COMMENT '登录账号',
  `password` varchar(50) DEFAULT '' COMMENT '密码',
  `phone` varchar(20) DEFAULT '' COMMENT '电话',
  `random_str` varchar(10) DEFAULT '' COMMENT '随机字符串',
  `sex` int(1) DEFAULT '0' COMMENT '性别',
  `state` int(1) DEFAULT '0' COMMENT '状态',
  `user_name` varchar(100) DEFAULT '' COMMENT '姓名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `sys_user` */

insert  into `sys_user`(`id`,`company_id`,`create_time`,`create_user_id`,`deleted`,`update_time`,`update_user_id`,`canceled`,`age`,`birth_day`,`hotel_id`,`is_update_password`,`login_name`,`password`,`phone`,`random_str`,`sex`,`state`,`user_name`) values ('402880ec718ad71a01718ad99c000000','cde2e5c4a6ee11e9b80900ff3ae20cce',1587172449274,'4865dad0e30f11e9a426cfca894de597',0,1587172449274,'4865dad0e30f11e9a426cfca894de597',0,NULL,NULL,NULL,NULL,'qhs','ab86ed3516b69b01f05bc1775b27eee2','18438606923','jdw',1,0,'齐海森'),('4865dad0e30f11e9a426cfca894de597','cde2e5c4a6ee11e9b80900ff3ae20cce',1569830572,'4865dad0e30f11e9a426cfca894de597',0,1571981459424,'4865dad0e30f11e9a426cfca894de597',0,0,'','',0,'admin','39dca375882f166ef1837872c165269f','13926576007','pvl',1,0,'管理员');

/*Table structure for table `sys_user_role` */

DROP TABLE IF EXISTS `sys_user_role`;

CREATE TABLE `sys_user_role` (
  `id` varchar(36) NOT NULL,
  `company_id` varchar(36) DEFAULT NULL COMMENT '组织机构(公司Id)',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(36) DEFAULT NULL COMMENT '创建者ID',
  `deleted` int(2) DEFAULT '0' COMMENT '是否已删除',
  `update_time` bigint(20) DEFAULT NULL COMMENT '更新时间',
  `update_user_id` varchar(36) DEFAULT NULL COMMENT '更新者ID',
  `role_id` varchar(36) DEFAULT '' COMMENT '角色id',
  `user_id` varchar(36) DEFAULT '' COMMENT '用户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `sys_user_role` */

insert  into `sys_user_role`(`id`,`company_id`,`create_time`,`create_user_id`,`deleted`,`update_time`,`update_user_id`,`role_id`,`user_id`) values ('40289b5e78588b0e017858944e320001',NULL,1616393817649,'4865dad0e30f11e9a426cfca894de597',0,1616393817649,'4865dad0e30f11e9a426cfca894de597','402880f56e020f14016e02883939000b','402880ec718ad71a01718ad99c000000'),('40289b5e78588b0e01785898b7310002',NULL,1616394106672,'4865dad0e30f11e9a426cfca894de597',0,1616394106672,'4865dad0e30f11e9a426cfca894de597','402880ec70d302b00170d34335f50000','4865dad0e30f11e9a426cfca894de597');

/*Table structure for table `sys_user_token` */

DROP TABLE IF EXISTS `sys_user_token`;

CREATE TABLE `sys_user_token` (
  `id` varchar(36) NOT NULL,
  `company_id` varchar(36) DEFAULT NULL COMMENT '组织机构(公司Id)',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(36) DEFAULT NULL COMMENT '创建者ID',
  `deleted` int(2) DEFAULT '0' COMMENT '是否已删除',
  `update_time` bigint(20) DEFAULT NULL COMMENT '更新时间',
  `update_user_id` varchar(36) DEFAULT NULL COMMENT '更新者ID',
  `over_time` bigint(20) DEFAULT NULL COMMENT '过期时间',
  `token` varchar(36) DEFAULT '' COMMENT '用户Token',
  `user_id` varchar(36) DEFAULT '' COMMENT '用户Id',
  `warehouse_id` varchar(36) DEFAULT '' COMMENT '所属仓库id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `sys_user_token` */

/*Table structure for table `tp_hotel_plan` */

DROP TABLE IF EXISTS `tp_hotel_plan`;

CREATE TABLE `tp_hotel_plan` (
  `id` varchar(36) NOT NULL,
  `company_id` varchar(36) DEFAULT NULL COMMENT '组织机构(公司Id)',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(36) DEFAULT NULL COMMENT '创建者ID',
  `deleted` int(2) DEFAULT '0' COMMENT '是否已删除',
  `update_time` bigint(20) DEFAULT NULL COMMENT '更新时间',
  `update_user_id` varchar(36) DEFAULT NULL COMMENT '更新者ID',
  `canceled` int(2) DEFAULT '0' COMMENT '是否已禁用 0-未禁用 1-已禁用',
  `date` int(10) DEFAULT NULL COMMENT '日期',
  `member_price` decimal(10,2) DEFAULT '0.00' COMMENT '会员价格',
  `plan_count` int(4) DEFAULT '0' COMMENT '可销售数量',
  `price` decimal(10,2) DEFAULT '0.00' COMMENT '门市价格',
  `remark` varchar(100) DEFAULT '' COMMENT '备注',
  `sales_count` int(4) DEFAULT '0' COMMENT '已销售数量',
  `hotel_id` varchar(36) DEFAULT NULL COMMENT '酒店id',
  `hotel_room_id` varchar(36) DEFAULT NULL COMMENT '酒店房型id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `tp_hotel_plan` */

insert  into `tp_hotel_plan`(`id`,`company_id`,`create_time`,`create_user_id`,`deleted`,`update_time`,`update_user_id`,`canceled`,`date`,`member_price`,`plan_count`,`price`,`remark`,`sales_count`,`hotel_id`,`hotel_room_id`) values ('40289b5e78588b0e017858a18dd70028',NULL,1616394685910,'4865dad0e30f11e9a426cfca894de597',0,1616394685910,'4865dad0e30f11e9a426cfca894de597',0,151251,'100.00',2,'110.00',NULL,0,'2c91d18e6e9fb4d2016ea09940f4000a','2c91d18e6e9fb4d2016ea0a5502a000f');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
