insert into "customers" ("id", "name", "cpf", "phone", "email", "registration_date") values (1, 'João Silva', '12345678900', '21999999999', 'joao.silva@example.com', current_date), (2, 'Maria Oliveira', '23456789001', '21888888888', 'maria.oliveira@example.com', current_date), (3, 'Pedro Souza', '34567890123', '21777777777', 'pedro.souza@example.com', current_date), (4, 'Ana Santos', '45678901224', '21666666666', 'ana.santos@example.com', current_date), (5, 'Carlos Lima', '56789012145', '21555555555', 'carlos.lima@example.com', current_date), (6, 'Mariana Costa', '67890123456', '21444444444', 'mariana.costa@example.com', current_date), (7, 'José Pereira', '78901234567', '21333333333', 'jose.pereira@example.com', current_date), (8, 'Luiza Gomes', '89002345678', '21222222222', 'luiza.gomes@example.com', current_date), (9, 'Fernando Martins', '90123456289', '21111111111', 'fernando.martins@example.com', current_date), (10, 'Aline Mendes', '01214567890', '21999999998', 'aline.mendes@example.com', current_date), (11, 'Rafaela Almeida', '12345678901', '21888888887', 'rafaela.almeida@example.com', current_date), (12, 'Rodrigo Santos', '23456789012', '21777777776', 'rodrigo.santos@example.com', current_date), (13, 'Juliana Lima', '34563790123', '21666666665', 'juliana.lima@example.com', current_date), (14, 'Gustavo Oliveira', '45678901234', '21555555554', 'gustavo.oliveira@example.com', current_date), (15, 'Camila Silva', '56789012345', '21444444443', 'camila.silva@example.com', current_date), (16, 'Marcos Costa', '37890123456', '21333333332', 'marcos.costa@example.com', current_date), (17, 'Carolina Pereira', '70901234567', '21222222221', 'carolina.pereira@example.com', current_date), (18, 'Paulo Gomes', '89012345678', '21111111110', 'paulo.gomes@example.com', current_date), (19, 'Isabela Martins', '90123456789', '21999999997', 'isabela.martins@example.com', current_date), (20, 'Renato Mendes', '01234567890', '21888888886', 'renato.mendes@example.com', current_date);

insert into "addresses" ("city", "state", "zip_code", "address_type", "farm_name", "village") values ('Rio Branco', 'Acre', '12345-678', 'rural', 'Fazenda Sagarana', 'Vale dos Açudes'), ('Porto Velho', 'Rondônia', '78900-000', 'rural', 'Fazenda Esperança', 'Vale Verde'), ('Manaus', 'Amazonas', '23456-789', 'urban', 'Rua das Flores', 'Centro'), ('Belém', 'Pará', '45678-901', 'urban', 'Av. da Independência', 'Reduto'), ('Teresina', 'Piauí', '56789-012', 'urban', 'Rua dos Ipês', 'Jóquei'), ('Fortaleza', 'Ceará', '67890-123', 'urban', 'Av. Dom Luís', 'Aldeota'), ('Natal', 'Rio Grande do Norte', '78901-234', 'urban', 'Rua Mossoró', 'Petrópolis'), ('João Pessoa', 'Paraíba', '89012-345', 'urban', 'Av. Epitácio Pessoa', 'Tambaú'), ('Recife', 'Pernambuco', '90123-456', 'urban', 'Rua do Sol', 'Boa Vista'), ('Maceió', 'Alagoas', '01234-567', 'urban', 'Av. Fernandes Lima', 'Farol')

insert into "addresses" ("city", "state", "zip_code", "address_type", "street", "street_number", "complement", "neighborhood") values ('São Paulo', 'São Paulo', '01000-000', 'urban', 'Avenida Paulista', 1023, 'Próximo ao Parque Trianon', 'Bela Vista'), ('Rio de Janeiro', 'Rio de Janeiro', '20000-000', 'urban', 'Rua Nossa Senhora de Copacabana', 987, 'Perto do Posto 6', 'Copacabana'), ('Belo Horizonte', 'Minas Gerais', '30000-000', 'urban', 'Avenida Afonso Pena', 500, 'Centro', 'Funcionários'), ('Brasília', 'Distrito Federal', '70000-000', 'urban', 'Esplanada dos Ministérios', 1000, 'Asa Norte', 'Plano Piloto'), ('Curitiba', 'Paraná', '80000-000', 'urban', 'Rua XV de Novembro', 789, 'Centro', 'Batel'), ('Porto Alegre', 'Rio Grande do Sul', '90000-000', 'urban', 'Avenida Borges de Medeiros', 1234, 'Centro', 'Moinhos de Vento'), ('Florianópolis', 'Santa Catarina', '88000-000', 'urban', 'Avenida Beira Mar Norte', 567, 'Centro', 'Trindade'), ('Salvador', 'Bahia', '40000-000', 'urban', 'Pelourinho', 321, 'Centro Histórico', 'Barra'), ('Goiânia', 'Goiás', '74000-000', 'urban', 'Avenida Goiás', 789, 'Setor Central', 'Jardim Goiás'), ('Campo Grande', 'Mato Grosso do Sul', '79000-000', 'urban', 'Avenida Afonso Pena', 456, 'Centro', 'Jardim dos Estados');

