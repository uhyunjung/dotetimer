-- User 데이터 생성
INSERT INTO user(coin_count,email,introduction,name,opened,password,premium,img,refresh_token) VALUES (0,"tester1@gmail.com","안녕하세요!","dotetimer1",false,"{bcrypt}$2a$10$cp..8Z39B0WvHRd2MT/tl.qKUhZXATcBDxGn6nE.nAMU2GpRjEAe.",false,"/image/1.jpg",""); -- 비밀번호 : abcdefgh
INSERT INTO user(coin_count,email,introduction,name,opened,password,premium,img,refresh_token) VALUES (0,"tester2@gmail.com","반가워요","dotetimer2",false,"{bcrypt}$2a$10$cp..8Z39B0WvHRd2MT/tl.qKUhZXATcBDxGn6nE.nAMU2GpRjEAe.",false,"/image/1.jpg","");
INSERT INTO user(coin_count,email,introduction,name,opened,password,premium,img,refresh_token) VALUES (0,"tester3@gmail.com","열심히 해봐요","dotetimer3",false,"{bcrypt}$2a$10$cp..8Z39B0WvHRd2MT/tl.qKUhZXATcBDxGn6nE.nAMU2GpRjEAe.",false,"/image/me.jpg","");
INSERT INTO user(coin_count,email,introduction,name,opened,password,premium,img,refresh_token) VALUES (0,"tester4@gmail.com","","dotetimer4",false,"{bcrypt}$2a$10$cp..8Z39B0WvHRd2MT/tl.qKUhZXATcBDxGn6nE.nAMU2GpRjEAe.",false,"/image/1.jpg","");
INSERT INTO user(coin_count,email,introduction,name,opened,password,premium,img,refresh_token) VALUES (0,"tester5@gmail.com","오늘부터 시작","dotetimer5",false,"{bcrypt}$2a$10$cp..8Z39B0WvHRd2MT/tl.qKUhZXATcBDxGn6nE.nAMU2GpRjEAe.",false,"/image/user.jpg","");
INSERT INTO user(coin_count,email,introduction,name,opened,password,premium,img,refresh_token) VALUES (0,"tester6@gmail.com","","dotetimer6",false,"{bcrypt}$2a$10$cp..8Z39B0WvHRd2MT/tl.qKUhZXATcBDxGn6nE.nAMU2GpRjEAe.",false,"/image/1.jpg","");
INSERT INTO user(coin_count,email,introduction,name,opened,password,premium,img,refresh_token) VALUES (0,"tester7@gmail.com","화이팅!","dotetimer7",false,"{bcrypt}$2a$10$cp..8Z39B0WvHRd2MT/tl.qKUhZXATcBDxGn6nE.nAMU2GpRjEAe.",false,"/image/1.jpg","");
INSERT INTO user(coin_count,email,introduction,name,opened,password,premium,img,refresh_token) VALUES (1,"tester8@gmail.com","만나서 반가워요","dotetimer8",false,"{bcrypt}$2a$10$cp..8Z39B0WvHRd2MT/tl.qKUhZXATcBDxGn6nE.nAMU2GpRjEAe.",false,"/image/hi.jpg","");
INSERT INTO user(coin_count,email,introduction,name,opened,password,premium,img,refresh_token) VALUES (2,"tester9@gmail.com","","dotetimer9",false,"{bcrypt}$2a$10$cp..8Z39B0WvHRd2MT/tl.qKUhZXATcBDxGn6nE.nAMU2GpRjEAe.",false,"/image/1.jpg","");
INSERT INTO user(coin_count,email,introduction,name,opened,password,premium,img,refresh_token) VALUES (2,"tester10@gmail.com","","dotetimer10",false,"{bcrypt}$2a$10$cp..8Z39B0WvHRd2MT/tl.qKUhZXATcBDxGn6nE.nAMU2GpRjEAe.",false,"/image/1.jpg","");

-- UserRoles 데이터 생성
INSERT INTO user_roles(user_id,roles) VALUES (1,"ROLE_USER");
INSERT INTO user_roles(user_id,roles) VALUES (2,"ROLE_USER");
INSERT INTO user_roles(user_id,roles) VALUES (3,"ROLE_USER");
INSERT INTO user_roles(user_id,roles) VALUES (4,"ROLE_USER");
INSERT INTO user_roles(user_id,roles) VALUES (5,"ROLE_USER");
INSERT INTO user_roles(user_id,roles) VALUES (6,"ROLE_USER");
INSERT INTO user_roles(user_id,roles) VALUES (7,"ROLE_USER");
INSERT INTO user_roles(user_id,roles) VALUES (8,"ROLE_USER");
INSERT INTO user_roles(user_id,roles) VALUES (9,"ROLE_USER");
INSERT INTO user_roles(user_id,roles) VALUES (10,"ROLE_USER");

