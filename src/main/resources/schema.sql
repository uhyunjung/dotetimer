SET foreign_key_checks = 0;
DROP TABLE IF EXISTS USER CASCADE;
DROP TABLE IF EXISTS USER_ROLES CASCADE;
DROP TABLE IF EXISTS USER_SEQ CASCADE;
DROP TABLE IF EXISTS STUDY_GROUP CASCADE;
DROP TABLE IF EXISTS GROUP_JOIN CASCADE;
DROP TABLE IF EXISTS PLAN CASCADE;
DROP TABLE IF EXISTS PLAN_INFO CASCADE;
DROP TABLE IF EXISTS REVIEW CASCADE;
DROP TABLE IF EXISTS REVIEW_LIKE CASCADE;
DROP TABLE IF EXISTS FOLLOW CASCADE;
DROP TABLE IF EXISTS COIN CASCADE;
DROP TABLE IF EXISTS DONATE CASCADE;
SET foreign_key_checks = 1;

CREATE TABLE USER (
	-- Field Type Null Default
	id int not null auto_increment,
	coin_count int not null default 0,
	email varchar(255) not null unique,
	introduction varchar(255),
	name varchar(255),
	opened bit(1) not null default b'0',
	password varchar(300) not null,
	premium bit(1) not null default b'0',
	img varchar(255),
	refresh_token varchar(255),

	-- Key
	primary key(id)
) charset = utf8;

CREATE TABLE USER_ROLES (
    user_id int	not null,
    roles varchar(255),

    constraint fk_user_roles_to_user foreign key(user_id) references user(id) on delete restrict on update restrict
);

CREATE TABLE USER_SEQ (
    next_val bigint
);

CREATE TABLE FOLLOW (
    id int not null auto_increment,
    follower_id int not null,
    following_id int not null,

    primary key(id),
    constraint fk_follower_to_user foreign key(follower_id) references user(id) on delete restrict on update restrict,
    constraint fk_following_to_user foreign key(following_id) references user(id) on delete restrict on update restrict
);

CREATE TABLE COIN (
    id int not null auto_increment,
    coin_count int not null,
    studied_at date,
    user_id int not null,

    primary key(id),
    constraint fk_coin_to_user foreign key(user_id) references user(id) on delete restrict on update restrict
);

CREATE TABLE DONATE (
    id int not null auto_increment,
    coin_count int not null,
    donated_at date,
    user_id int not null,

    primary key(id),
    constraint fk_donate_to_user foreign key(user_id) references user(id) on delete restrict on update restrict
);

CREATE TABLE STUDY_GROUP (
    id int not null auto_increment,
    category varchar(255),
    created_at date,
    details varchar(255),
    join_count int not null,
    name varchar(255),
    password varchar(255),
    theme varchar(255),
    user_id int not null,

    primary key(id),
    constraint fk_study_group_to_user foreign key(user_id) references user(id) on delete restrict on update restrict
) charset = utf8;

CREATE TABLE GROUP_JOIN (
    id int not null auto_increment,
    joined_at date,
    study_group_id int not null,
    user_id int not null,

    primary key(id),
    constraint fk_group_join_to_study_group foreign key(study_group_id) references study_group(id) on delete restrict on update restrict,
    constraint fk_group_join_to_user foreign key(user_id) references user(id) on delete restrict on update restrict
);

CREATE TABLE PLAN_INFO (
    id int not null auto_increment,
    category varchar(255),
    color varchar(255),
    completed_at date,
    repeat_day varchar(255),
    title varchar(255),

    primary key(id)
) charset = utf8;

CREATE TABLE PLAN (
    id int not null auto_increment,
    end_time time,
    recorded bit(1) not null,
    start_time time,
    coin_id int not null,
    plan_info_id int not null,

    primary key(id),
    constraint fk_plan_to_coin foreign key(coin_id) references coin(id) on delete restrict on update restrict,
    constraint fk_plan_to_plan_info foreign key(plan_info_id) references plan_info(id) on delete restrict on update restrict
);

CREATE TABLE REVIEW (
    id int not null auto_increment,
    bad varchar(255),
    good varchar(255),
    plan varchar(255),
    reviewed_at	date,
    user_id int not null,

    primary key(id),
    constraint fk_review_to_user foreign key(user_id) references user(id) on delete restrict on update restrict
) charset = utf8;

CREATE TABLE REVIEW_LIKE (
    id int not null auto_increment,
    review_id int not null,
    user_id int not null,

    primary key(id),
    constraint fk_review_like_to_review foreign key(review_id) references review(id) on delete restrict on update restrict,
    constraint fk_review_like_to_user foreign key(user_id) references user(id) on delete restrict on update restrict
);