insert into "equipments" ("type", "model", "serial_number", "manufacturer") values ('ROUTER', 'TP-Link Deco M5', 'SN031', 'TP-Link'), ('MODEM', 'Arris SURFboard SBG6700AC', 'SN032', 'Arris'), ('ROUTER', 'Linksys Velop', 'SN033', 'Linksys'), ('MODEM', 'Netgear Nighthawk AC1900', 'SN034', 'Netgear'), ('ROUTER', 'Asus RT-AC88U', 'SN035', 'Asus'), ('MODEM', 'Motorola MG7700', 'SN036', 'Motorola'), ('ROUTER', 'Google Nest Wifi', 'SN037', 'Google'), ('MODEM', 'Zoom Telephonics AC1900', 'SN038', 'Zoom'), ('ROUTER', 'D-Link DIR-867', 'SN039', 'D-Link'), ('MODEM', 'Actiontec C3000A', 'SN040', 'Actiontec'), ('ROUTER', 'Synology RT2600ac', 'SN041', 'Synology'), ('MODEM', 'Hitron CODA-4582', 'SN042', 'Hitron'), ('ROUTER', 'Tenda AC10U', 'SN043', 'Tenda'), ('MODEM', 'Technicolor TC4400', 'SN044', 'Technicolor'), ('ROUTER', 'Buffalo AirStation', 'SN045', 'Buffalo'), ('MODEM', 'Cisco DPC3941T', 'SN046', 'Cisco'), ('ROUTER', 'TP-Link Archer C7', 'SN047', 'TP-Link'), ('MODEM', 'Zyxel C1100Z', 'SN048', 'Zyxel'), ('ROUTER', 'Apple AirPort Extreme', 'SN049', 'Apple'), ('MODEM', 'Hitron CGNM-2250', 'SN050', 'Hitron'), ('ROUTER', 'Huawei WS5200', 'SN051', 'Huawei'), ('MODEM', 'UBEE DDW36C', 'SN052', 'UBEE'), ('ROUTER', 'MikroTik RB3011UiAS-RM', 'SN053', 'MikroTik'), ('MODEM', 'Zoom 5370', 'SN054', 'Zoom'), ('ROUTER', 'Linksys EA7500', 'SN055', 'Linksys'), ('MODEM', 'Motorola MB7621', 'SN056', 'Motorola'), ('ROUTER', 'Asus RT-AX88U', 'SN057', 'Asus'), ('MODEM', 'Netgear CM500', 'SN058', 'Netgear'), ('ROUTER', 'Google Wifi', 'SN059', 'Google'), ('MODEM', 'Arris SB6190', 'SN060', 'Arris'), ('ROUTER', 'D-Link EXO AC2600', 'SN061', 'D-Link'), ('MODEM', 'TP-Link TC-7610', 'SN062', 'TP-Link'), ('ROUTER', 'Synology MR2200ac', 'SN063', 'Synology'), ('MODEM', 'Actiontec GT784WN', 'SN064', 'Actiontec'), ('ROUTER', 'Netgear Nighthawk X6', 'SN065', 'Netgear'), ('MODEM', 'Technicolor TC8717T', 'SN066', 'Technicolor'), ('ROUTER', 'Buffalo WZR-1750DHP', 'SN067', 'Buffalo'), ('MODEM', 'Cisco DPC3010', 'SN068', 'Cisco');

insert into "plans" ("name", "speed", "price") values ('Starter Plan', 100, 99.99), ('Deluxe Plan', 300, 199.99), ('Platinum Plan', 500, 299.99);

insert into "technicians" ("name", "phone", "email") values ('Marcos Oliveira', '21987654321', 'marcos@wnet.com'), ('João Silva', '21987654322', 'joao@wnet.com'), ('Ana Souza', '21987654323', 'ana@wnet.com'), ('Pedro Santos', '21987654324', 'pedro@wnet.com');