-- Follow 데이터 생성
INSERT INTO follow(follower_id,following_id) VALUES (1,2);
INSERT INTO follow(follower_id,following_id) VALUES (1,3);
INSERT INTO follow(follower_id,following_id) VALUES (1,4);
INSERT INTO follow(follower_id,following_id) VALUES (1,7);
INSERT INTO follow(follower_id,following_id) VALUES (2,1);
INSERT INTO follow(follower_id,following_id) VALUES (3,4);
INSERT INTO follow(follower_id,following_id) VALUES (4,5);
INSERT INTO follow(follower_id,following_id) VALUES (6,5);
INSERT INTO follow(follower_id,following_id) VALUES (7,8);
INSERT INTO follow(follower_id,following_id) VALUES (7,9);
INSERT INTO follow(follower_id,following_id) VALUES (8,1);

-- Coin 데이터 생성
INSERT INTO coin(coin_count,studied_at,user_id) VALUES (0,"2023-01-31 12:10:30",1);
INSERT INTO coin(coin_count,studied_at,user_id) VALUES (0,"2023-01-22 12:10:30",1);
INSERT INTO coin(coin_count,studied_at,user_id) VALUES (0,"2023-01-23 12:10:30",1);
INSERT INTO coin(coin_count,studied_at,user_id) VALUES (0,"2023-01-27 12:10:30",1);
INSERT INTO coin(coin_count,studied_at,user_id) VALUES (0,"2023-01-25 12:10:30",2);
INSERT INTO coin(coin_count,studied_at,user_id) VALUES (10,"2023-01-22 12:10:30",1);
INSERT INTO coin(coin_count,studied_at,user_id) VALUES (3,"2023-01-23 12:10:30",2);
INSERT INTO coin(coin_count,studied_at,user_id) VALUES (3,"2023-01-26 12:10:30",2);
INSERT INTO coin(coin_count,studied_at,user_id) VALUES (3,"2023-01-29 12:10:30",9);
INSERT INTO coin(coin_count,studied_at,user_id) VALUES (3,"2023-01-30 12:10:30",10);

-- Donate 데이터 생성
INSERT INTO donate(coin_count,donated_at,user_id) VALUES (3,"2023-01-24 12:10:30",6);
INSERT INTO donate(coin_count,donated_at,user_id) VALUES (3,"2023-01-24 12:10:30",7);
INSERT INTO donate(coin_count,donated_at,user_id) VALUES (2,"2023-01-24 12:10:30",8);
INSERT INTO donate(coin_count,donated_at,user_id) VALUES (1,"2023-01-24 12:10:30",9);
INSERT INTO donate(coin_count,donated_at,user_id) VALUES (1,"2023-01-24 12:10:30",10);

-- StudyGroup 데이터 생성
INSERT INTO study_group(category,created_at,details,join_count,name,password,theme,user_id) VALUES ("취업 준비","2023-01-29 12:10:30","개발자로 일하자",10,"포에버","","RED",1);
INSERT INTO study_group(category,created_at,details,join_count,name,password,theme,user_id) VALUES ("자격증 준비","2023-01-29 12:10:30","^^",4,"정처기 모임","","RED",2);
INSERT INTO study_group(category,created_at,details,join_count,name,password,theme,user_id) VALUES ("중간고사","2023-01-29 12:10:30","시험 100점",5,"들어와","","BLUE",3);
INSERT INTO study_group(category,created_at,details,join_count,name,password,theme,user_id) VALUES ("취업 준비","2023-01-29 12:10:30","모두 환영",6,"개발","","GREEN",4);
INSERT INTO study_group(category,created_at,details,join_count,name,password,theme,user_id) VALUES ("토익","2023-01-29 12:10:30","컴온",7,"화이팅","","BLUE",5);
INSERT INTO study_group(category,created_at,details,join_count,name,password,theme,user_id) VALUES ("취업 준비","2023-01-29 12:10:30","가자",8,"일개미","","RED",6);
INSERT INTO study_group(category,created_at,details,join_count,name,password,theme,user_id) VALUES ("","2023-01-29 12:10:30","",9,"","","",7);
INSERT INTO study_group(category,created_at,details,join_count,name,password,theme,user_id) VALUES ("","2023-01-29 12:10:30","",10,"","","",8);
INSERT INTO study_group(category,created_at,details,join_count,name,password,theme,user_id) VALUES ("","2023-01-29 12:10:30","",11,"","","",8);
INSERT INTO study_group(category,created_at,details,join_count,name,password,theme,user_id) VALUES ("","2023-01-29 12:10:30","",12,"","","",9);

