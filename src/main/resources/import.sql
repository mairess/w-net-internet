insert into "customers" ("id", "name", "cpf", "phone", "email", "registration_date") values (1, 'João Silva', '12345678900', '21999999999', 'joao@example.com', current_date);
insert into "addresses" ("city", "state", "zip_code", "address_type", "farm_name", "village") values ('CityName', 'StateName', '12345-678', 'rural', 'FarmName', 'VillageName');
insert into "addresses" ("city", "state", "zip_code", "address_type", "street", "street_number", "complement", "neighborhood") values ('CityName', 'StateName', '12345-678', 'urban', 'StreetName', 123, 'ComplementName', 'NeighborhoodName');
insert into "equipments" ("type", "model", "serial_number", "manufacturer", "provision_date") values ('Type', 'Model', 'Serial123', 'ManufacturerName', current_date);
insert into "plans" ("name", "speed", "price", "is_active") values ('PlanName', 100, 99.99, true);
insert into "technicians" ("name", "phone", "email") values ('TechnicianName', '123456789', 'technician@example.com');