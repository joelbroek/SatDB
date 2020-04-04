create table space_agency
(
    id int not null,
    name varchar(30) not null,
    primary key (id)
);

grant select on space_agency to public;

create table constellation
(
    name varchar(20),
    purpose varchar(20),
    primary key (name)
);

create table orbit
(
    id int not null,
    type varchar(20) not null,
    longitude float,
    eccentricity int,
    axis int,
    primary key (id, type)
);

create table satellite
(
    id int not null,
    name varchar(20) not null,
    orbit_id int not null,
    orbit_type varchar(20) not null,
    constellation varchar(20),
    primary key (id),
    foreign key (orbit_id, orbit_type) references orbit(id, type) ON DELETE CASCADE,
    foreign key (constellation) references constellation(name) ON DELETE SET NULL
);

create table gps_sat
(
    id int not null,
    svn int,
    primary key (id),
    foreign key (id) references satellite ON DELETE CASCADE
);

create table comms_sat
(
    id int not null,
    frequency decimal,
    primary key (id),
    foreign key (id) references satellite ON DELETE CASCADE
);

create table geostationary_sat
(
    id int not null,
    image_type varchar(20),
    primary key (id),
    foreign key (id) references satellite ON DELETE CASCADE
);

create table launch_request
(
    id varchar(20) not null,
    is_approved number(1) not null,
    launch_system varchar(20),
    sat_id int not null unique,
    agency_id int not null,
    scheduled_date varchar(20),
    primary key (id),
    foreign key (sat_id) references satellite(id) ON DELETE CASCADE,
    foreign key (agency_id) references space_agency(id) ON DELETE CASCADE
);

create table location
(
    latitude float not null,
    longitude float not null,
    location varchar(20) not null,
    primary key (latitude, longitude)
);

create table ground_station
(
    name varchar(20) not null,
    latitude float,
    longitude float,
    primary key (name),
    foreign key (latitude, longitude) references location ON DELETE CASCADE
);

create table satellite_sends_data
(
    title varchar(20),
    sat_id int,
    timestamp varchar(20),
    message varchar(140),
    primary key (title, sat_id),
    foreign key (sat_id) references satellite(id) ON DELETE CASCADE
);

create table ground_station_recieves_data
(
    title varchar(20) not null,
    sat_id int not null,
    ground_station_name varchar(20) not null,
    timestamp varchar(20) not null,
    primary key (title, sat_id, ground_station_name),
    foreign key (title, sat_id) references satellite_sends_data(title, sat_id) ON DELETE CASCADE,
    foreign key (ground_station_name) references ground_station(name) ON DELETE CASCADE
);

create table user_type
(
    user_type varchar(20) not null,
    clearance int,
    primary key (user_type)
);

create table sat_user
(
    username varchar(20) not null,
    password varchar(20),
    user_type varchar(20),
    primary key (username),
    foreign key (user_type) references user_type ON DELETE CASCADE
);

create table user_tracks_constellation
(
    username varchar(20) not null,
    constellation_name varchar(20) not null,
    is_favourite number(1) not null,
    primary key (username, constellation_name),
    foreign key (username) references sat_user ON DELETE CASCADE,
    foreign key (constellation_name) references constellation ON DELETE CASCADE
);

insert into space_agency(id, name)
values(0, 'NASA');

insert into space_agency(id, name)
values(1, 'Canadian Space Agency');

insert into space_agency(id, name)
values(2, 'Roscosmos');

insert into space_agency(id, name)
values(3, 'UK Space Agency');

insert into space_agency(id, name)
values(4, 'CNES');


insert into constellation(name, purpose)
values('Constellation1', 'Weather');

insert into constellation(name, purpose)
values('Constellation2', 'Navigation');

insert into constellation(name, purpose)
values('Constellation3', 'Large Scale Image');

insert into constellation(name, purpose)
values('Inter Constellation', 'Internet');

insert into constellation(name, purpose)
values('Constellation4', 'Internet');

insert into orbit(id, type, longitude, eccentricity, axis)
values(0, 'low', 100, 1, 12);

insert into orbit(id, type, longitude, eccentricity, axis)
values(0, 'medium', 30, 3, 56);

insert into orbit(id, type, longitude, eccentricity, axis)
values(1, 'low', -25, 5, 90);

insert into orbit(id, type, longitude, eccentricity, axis)
values(1, 'medium', -168, 2, 0);

insert into orbit(id, type, longitude, eccentricity, axis)
values(2, 'medium', 40, 3, 90);