-- GroupJoin 데이터 생성
INSERT INTO group_join(joined_at,study_group_id,user_id) VALUES ("2023-01-29 12:10:30",1,1);
INSERT INTO group_join(joined_at,study_group_id,user_id) VALUES ("2023-01-29 12:10:30",2,1);
INSERT INTO group_join(joined_at,study_group_id,user_id) VALUES ("2023-01-29 12:10:30",2,2);
INSERT INTO group_join(joined_at,study_group_id,user_id) VALUES ("2023-01-29 12:10:30",3,1);
INSERT INTO group_join(joined_at,study_group_id,user_id) VALUES ("2023-01-29 12:10:30",3,3);
INSERT INTO group_join(joined_at,study_group_id,user_id) VALUES ("2023-01-29 12:10:30",4,3);
INSERT INTO group_join(joined_at,study_group_id,user_id) VALUES ("2023-01-29 12:10:30",4,4);
INSERT INTO group_join(joined_at,study_group_id,user_id) VALUES ("2023-01-29 12:10:30",1,5);
INSERT INTO group_join(joined_at,study_group_id,user_id) VALUES ("2023-01-29 12:10:30",1,2);
INSERT INTO group_join(joined_at,study_group_id,user_id) VALUES ("2023-01-29 12:10:30",1,3);
INSERT INTO group_join(joined_at,study_group_id,user_id) VALUES ("2023-01-29 12:10:30",1,4);
INSERT INTO group_join(joined_at,study_group_id,user_id) VALUES ("2023-01-29 12:10:30",1,5);
INSERT INTO group_join(joined_at,study_group_id,user_id) VALUES ("2023-01-29 12:10:30",2,6);
INSERT INTO group_join(joined_at,study_group_id,user_id) VALUES ("2023-01-29 12:10:30",5,5);
INSERT INTO group_join(joined_at,study_group_id,user_id) VALUES ("2023-01-29 12:10:30",6,6);
INSERT INTO group_join(joined_at,study_group_id,user_id) VALUES ("2023-01-29 12:10:30",7,7);
INSERT INTO group_join(joined_at,study_group_id,user_id) VALUES ("2023-01-29 12:10:30",8,8);
INSERT INTO group_join(joined_at,study_group_id,user_id) VALUES ("2023-01-29 12:10:30",8,9);
INSERT INTO group_join(joined_at,study_group_id,user_id) VALUES ("2023-01-29 12:10:30",7,10);

-- PlanInfo 데이터 생성
INSERT INTO plan_info(category,color,completed_at,repeat_day,title) VALUES ("백엔드","GREEN","2024-01-24 12:10:30","월","스프링");
INSERT INTO plan_info(category,color,completed_at,repeat_day,title) VALUES ("","","2024-01-24 12:10:30","","");
INSERT INTO plan_info(category,color,completed_at,repeat_day,title) VALUES ("","","2024-01-24 12:10:30","","");
INSERT INTO plan_info(category,color,completed_at,repeat_day,title) VALUES ("","","2024-01-24 12:10:30","","");
INSERT INTO plan_info(category,color,completed_at,repeat_day,title) VALUES ("","","2024-01-24 12:10:30","","");
INSERT INTO plan_info(category,color,completed_at,repeat_day,title) VALUES ("","","2024-01-24 12:10:30","","");
INSERT INTO plan_info(category,color,completed_at,repeat_day,title) VALUES ("","","2024-01-24 12:10:30","","");
INSERT INTO plan_info(category,color,completed_at,repeat_day,title) VALUES ("","","2024-01-24 12:10:30","","");
INSERT INTO plan_info(category,color,completed_at,repeat_day,title) VALUES ("","","2024-01-24 12:10:30","","");
INSERT INTO plan_info(category,color,completed_at,repeat_day,title) VALUES ("","","2024-01-24 12:10:30","","");

