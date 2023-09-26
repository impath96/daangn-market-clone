-- 카테고리 --
insert into CATEGORY (category_id, category_name, created_date_time, updated_date_time)
values (1, '디지털기기', now(), now()),
       (2, '생활가전', now(), now()),
       (3, '가구/인테리어', now(), now()),
       (4, '생활/주방', now(), now()),
       (5, '여성의류', now(), now()),
       (6, '뷰티/미용', now(), now()),
       (7, '남성패션/잡화', now(), now()),
       (8, '스포츠/레저', now(), now()),
       (9, '취미/게임/음반', now(), now()),
       (10, '도서', now(), now()),
       (11, '티켓/교환권', now(), now()),
       (12, '가공식품', now(), now()),
       (13, '반려동물용품', now(), now()),
       (14, '식물', now(), now());

--
insert into region(region_id, state, city, town)
values (1, '부산시', '사하구', '감천동'),
       (2, '부산시', '사하구', '당리동'),
       (3, '부산시', '사하구', '하단동'),
       (4, '부산시', '사하구', '다대동');




