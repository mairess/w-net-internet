
insert into "PLANS" ("NAME", "SPEED", "PRICE") values ('Starter Plan', 100, 99.99), ('Deluxe Plan', 300, 199.99), ('Platinum Plan', 500, 299.99);

insert into "TECHNICIANS" ("full_name", "phone", "email") values ('Marcos Oliveira', '21987654321', 'marcos@wnet.com'), ('João Silva', '21987654322', 'joao@wnet.com'), ('Ana Souza', '21987654323', 'ana@wnet.com'), ('Pedro Santos', '21987654324', 'pedro@wnet.com');

insert into "CUSTOMERS" ("FULL_NAME", "CPF", "PHONE", "EMAIL", "REGISTRATION_DATE") values ('João Silva', '12345678900', '21999999999', 'joao.silva@example.com', current_date), ('Maria Oliveira', '23456789001', '21888888888', 'maria.oliveira@example.com', current_date), ('Pedro Souza', '34567890123', '21777777777', 'pedro.souza@example.com', current_date), ('Ana Santos', '45678901224', '21666666666', 'ana.santos@example.com', current_date), ('Carlos Lima', '56789012145', '21555555555', 'carlos.lima@example.com', current_date), ('Mariana Costa', '67890123456', '21444444444', 'mariana.costa@example.com', current_date), ('José Pereira', '78901234567', '21333333333', 'jose.pereira@example.com', current_date), ('Luiza Gomes', '89002345678', '21222222222', 'luiza.gomes@example.com', current_date), ('Fernando Martins', '90123456289', '21111111111', 'fernando.martins@example.com', current_date), ('Aline Mendes', '01214567890', '21999999998', 'aline.mendes@example.com', current_date), ('Rafaela Almeida', '12345678901', '21888888887', 'rafaela.almeida@example.com', current_date), ('Rodrigo Santos', '23456789012', '21777777776', 'rodrigo.santos@example.com', current_date), ('Juliana Lima', '34563790123', '21666666665', 'juliana.lima@example.com', current_date), ('Gustavo Oliveira', '45678901234', '21555555554', 'gustavo.oliveira@example.com', current_date), ('Camila Silva', '56789012345', '21444444443', 'camila.silva@example.com', current_date), ('Marcos Costa', '37890123456', '21333333332', 'marcos.costa@example.com', current_date), ('Carolina Pereira', '70901234567', '21222222221', 'carolina.pereira@example.com', current_date), ('Paulo Gomes', '89012345678', '21111111110', 'paulo.gomes@example.com', current_date), ('Isabela Martins', '90123456789', '21999999997', 'isabela.martins@example.com', current_date), ('Renato Mendes', '01234567890', '21888888886', 'renato.mendes@example.com', current_date);

insert into "INSTALLATIONS" ("is_active", "plan_id", "technician_id", "installation_date") values (true, 2, 2, current_date), (true, 3, 1, current_date), (true, 1, 3, current_date), (true, 2, 4, current_date), (true, 1, 2, current_date), (true, 3, 3, current_date), (true, 1, 1, current_date), (true, 2, 4, current_date), (true, 3, 2, current_date), (true, 1, 3, current_date), (true, 2, 1, current_date), (true, 3, 4, current_date), (true, 1, 2, current_date), (true, 2, 3, current_date), (true, 3, 4, current_date), (true, 1, 1, current_date), (true, 2, 2, current_date), (true, 3, 3, current_date), (true, 1, 4, current_date), (true, 2, 1, current_date);