insert into orbit(id, type, longitude, eccentricity, axis)
values(5, 'high', 5, 3, 86);

insert into orbit(id, type, longitude, eccentricity, axis)
values(3, 'medium', 28, 3, 27);

insert into orbit(id, type, longitude, eccentricity, axis)
values(4, 'medium', -100, 8, 76);

insert into orbit(id, type, longitude, eccentricity, axis)
values(6, 'high', 56, 6, 130);

insert into orbit(id, type, longitude, eccentricity, axis)
values(5, 'medium', 56, 7, 40);

insert into orbit(id, type, longitude, eccentricity, axis)
values(0, 'high', 25, 1, 51);

insert into orbit(id, type, longitude, eccentricity, axis)
values(1, 'high', 10, 3, 180);

insert into orbit(id, type, longitude, eccentricity, axis)
values(2, 'high', 54, 1, 100);

insert into orbit(id, type, longitude, eccentricity, axis)
values(3, 'high', -43, 6, 102);

insert into orbit(id, type, longitude, eccentricity, axis)
values(4, 'high', -3, 8, 78);

insert into satellite(id, name, orbit_id, orbit_type, constellation)
values(0, 'Sputnik 1', 0, 'low', null);

insert into satellite(id, name, orbit_id, orbit_type, constellation)
values(1, 'Skylab', 0, 'medium', 'Inter Constellation');

insert into satellite(id, name, orbit_id, orbit_type, constellation)
values(2, 'Evisat', 1, 'low', null);

insert into satellite(id, name, orbit_id, orbit_type, constellation)
values(3, 'Explorer 1', 1, 'medium', null);

insert into satellite(id, name, orbit_id, orbit_type, constellation)
values(4, 'Cloudsat', 2, 'medium', 'Inter Constellation');

insert into satellite(id, name, orbit_id, orbit_type, constellation)
values(5, 'Telstar', 5, 'high', null);

insert into satellite(id, name, orbit_id, orbit_type, constellation)
values(6, 'Intelsat', 3, 'medium', 'Inter Constellation');

insert into satellite(id, name, orbit_id, orbit_type, constellation)
values(7, 'SCORE', 4, 'medium', null);

insert into satellite(id, name, orbit_id, orbit_type, constellation)
values(8, 'Echo', 6, 'high', 'Constellation1');

insert into satellite(id, name, orbit_id, orbit_type, constellation)
values(9, 'Boeing 702', 5, 'medium', 'Constellation1');

insert into satellite(id, name, orbit_id, orbit_type, constellation)
values(10, 'Meteosat', 0, 'high', null);

insert into satellite(id, name, orbit_id, orbit_type, constellation)
values(11, 'GOES 13', 1, 'high', null);

insert into satellite(id, name, orbit_id, orbit_type, constellation)
values(12, 'GOES 14', 2, 'high', 'Constellation3');

insert into satellite(id, name, orbit_id, orbit_type, constellation)
values(13, 'GOES 15', 3, 'high', null);

insert into satellite(id, name, orbit_id, orbit_type, constellation)
values(14, 'GOES 16', 4, 'high', 'Constellation3');

insert into gps_sat(id, svn)
values(0, 200);

insert into gps_sat(id, svn)
values(1, 200);

insert into gps_sat(id, svn)
values(2, 200);

insert into gps_sat(id, svn)
values(3, 200);

insert into gps_sat(id, svn)
values(4, 200);

insert into comms_sat(id, frequency)
values(5, 2.4);

insert into comms_sat(id, frequency)
values(6, 5.0);

insert into comms_sat(id, frequency)
values(7, 5.0);

insert into comms_sat(id, frequency)
values(8, 2.4);

insert into comms_sat(id, frequency)
values(9, 2.4);

insert into geostationary_sat(id, image_type)
values(10, 'Visible');

insert into geostationary_sat(id, image_type)
values(11, 'Infrared');

insert into geostationary_sat(id, image_type)
values(12, 'Infrared');

insert into geostationary_sat(id, image_type)
values(13, 'Water Vapour');

insert into geostationary_sat(id, image_type)
values(14, 'Infrared');

insert into launch_request(id, is_approved, launch_system, sat_id, agency_id, scheduled_date)
values(0, 1, 'SLS', 0, 1, '2020-02-11');

insert into launch_request(id, is_approved, launch_system, sat_id, agency_id, scheduled_date)
values(1, 0, null, 1, 1, '2020-02-11');

