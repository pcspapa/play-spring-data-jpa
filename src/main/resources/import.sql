-- many to one
insert into mto_person_uni(name) values('name');
insert into mto_phone_uni(person_id, number) values(1, '123-456-7890');
insert into mto_phone_uni(person_id, number) values(1, '234-567-8901');

-- one to many on uni
insert into otm_person_uni(name) values('name');
insert into otm_phone_uni(person_id, number) values(1, '123-456-7890');

-- one to many on bi
insert into otm_person_bi(name) values('name');
insert into otm_phone_bi(person_id, number) values(1, '123-456-7890');