insert into "ADDRESSES" ("CITY", "STATE", "ZIP_CODE", "STREET", "STREET_NUMBER", "COMPLEMENT", "NEIGHBORHOOD", "INSTALLATION_ID", "CUSTOMER_ID") values ('São Paulo', 'São Paulo', '01000-000', 'Avenida Paulista', 1023, 'Próximo ao Parque Trianon', 'Bela Vista', 1, 1), ('Rio de Janeiro', 'Rio de Janeiro', '20000-000', 'Rua do Ouvidor', 50, 'Centro Histórico', 'Centro', 2, 2), ('Belo Horizonte', 'Minas Gerais', '30000-000', 'Avenida Afonso Pena', 1234, 'Em frente ao Parque Municipal', 'Centro', 3, 3), ('Porto Alegre', 'Rio Grande do Sul', '90000-000', 'Rua dos Andradas', 500, 'Próximo ao Mercado Público', 'Centro Histórico', 4, 4), ('Curitiba', 'Paraná', '80000-000', 'Rua XV de Novembro', 1500, 'Próximo ao Passeio Público', 'Centro', 5, 5), ('Salvador', 'Bahia', '40000-000', 'Avenida Sete de Setembro', 300, 'Próximo ao Elevador Lacerda', 'Comércio', 6, 6), ('Brasília', 'Distrito Federal', '70000-000', 'Eixo Monumental', 100, 'Próximo à Esplanada dos Ministérios', 'Zona Cívico-Administrativa', 7, 7), ('Fortaleza', 'Ceará', '60000-000', 'Avenida Beira Mar', 2000, 'Próximo ao Mercado Central', 'Praia de Iracema', 8, 8), ('Recife', 'Pernambuco', '50000-000', 'Rua da Aurora', 1200, 'Próximo ao Parque Treze de Maio', 'Boa Vista', 9, 9), ('Manaus', 'Amazonas', '69000-000', 'Avenida Eduardo Ribeiro', 400, 'Próximo ao Teatro Amazonas', 'Centro', 10, 10), ('São José dos Campos', 'São Paulo', '12200-000', 'Fazenda Boa Esperança', NULL, 'Próximo ao Rio Paraíba', 'Zona Rural', 11, 11), ('Piracicaba', 'São Paulo', '13400-000', 'Sítio das Palmeiras', NULL, 'Perto da Estrada Municipal', 'Zona Rural', 12, 12), ('Ribeirão Preto', 'São Paulo', '14000-000', 'Chácara Santa Maria', NULL, 'Ao lado da Igreja São João', 'Zona Rural', 13, 13), ('Campinas', 'São Paulo', '13000-000', 'Fazenda Santo Antônio', NULL, 'Próximo ao Lago Azul', 'Zona Rural', 14, 14), ('Sorocaba', 'São Paulo', '18000-000', 'Sítio São José', NULL, 'Cerca de 5 km da Rodovia Raposo Tavares', 'Zona Rural', 15, 15), ('Jundiaí', 'São Paulo', '13200-000', 'Chácara Bela Vista', NULL, 'Depois da Ponte Velha', 'Zona Rural', 16, 16), ('Bauru', 'São Paulo', '17000-000', 'Fazenda Primavera', NULL, 'Na Estrada do Barreiro', 'Zona Rural', 17, 17), ('Taubaté', 'São Paulo', '12000-000', 'Sítio Boa Vista', NULL, 'Perto do Posto de Saúde', 'Zona Rural', 18, 18), ('Marília', 'São Paulo', '17500-000', 'Chácara Sol Nascente', NULL, 'Próximo ao Campo de Futebol', 'Zona Rural', 19, 19), ('Presidente Prudente', 'São Paulo', '19000-000', 'Fazenda São Pedro', NULL, 'A 3 km do Centro', 'Zona Rural', 20, 20);