insert into launch_request(id, is_approved, launch_system, sat_id, agency_id, scheduled_date)
values(2, 1, 'Atlas', 2, 0, '2020-02-11');

insert into launch_request(id, is_approved, launch_system, sat_id, agency_id, scheduled_date)
values(3, 1, 'Alpha', 3, 4, '2020-02-11');

insert into launch_request(id, is_approved, launch_system, sat_id, agency_id, scheduled_date)
values(4, 0, 'SLS', 4, 1, '2020-02-11');

insert into location(latitude, longitude, location)
values(49.3, 123.1, 'Vancouver');

insert into location(latitude, longitude, location)
values(49.2, 123.2, 'UBC');

insert into location(latitude, longitude, location)
values(47.6, 122.3, 'Seattle');

insert into location(latitude, longitude, location)
values(55.9, 3.2, 'Edinburgh');

insert into location(latitude, longitude, location)
values(43.7, 7.2, 'Nice');

insert into ground_station(name, latitude, longitude)
values('GroundStation 1', 49.3, 123.1);

insert into ground_station(name, latitude, longitude)
values('GroundStation 2', 47.6, 122.3);

insert into ground_station(name, latitude, longitude)
values('GroundStation 3', 55.9, 3.2);

insert into ground_station(name, latitude, longitude)
values('Earth Station', 43.7, 7.2);

insert into ground_station(name, latitude, longitude)
values('Dan`s Disk', 49.2, 123.2);

insert into satellite_sends_data(title, sat_id, timestamp, message)
values('Test1', 0, '2020-02-02 12:32:40', 'This is a test');

insert into satellite_sends_data(title, sat_id, timestamp, message)
values('Test2', 0, '2020-02-02 16:00:01', 'This is the second test');

insert into satellite_sends_data(title, sat_id, timestamp, message)
values('Weather0034', 12, '2020-02-08 03:20:34', 'weather.jpg');

insert into satellite_sends_data(title, sat_id, timestamp, message)
values('Temps00120', 13, '2020-02-10 18:03:56', 'clouds.jpg');

insert into satellite_sends_data(title, sat_id, timestamp, message)
values('Message9999', 4, '2020-02-28 11:59:59', 'This is a message');

insert into ground_station_recieves_data(title, sat_id, ground_station_name, timestamp)
values('Test1', 0, 'GroundStation 1', '2020-02-02 12:33:55');

insert into ground_station_recieves_data(title, sat_id, ground_station_name, timestamp)
values('Test1', 0, 'GroundStation 2', '2020-02-02 12:35:01');

insert into ground_station_recieves_data(title, sat_id, ground_station_name, timestamp)
values('Test1', 0, 'GroundStation 3', '2020-02-02 12:33:30');

insert into ground_station_recieves_data(title, sat_id, ground_station_name, timestamp)
values('Temps00120', 13, 'Earth Station', '2020-02-10 18:05:50');

insert into ground_station_recieves_data(title, sat_id, ground_station_name, timestamp)
values('Message9999', 4, 'Dan`s Disk', '2020-02-28 12:00:20');

insert into user_type(user_type, clearance)
values('Guest', 0);

insert into user_type(user_type, clearance)
values('Member', 1);

insert into user_type(user_type, clearance)
values('Authority', 2);

insert into user_type(user_type, clearance)
values('Admin', 3);

insert into user_type(user_type, clearance)
values('Super', 4);

insert into sat_user(username, password, user_type)
values('User1', 'password', 'Guest');

insert into sat_user(username, password, user_type)
values('User2', 'password', 'Guest');

insert into sat_user(username, password, user_type)
values('nasa', 'password1', 'Authority');

insert into sat_user(username, password, user_type)
values('spaceguy', 'secretpassword', 'Member');

insert into sat_user(username, password, user_type)
values('dan1', 'stars', 'Member');

insert into user_tracks_constellation(username, constellation_name, is_favourite)
values('User1', 'Constellation1', 0);

insert into user_tracks_constellation(username, constellation_name, is_favourite)
values('spaceguy', 'Constellation1', 0);

insert into user_tracks_constellation(username, constellation_name, is_favourite)
values('spaceguy', 'Constellation2', 0);

insert into user_tracks_constellation(username, constellation_name, is_favourite)
values('spaceguy', 'Inter Constellation', 1);

insert into user_tracks_constellation(username, constellation_name, is_favourite)
values('spaceguy', 'Constellation3', 1);