-- Plan(Plan) 데이터 생성
INSERT INTO plan(end_time,recorded,start_time,coin_id,plan_info_id) VALUES ("12:20:30",false,"12:10:20",1,1);
INSERT INTO plan(end_time,recorded,start_time,coin_id,plan_info_id) VALUES ("14:20:30",false,"13:10:20",1,2);
INSERT INTO plan(end_time,recorded,start_time,coin_id,plan_info_id) VALUES ("17:20:30",false,"14:10:20",1,3);
INSERT INTO plan(end_time,recorded,start_time,coin_id,plan_info_id) VALUES ("12:20:30",false,"12:10:20",2,2);
INSERT INTO plan(end_time,recorded,start_time,coin_id,plan_info_id) VALUES ("12:20:30",false,"12:10:20",3,3);
INSERT INTO plan(end_time,recorded,start_time,coin_id,plan_info_id) VALUES ("12:20:30",false,"12:10:20",4,4);
INSERT INTO plan(end_time,recorded,start_time,coin_id,plan_info_id) VALUES ("12:20:30",false,"12:10:20",5,5);

-- Plan(Record) 데이터 생성
INSERT INTO plan(end_time,recorded,start_time,coin_id,plan_info_id) VALUES ("13:50:30",true,"12:10:20",6,6);
INSERT INTO plan(end_time,recorded,start_time,coin_id,plan_info_id) VALUES ("12:40:30",true,"12:10:20",7,7);
INSERT INTO plan(end_time,recorded,start_time,coin_id,plan_info_id) VALUES ("12:40:30",true,"12:10:20",8,8);
INSERT INTO plan(end_time,recorded,start_time,coin_id,plan_info_id) VALUES ("12:40:30",true,"12:10:20",9,9);
INSERT INTO plan(end_time,recorded,start_time,coin_id,plan_info_id) VALUES ("12:40:30",true,"12:10:20",10,9);

-- Review 데이터 생성
INSERT INTO review(bad,good,plan,reviewed_at,user_id) VALUES ("오늘 늦잠을 잤어요","대신 일어나자마자 의자에 앉아서 한 자라도 더 봤으니 뿌듯하다","내일은 일찍 일어나야지","2023-01-24 12:10:30",1);
INSERT INTO review(bad,good,plan,reviewed_at,user_id) VALUES ("","","","2023-01-24 12:10:30",2);
INSERT INTO review(bad,good,plan,reviewed_at,user_id) VALUES ("","","","2023-01-24 12:10:30",3);
INSERT INTO review(bad,good,plan,reviewed_at,user_id) VALUES ("","","","2023-01-24 12:10:30",4);
INSERT INTO review(bad,good,plan,reviewed_at,user_id) VALUES ("","","","2023-01-24 12:10:30",5);
INSERT INTO review(bad,good,plan,reviewed_at,user_id) VALUES ("","","","2023-01-24 12:10:30",6);
INSERT INTO review(bad,good,plan,reviewed_at,user_id) VALUES ("","","","2023-01-24 12:10:30",7);
INSERT INTO review(bad,good,plan,reviewed_at,user_id) VALUES ("","","","2023-01-24 12:10:30",8);
INSERT INTO review(bad,good,plan,reviewed_at,user_id) VALUES ("","","","2023-01-24 12:10:30",9);
INSERT INTO review(bad,good,plan,reviewed_at,user_id) VALUES ("","","","2023-01-24 12:10:30",9);

-- ReviewLike 데이터 생성
INSERT INTO review_like(review_id,user_id) VALUES (1,2);
INSERT INTO review_like(review_id,user_id) VALUES (1,3);
INSERT INTO review_like(review_id,user_id) VALUES (1,4);
INSERT INTO review_like(review_id,user_id) VALUES (1,7);
INSERT INTO review_like(review_id,user_id) VALUES (2,1);
INSERT INTO review_like(review_id,user_id) VALUES (3,4);
INSERT INTO review_like(review_id,user_id) VALUES (4,5);
INSERT INTO review_like(review_id,user_id) VALUES (6,5);
INSERT INTO review_like(review_id,user_id) VALUES (7,8);
INSERT INTO review_like(review_id,user_id) VALUES (7,9);
INSERT INTO review_like(review_id,user_id) VALUES (8,1);