insert into "EQUIPMENTS" ("TYPE", "MODEL", "SERIAL_NUMBER", "MANUFACTURER", "INSTALLATION_ID") values ('ROUTER', 'TP-Link Deco M5', 'SN031', 'TP-Link', 1), ('MODEM', 'Arris SURFboard SBG6700AC', 'SN032', 'Arris', 1), ('ROUTER', 'Linksys Velop', 'SN033', 'Linksys', 2), ('MODEM', 'Netgear Nighthawk AC1900', 'SN034', 'Netgear', 2), ('ROUTER', 'Asus RT-AC88U', 'SN035', 'Asus', 3), ('MODEM', 'Motorola MG7700', 'SN036', 'Motorola', 3), ('ROUTER', 'Google Nest Wifi', 'SN037', 'Google', 4), ('MODEM', 'Zoom Telephonics AC1900', 'SN038', 'Zoom', 4), ('ROUTER', 'D-Link DIR-867', 'SN039', 'D-Link', 5), ('MODEM', 'Actiontec C3000A', 'SN040', 'Actiontec', 5), ('ROUTER', 'Synology RT2600ac', 'SN041', 'Synology', 6), ('MODEM', 'Hitron CODA-4582', 'SN042', 'Hitron', 6), ('ROUTER', 'Tenda AC10U', 'SN043', 'Tenda', 7), ('MODEM', 'Technicolor TC4400', 'SN044', 'Technicolor', 7), ('ROUTER', 'Buffalo AirStation', 'SN045', 'Buffalo', 8), ('MODEM', 'Cisco DPC3941T', 'SN046', 'Cisco', 8), ('ROUTER', 'TP-Link Archer C7', 'SN047', 'TP-Link', 9), ('MODEM', 'Zyxel C1100Z', 'SN048', 'Zyxel', 9), ('ROUTER', 'Apple AirPort Extreme', 'SN049', 'Apple', 10), ('MODEM', 'Hitron CGNM-2250', 'SN050', 'Hitron', 10), ('ROUTER', 'Huawei WS5200', 'SN051', 'Huawei', 11), ('MODEM', 'UBEE DDW36C', 'SN052', 'UBEE', 11), ('ROUTER', 'MikroTik RB3011UiAS-RM', 'SN053', 'MikroTik', 12), ('MODEM', 'Zoom 5370', 'SN054', 'Zoom', 12), ('ROUTER', 'Linksys EA7500', 'SN055', 'Linksys', 13), ('MODEM', 'Motorola MB7621', 'SN056', 'Motorola', 13), ('ROUTER', 'Asus RT-AX88U', 'SN057', 'Asus', 14), ('MODEM', 'Netgear CM500', 'SN058', 'Netgear', 14), ('ROUTER', 'Google Wifi', 'SN059', 'Google', 15), ('MODEM', 'Arris SB6190', 'SN060', 'Arris', 15), ('ROUTER', 'D-Link EXO AC2600', 'SN061', 'D-Link', 16), ('MODEM', 'TP-Link TC-7610', 'SN062', 'TP-Link', 16), ('ROUTER', 'Synology MR2200ac', 'SN063', 'Synology', 17), ('MODEM', 'Actiontec GT784WN', 'SN064', 'Actiontec', 17), ('ROUTER', 'Netgear Nighthawk X6', 'SN065', 'Netgear', 18), ('MODEM', 'Technicolor TC8717T', 'SN066', 'Technicolor', 18), ('ROUTER', 'Buffalo WZR-1750DHP', 'SN067', 'Buffalo', 19), ('MODEM', 'Cisco DPC3010', 'SN068', 'Cisco', 19);

insert into "USERS" ("ROLE", "EMAIL", "FULL_NAME", "PASSWORD", "USERNAME") values ('ADMIN', 'juca@provedor.com', 'Juca Almeida', '$2a$10$mI4JPFBW4sE2zseRF7yC1OjsIcmZf8Wa5RkiZin5slC7lSfOwqC26', 'juca'), ('TECHNICIAN', 'osvaldo@provedor.com', 'Osvaldo Ferreira', '$2a$10$mI4JPFBW4sE2zseRF7yC1OjsIcmZf8Wa5RkiZin5slC7lSfOwqC26', 'osvaldo'), ('TECHNICIAN', 'mirosmar@provedor.com', 'Mirosmar Fernandes', '$2a$10$mI4JPFBW4sE2zseRF7yC1OjsIcmZf8Wa5RkiZin5slC7lSfOwqC26', 'mirosmar');